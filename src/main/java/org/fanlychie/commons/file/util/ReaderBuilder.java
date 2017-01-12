package org.fanlychie.commons.file.util;

import org.fanlychie.commons.file.exception.RuntimeCastException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * Reader 构建器
 * <p>
 * Created by fanlychie on 2017/1/11.
 */
public final class ReaderBuilder {

    /**
     * 构建 FileReader 对象
     *
     * @param file 文件对象
     * @return 返回 FileReader 对象
     */
    public static FileReader buildFileReader(File file) {
        try {
            return new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 FileReader 对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回 FileReader 对象
     */
    public static FileReader buildFileReader(String fileName) {
        try {
            return new FileReader(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedReader 对象
     *
     * @param file 文件对象
     * @return 返回 BufferedReader 对象
     */
    public static BufferedReader buildBufferedReader(File file) {
        try {
            return new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedReader 对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回 BufferedReader 对象
     */
    public static BufferedReader buildBufferedReader(String fileName) {
        try {
            return new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 StringReader 对象
     *
     * @param str 字符串内容
     * @return 返回 StringReader 对象
     */
    public static StringReader buildStringReader(String str) {
        return new StringReader(str);
    }

    /**
     * 构建 InputStreamReader 对象
     *
     * @param inputStream InputStream
     * @return 返回 InputStreamReader 对象
     */
    public static InputStreamReader buildInputStreamReader(InputStream inputStream) {
        return new InputStreamReader(inputStream);
    }

    /**
     * 构建 InputStreamReader 对象
     *
     * @param inputStream InputStream
     * @param charset     字符集编码
     * @return 返回 InputStreamReader 对象
     */
    public static InputStreamReader buildInputStreamReader(InputStream inputStream, String charset) {
        try {
            return new InputStreamReader(inputStream, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 InputStreamReader 对象
     *
     * @param file 文件对象
     * @return 返回 InputStreamReader 对象
     */
    public static InputStreamReader buildInputStreamReader(File file) {
        try {
            return new InputStreamReader(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 InputStreamReader 对象
     *
     * @param file    文件对象
     * @param charset 字符集编码
     * @return 返回 InputStreamReader 对象
     */
    public static InputStreamReader buildInputStreamReader(File file, String charset) {
        try {
            return new InputStreamReader(new FileInputStream(file), charset);
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 InputStreamReader 对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回 InputStreamReader 对象
     */
    public static InputStreamReader buildInputStreamReader(String fileName) {
        try {
            return new InputStreamReader(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 InputStreamReader 对象
     *
     * @param fileName 文件绝对路径的名称
     * @param charset  字符集编码
     * @return 返回 InputStreamReader 对象
     */
    public static InputStreamReader buildInputStreamReader(String fileName, String charset) {
        try {
            return new InputStreamReader(new FileInputStream(fileName), charset);
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

}