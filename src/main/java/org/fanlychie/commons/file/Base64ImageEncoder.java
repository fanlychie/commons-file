package org.fanlychie.commons.file;

import org.fanlychie.commons.file.exception.Base64EncodeImageException;
import org.fanlychie.commons.file.exception.RuntimeCastException;
import org.fanlychie.commons.file.util.FileUtils;
import org.fanlychie.commons.file.util.InputStreamBuilder;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Base64 图片编码器
 * Created by fanlychie on 2017/1/12.
 */
public class Base64ImageEncoder {

    /**
     * 图片扩展名
     */
    private String extension;

    /**
     * 容错模式, 默认关闭.
     * 若开启此模式, 当得不到图片扩展名时, 则使用默认的图片扩展名.
     */
    private boolean faultToleranceMode;

    /**
     * 图片资源输入流
     */
    private BufferedInputStream bufferedInputStream;

    /**
     * 默认使用的图片扩展名
     */
    private static final String DEFAULT_EXTENSION = "jpg";

    /**
     * IO 缓存, 512KB
     */
    private static final byte[] BUFFER = new byte[512 * 1024];

    /**
     * 图片的 Data URI Scheme 对照表
     */
    private static final Map<String, String> DATA_URI_SCHEME = new HashMap<>();

    /**
     * 初始化数据
     */
    static {
        DATA_URI_SCHEME.put("gif", "data:image/gif;base64,");
        DATA_URI_SCHEME.put("png", "data:image/png;base64,");
        DATA_URI_SCHEME.put("ico", "data:image/x-icon;base64,");
        DATA_URI_SCHEME.put("jpg", "data:image/jpeg;base64,");
        DATA_URI_SCHEME.put("jpeg", "data:image/jpeg;base64,");
    }

    /**
     * 创建一个 Base64 图片编码器对象
     *
     * @param imgFile 图片文件
     */
    public Base64ImageEncoder(File imgFile) {
        this.extension = FileUtils.getFileExtension(imgFile.getName());
        this.bufferedInputStream = InputStreamBuilder.buildBufferedInputStream(imgFile);
    }

    /**
     * 创建一个 Base64 图片编码器对象
     *
     * @param imgUrl 图片地址
     */
    public Base64ImageEncoder(String imgUrl) {
        this.extension = FileUtils.getUrlFileExtension(imgUrl);
        this.bufferedInputStream = new BufferedInputStream(InputStreamBuilder.buildHpptUrlInputStream(imgUrl));
    }

    /**
     * 创建一个 Base64 图片编码器对象
     *
     * @param inputStream InputStream
     * @param extension   图片扩展名, eg: 'jpg', 'png'...
     */
    public Base64ImageEncoder(InputStream inputStream, String extension) {
        this.extension = extension;
        this.bufferedInputStream = new BufferedInputStream(inputStream);
    }

    /**
     * 设置图片扩展名
     *
     * @param extension 图片扩展名, eg: 'jpg', 'png'...
     * @return
     */
    public Base64ImageEncoder setExtension(String extension) {
        this.extension = extension;
        return this;
    }

    /**
     * 设置容错模式
     *
     * @param faultToleranceMode 若为 true, 当提取不到扩展名时使用 'jpg' 作为图片文件的扩展名
     * @return
     */
    public Base64ImageEncoder setFaultToleranceMode(boolean faultToleranceMode) {
        this.faultToleranceMode = faultToleranceMode;
        return this;
    }

    @Override
    public String toString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int read;
            while ((read = bufferedInputStream.read(BUFFER)) != -1) {
                byteArrayOutputStream.write(BUFFER, 0, read);
            }
            String data = new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray()));
            String dataUriScheme = DATA_URI_SCHEME.get(extension.toLowerCase());
            if (dataUriScheme == null) {
                if (faultToleranceMode) {
                    dataUriScheme = DATA_URI_SCHEME.get(DEFAULT_EXTENSION);
                } else {
                    throw new Base64EncodeImageException("不支持使用 Base64 编码的图片类型: " + extension);
                }
            }
            return dataUriScheme + data;
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeCastException(e);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeCastException(e);
                }
            }
        }
    }

}