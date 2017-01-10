package org.fanlychie.commons.file.util;

import org.fanlychie.commons.file.WritableStream;

import java.io.*;

/**
 * 文件操作工具类
 *
 * Created by fanlychie on 2017/1/10.
 */
public final class FileUtils {

    // 默认字符集编码
    private static final String DEFAULT_CHARSET = "GBK";

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
        try {
            return new WritableStream(new FileInputStream(file), DEFAULT_CHARSET);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构造一个可写的流对象
     *
     * @param inputStream 要写出的输入流对象
     * @return 返回一个可写的流对象
     */
    public static WritableStream write(InputStream inputStream) {
        try {
            return new WritableStream(inputStream, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构造一个可写的流对象
     *
     * @param inputStream 要写出的输入流对象
     * @return 返回一个可写的流对象
     */
    public static WritableStream write(InputStream inputStream, String charset) {
        try {
            return new WritableStream(inputStream, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构造一个可写的流对象
     *
     * @param content 要写出的文本内容
     * @return 返回一个可写的流对象
     */
    public static WritableStream write(String content) {
        return new WritableStream(new StringReader(content));
    }

}