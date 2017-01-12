package org.fanlychie.commons.file;

import org.fanlychie.commons.file.exception.RuntimeCastException;
import org.fanlychie.commons.file.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http URL 流
 * <p>
 * Created by fanlychie on 2017/1/12.
 */
public class HttpURLStream {

    private String url;

    // 3分钟
    private int readTimeout = 3 * 60 * 1000;

    // 30秒
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
        String fileName = null;
        int index = url.lastIndexOf("/");
        if (index != -1) {
            fileName = url.substring(index + 1);
        }
        if (fileName == null) {
            throw new IllegalArgumentException(url + " 无法截取有效的文件名称");
        }
        toFolder(dest, fileName);
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
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
        conn.setReadTimeout(readTimeout);
        conn.setConnectTimeout(connectTimeout);
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0");
        try {
            FileUtils.writeStream(conn.getInputStream()).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
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