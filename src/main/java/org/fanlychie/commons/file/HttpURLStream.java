package org.fanlychie.commons.file;

import org.fanlychie.commons.file.util.FileUtils;
import org.fanlychie.commons.file.util.InputStreamBuilder;

import java.io.File;

/**
 * Http URL 流
 * Created by fanlychie on 2017/1/12.
 */
public class HttpURLStream {

    /**
     * 超链接地址
     */
    private String url;

    /**
     * 读取超时时间, 默认3分钟
     */
    private int readTimeout = 3 * 60 * 1000;

    /**
     * 连接超时时间, 默认30秒
     */
    private int connectTimeout = 30 * 1000;

    /**
     * Http URL 流
     *
     * @param url 超链接地址
     */
    public HttpURLStream(String url) {
        this.url = url;
    }

    /**
     * 下载到目录
     *
     * @param dest 目录绝对路径的名称
     */
    public void toFolder(String dest) {
        toFolder(new File(dest));
    }

    /**
     * 下载到目录
     *
     * @param dest 目录
     */
    public void toFolder(File dest) {
        toFolder(dest, FileUtils.getUrlFileName(url));
    }

    /**
     * 下载到目录
     *
     * @param dest     目录绝对路径的名称
     * @param fileName 文件名称
     */
    public void toFolder(String dest, String fileName) {
        toFolder(new File(dest), fileName);
    }

    /**
     * 下载到目录
     *
     * @param dest     目录
     * @param fileName 文件名称
     */
    public void toFolder(File dest, String fileName) {
        if (!dest.isDirectory()) {
            throw new IllegalArgumentException(dest + " 不是一个有效的目录");
        }
        toFile(new File(dest, fileName));
    }

    /**
     * 下载到文件
     *
     * @param dest 文件绝对路径的名称
     */
    public void toFile(String dest) {
        toFile(new File(dest));
    }

    /**
     * 下载到文件
     *
     * @param dest 文件
     */
    public void toFile(File dest) {
        FileUtils.writeStream(InputStreamBuilder.buildHpptUrlInputStream(url, readTimeout, connectTimeout)).toFile(dest);
    }

    /**
     * 设置读取超时时间, 默认为 3分钟
     *
     * @param readTimeout 超时时间, 毫秒单位
     * @return
     */
    public HttpURLStream setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * 设置连接超时时间, 默认为 30秒
     *
     * @param connectTimeout 超时时间, 毫秒单位
     * @return
     */
    public HttpURLStream setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

}