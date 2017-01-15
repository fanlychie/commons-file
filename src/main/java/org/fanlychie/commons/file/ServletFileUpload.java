package org.fanlychie.commons.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.fanlychie.commons.file.exception.RuntimeCastException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Servlet 文件上传
 * Created by fanlychie on 2017/1/13.
 */
public class ServletFileUpload extends LocalFileUploadSupport {

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
     * 设置允许上传的文件类型
     *
     * @param extension 文件扩展名, 默认为空, 表示不限制, eg: "jpg", "png" ...
     * @return
     */
    public ServletFileUpload setAllowedFileExtension(String... extension) {
        this.setAllowedFileExtensions(extension);
        return this;
    }

    /**
     * 设置允许上传的文件大小
     *
     * @param minSize 最小大小, 单位(B), 默认为0, 表示不限制
     * @param maxSize 最大大小, 单位(B), 默认为0, 表示不限制
     * @return
     */
    public ServletFileUpload setAllowedFileSize(long minSize, long maxSize) {
        this.setAllowedFileSizeRange(minSize, maxSize);
        return this;
    }

    /**
     * 设置文件大小支持功能
     *
     * @param fileSizeSupportedFunction 额外的文件支持功能, 当上传的文件大小超出限制时, 可以通过此参数手工处理文件并手工完成上传作业.<br>
     *                                  fileSizeSupportedFunction 参数：<br>
     *                                  InputStream -> 表示上传的文件输入流<br>
     *                                  File -> 本地存储的目标文件对象, 文件名不能改动, 否则上传后工具类找不到文件位置<br>
     *                                  Boolean -> 转换成功时需返回 true, 失败返回 false<br>
     * @return
     */
    public ServletFileUpload setFileSizeSupportedFunction(BiFunction<InputStream, File, Boolean> fileSizeSupportedFunction) {
        this.fileSizeSupportedFunction = fileSizeSupportedFunction;
        return this;
    }

    /**
     * 设置文件类型支持功能
     *
     * @param fileExtensionSupportedFunction 额外的文件支持功能, 当上传的文件是不支持的文件类型时, 可以通过此参数手工处理文件并手工完成上传作业.<br>
     *                                       fileExtensionSupportedFunction 参数：<br>
     *                                       InputStream -> 表示上传的文件输入流<br>
     *                                       File -> 本地存储的目标文件对象, 文件名不能改动(扩展名称可以改动), 否则上传后工具类找不到文件位置<br>
     *                                       Boolean -> 转换成功时需返回 true, 失败返回 false<br>
     * @return
     */
    public ServletFileUpload setFileExtensionSupportedFunction(BiFunction<InputStream, File, Boolean> fileExtensionSupportedFunction) {
        this.fileExtensionSupportedFunction = fileExtensionSupportedFunction;
        return this;
    }

    /**
     * 执行文件上传
     *
     * @return 返回文件上传报告
     */
    public FileUploadReport execute() {
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