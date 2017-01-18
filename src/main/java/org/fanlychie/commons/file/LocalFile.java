package org.fanlychie.commons.file;

import java.io.File;

/**
 * 本地文件
 * Created by fanlychie on 2017/1/13.
 */
public class LocalFile {

    /**
     * 文件对象
     */
    private File file;

    /**
     * 表示此文件的唯一 Key 字符串
     */
    private String key;

    /**
     * 创建一个本地文件对象
     *
     * @param key  表示本地文件的 Key
     * @param file 本地文件对象
     */
    LocalFile(String key, File file) {
        this.key = key;
        this.file = file;
    }

    /**
     * 获取本地文件
     *
     * @return 返回本地文件对象
     */
    public File getFile() {
        return file;
    }

    /**
     * 获取表示本地文件的 KEY
     *
     * @return 返回表示本地文件的 KEY
     */
    public String getKey() {
        return key;
    }

}