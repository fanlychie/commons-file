package org.fanlychie.commons.file.util;

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
     * @param file 要写出的文件对象
     * @return 返回一个可写的流对象
     */
    public static WritableStream write(File file) {
        return new WritableStream(file);
    }

    /**
     * 构造一个可写的流对象
     *
     * @param inputStream 要写出的输入流对象
     * @return 返回一个可写的流对象
     */
    public static WritableStream write(InputStream inputStream) {
        return new WritableStream(inputStream);
    }

    /**
     * 构造一个可写的流对象
     *
     * @param inputStream 要写出的输入流对象
     * @param charset     使用的字符集编码
     * @return 返回一个可写的流对象
     */
    public static WritableStream write(InputStream inputStream, String charset) {
        return new WritableStream(inputStream, charset);
    }

    /**
     * 构造一个可写的流对象
     *
     * @param content 要写出的文本内容
     * @return 返回一个可写的流对象
     */
    public static WritableStream write(String content) {
        return new WritableStream(content);
    }

    /**
     * 打开文件, 构造一个可读的流对象
     *
     * @param file 操作的文件对象
     * @return 返回一个可读的流对象
     */
    public static ReadableStream open(File file) {
        return new ReadableStream(file);
    }

    /**
     * 打开文件, 构造一个可读的流对象
     *
     * @param file    操作的文件对象
     * @param charset 字符集编码
     * @return 返回一个可读的流对象
     */
    public static ReadableStream open(File file, String charset) {
        return new ReadableStream(file, charset);
    }

    /**
     * 打开输入流, 构造一个可读的流对象
     *
     * @param inputStream 操作的输入流对象
     * @return 返回一个可读的流对象
     */
    public static ReadableStream open(InputStream inputStream) {
        return new ReadableStream(inputStream);
    }

    /**
     * 打开输入流, 构造一个可读的流对象
     *
     * @param inputStream 操作的输入流对象
     * @param charset     字符集编码
     * @return 返回一个可读的流对象
     */
    public static ReadableStream open(InputStream inputStream, String charset) {
        return new ReadableStream(inputStream, charset);
    }

}