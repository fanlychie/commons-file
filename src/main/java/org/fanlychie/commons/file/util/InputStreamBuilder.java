package org.fanlychie.commons.file.util;

import org.fanlychie.commons.file.exception.RuntimeCastException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * InputStream 构建器
 * <p>
 * Created by fanlychie on 2017/1/12.
 */
public final class InputStreamBuilder {

    /**
     * 构建 FileInputStream 对象
     *
     * @param file 文件
     * @return 返回 FileInputStream 对象
     */
    public static FileInputStream buildFileInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 FileInputStream 对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回 FileInputStream 对象
     */
    public static FileInputStream buildFileInputStream(String fileName) {
        try {
            return new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedInputStream 对象
     *
     * @param file 文件
     * @return 返回 BufferedInputStream 对象
     */
    public static BufferedInputStream buildBufferedInputStream(File file) {
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedInputStream 对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回 BufferedInputStream 对象
     */
    public static BufferedInputStream buildBufferedInputStream(String fileName) {
        try {
            return new BufferedInputStream(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedInputStream 对象
     *
     * @param inputStream InputStream
     * @return 返回 BufferedInputStream 对象
     */
    public static BufferedInputStream buildBufferedInputStream(InputStream inputStream) {
        return new BufferedInputStream(inputStream);
    }

    /**
     * 构建 InputStream 对象
     *
     * @param url HTTP 链接地址
     * @return 返回 InputStream 对象
     */
    public static InputStream buildHpptUrlInputStream(String url) {
        return buildHpptUrlInputStream(url, 3 * 60 * 1000, 30 * 1000);
    }

    /**
     * 构建 InputStream 对象
     *
     * @param url            HTTP 链接地址
     * @param readTimeout    读取超时时间, 单位毫秒
     * @param connectTimeout 连接超时时间, 单位毫秒
     * @return 返回 InputStream 对象
     */
    public static InputStream buildHpptUrlInputStream(String url, int readTimeout, int connectTimeout) {
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
            return conn.getInputStream();
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

}