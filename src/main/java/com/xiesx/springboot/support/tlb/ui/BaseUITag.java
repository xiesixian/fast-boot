package com.xiesx.springboot.support.tlb.ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.xiesx.springboot.support.tlb.TagUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseUITag extends BodyTagSupport {

    private static final long serialVersionUID = 8425802569545340622L;

    /**
     * 模板文件路径
     */
    private String src;

    /**
     * 字符编码
     */
    private String charsetEncoding;

    /**
     * 清理占位符
     */
    private boolean cleanup = true;

    private StringBuilder __tmplBodyPart;

    private StringBuilder __tmplScriptPart;

    private StringBuilder __tmplMetaPart;

    private StringBuilder __tmplCssPart;

    private Map<String, String> __tmplPropertyPart;

    @Override
    public int doStartTag() throws JspException {
        __tmplBodyPart = new StringBuilder();
        __tmplScriptPart = new StringBuilder();
        __tmplMetaPart = new StringBuilder();
        __tmplCssPart = new StringBuilder();
        __tmplPropertyPart = new HashMap<String, String>();
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        this.src = null;
        this.charsetEncoding = null;
        this.cleanup = true;
        return super.doEndTag();
    }

    @Override
    public void release() {
        __tmplBodyPart = null;
        __tmplScriptPart = null;
        __tmplMetaPart = null;
        __tmplCssPart = null;
        if (__tmplPropertyPart != null) {
            __tmplPropertyPart.clear();
            __tmplPropertyPart = null;
        }
        super.release();
    }

    public String mergeContent(String tmplContent) throws JspException {
        String _tmplContent = tmplContent;
        if (StringUtils.isNotEmpty(_tmplContent)) {
            if (__tmplMetaPart.length() > 0) {
                _tmplContent =
                        TagUtils.replaceRegText(_tmplContent, "meta", __tmplMetaPart.toString());
            }
            if (__tmplCssPart.length() > 0) {
                _tmplContent =
                        TagUtils.replaceRegText(_tmplContent, "css", __tmplCssPart.toString());
            }
            if (__tmplScriptPart.length() > 0) {
                _tmplContent = TagUtils.replaceRegText(_tmplContent, "script",
                        __tmplScriptPart.toString());
            }
            if (__tmplBodyPart.length() > 0) {
                _tmplContent =
                        TagUtils.replaceRegText(_tmplContent, "body", __tmplBodyPart.toString());
            }
            for (Map.Entry<String, String> _entry : __tmplPropertyPart.entrySet()) {
                _tmplContent =
                        TagUtils.replaceRegText(_tmplContent, _entry.getKey(), _entry.getValue());
            }
        }
        return _tmplContent;
    }

    public String buildSrcUrl() {
        if (StringUtils.isNotEmpty(this.getSrc())) {
            StringBuilder _url = new StringBuilder();
            if (!this.getSrc().startsWith("/")) {
                _url.append("/WEB-INF/jsp/");
            }
            _url.append(this.getSrc());
            if (!this.getSrc().endsWith(".jsp")) {
                _url.append(".jsp");
            }
            return _url.toString();
        }
        return this.getSrc();
    }

    public void writerToBodyPart(String content) {
        __tmplBodyPart.append(Matcher.quoteReplacement(content));
    }

    public String getMetaPartContent() {
        return __tmplMetaPart.toString();
    }

    public void writerToMetaPart(String content) {
        __tmplMetaPart.append(Matcher.quoteReplacement(content));
    }

    public String getCssPartContent() {
        return __tmplCssPart.toString();
    }

    public void writerToCssPart(String content) {
        __tmplCssPart.append(Matcher.quoteReplacement(content));
    }

    public String getScriptPartContent() {
        return __tmplScriptPart.toString();
    }

    public void writerToScriptPart(String content) {
        __tmplScriptPart.append(Matcher.quoteReplacement(content));
    }

    public void putProperty(String key, String value) {
        __tmplPropertyPart.put(key, Matcher.quoteReplacement(value));
    }

    protected Map<String, String> getPropertyPart() {
        return Collections.unmodifiableMap(this.__tmplPropertyPart);
    }
}
