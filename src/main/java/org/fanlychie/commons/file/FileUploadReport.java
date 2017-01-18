package org.fanlychie.commons.file;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传报告
 * Created by fanlychie on 2017/1/13.
 */
public class FileUploadReport {

    /**
     * 是否健康的
     */
    private boolean healthy;

    /**
     * 文件上传失败的个数
     */
    private int failedNumber;

    /**
     * 文件上传成功的个数
     */
    private int successfulNumber;

    /**
     * 此容器用于存储上传成功的本地文件 Key 列表
     */
    private List<String> fileKeys = new ArrayList<>();

    /**
     * 此容器用于存储文件上传失败的信息
     */
    private List<String> failedMsgs = new ArrayList<>();

    /**
     * 获取失败的文件个数
     *
     * @return 返回失败的文件个数
     */
    public int getFailedNumber() {
        return failedNumber;
    }

    /**
     * 获取成功的文件个数
     *
     * @return 返回成功的文件个数
     */
    public int getSuccessfulNumber() {
        return successfulNumber;
    }

    /**
     * 获取成功的文件 Key 列表
     *
     * @return 返回成功的文件 Key 列表
     */
    public List<String> getFileKeys() {
        return fileKeys;
    }

    /**
     * 获取失败的文件消息列表
     *
     * @return 返回失败的文件消息列表
     */
    public List<String> getFailedMsgs() {
        return failedMsgs;
    }

    /**
     * 报告是否健康的, 若是, 表明上传全部成功, 否则表明存在上传失败或空的文件
     *
     * @return true/false
     */
    public boolean isHealthy() {
        healthy = failedNumber == 0 && successfulNumber > 0;
        return healthy;
    }

    /**
     * 添加文件上传反馈
     *
     * @param content 反馈的内容
     * @param success 文件上传是否成功
     */
    void addFileUploadFeedback(String content, boolean success) {
        if (success) {
            this.successfulNumber++;
            this.fileKeys.add(content);
        } else {
            this.failedNumber++;
            this.failedMsgs.add(content);
        }
    }

}