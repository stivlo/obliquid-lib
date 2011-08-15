package org.obliquid.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Return info about the Web Client: returnIpAddress().
 * 
 * @author stivlo
 * 
 */
public final class WebClient {

        /**
         * Utility class.
         */
        private WebClient() {
                //for the moment there is no reason to allow instances of this class
        }

        /**
         * Return the client IP address. In case the address is localhost,
         * "82.96.97.90" is returned instead, to allow testing.
         * 
         * @param request
         *                the servlet request
         * @return the client IP address
         */
        public static String returnIpAddress(final HttpServletRequest request) {
                String address = request.getRemoteAddr();
                //                if (address.equals("0:0:0:0:0:0:0:1")) {
                //                        //address = "";
                //                }
                return address;
        }

}
