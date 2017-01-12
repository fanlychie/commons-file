package org.fanlychie.commons.file.util;

import org.fanlychie.commons.file.exception.RuntimeCastException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Writer 构建器
 * <p>
 * Created by fanlychie on 2017/1/11.
 */
public final class WriterBuilder {

    /**
     * 构建 FileWriter 对象
     *
     * @param file 文件对象
     * @return 返回 FileWriter 对象
     */
    public static FileWriter buildFileWriter(File file) {
        try {
            return new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 FileWriter 对象
     *
     * @param file   文件对象
     * @param append 是否追加到文件, true: 追加, false: 覆盖
     * @return 返回 FileWriter 对象
     */
    public static FileWriter buildFileWriter(File file, boolean append) {
        try {
            return new FileWriter(file, append);
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 FileWriter 对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回 FileWriter 对象
     */
    public static FileWriter buildFileWriter(String fileName) {
        try {
            return new FileWriter(fileName);
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 FileWriter 对象
     *
     * @param fileName 文件绝对路径的名称
     * @param append   是否追加到文件, true: 追加, false: 覆盖
     * @return 返回 FileWriter 对象
     */
    public static FileWriter buildFileWriter(String fileName, boolean append) {
        try {
            return new FileWriter(fileName, append);
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedWriter 对象
     *
     * @param file 文件对象
     * @return 返回 BufferedWriter 对象
     */
    public static BufferedWriter buildBufferedWriter(File file) {
        try {
            return new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedWriter 对象
     *
     * @param file   文件对象
     * @param append 是否追加到文件, true: 追加, false: 覆盖
     * @return 返回 BufferedWriter 对象
     */
    public static BufferedWriter buildBufferedWriter(File file, boolean append) {
        try {
            return new BufferedWriter(new FileWriter(file, append));
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedWriter 对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回 BufferedWriter 对象
     */
    public static BufferedWriter buildBufferedWriter(String fileName) {
        try {
            return new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedWriter 对象
     *
     * @param fileName 文件绝对路径的名称
     * @param append   是否追加到文件, true: 追加, false: 覆盖
     * @return 返回 BufferedWriter 对象
     */
    public static BufferedWriter buildBufferedWriter(String fileName, boolean append) {
        try {
            return new BufferedWriter(new FileWriter(fileName, append));
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 OutputStreamWriter 对象
     *
     * @param outputStream outputStream
     * @return 返回 OutputStreamWriter 对象
     */
    public static OutputStreamWriter buildOutputStreamWriter(OutputStream outputStream) {
        return new OutputStreamWriter(outputStream);
    }

    /**
     * 构建 OutputStreamWriter 对象
     *
     * @param outputStream outputStream
     * @param charset      字符集编码
     * @return 返回 OutputStreamWriter 对象
     */
    public static OutputStreamWriter buildOutputStreamWriter(OutputStream outputStream, String charset) {
        try {
            return new OutputStreamWriter(outputStream, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeCastException(e);
        }
    }

}