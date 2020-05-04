package com.xiesx.springboot.tld.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.xiesx.springboot.tld.ui.BaseUITag;
import com.xiesx.springboot.utils.RuntimeUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ScriptTag extends BodyTagSupport {

	private static final long serialVersionUID = -2774067114541501535L;

	private BaseUITag __ui;

	private String src;

	private String value;

	private String type;

	private String wrapper;

	@Override
	public int doStartTag() throws JspException {
		__ui = (BaseUITag) this.getParent();
		if (__ui == null) {
			throw new JspException("Parent UITag or LayoutTag not found.");
		}
		return super.doStartTag();
	}

	@Override
	public int doAfterBody() throws JspException {
		try {
			if (this.bodyContent != null) {
				String _propValue = this.bodyContent.getString();
				if (StringUtils.isNotBlank(_propValue)) {
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
		StringBuilder _scriptTmpl = new StringBuilder("<script");
		if (StringUtils.isNotBlank(this.getId())) {
			_scriptTmpl.append(" id=\"").append(this.getId()).append("\"");
		}
		boolean _isEmpty = true;
		if (StringUtils.isNotBlank(this.getSrc())) {
			_scriptTmpl.append(" src=\"").append(this.getSrc()).append("\"");
			_isEmpty = false;
		}
		_scriptTmpl.append(" type=\"").append(StringUtils.defaultIfBlank(this.getType(), "text/javascript")).append("\">");
		if (_isEmpty && StringUtils.isNotEmpty(this.getValue())) {
			String _wrapper = StringUtils.defaultIfBlank(this.getWrapper(), "script");
			String _content = StringUtils.substringBetween(this.getValue(), "<" + _wrapper + ">", "</" + _wrapper + ">");
			if (StringUtils.isNotBlank(_content)) {
				this.setValue(_content);
			}
			_scriptTmpl.append(this.getValue()).append("\n");
			_isEmpty = false;
		}
		_scriptTmpl.append("</script>\n");
		if (!_isEmpty) {
			__ui.writerToScriptPart(_scriptTmpl.toString());
		}
		//
		this.__ui = null;
		this.src = null;
		this.value = null;
		this.id = null;
		this.type = null;
		this.wrapper = null;
		return super.doEndTag();
	}
 
}
