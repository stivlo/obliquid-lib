package org.obliquid.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * An example implementation of a JSP tag.
 * 
 * @author stivlo
 * 
 */
public class FirstTag implements Tag {

        /**
         * A PageContext instance provides access to all the namespaces
         * associated with a JSP page, provides access to several page
         * attributes, as well as a layer above the implementation details.
         * Implicit objects are added to the pageContext automatically.
         */
        private PageContext pc = null;

        /** Parent tag. */
        private Tag parent = null;

        /** Person's name parameter. */
        private String name = null;

        @Override
        public final void setPageContext(final PageContext p) {
                pc = p;
        }

        @Override
        public final void setParent(final Tag t) {
                parent = t;
        }

        @Override
        public final Tag getParent() {
                return parent;
        }

        /**
         * Set the person's name.
         * 
         * @param theName
         *                the name to set
         */
        public final void setName(final String theName) {
                name = theName;
        }

        /**
         * The name parameter.
         * 
         * @return the name parameter
         */
        public final String getName() {
                return name;
        }

        @Override
        public final int doStartTag() throws JspException {
                try {
                        if (name != null) {
                                pc.getOut().write("Hello " + name + "!");
                        } else {
                                pc.getOut().write("You didn't enter your name");
                                pc.getOut().write(", what are you afraid of ?");
                        }
                } catch (IOException e) {
                        throw new JspTagException("An IOException occurred.");
                }
                return SKIP_BODY;
        }

        @Override
        public final int doEndTag() throws JspException {
                return EVAL_PAGE;
        }

        @Override
        public final void release() {
                pc = null;
                parent = null;
                name = null;
        }

}
