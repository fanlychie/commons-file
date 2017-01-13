package org.fanlychie.commons.file;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传报告
 * Created by 范忠云 on 2017/1/13.
 */
public class FileUploadReport {

    private int failNum;

    private int successNum;

    private List<String> fileKeys = new ArrayList<>();

    private List<String> failMsgs = new ArrayList<>();

    /**
     * 获取失败的文件个数
     *
     * @return 返回失败的文件个数
     */
    public int getFailNum() {
        return failNum;
    }

    /**
     * 获取成功的文件个数
     *
     * @return 返回成功的文件个数
     */
    public int getSuccessNum() {
        return successNum;
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
    public List<String> getFailMsgs() {
        return failMsgs;
    }

    /**
     * 报告是否健康的, 若是, 表明上传全部成功, 否则表明存在上传失败的文件
     *
     * @return true/false
     */
    public boolean isHealthy() {
        return failNum == 0;
    }

    /**
     * 添加文件上传反馈
     *
     * @param content 反馈的内容
     * @param success 文件上传是否成功
     */
    public void addFileUploadFeedback(String content, boolean success) {
        if (success) {
            this.successNum++;
            this.fileKeys.add(content);
        } else {
            this.failNum++;
            this.failMsgs.add(content);
        }
    }

}