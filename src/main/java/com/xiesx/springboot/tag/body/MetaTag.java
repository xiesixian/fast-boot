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
public class MetaTag extends BodyTagSupport {

    private static final long serialVersionUID = 7014634016889539200L;

    private BaseUITag __ui;

    private String attrKey;

    private String attrValue;

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
                    this.setAttrValue(_propValue);
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
        StringBuilder _metaTmpl = new StringBuilder("<meta");
        boolean _isEmpty = true;
        if (StringUtils.isNotEmpty(this.getAttrKey())) {
            _metaTmpl.append(" ").append(this.getAttrKey());
            _isEmpty = false;
        }
        if (StringUtils.isNotEmpty(this.getAttrValue())) {
            _metaTmpl.append(" ").append(this.getAttrValue());
            _isEmpty = false;
        }
        _metaTmpl.append(">\n");
        if (!_isEmpty) {
            __ui.writerToMetaPart(_metaTmpl.toString());
        }
        //
        this.__ui = null;
        this.attrKey = null;
        this.attrValue = null;
        return super.doEndTag();
    }
}
