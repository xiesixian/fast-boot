package com.xiesx.springboot.support.tlb;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang3.StringUtils;

public class TagUtils {

    /**
     * @param source 源字符串
     * @param key 键
     * @param value 值
     * @return 占位符替换
     */
    public static String replaceRegText(String source, String key, String value) {
        String _regex = "@\\{" + key + "}";
        return source.replaceAll(_regex, value);
    }

    public static String replaceRegClear(String source) {
        return replaceRegText(source, "(.+?)", "");
    }

    /**
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @param jspFile JSP文件路径
     * @param charsetEncoding 字符编码
     * @return 执行JSP并返回HTML源码
     * @throws ServletException 可能产生的异常
     * @throws IOException 可能产生的异常
     */
    public static String includeJSP(HttpServletRequest request, HttpServletResponse response, String jspFile,
            String charsetEncoding) throws ServletException, IOException {
        final OutputStream _output = new ByteArrayOutputStream();
        final PrintWriter _writer = new PrintWriter(new OutputStreamWriter(_output,
                StringUtils.defaultIfEmpty(charsetEncoding, response.getCharacterEncoding())));
        final ServletOutputStream _servletOutput = new ServletOutputStream() {

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener arg0) {}

            @Override
            public void write(int b) throws IOException {
                _output.write(b);
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                _output.write(b, off, len);
            }
        };
        HttpServletResponse _response = new HttpServletResponseWrapper(response) {

            @Override
            public ServletOutputStream getOutputStream() {
                return _servletOutput;
            }

            @Override
            public PrintWriter getWriter() {
                return _writer;
            }
        };
        request.getRequestDispatcher(jspFile).include(request, _response);
        _writer.flush();
        return _output.toString();
    }
}
