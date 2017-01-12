package org.fanlychie.commons.file.util;

import org.fanlychie.commons.file.exception.RuntimeCastException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * OutputStream 构建器
 * <p>
 * Created by fanlychie on 2017/1/12.
 */
public final class OutputStreamBuilder {

    /**
     * 构建 FileOutputStream 对象
     *
     * @param file 文件
     * @return 返回 FileOutputStream 对象
     */
    public static FileOutputStream buildFileOutputStream(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 FileOutputStream 对象
     *
     * @param file   文件
     * @param append 是否追加到文件, true: 追加, false: 覆盖
     * @return 返回 FileOutputStream 对象
     */
    public static FileOutputStream buildFileOutputStream(File file, boolean append) {
        try {
            return new FileOutputStream(file, append);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 FileOutputStream 对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回 FileOutputStream 对象
     */
    public static FileOutputStream buildFileOutputStream(String fileName) {
        try {
            return new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 FileOutputStream 对象
     *
     * @param fileName 文件绝对路径的名称
     * @param append   是否追加到文件, true: 追加, false: 覆盖
     * @return 返回 FileOutputStream 对象
     */
    public static FileOutputStream buildFileOutputStream(String fileName, boolean append) {
        try {
            return new FileOutputStream(fileName, append);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedOutputStream 对象
     *
     * @param file 文件
     * @return 返回 BufferedOutputStream 对象
     */
    public static BufferedOutputStream buildBufferedOutputStream(File file) {
        try {
            return new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedOutputStream 对象
     *
     * @param file   文件
     * @param append 是否追加到文件, true: 追加, false: 覆盖
     * @return 返回 BufferedOutputStream 对象
     */
    public static BufferedOutputStream buildBufferedOutputStream(File file, boolean append) {
        try {
            return new BufferedOutputStream(new FileOutputStream(file, append));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedOutputStream 对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回 BufferedOutputStream 对象
     */
    public static BufferedOutputStream buildBufferedOutputStream(String fileName) {
        try {
            return new BufferedOutputStream(new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedOutputStream 对象
     *
     * @param fileName 文件绝对路径的名称
     * @param append   是否追加到文件, true: 追加, false: 覆盖
     * @return 返回 BufferedOutputStream 对象
     */
    public static BufferedOutputStream buildBufferedOutputStream(String fileName, boolean append) {
        try {
            return new BufferedOutputStream(new FileOutputStream(fileName, append));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedOutputStream 对象
     *
     * @param out OutputStream
     * @return 返回 BufferedOutputStream 对象
     */
    public static BufferedOutputStream buildBufferedOutputStream(OutputStream out) {
        return new BufferedOutputStream(out);
    }

}