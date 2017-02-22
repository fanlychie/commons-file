package org.fanlychie.commons.file;

/**
 * 本地文件上传全局配置
 * Created by fanlychie on 2017/1/13.
 */
public class LocalFileUploadConfig {

    /**
     * 本地上传的文件存储的子目录长度
     */
    static int childFolderLength = 5;

    /**
     * 本地上传的文件存储根目录
     */
    static String storageRootFolder = System.getProperty("java.io.tmpdir");

    /**
     * 设置本地上传的文件存储根目录
     *
     * @param storageRootFolder 上传的文件存储的根目录, 默认使用 Java IO 临时目录
     */
    public void setStorageRootFolder(String storageRootFolder) {
        LocalFileUploadConfig.storageRootFolder = storageRootFolder;
    }

    /**
     * 设置本地上传的文件存储的子目录长度
     *
     * @param childFolderLength 上传的文件存储的子目录长度, 默认长度为 5
     */
    public void setChildFolderLength(int childFolderLength) {
        LocalFileUploadConfig.childFolderLength = childFolderLength;
    }

    /**
     * 获取本地上传的文件存储的子目录长度
     *
     * @return 返回本地上传的文件存储的子目录长度
     */
    public static int getChildFolderLength() {
        return childFolderLength;
    }

    /**
     * 获取本地上传的文件存储根目录
     *
     * @return 返回本地上传的文件存储根目录
     */
    public static String getStorageRootFolder() {
        return storageRootFolder;
    }

}