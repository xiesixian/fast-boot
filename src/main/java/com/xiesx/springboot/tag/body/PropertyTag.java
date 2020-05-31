package com.xiesx.springboot.tag.body;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.xiesx.springboot.tag.ui.BaseUITag;
import com.xiesx.springboot.utils.RuntimeUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PropertyTag extends BodyTagSupport {

    private static final long serialVersionUID = -3493408190832765819L;

    private BaseUITag __ui;

    private String name;

    private String value;

    @Override
    public int doStartTag() throws JspException {
        __ui = (BaseUITag) this.getParent();
        if (ObjectUtils.isEmpty(__ui)) {
            throw new JspException("Parent UITag or LayoutTag not found.");
        }
        return super.doStartTag();
    }

    @Override
    public int doAfterBody() throws JspException {
        try {
            if (this.bodyContent != null) {
                String _propValue = this.bodyContent.getString();
                if (StringUtils.isNotEmpty(_propValue)) {
                    this.setValue(_propValue);
                }
                this.bodyContent.clearBody();
            }
        } catch (Exception e) {
            throw new JspException(RuntimeUtils.unwrapThrow(e));
        }
        return super.doAfterBody();
    }

    @Override
    public int doEndTag() throws JspException {
        if (StringUtils.isNotEmpty(this.getName()) && StringUtils.isNotEmpty(this.getValue())) {
            __ui.putProperty(this.getName(), this.getValue());
        }
        //
        this.__ui = null;
        this.name = null;
        this.value = null;
        return super.doEndTag();
    }
}
