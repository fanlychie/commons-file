package org.fanlychie.commons.file.util;

import org.fanlychie.commons.file.HttpURLStream;
import org.fanlychie.commons.file.ReadableStream;
import org.fanlychie.commons.file.WritableStream;

import java.io.File;
import java.io.InputStream;

/**
 * 文件操作工具类
 * <p>
 * Created by fanlychie on 2017/1/10.
 */
public final class FileUtils {

    // 私有
    private FileUtils() {

    }

    /**
     * 构造一个可写的流对象
     *
     * @param file 文件对象
     * @return 返回一个可写的流对象
     */
    public static WritableStream writeFile(File file) {
        return new WritableStream(file);
    }

    /**
     * 构造一个可写的流对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回一个可写的流对象
     */
    public static WritableStream writeFile(String fileName) {
        return new WritableStream(new File(fileName));
    }

    /**
     * 构造一个可写的流对象
     *
     * @param str 字符串内容
     * @return 返回一个可写的流对象
     */
    public static WritableStream writeString(String str) {
        return new WritableStream(str);
    }

    /**
     * 构造一个可写的流对象
     *
     * @param inputStream 输入流对象
     * @return 返回一个可写的流对象
     */
    public static WritableStream writeStream(InputStream inputStream) {
        return new WritableStream(inputStream);
    }

    /**
     * 打开文件, 构造一个可读的流对象
     *
     * @param file 文件对象
     * @return 返回一个可读的流对象
     */
    public static ReadableStream openFile(File file) {
        return new ReadableStream(file);
    }

    /**
     * 打开文件, 构造一个可读的流对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回一个可读的流对象
     */
    public static ReadableStream openFile(String fileName) {
        return new ReadableStream(fileName);
    }

    /**
     * 打开文件, 构造一个可读的流对象
     *
     * @param file    文件对象
     * @param charset 字符集编码
     * @return 返回一个可读的流对象
     */
    public static ReadableStream openFile(File file, String charset) {
        return new ReadableStream(file, charset);
    }

    /**
     * 打开文件, 构造一个可读的流对象
     *
     * @param fileName 文件绝对路径的名称
     * @param charset  字符集编码
     * @return 返回一个可读的流对象
     */
    public static ReadableStream openFile(String fileName, String charset) {
        return new ReadableStream(fileName, charset);
    }

    /**
     * 打开输入流, 构造一个可读的流对象
     *
     * @param inputStream 输入流对象
     * @return 返回一个可读的流对象
     */
    public static ReadableStream openStream(InputStream inputStream) {
        return new ReadableStream(inputStream);
    }

    /**
     * 打开输入流, 构造一个可读的流对象
     *
     * @param inputStream 输入流对象
     * @param charset     字符集编码
     * @return 返回一个可读的流对象
     */
    public static ReadableStream openStream(InputStream inputStream, String charset) {
        return new ReadableStream(inputStream, charset);
    }

    /**
     * 打开超链接地址, 构造一个 HTTP URL 流对象
     *
     * @param url 超链接地址
     * @return
     */
    public static HttpURLStream openUrl(String url) {
        return new HttpURLStream(url);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 返回文件扩展名, eg: 'jpg', 'png'
     */
    public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return index == -1 ? "" : fileName.substring(index + 1).toLowerCase();
    }

}