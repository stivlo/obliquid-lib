package org.obliquid.datatype;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Hold and validate an IP address
 * 
 * @author stivlo
 * 
 */
public class IpAddress extends DataType {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean isValid(String ipAddressString) {
        try {
            InetAddress.getByName(ipAddressString);
        } catch (UnknownHostException e) {
            return false;
        }
        return true;
    }

    //public static String shortForm(String ipAddressString) {
    //    try {
    //        InetAddress address = InetAddress.getByName(ipAddressString);
    //        return address.getHostAddress();
    //    } catch (UnknownHostException e) {
    //        return "";
    //    }
    //}

}
