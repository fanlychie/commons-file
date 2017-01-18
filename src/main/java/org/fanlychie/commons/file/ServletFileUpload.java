package org.fanlychie.commons.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.fanlychie.commons.file.exception.RuntimeCastException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Servlet 文件上传
 * Created by fanlychie on 2017/1/13.
 */
public class ServletFileUpload extends LocalFileUpload {

    /**
     * HttpServletRequest
     */
    private HttpServletRequest request;

    /**
     * 创建一个 Servlet 文件上传
     *
     * @param request HttpServletRequest
     */
    public ServletFileUpload(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 执行文件上传
     *
     * @return 返回文件上传报告
     */
    @Override
    public FileUploadReport upload() {
        FileUploadReport report = new FileUploadReport();
        if (!org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent(request)) {
            report.addFileUploadFeedback("不支持文件上传的表单域", false);
        } else {
            org.apache.commons.fileupload.servlet.ServletFileUpload fileUpload = new org.apache.commons.fileupload.servlet.ServletFileUpload(new DiskFileItemFactory());
            fileUpload.setHeaderEncoding("UTF-8");
            List<FileItem> fileItems = null;
            try {
                fileItems = fileUpload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
                report.addFileUploadFeedback("文件上传失败, 请重新上传", false);
            }
            if (fileItems != null) {
                fileItems.stream().filter(fileItem -> !fileItem.isFormField()).forEach(fileItem -> {
                    preFileUpload(report, fileItem, fileItem.getName(), fileItem.getSize(), localFile -> {
                        try {
                            fileItem.write(localFile);
                        } catch (Exception e) {
                            throw new RuntimeCastException(e);
                        }
                    });
                });
            }
        }
        return report;
    }

}