package org.fanlychie.commons.file;

import org.fanlychie.commons.file.exception.RuntimeCastException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * Spring MVC 文件上传
 * Created by fanlychie on 2017/1/13.
 */
public class SpringMVCFileUpload extends LocalFileUploadSupport {

    // SpringMVC 文件对象数组
    private MultipartFile[] files;

    /**
     * 创建一个 Spring MVC 文件上传对象
     *
     * @param files 文件数组
     */
    public SpringMVCFileUpload(MultipartFile[] files) {
        this.files = files;
    }

    /**
     * 设置允许上传的文件类型
     *
     * @param extension 文件扩展名, 默认为空, 表示不限制, eg: "jpg", "png" ...
     * @return
     */
    public SpringMVCFileUpload setAllowedFileExtension(String... extension) {
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
    public SpringMVCFileUpload setAllowedFileSize(long minSize, long maxSize) {
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
    public SpringMVCFileUpload setFileSizeSupportedFunction(BiFunction<InputStream, File, Boolean> fileSizeSupportedFunction) {
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
    public SpringMVCFileUpload setFileExtensionSupportedFunction(BiFunction<InputStream, File, Boolean> fileExtensionSupportedFunction) {
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
        Arrays.stream(files).filter(file -> file != null && !file.isEmpty()).forEach(file -> {
            preFileUpload(report, file, file.getOriginalFilename(), file.getSize(), localFile -> {
                try {
                    file.transferTo(localFile);
                } catch (IOException e) {
                    throw new RuntimeCastException(e);
                }
            });
        });
        return report;
    }

}