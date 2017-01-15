package org.fanlychie.commons.file.util;

import org.fanlychie.commons.file.Base64ImageDecoder;
import org.fanlychie.commons.file.Base64ImageEncoder;
import org.fanlychie.commons.file.HttpURLStream;
import org.fanlychie.commons.file.LocalFile;
import org.fanlychie.commons.file.ReadableStream;
import org.fanlychie.commons.file.ServletFileUpload;
import org.fanlychie.commons.file.SpringMVCFileUpload;
import org.fanlychie.commons.file.WritableStream;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;

/**
 * 文件操作工具类
 * Created by fanlychie on 2017/1/10.
 */
public final class FileUtils {

    // 文件大小单位
    private static final String[] FILE_SIZE_UNIT = {"B", "KB", "M", "G"};

    // 私有
    private FileUtils() {

    }

    /**
     * 写出文件
     *
     * @param file 文件对象
     * @return 返回一个可写的流对象
     */
    public static WritableStream writeFile(File file) {
        return new WritableStream(file);
    }

    /**
     * 写出文件
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回一个可写的流对象
     */
    public static WritableStream writeFile(String fileName) {
        return new WritableStream(new File(fileName));
    }

    /**
     * 写出字符串
     *
     * @param str 字符串内容
     * @return 返回一个可写的流对象
     */
    public static WritableStream writeStr(String str) {
        return new WritableStream(str);
    }

    /**
     * 写出输入流
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
     * Base64 编码图片文件
     *
     * @param imgFile 图片文件
     * @return 返回编码的字符串
     */
    public static Base64ImageEncoder encodeImageFileBase64(File imgFile) {
        return new Base64ImageEncoder(imgFile);
    }

    /**
     * Base64 编码图片文件
     *
     * @param imgFileName 图片文件的绝对路径名称
     * @return 返回编码的字符串
     */
    public static Base64ImageEncoder encodeImageFileBase64(String imgFileName) {
        return new Base64ImageEncoder(new File(imgFileName));
    }

    /**
     * Base64 编码 URL 链接的图片
     *
     * @param imgUrl URL 链接的图片地址
     * @return 返回编码的字符串
     */
    public static Base64ImageEncoder encodeImageUrlBase64(String imgUrl) {
        return new Base64ImageEncoder(imgUrl);
    }

    /**
     * Base64 编码图片输入流
     *
     * @param inputStream InputStream
     * @param extension   图片文件的扩展名
     * @return 返回编码的字符串
     */
    public static Base64ImageEncoder encodeImageStreamBase64(InputStream inputStream, String extension) {
        return new Base64ImageEncoder(inputStream, extension);
    }

    /**
     * Base64 解码图片字符串
     *
     * @param imgBase64Str Base64 编码的字符串内容
     * @return 返回 Base64 图片解码器
     */
    public static Base64ImageDecoder decodeImageStrBase64(String imgBase64Str) {
        return new Base64ImageDecoder(imgBase64Str);
    }

    /**
     * Spring MVC 文件上传
     *
     * @param file 文件对象
     * @return 返回 SpringMVC 文件上传对象
     */
    public static SpringMVCFileUpload uploadFile(MultipartFile file) {
        return new SpringMVCFileUpload(new MultipartFile[]{file});
    }

    /**
     * Spring MVC 文件上传
     *
     * @param files 文件对象数组
     * @return 返回 SpringMVC 文件上传对象
     */
    public static SpringMVCFileUpload uploadFile(MultipartFile[] files) {
        return new SpringMVCFileUpload(files);
    }

    /**
     * Servlet 文件上传
     *
     * @param request HttpServletRequest
     * @return 返回 Servlet 文件上传对象
     */
    public static ServletFileUpload uploadFile(HttpServletRequest request) {
        return new ServletFileUpload(request);
    }

    /**
     * 创建本地文件
     *
     * @param extension 文件扩展名, eg: 'jpg', 'png'...
     * @return 返回本地文件对象
     */
    public static LocalFile createLocalFile(String extension) {
        return LocalFile.create(extension);
    }

    /**
     * 获取本地文件资源
     *
     * @param extension 文件扩展名, eg: 'jpg', 'png'...
     * @return 返回本地文件对象
     */
    public static LocalFile getLocalFileSource(String extension) {
        return LocalFile.getSource(extension);
    }

    /**
     * 获取本地文件
     *
     * @param fileKey 文件存储 KEY
     * @return 返回 KEY 表示的本地文件
     */
    public static File getLocalFile(String fileKey) {
        return LocalFile.get(fileKey);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 返回文件扩展名, eg: 'jpg', 'png'
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null) {
            throw new NullPointerException();
        }
        int index = fileName.lastIndexOf(".");
        return index == -1 ? "" : fileName.substring(index + 1).toLowerCase();
    }

    /**
     * 获取 URL 链接地址的文件名称
     *
     * @param url URL 链接地址
     * @return 返回提取到的文件名称
     */
    public static String getUrlFileName(String url) {
        if (url == null) {
            throw new NullPointerException();
        }
        int index = url.lastIndexOf("/");
        if (index == -1) {
            throw new IllegalArgumentException("无法提取 URL 链接地址的文件名称: " + url);
        }
        return url.substring(index + 1);
    }

    /**
     * 获取 URL 链接地址的文件扩展名
     *
     * @param url URL 链接地址
     * @return 返回文件扩展名, eg: 'jpg', 'png'
     */
    public static String getUrlFileExtension(String url) {
        return getFileExtension(getUrlFileName(url));
    }

    /**
     * 获取表示文件大小的单位信息
     *
     * @param size 文件大小, 单位(B)
     * @return 返回换算后的大小单位, eg: "2M" or "2KB" ...
     */
    public static String getFileSize(long size) {
        int index = 0;
        while (size / 1024 > 0 && index < FILE_SIZE_UNIT.length) {
            size = Math.round(size / 1024);
            index++;
        }
        return size + FILE_SIZE_UNIT[index];
    }

}