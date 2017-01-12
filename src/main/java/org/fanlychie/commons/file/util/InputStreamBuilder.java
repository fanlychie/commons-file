package org.fanlychie.commons.file.util;

import org.fanlychie.commons.file.exception.RuntimeCastException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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

}