package org.obliquid.jsonp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/**
 * FilterOutputStream for Servlets.
 * 
 * @author stivlo
 * 
 */
public class FilterServletOutputStream extends ServletOutputStream {

        /**
         * Output Stream to write to.
         */
        private DataOutputStream stream;

        /**
         * Create a new FilterServletOutputStream.
         * 
         * @param output
         *                the output stream to write to
         */
        public FilterServletOutputStream(final OutputStream output) {
                stream = new DataOutputStream(output);
        }

        @Override
        public final void write(final int b) throws IOException {
                stream.write(b);
        }

        @Override
        public final void write(final byte[] b) throws IOException {
                stream.write(b);
        }

        @Override
        public final void write(final byte[] b, final int off, final int len) throws IOException {
                stream.write(b, off, len);
        }

}
