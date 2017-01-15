package org.fanlychie.commons.file;

import org.fanlychie.commons.file.exception.Base64DecodeImageException;
import org.fanlychie.commons.file.exception.RuntimeCastException;
import org.fanlychie.commons.file.util.OutputStreamBuilder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base64 图片解码器
 * Created by fanlychie on 2017/1/12.
 */
public class Base64ImageDecoder {

    /**
     * Base64 编码的字符串内容
     */
    private String imgBase64Str;

    /**
     * 图片的 Data URI Scheme 对照表
     */
    private static final Map<String, String> DATA_URI_SCHEME = new HashMap<>();

    /**
     * 初始化数据
     */
    static {
        DATA_URI_SCHEME.put("data:image/gif;base64,", ".gif");
        DATA_URI_SCHEME.put("data:image/png;base64,", ".png");
        DATA_URI_SCHEME.put("data:image/jpeg;base64,", ".jpg");
        DATA_URI_SCHEME.put("data:image/x-icon;base64,", ".ico");
    }

    /**
     * 创建一个 Base64 图片解码器
     *
     * @param imgBase64Str Base64 编码的字符串内容
     */
    public Base64ImageDecoder(String imgBase64Str) {
        this.imgBase64Str = imgBase64Str;
    }

    /**
     * 解码输出到文件
     *
     * @param dest 目标文件
     */
    public void toFile(String dest) {
        toFile(new File(dest));
    }

    /**
     * 解码输出到文件
     *
     * @param dest 目标文件的绝对路径
     */
    public void toFile(File dest) {
        toFolder(dest.getParentFile(), dest.getName());
    }

    /**
     * 解码输出到目标文件夹
     *
     * @param destFolder 目标文件夹
     * @return 返回解码成功后的文件
     */
    public File toFolder(File destFolder) {
        return toFolder(destFolder, null);
    }

    /**
     * 解码输出到目标文件夹
     *
     * @param destFolder 目标文件夹绝对路径
     * @return 返回解码成功后的文件
     */
    public File toFolder(String destFolder) {
        return toFolder(new File(destFolder), null);
    }

    /**
     * 解码输出到目标文件夹
     *
     * @param destFolder 目标文件夹绝对路径
     * @param fileName   存储文件的名称
     * @return 返回解码成功后的文件
     */
    public File toFolder(String destFolder, String fileName) {
        return toFolder(new File(destFolder), fileName);
    }

    /**
     * 解码输出到目标文件夹
     *
     * @param destFolder 目标文件夹
     * @param fileName   存储文件的名称
     * @return 返回解码成功后的文件
     */
    public File toFolder(File destFolder, String fileName) {
        if (!destFolder.isDirectory()) {
            throw new Base64DecodeImageException("\"" + destFolder + "\" 不是一个有效的目录");
        }
        Pattern pattern = Pattern.compile("(data:image/\\w+;base64,)(\\S+)");
        Matcher matcher = pattern.matcher(imgBase64Str);
        if (fileName == null) {
            String scheme = matcher.replaceAll("$1");
            String extension = DATA_URI_SCHEME.get(scheme);
            if (extension == null) {
                throw new Base64DecodeImageException("不支持使用 Base64 解码的图片内容: " + imgBase64Str);
            }
            fileName = UUID.randomUUID().toString().replace("-", "") + extension;
        }
        File dest = new File(destFolder, fileName);
        imgBase64Str = matcher.replaceAll("$2");
        byte[] data = Base64.getDecoder().decode(imgBase64Str.getBytes());
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                data[i] += 256;
            }
        }
        try (BufferedOutputStream os = OutputStreamBuilder.buildBufferedOutputStream(dest)) {
            os.write(data);
            os.flush();
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
        return dest;
    }

}