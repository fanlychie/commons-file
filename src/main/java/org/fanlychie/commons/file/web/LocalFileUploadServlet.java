package org.fanlychie.commons.file.web;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fanlychie.commons.file.FileUploadReport;
import org.fanlychie.commons.file.FileUtils;
import org.fanlychie.commons.file.exception.RuntimeCastException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 本地文件上传 Servlet
 * Created by fanlychie on 2017/1/18.
 */
public class LocalFileUploadServlet extends HttpServlet {

    /**
     * 上传的文件最小大小, 默认0, 表示不限制
     */
    private long minSize;

    /**
     * 上传的文件最大大小, 默认0, 表示不限制
     */
    private long maxSize;

    /**
     * 允许上传的文件类型
     */
    private String[] allowedFileExtensions;

    /**
     * 日志
     */
    private Log log = LogFactory.getLog(LocalFileUploadServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        String minSizeStr = config.getInitParameter("minSize");
        if (minSizeStr != null) {
            try {
                this.minSize = Long.parseLong(minSizeStr);
            } catch (Throwable e) {
                throw new IllegalArgumentException("minSize 不是一个有效的整数: " + minSizeStr);
            }
        }
        String maxSizeStr = config.getInitParameter("maxSize");
        if (maxSizeStr != null) {
            try {
                this.maxSize = Long.parseLong(maxSizeStr);
            } catch (Throwable e) {
                throw new IllegalArgumentException("maxSize 不是一个有效的整数: " + minSizeStr);
            }
        }
        String allowedFileExtensionsStr = config.getInitParameter("allowedFileExtensions");
        if (allowedFileExtensionsStr != null) {
            this.allowedFileExtensions = allowedFileExtensionsStr.split(",");
            for (int i = 0; i < allowedFileExtensions.length; i++) {
                allowedFileExtensions[i] = allowedFileExtensions[i].trim();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            FileUploadReport report = FileUtils.uploadFile(request)
                    .setAllowedFileSize(minSize, maxSize)
                    .setAllowedFileExtensions(allowedFileExtensions)
                    .upload();
            String reportJsonStr = JSON.toJSONString(report);
            if (log.isDebugEnabled()) {
                log.debug("本地文件上传: " + reportJsonStr);
            }
            writeOut(response, reportJsonStr);
        } catch (Throwable e) {
            log.error("本地文件上传失败", e);
            FileUploadReport report = new FileUploadReport();
            report.getFailedMsgs().add("本地文件上传失败");
            writeOut(response, JSON.toJSONString(report));
        }
    }

    private void writeOut(HttpServletResponse response, String content) {
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().write(content);
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

}