package org.obliquid.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public class FirstTag implements Tag {

    private PageContext pc = null;
    private Tag parent = null;
    private String name = null;

    @Override
    public void setPageContext(PageContext p) {
        pc = p;
    }

    @Override
    public void setParent(Tag t) {
        parent = t;
    }

    @Override
    public Tag getParent() {
        return parent;
    }

    public void setName(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    @Override
    public int doStartTag() throws JspException {
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
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        pc = null;
        parent = null;
        name = null;
    }

}