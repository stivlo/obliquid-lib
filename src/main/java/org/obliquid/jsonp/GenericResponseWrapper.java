package org.obliquid.jsonp;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Response wrapper for Http Servlets.
 * 
 * @author stivlo
 * 
 */
public class GenericResponseWrapper extends HttpServletResponseWrapper {

        /** the output. */
        private ByteArrayOutputStream output;

        /** content length. */
        private int contentLength;

        /** content type (mime type). */
        private String contentType;

        /**
         * Creates a new GenericResponseWrapper from an HttpServletResponse.
         * 
         * @param response
         *                the response to wrap.
         */
        public GenericResponseWrapper(final HttpServletResponse response) {
                super(response);
                output = new ByteArrayOutputStream();
        }

        /**
         * Return the whole output as a byte array.
         * 
         * @return the output as a byte array
         */
        public final byte[] getData() {
                return output.toByteArray();
        }

        @Override
        public final ServletOutputStream getOutputStream() {
                return new FilterServletOutputStream(output);
        }

        @Override
        public final PrintWriter getWriter() {
                return new PrintWriter(getOutputStream(), true);
        }

        @Override
        public final void setContentLength(final int length) {
                this.contentLength = length;
                super.setContentLength(length);
        }

        /**
         * Return the content length.
         * 
         * @return content length in bytes.
         */
        public final int getContentLength() {
                return contentLength;
        }

        @Override
        public final void setContentType(final String type) {
                this.contentType = type;
                super.setContentType(type);
        }

        @Override
        public final String getContentType() {
                return contentType;
        }

}
