package org.obliquid.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.obliquid.helpers.StringHelper;

/**
 * Truncate text after maxLength or 80 chars, adding "&hellip;" if the text was
 * longer (this actually means that the resulting String is longer than
 * maxLength. Implementation inspired from stackoverflow.com #2502282
 */
public class Ellipsis extends SimpleTagSupport {

        /**
         * How many characters are allowed (maximum). Default value.
         */
        private static final int MAX_LENGTH = 80;

        /** How many characters are allowed before truncating. */
        private int maxLength = MAX_LENGTH;

        @Override
        public final void doTag() {
                final JspWriter out = getJspContext().getOut();
                final JspFragment body = getJspBody();
                final StringWriter stringWriter = new StringWriter();
                try {
                        body.invoke(stringWriter);
                        out.print(StringHelper.ellipsis(stringWriter.getBuffer().toString(), getMaxLength()));
                } catch (JspException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        /**
         * Set the maxLength. This method is called automatically
         * 
         * @param maxLengthIn
         *                how many character to keep before truncating
         */
        public final void setMaxLength(final int maxLengthIn) {
                maxLength = maxLengthIn;
        }

        /**
         * Return the maxLenth.
         * 
         * @return maxLength
         */
        public final int getMaxLength() {
                return maxLength;
        }

}
