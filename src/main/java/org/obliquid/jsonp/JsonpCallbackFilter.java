package org.obliquid.jsonp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Adds a JSONP wrapper.
 * 
 * @author stivlo
 * 
 */
public class JsonpCallbackFilter implements Filter {

        /** Logger instance for this class. */
        private static Logger log = Logger.getLogger(JsonpCallbackFilter.class);

        /**
         * I don't need to do any initialisation.
         * 
         * @param filterConfig
         *                filter configuration
         * @throws ServletException
         *                 in case of problems
         */
        @Override
        public void init(final FilterConfig filterConfig) throws ServletException {
                //I don't need it
        }

        /**
         * I don't need to do any cleanup.
         */
        @Override
        public void destroy() {
                //I don't need it
        }

        @Override
        public final void doFilter(final ServletRequest request, final ServletResponse response,
                        final FilterChain chain) throws IOException, ServletException {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                Map<String, String[]> parms = httpRequest.getParameterMap();
                if (parms.containsKey("callback")) {
                        if (log.isDebugEnabled()) {
                                log.debug("Wrapping response with JSONP callback '"
                                                + parms.get("callback")[0] + "'");
                        }
                        OutputStream out = httpResponse.getOutputStream();
                        GenericResponseWrapper wrapper = new GenericResponseWrapper(httpResponse);
                        chain.doFilter(request, wrapper);
                        out.write(new String(parms.get("callback")[0] + "(").getBytes());
                        out.write(wrapper.getData());
                        out.write(new String(");").getBytes());
                        //it's not JSON. After the call to doFilter or it would be overwritten
                        wrapper.setContentType("text/javascript;charset=UTF-8");
                        out.close();
                } else {
                        //pass the request and response on to the filter chain
                        chain.doFilter(request, response);
                }
        }

}
