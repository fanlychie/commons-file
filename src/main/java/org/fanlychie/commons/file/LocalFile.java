package org.fanlychie.commons.file;

import org.fanlychie.commons.file.exception.LocalFileNotFoundException;
import org.fanlychie.commons.file.exception.RuntimeCastException;
import org.fanlychie.commons.file.util.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 本地文件
 * Created by fanlychie on 2017/1/13.
 */
public class LocalFile {

    /**
     * 文件对象
     */
    private File file;

    /**
     * 表示此文件的唯一 Key 字符串
     */
    private String key;

    /**
     * 创建一个本地文件对象
     *
     * @param key  表示本地文件的 Key
     * @param file 本地文件对象
     */
    private LocalFile(String key, File file) {
        this.key = key;
        this.file = file;
    }

    /**
     * 创建一个本地文件对象
     *
     * @param extension 文件扩展名, eg: 'jpg', 'png'...
     * @return 返回本地文件对象
     */
    public static LocalFile create(String extension) {
        if (extension == null) {
            throw new NullPointerException();
        }
        String uuidStr = UUID.randomUUID().toString().replace("-", "");
        String fileName = uuidStr + "." + extension;
        return new LocalFile(uuidStr, new File(getChildFolder(uuidStr, true), fileName));
    }

    /**
     * 获取本地文件
     *
     * @param fileKey 表示本地文件的 Key
     * @return 返回文件对象
     */
    public static File get(String fileKey) {
        File childFoloder = getChildFolder(fileKey, false);
        if (childFoloder != null) {
            for (File file : childFoloder.listFiles()) {
                if (file.getName().startsWith(fileKey)) {
                    return file;
                }
            }
        }
        return null;
    }

    /**
     * 获取本地文件资源
     *
     * @param fileKey 表示本地文件的 Key
     * @return 返回本地文件对象
     */
    public static LocalFile getSource(String fileKey) {
        return new LocalFile(fileKey, get(fileKey));
    }

    /**
     * 输出
     *
     * @param response HttpServletResponse
     */
    public void output(HttpServletResponse response) {
        output(response, null);
    }

    /**
     * 输出
     *
     * @param response    HttpServletResponse
     * @param contentType content-type 类型
     */
    public void output(HttpServletResponse response, String contentType) {
        if (file == null) {
            throw new LocalFileNotFoundException("找不到文件: " + file);
        }
        if (contentType == null) {
            contentType = getContentType(FileUtils.getFileExtension(file.getName()));
        }
        response.setContentType(contentType);
        try {
            FileUtils.writeFile(file).toOutputStream(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 获取本地文件
     *
     * @return 返回本地文件对象
     */
    public File getFile() {
        return file;
    }

    /**
     * 获取表示本地文件的 KEY
     *
     * @return 返回表示本地文件的 KEY
     */
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "LocalFile{" + "file=" + file + ", key='" + key + '\'' + '}';
    }

    /**
     * 获取本地文件的子文件夹目录
     *
     * @param fileKey 表示本地文件的 Key
     * @param create  目录不存在时是否创建
     * @return 返回子文件夹目录
     */
    private static File getChildFolder(String fileKey, boolean create) {
        if (fileKey != null && fileKey.length() > LocalFileUpload.childFolderLength) {
            String childFolderName = fileKey.substring(0, LocalFileUpload.childFolderLength);
            File childFoloder = new File(LocalFileUpload.storageRootFolder, childFolderName);
            if (!childFoloder.exists()) {
                if (create) {
                    childFoloder.mkdirs();
                } else {
                    return null;
                }
            }
            return childFoloder;
        }
        return null;
    }

    /**
     * 获取文件 content-type
     *
     * @param extension 文件扩展名
     * @return 返回 content-type 字符串
     */
    private String getContentType(String extension) {
        switch (extension) {
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "rtf":
                return "application/rtf";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "pdf":
                return "application/pdf";
            case "swf":
                return "application/x-shockwave-flash";
            case "dll":
                return "application/x-msdownload";
            case "exe":
            case "msi":
            case "chm":
            case "rar":
                return "application/octet-stream";
            case "tar":
                return "application/x-tar";
            case "zip":
                return "application/x-zip-compressed";
            case "z":
            case "tgz":
                return "application/x-compressed";
            case "wav":
                return "audio/wav";
            case "wma":
                return "audio/x-ms-wma";
            case "wmv":
                return "video/x-ms-wmv";
            case "mp2":
            case "mp3":
            case "mpe":
            case "mpg":
            case "mpeg":
                return "audio/mpeg";
            case "bmp":
                return "image/bmp";
            case "gif":
                return "image/gif";
            case "png":
                return "image/png";
            case "tif":
            case "tiff":
                return "image/tiff";
            case "jpe":
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "ico":
                return "image/x-icon";
            case "txt":
                return "text/plain";
            case "xml":
                return "text/xml";
            case "html":
                return "text/html";
            case "css":
                return "text/css";
            case "js":
                return "text/javascript";
            case "mht":
            case "mhtml":
                return "message/rfc822";
            default:
                return "";
        }
    }

}