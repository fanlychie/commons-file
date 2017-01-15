package org.fanlychie.commons.file;

import org.apache.commons.fileupload.FileItem;
import org.fanlychie.commons.file.util.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * 本地文件上传支持
 * Created by fanlychie on 2017/1/13.
 */
public abstract class LocalFileUploadSupport {

    /**
     * 上传的文件最小大小, 默认0, 表示不限制
     */
    protected long minSize;

    /**
     * 上传的文件最大大小, 默认0, 表示不限制
     */
    protected long maxSize;

    /**
     * 允许上传的文件类型
     */
    protected List<String> allowedFileExtension;

    /**
     * 上传的文件类型不支持的提示信息
     */
    protected String unsupportedFileExtensionMsg;

    /**
     * 上传的文件大小超出限制的提示信息
     */
    protected String unsupportedFileSizeMsg;

    /**
     * 额外的文件大小支持功能, 当上传的文件大小超出限制时, 则使用此功能继续完成上传文件的作业.
     * 若调用端没有提供此功能, 当上传的文件大小超出限制时, 则不上传此文件并反馈相应的提示信息.
     */
    protected BiFunction<InputStream, File, Boolean> fileSizeSupportedFunction;

    /**
     * 额外的文件类型支持功能, 当上传的文件是不支持的文件类型时, 则使用此功能继续完成上传文件的作业.
     * 若调用端没有提供此功能, 当上传的文件是不支持的文件类型时, 则不上传此文件并反馈相应的提示信息.
     */
    protected BiFunction<InputStream, File, Boolean> fileExtensionSupportedFunction;

    /**
     * 设置允许上传的文件大小
     *
     * @param minSize 最小大小, 单位(B), 默认为0, 表示不限制
     * @param maxSize 最大大小, 单位(B), 默认为0, 表示不限制
     */
    protected void setAllowedFileSizeRange(long minSize, long maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.unsupportedFileSizeMsg = "请上传 ";
        if (maxSize != 0 && minSize == 0) {
            minSize = 1;
        }
        if (minSize != 0 && maxSize != 0) {
            this.unsupportedFileSizeMsg += FileUtils.getFileSize(minSize);
            this.unsupportedFileSizeMsg += " ~ ";
            this.unsupportedFileSizeMsg += FileUtils.getFileSize(maxSize);
            this.unsupportedFileSizeMsg += " 的文件";
        }
    }

    /**
     * 设置允许上传的文件类型
     *
     * @param extension 文件扩展名, 默认为空, 表示不限制, eg: "jpg", "png" ...
     */
    protected void setAllowedFileExtensions(String... extension) {
        this.allowedFileExtension = new ArrayList<>();
        Arrays.stream(extension).forEach(item -> allowedFileExtension.add(item.toLowerCase()));
        this.unsupportedFileExtensionMsg = Arrays.toString(allowedFileExtension.toArray());
        this.unsupportedFileExtensionMsg = unsupportedFileExtensionMsg.substring(1, unsupportedFileExtensionMsg.length() - 1);
    }

    /**
     * 在文件上传前执行, 以完成文件大小、类型的检验和处理
     *
     * @param report   文件上传报告
     * @param source   上传的文件源
     * @param fileName 文件名称
     * @param fileSize 文件大小
     * @param consumer 本地文件对象
     */
    protected void preFileUpload(FileUploadReport report, Object source, String fileName, long fileSize, Consumer<File> consumer) {
        String extension = FileUtils.getFileExtension(fileName);
        if (allowedFileExtension == null || (allowedFileExtension != null && allowedFileExtension.contains(extension))) {
            String fileSizeMsg = null;
            if (minSize != 0 && fileSize < minSize) {
                fileSizeMsg = "太小";
            } else if (maxSize != 0 && fileSize > maxSize) {
                fileSizeMsg = "太大";
            }
            if (fileSizeMsg != null) {
                if (fileSizeSupportedFunction != null) {
                    callSupportedFileUploadFunction(report, source, fileName, extension, fileSizeSupportedFunction);
                } else {
                    report.addFileUploadFeedback("文件 \"" + fileName + "\" " + fileSizeMsg + ", " + unsupportedFileSizeMsg, false);
                }
            } else {
                LocalFile localFile = FileUtils.createLocalFile(extension);
                try {
                    consumer.accept(localFile.getFile());
                    report.addFileUploadFeedback(localFile.getKey(), true);
                } catch (Throwable e) {
                    e.printStackTrace(); // 为不打断多文件上传, 此处不抛出异常
                    report.addFileUploadFeedback("文件 \"" + fileName + "\" 上传失败, 请重新选择上传", false);
                }
            }
        } else {
            if (fileExtensionSupportedFunction != null) {
                callSupportedFileUploadFunction(report, source, fileName, extension, fileExtensionSupportedFunction);
            } else {
                report.addFileUploadFeedback("文件 \"" + fileName + "\" 是不支持上传的类型, 请选择 " + unsupportedFileExtensionMsg + " 类型的文件", false);
            }
        }
    }

    /**
     * 调用额外支持文件上传的功能
     *
     * @param report     文件上传报告
     * @param source     上传的文件源
     * @param fileName   文件名称
     * @param extension  文件扩展名
     * @param biFunction 额外支持文件上传的功能
     */
    private void callSupportedFileUploadFunction(FileUploadReport report, Object source, String fileName, String extension, BiFunction<InputStream, File, Boolean> biFunction) {
        try {
            InputStream in = null;
            if (source instanceof FileItem) {
                in = ((FileItem) source).getInputStream();
            } else if (source instanceof MultipartFile) {
                in = ((MultipartFile) source).getInputStream();
            }
            LocalFile localFile = FileUtils.createLocalFile(extension);
            Boolean executeResult = biFunction.apply(in, localFile.getFile());
            if (executeResult != null && executeResult) {
                report.addFileUploadFeedback(localFile.getKey(), true);
            } else {
                report.addFileUploadFeedback("文件 \"" + fileName + "\" 上传失败, 请重新选择上传", false);
            }
        } catch (Throwable e) {
            e.printStackTrace(); // 为不打断多文件上传, 此处不抛出异常
            report.addFileUploadFeedback("文件 \"" + fileName + "\" 上传失败, 请重新选择上传", false);
        }
    }

}