package org.obliquid.ec2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import org.apache.commons.lang.ArrayUtils;
import org.obliquid.client.ClientFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.RevokeSecurityGroupIngressRequest;

/**
 * Authorize/Revoke authorization to IP addresses or CIDR to connect to our servers in Amazon cloud
 * 
 * @author stivlo
 */
public class FirewallManager {

    private static AmazonEC2 ec2 = null;

    public FirewallManager() {
        //empty constructor
    }

    private static void init() {
        ec2 = ClientFactory.createElasticComputeCloudClient();
        if (ec2 == null) {
            throw new RejectedExecutionException("Could not connect to Amazon AWS");
        }
    }

    /**
     * Return the IP permissions for the security group specified.
     * 
     * @param group
     * @param portsToCheck
     *            list of ports to check, if null show all
     * @return a list of IpPermission
     */
    public List<IpPermission> describeIpPermissions(String group, int[] portsToCheck) {
        if (ec2 == null) {
            init();
        }
        DescribeSecurityGroupsRequest secReq = new DescribeSecurityGroupsRequest();
        secReq.setGroupNames(Arrays.asList(new String[] { group }));
        DescribeSecurityGroupsResult secDesc = ec2.describeSecurityGroups(secReq);
        List<IpPermission> ipPermissions = secDesc.getSecurityGroups().get(0).getIpPermissions();
        IpPermission aPerm;
        int port;
        for (int i = 0; i < ipPermissions.size(); i++) {
            aPerm = ipPermissions.get(i);
            port = aPerm.getFromPort();
            if (portsToCheck != null && !ArrayUtils.contains(portsToCheck, port)) {
                ipPermissions.remove(aPerm);
                i--;
            }
        }
        return ipPermissions;
    }

    public List<IpPermission> describeIpPermissionsForAllPorts(String group) {
        return describeIpPermissions(group, null);
    }

    /**
     * Return the IP permissions for the security group specified. Will check only ports 22, 3389,
     * 7071
     * 
     * @param group
     *            the security group
     * @return a list of IpPermission
     */
    public List<IpPermission> describeIpPermissions(String group) {
        final int[] portsToCheck = { 22, 3389, 7071 };
        return describeIpPermissions(group, portsToCheck);
    }

    /**
     * Revoke all authorization in groups web and mail for ports portsToCheck
     * 
     * @throws ServiceException
     */
    public void revokeAll() {
        if (ec2 == null) {
            init();
        }
        revokeAll("web");
        revokeAll("mail");
    }

    /**
     * Revoke all IP permissions for a security group (only for ports portsToCheck)
     * 
     * @param securityGroup
     *            the security group
     */
    private void revokeAll(String securityGroup) {
        if (ec2 == null) {
            init();
        }
        List<IpPermission> ipPermissions = describeIpPermissions(securityGroup);
        if (ipPermissions.size() == 0) {
            return; //nothing to delete!
        }
        RevokeSecurityGroupIngressRequest revokeRequest = new RevokeSecurityGroupIngressRequest();
        revokeRequest.setGroupName(securityGroup);
        revokeRequest.setIpPermissions(ipPermissions);
        ec2.revokeSecurityGroupIngress(revokeRequest);
    }

    /**
     * Revoke a specific IP permission
     * 
     * @param securityGroup
     *            the security group
     * @param fromPort
     *            the starting port
     * @param toPort
     *            the ending point
     * @param cidrIp
     *            an IP address or CIDR
     */
    public void revoke(String securityGroup, int fromPort, int toPort, String cidrIp) {
        if (ec2 == null) {
            init();
        }
        RevokeSecurityGroupIngressRequest revokeRequest = new RevokeSecurityGroupIngressRequest();
        revokeRequest.setGroupName(securityGroup);
        revokeRequest.setIpProtocol("tcp");
        revokeRequest.setFromPort(fromPort);
        revokeRequest.setToPort(toPort);
        revokeRequest.setCidrIp(cidrIp);
        ec2.revokeSecurityGroupIngress(revokeRequest);
    }

    /**
     * Authorize an IP or CIDR to access the servers. Group web: port 22 and 3389 Group mail: port
     * 22 and 7071
     * 
     * @param ipAddress
     *            an IP address or CIDR as a string
     * @throws IOException
     */
    public void authorize(String ipAddress) throws IOException {
        if (ec2 == null) {
            init();
        }
        authorize(ipAddress, "mail", 22);
        authorize(ipAddress, "mail", 7071);
        authorize(ipAddress, "web", 22);
        authorize(ipAddress, "web", 3389);
    }

    /**
     * Authorize an IP or CIDR to access a particular port.
     * 
     * @param ipAddress
     *            an IP address or CIDR as a string
     * @param group
     *            the security group
     * @param port
     *            the port to be added
     */
    private void authorize(String ipAddress, String group, int port) {
        if (ec2 == null) {
            init();
        }
        try {
            String cidr = buildCidr(ipAddress);
            AuthorizeSecurityGroupIngressRequest authReq = new AuthorizeSecurityGroupIngressRequest();
            authReq.setIpProtocol("tcp");
            authReq.setGroupName(group);
            authReq.setFromPort(port);
            authReq.setToPort(port);
            authReq.setCidrIp(cidr);
            ec2.authorizeSecurityGroupIngress(authReq);
        } catch (AmazonServiceException ex) {
            // ignore duplicate permission errors
        }
    }

    /**
     * Build a CIDR (Classless Inter Domain Routing) address from a single IP adding /32 or return
     * the input parameter in case it contains already a slash.
     * 
     * @param ipAddressOrCidr
     * @return
     */
    private String buildCidr(String ipAddressOrCidr) {
        String cidr;
        if (ipAddressOrCidr.contains("/")) {
            cidr = ipAddressOrCidr;
        } else {
            cidr = ipAddressOrCidr + "/32";
        }
        return cidr;
    }

}
