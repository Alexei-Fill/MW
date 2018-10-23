package com.epam.movie_warehouse.taglib;

import org.apache.taglibs.standard.tag.common.core.Util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.EMPTY_STRING;

public class LocalDateTag extends TagSupport {
    private static final String DEFAULT_FORMATTER = "d.MM.yyyy";
    private LocalDate value;
    private String pattern;
    private String var;
    private int scope;

    public LocalDateTag() {
        super();
        init();
    }

    private void init() {
        this.pattern = this.var = null;
        this.value = null;
        this.scope = PageContext.PAGE_SCOPE;
    }

    public void setVar(final String var) {
        this.var = var;
    }

    public void setScope(final String scope) {
        this.scope = Util.getScope(scope);
    }

    public void setValue(final LocalDate value) {
        this.value = value;
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    @Override
    public int doEndTag() throws JspException {
        String formatted;
        if (this.value == null) {
            if (this.var != null) {
                this.pageContext.removeAttribute(this.var, this.scope);
            }
            return EVAL_PAGE;
        }

        if (this.pattern != null && !EMPTY_STRING.equals(this.pattern)) {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.pattern);
            formatted = formatter.format(this.value);
        } else {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMATTER);
            formatted = formatter.format(this.value);
        }
        if (this.var != null) {
            this.pageContext.setAttribute(this.var, formatted, this.scope);
        } else {
            try {
                this.pageContext.getOut().print(formatted);
            } catch (final IOException ioe) {
                throw new JspTagException(ioe.toString(), ioe);
            }
        }
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        init();
    }

}

