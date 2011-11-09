package org.obliquid.jsonp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.regex.Pattern;

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
         * The regular expression to ensure that the callback is safe for
         * display to a browser.
         */
        public static final Pattern SAFE_PATTERN = Pattern.compile("[a-zA-Z0-9_]+");

        /**
         * Default callback name to be used when the provided one contains
         * invalid characters.
         */
        public static final String DEFAULT_CALLBACK = "callbackContainsInvalidChars";

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

        /**
         * If the request has a callback parameter, wrap the output with the
         * function name contained in callback parameter.
         * 
         * @param request
         *                Servlet request
         * @param response
         *                Servlet response
         * @param chain
         *                a view into the invocation chain of a filtered request
         *                for a resource
         * @throws IOException
         *                 IO problems
         * @throws ServletException
         *                 other Servlet problems
         */
        @Override
        public final void doFilter(final ServletRequest request, final ServletResponse response,
                        final FilterChain chain) throws IOException, ServletException {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                Map<String, String[]> parms = httpRequest.getParameterMap();
                if (parms.containsKey("callback")) {
                        String callback = sanitize(parms.get("callback")[0]);
                        if (log.isDebugEnabled()) {
                                log.debug("Wrapping response with JSONP callback '" + callback + "'");
                        }
                        addCallback(request, httpResponse, chain, callback);
                } else {
                        //pass the request and response on to the filter chain
                        chain.doFilter(request, response);
                }
        }

        /**
         * Sanitise the callback function name, if it contains invalid
         * characters, the default name will be returned.
         * 
         * @param callback
         *                proposed function name
         * @return the same name used as parameter or the default name
         */
        private String sanitize(final String callback) {
                if (!SAFE_PATTERN.matcher(callback).matches()) {
                        return DEFAULT_CALLBACK;
                }
                return callback;
        }

        /**
         * Wrap the output with the callback.
         * 
         * @param request
         *                HTTP request
         * @param response
         *                HTTP response
         * @param chain
         *                a view into the invocation chain of a filtered request
         *                for a resource
         * @param callback
         *                callback function name, already sanitized
         * @throws IOException
         *                 IO problems
         * @throws ServletException
         *                 other Servlet problems
         */
        private void addCallback(final ServletRequest request, final HttpServletResponse response,
                        final FilterChain chain, final String callback) throws IOException, ServletException {
                OutputStream out = response.getOutputStream();
                GenericResponseWrapper wrapper = new GenericResponseWrapper(response);
                chain.doFilter(request, wrapper);
                out.write((callback + "(").getBytes());
                out.write(wrapper.getData());
                out.write(");".getBytes());
                //it's not JSON. It's after the call to doFilter or it would be overwritten
                wrapper.setContentType("text/javascript;charset=UTF-8");
                out.close();
        }

}
