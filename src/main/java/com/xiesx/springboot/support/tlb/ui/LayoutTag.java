package com.xiesx.springboot.support.tlb.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.xiesx.springboot.support.tlb.TagUtils;
import com.xiesx.springboot.utils.ymp.RuntimeUtils;

public class LayoutTag extends BaseUITag {

    private static final long serialVersionUID = 7959201563636313024L;

    private BaseUITag __ui;

    /**
     * Layout模板文件内容
     */
    private String __tmplContent;

    /**
     * 自定义占位符名称, 若未提供则默认为body
     */
    private String name;

    @Override
    public int doStartTag() throws JspException {
        __ui = (BaseUITag) this.getParent();
        if (ObjectUtils.isEmpty(__ui)) {
            throw new JspException("Parent UITag or LayoutTag not found.");
        }
        try {
            if (StringUtils.isNotEmpty(this.getSrc())) {
                __tmplContent =
                        TagUtils.includeJSP((HttpServletRequest) this.pageContext.getRequest(),
                                (HttpServletResponse) this.pageContext.getResponse(),
                                this.buildSrcUrl(), __ui.getCharsetEncoding());
            } else {
                __tmplContent = "";
            }
        } catch (Exception e) {
            throw new JspException(RuntimeUtils.unwrapThrow(e));
        }
        return super.doStartTag();
    }

    @Override
    public int doAfterBody() throws JspException {
        try {
            if (this.bodyContent != null) {
                String _layoutBody = StringUtils.defaultIfEmpty(this.bodyContent.getString(), "");
                if (StringUtils.isNotEmpty(__tmplContent)) {
                    this.writerToBodyPart(_layoutBody);
                } else {
                    __tmplContent = _layoutBody;
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
        try {
            __ui.writerToMetaPart(this.getMetaPartContent());
            __ui.writerToCssPart(this.getCssPartContent());
            __ui.writerToScriptPart(this.getScriptPartContent());
            __tmplContent = this.mergeContent(StringUtils.defaultIfEmpty(__tmplContent, ""));
            if (StringUtils.isNotEmpty(name) && !"body".equalsIgnoreCase(name)) {
                __ui.putProperty(name,
                        !isCleanup() ? __tmplContent : TagUtils.replaceRegClear(__tmplContent));
            } else {
                __ui.writerToBodyPart(
                        !isCleanup() ? __tmplContent : TagUtils.replaceRegClear(__tmplContent));
            }
        } catch (Exception e) {
            throw new JspException(RuntimeUtils.unwrapThrow(e));
        }
        this.__ui = null;
        this.__tmplContent = null;
        this.name = null;
        return super.doEndTag();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
