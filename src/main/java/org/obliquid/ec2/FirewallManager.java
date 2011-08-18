package org.obliquid.ec2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.obliquid.util.ClientFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.RevokeSecurityGroupIngressRequest;

/**
 * Authorise/Revoke authorisation to IP addresses or CIDR to connect to our
 * servers in Amazon cloud.
 * 
 * @author stivlo
 */
public class FirewallManager {

        /** An instance of the AmazonEC2 client. */
        private static AmazonEC2 ec2 = null;

        /** SSH port. */
        private static final int SSH_PORT = 22;

        /** Remote desktop port. */
        private static final int RDESKTOP_PORT = 3389;

        /** Zimbra port. */
        private static final int ZIMBRA_PORT = 7071;

        /** Logger instance. */
        private static final Logger LOG = Logger.getLogger(FirewallManager.class);

        /**
         * Default constructor.
         */
        public FirewallManager() {
                //empty constructor
        }

        /**
         * Initialisation.
         */
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
         *                the security group to check
         * @param portsToCheck
         *                list of ports to check, if null show all
         * @return a list of IpPermission
         */
        public final List<IpPermission> describeIpPermissions(final String group, final int[] portsToCheck) {
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

        /**
         * Build a list of permissions for all ports of a security group.
         * 
         * @param group
         *                security group to check
         * @return a list of permissions
         */
        public final List<IpPermission> describeIpPermissionsForAllPorts(final String group) {
                return describeIpPermissions(group, null);
        }

        /**
         * Return the IP permissions for the security group specified. Will
         * check only ports 22, 3389, 7071
         * 
         * @param group
         *                the security group
         * @return a list of IpPermission
         */
        public final List<IpPermission> describeIpPermissions(final String group) {
                final int[] portsToCheck = { SSH_PORT, RDESKTOP_PORT, ZIMBRA_PORT };
                return describeIpPermissions(group, portsToCheck);
        }

        /**
         * Revoke all authorisation in groups web and mail for ports
         * portsToCheck.
         */
        public final void revokeAll() {
                if (ec2 == null) {
                        init();
                }
                revokeAll("web");
                revokeAll("mail");
        }

        /**
         * Revoke all IP permissions for a security group (only for ports
         * portsToCheck).
         * 
         * @param securityGroup
         *                the security group
         */
        private void revokeAll(final String securityGroup) {
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
         * Revoke a specific IP permission.
         * 
         * @param securityGroup
         *                the security group
         * @param fromPort
         *                the starting port
         * @param toPort
         *                the ending point
         * @param cidrIp
         *                an IP address or CIDR
         */
        public final void revoke(final String securityGroup, final int fromPort, final int toPort,
                        final String cidrIp) {
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
         * Authorise an IP or CIDR to access the servers. Group web: port 22 and
         * 3389 Group mail: port 22 and 7071
         * 
         * @param ipAddress
         *                an IP address or CIDR as a string
         */
        public final void authorize(final String ipAddress) {
                if (ec2 == null) {
                        init();
                }
                authorize(ipAddress, "mail", SSH_PORT);
                authorize(ipAddress, "mail", ZIMBRA_PORT);
                authorize(ipAddress, "web", SSH_PORT);
                authorize(ipAddress, "web", RDESKTOP_PORT);
        }

        /**
         * Authorise an IP or CIDR to access a particular port.
         * 
         * @param ipAddress
         *                an IP address or CIDR as a string
         * @param group
         *                the security group
         * @param port
         *                the port to be added
         */
        private void authorize(final String ipAddress, final String group, final int port) {
                if (ec2 == null) {
                        init();
                }
                try {
                        AuthorizeSecurityGroupIngressRequest authReq;
                        String cidr = buildCidr(ipAddress);
                        authReq = new AuthorizeSecurityGroupIngressRequest();
                        authReq.setIpProtocol("tcp");
                        authReq.setGroupName(group);
                        authReq.setFromPort(port);
                        authReq.setToPort(port);
                        authReq.setCidrIp(cidr);
                        ec2.authorizeSecurityGroupIngress(authReq);
                } catch (AmazonServiceException ex) {
                        //ignore duplicate permissions
                        LOG.debug(ex.toString());
                }
        }

        /**
         * Build a CIDR (Class-less InterDomain Routing) address from a single
         * IP adding /32 or return the input parameter in case it contains
         * already a slash.
         * 
         * @param ipAddressOrCidr
         *                an IP address or an IP address slash a number of bits
         *                (CIDR notation)
         * @return a CIDR address range
         */
        private String buildCidr(final String ipAddressOrCidr) {
                String cidr;
                if (ipAddressOrCidr.contains("/")) {
                        cidr = ipAddressOrCidr;
                } else {
                        cidr = ipAddressOrCidr + "/32";
                }
                return cidr;
        }

}
