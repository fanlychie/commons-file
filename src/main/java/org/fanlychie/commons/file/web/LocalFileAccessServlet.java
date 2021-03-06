package org.fanlychie.commons.file.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fanlychie.commons.file.FileUtils;
import org.fanlychie.commons.file.LocalFileUploadConfig;
import org.fanlychie.commons.file.exception.LocalFileNotFoundException;

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
    private String fileKeyParameter = "file";

    /**
     * 日志
     */
    private Log log = LogFactory.getLog(LocalFileAccessServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        String fileKeyParameterStr = config.getInitParameter("fileKeyParameter");
        if (fileKeyParameterStr != null) {
            fileKeyParameter = fileKeyParameterStr;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileKey = request.getParameter(fileKeyParameter);
        if (fileKey == null || fileKey.length() < LocalFileUploadConfig.getChildFolderLength()) {
            log.warn("访问本地文件的 " + fileKeyParameter + " 参数值不合法: " + fileKey);
        } else {
            try {
                FileUtils.accessLocalFile(response, fileKey);
            } catch (LocalFileNotFoundException e) {
                if (log.isDebugEnabled()) {
                    log.debug("找不到 Key 表示的文件: " + fileKey);
                }
            } catch (Throwable e) {
                log.error("访问本地文件出错, 参数 " + fileKeyParameter + ": " + fileKey, e);
            }
        }
    }

}