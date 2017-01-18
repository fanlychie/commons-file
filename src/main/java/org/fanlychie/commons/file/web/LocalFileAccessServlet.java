package org.fanlychie.commons.file.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fanlychie.commons.file.FileUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 本地文件访问 Servlet
 * Created by fanlychie on 2017/1/18.
 */
public class LocalFileAccessServlet extends HttpServlet {

    /**
     * 文件 Key 参数的名称
     */
    private String fileKeyParameterName = "file";

    /**
     * 日志
     */
    private Log log = LogFactory.getLog(LocalFileAccessServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        String fileKeyParameterNameStr = config.getInitParameter("fileKeyParameterName");
        if (fileKeyParameterNameStr != null) {
            fileKeyParameterName = fileKeyParameterNameStr;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileKey = request.getParameter(fileKeyParameterName);
        if (fileKey == null || fileKey.length() != 32) {
            log.warn("访问本地文件的 " + fileKeyParameterName + " 参数不合法: " + fileKey);
        } else {
            try {
                FileUtils.accessLocalFile(response, fileKey);
            } catch (Throwable e) {
                log.error("访问本地文件出错, " + fileKeyParameterName + ": " + fileKey, e);
            }
        }
    }

}