package org.fanlychie.commons.file.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fanlychie.commons.file.FileUtils;
import org.fanlychie.commons.file.exception.LocalFileNotFoundException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 本地文件下载 Servlet
 * Created by 范忠云 on 2017/1/20.
 */
public class LocalFileDownloadServlet extends HttpServlet {

    /**
     * 文件 Key 参数的名称
     */
    private String fileKeyParameter = "file";

    /**
     * 文件名参数名称, 供下载时使用
     */
    private String fileNameParameter = "name";

    /**
     * 日志
     */
    private Log log = LogFactory.getLog(LocalFileDownloadServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        String fileKeyParameterStr = config.getInitParameter("fileKeyParameter");
        if (fileKeyParameterStr != null) {
            fileKeyParameter = fileKeyParameterStr;
        }
        String fileNameParameterStr = config.getInitParameter("fileNameParameter");
        if (fileNameParameterStr != null) {
            fileNameParameter = fileNameParameterStr;
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileKey = request.getParameter(fileKeyParameter);
        if (fileKey == null || fileKey.length() != 32) {
            throw new IllegalArgumentException("下载本地文件的 " + fileKeyParameter + " 参数值不合法: " + fileKey);
        } else {
            File file = FileUtils.getLocalFile(fileKey);
            if (file == null) {
                throw new LocalFileNotFoundException("找不到 Key 表示的文件: " + fileKey);
            }
            String fileName = request.getParameter(fileNameParameter);
            if (fileName == null) {
                fileName = file.getName();
            }
            if (log.isDebugEnabled()) {
                log.debug("下载本地文件, Key: " + fileKey);
            }
            FileUtils.provideFileDownload(response, file, fileName);
        }
    }

}