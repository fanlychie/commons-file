package org.fanlychie.commons.file;

import org.fanlychie.commons.file.exception.Base64DecodeImageException;
import org.fanlychie.commons.file.exception.Base64EncodeImageException;
import org.fanlychie.commons.file.exception.LocalFileNotFoundException;
import org.fanlychie.commons.file.exception.RuntimeCastException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件操作工具类
 * Created by fanlychie on 2017/1/10.
 */
public final class FileUtils {

    /**
     * UTF-8 字符集编码
     */
    private static final String CHARSET_UTF8 = "UTF-8";

    // 文件大小单位
    private static final String[] FILE_SIZE_UNIT = {"B", "KB", "M", "G"};

    /**
     * 文件扩展名对应的 Data URI Scheme
     */
    private static final Map<String, String> EXTENSION_DATA_URI_SCHEME = new HashMap<>();

    /**
     * Data URI Scheme 对应的文件扩展名
     */
    private static final Map<String, String> DATA_URI_SCHEME_EXTENSION = new HashMap<>();

    // 私有
    private FileUtils() {

    }

    /**
     * 初始化数据
     */
    static {
        // gif
        EXTENSION_DATA_URI_SCHEME.put("gif", "data:image/gif;base64,");
        // png
        EXTENSION_DATA_URI_SCHEME.put("png", "data:image/png;base64,");
        // jpg
        EXTENSION_DATA_URI_SCHEME.put("jpg", "data:image/jpeg;base64,");
        // jpg
        EXTENSION_DATA_URI_SCHEME.put("jpeg", "data:image/jpeg;base64,");
        // ico
        EXTENSION_DATA_URI_SCHEME.put("ico", "data:image/x-icon;base64,");
        // 反转
        EXTENSION_DATA_URI_SCHEME.forEach((k, v) -> DATA_URI_SCHEME_EXTENSION.put(v, k));
    }

    /**
     * 逐行读取文件内容
     *
     * @param file     读取的文件对象
     * @param consumer 每行的内容
     */
    public static void readFileLineByLine(File file, Consumer<String> consumer) {
        readStreamLineByLine(getInputStream(file), consumer);
    }

    /**
     * 逐行读取文件内容
     *
     * @param pathname 读取的文件路径名称
     * @param consumer 每行的内容
     */
    public static void readFileLineByLine(String pathname, Consumer<String> consumer) {
        readStreamLineByLine(getInputStream(pathname), consumer);
    }

    /**
     * 读取文件内容
     *
     * @param file 读取的文件对象
     * @return 返回文件的内容
     */
    public static String readFileAsString(File file) {
        return readStreamAsString(getInputStream(file));
    }

    /**
     * 读取文件内容
     *
     * @param pathname 读取的文件路径名称
     * @return 返回文件的内容
     */
    public static String readFileAsString(String pathname) {
        return readStreamAsString(getInputStream(pathname));
    }

    /**
     * 读取文件内容到字符串容器
     *
     * @param file 读取的文件对象
     * @return 返回字符串容器对象
     */
    public static List<String> readFileAsListOfString(File file) {
        List<String> records = new ArrayList<>();
        readStreamLineByLine(getInputStream(file), line -> records.add(line));
        return records;
    }

    /**
     * 读取文件内容到字符串容器
     *
     * @param pathname 读取的文件路径名称
     * @return 返回字符串容器对象
     */
    public static List<String> readFileAsListOfString(String pathname) {
        return readFileAsListOfString(new File(pathname));
    }

    /**
     * 写文件
     *
     * @param file 操作的文件对象
     * @param text 写出的字符串内容
     */
    public static void writeFile(File file, String text) {
        writeFile(file, false, text);
    }

    /**
     * 写文件
     *
     * @param pathname 操作的文件路径名称
     * @param text     写出的字符串内容
     */
    public static void writeFile(String pathname, String text) {
        writeFile(new File(pathname), false, text);
    }

    /**
     * 追加到文件
     *
     * @param file 操作的文件对象
     * @param text 写出的字符串内容
     */
    public static void appendFile(File file, String text) {
        writeFile(file, true, text);
    }

    /**
     * 追加到文件
     *
     * @param pathname 操作的文件路径名称
     * @param text     写出的字符串内容
     */
    public static void appendFile(String pathname, String text) {
        writeFile(new File(pathname), true, text);
    }

    /**
     * 拷贝文件, 将源文件拷贝到目标文件
     *
     * @param src  源文件
     * @param dest 目标文件
     */
    public static void copyFile(File src, File dest) {
        writeInputStreamToOutputStream(getInputStream(src), getOutputStream(dest));
    }

    /**
     * 拷贝文件, 将源文件拷贝到目标文件
     *
     * @param srcPathname  源文件路径名称
     * @param destPathname 目标文件路径名称
     */
    public static void copyFile(String srcPathname, String destPathname) {
        writeInputStreamToOutputStream(getInputStream(srcPathname), getOutputStream(destPathname));
    }

    /**
     * 拷贝文件到目录, 将源文件拷贝到目录
     *
     * @param src 源文件
     * @param dir 目标目录
     */
    public static void copyFileToDirectory(File src, File dir) {
        writeInputStreamToOutputStream(getInputStream(src), getOutputStream(new File(dir, src.getName())));
    }

    /**
     * 拷贝文件到目录, 将源文件拷贝到目录
     *
     * @param srcPathname 源文件路径名称
     * @param dirPathname 目标目录路径名称
     */
    public static void copyFileToDirectory(String srcPathname, String dirPathname) {
        File src = new File(srcPathname);
        writeInputStreamToOutputStream(getInputStream(src), getOutputStream(new File(dirPathname, src.getName())));
    }

    /**
     * 提供文件下载
     *
     * @param response HttpServletResponse
     * @param file     被下载的文件对象
     * @param fileName 下载时显示的文件名称
     */
    public static void provideFileDownload(HttpServletResponse response, File file, String fileName) {
        try {
            fileName = new String(fileName.getBytes(CHARSET_UTF8), "ISO-8859-1");
            response.setContentType("application/octet-stream; charset=iso-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            writeInputStreamToOutputStream(getInputStream(file), response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 提供文件下载
     *
     * @param response HttpServletResponse
     * @param file     被下载的文件对象
     */
    public static void provideFileDownload(HttpServletResponse response, File file) {
        provideFileDownload(response, file, file.getName());
    }

    /**
     * 打开 URL 表示的文件
     *
     * @param url URL 链接
     * @return 返回 URL 表示的文件对象
     */
    public static URLFile openUrlFile(String url) {
        return new URLFile(url);
    }

    /**
     * Base64 编码图片文件
     *
     * @param src 图片文件
     * @return 返回编码的字符串
     */
    public static String encodeImageFileBase64(File src) {
        return encodeImageStreamBase64(getInputStream(src), getFileExtension(src));
    }

    /**
     * Base64 编码图片文件
     *
     * @param pathname 图片文件的路径名称
     * @return 返回编码的字符串
     */
    public static String encodeImageFileBase64(String pathname) {
        return encodeImageFileBase64(new File(pathname));
    }

    /**
     * Base64 编码 URL 图片
     *
     * @param url URL 链接的图片地址
     * @return 返回编码的字符串
     */
    public static String encodeImageUrlBase64(String url) {
        return encodeImageStreamBase64(new URLFile(url).getInputStream(), getUrlFileExtension(url));
    }

    /**
     * Base64 解码图片字符串
     *
     * @param dir            目标目录
     * @param fileName       图片存储的文件名称, eg: img.jpg. 若为 null, 则生成一个随机串作为文件名称
     * @param base64ImageStr Base64 编码的图片字符串内容
     * @return 返回图片的文件对象
     */
    public static File decodeBase64ImageStrToDirectory(File dir, String fileName, String base64ImageStr) {
        if (!dir.isDirectory()) {
            throw new Base64DecodeImageException("\"" + dir + "\" 不是一个有效的目录");
        }
        Pattern pattern = Pattern.compile("(data:image/\\w+;base64,)(\\S+)");
        Matcher matcher = pattern.matcher(base64ImageStr);
        if (fileName == null) {
            String scheme = matcher.replaceAll("$1");
            String extension = DATA_URI_SCHEME_EXTENSION.get(scheme);
            if (extension == null) {
                throw new Base64DecodeImageException("不支持使用 Base64 解码的图片内容: " + base64ImageStr);
            }
            fileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        }
        File dest = new File(dir, fileName);
        base64ImageStr = matcher.replaceAll("$2");
        byte[] data = Base64.getDecoder().decode(base64ImageStr.getBytes());
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                data[i] += 256;
            }
        }
        try (BufferedOutputStream os = new BufferedOutputStream(getOutputStream(dest))) {
            os.write(data);
            os.flush();
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
        return dest;
    }

    /**
     * Base64 解码图片字符串
     *
     * @param dir            目标目录路径名称
     * @param fileName       图片存储的文件名称, eg: img.jpg. 若为 null, 则生成一个随机串作为文件名称
     * @param base64ImageStr Base64 编码的图片字符串内容
     * @return 返回图片的文件对象
     */
    public static File decodeBase64ImageStrToDirectory(String dir, String fileName, String base64ImageStr) {
        return decodeBase64ImageStrToDirectory(new File(dir), fileName, base64ImageStr);
    }

    /**
     * Base64 解码图片字符串
     *
     * @param dir            目标目录路径名称
     * @param base64ImageStr Base64 编码的图片字符串内容
     * @return 返回图片的文件对象
     */
    public static File decodeBase64ImageStrToDirectory(File dir, String base64ImageStr) {
        return decodeBase64ImageStrToDirectory(dir, null, base64ImageStr);
    }

    /**
     * Base64 解码图片字符串
     *
     * @param dir            目标目录路径名称
     * @param base64ImageStr Base64 编码的图片字符串内容
     * @return 返回图片的文件对象
     */
    public static File decodeBase64ImageStrToDirectory(String dir, String base64ImageStr) {
        return decodeBase64ImageStrToDirectory(new File(dir), null, base64ImageStr);
    }

    /**
     * Base64 解码图片字符串
     *
     * @param dest           目标文件
     * @param base64ImageStr Base64 编码的图片字符串内容
     * @return 返回图片的文件对象
     */
    public static File decodeBase64ImageStrToFile(File dest, String base64ImageStr) {
        return decodeBase64ImageStrToDirectory(dest.getParentFile(), dest.getName(), base64ImageStr);
    }

    /**
     * Base64 解码图片字符串
     *
     * @param dest           目标文件路径名称
     * @param base64ImageStr Base64 编码的图片字符串内容
     * @return 返回图片的文件对象
     */
    public static File decodeBase64ImageStrToFile(String dest, String base64ImageStr) {
        return decodeBase64ImageStrToFile(new File(dest), base64ImageStr);
    }

    /**
     * Spring MVC 文件上传
     *
     * @param file 文件对象
     * @return 返回本地文件上传对象
     */
    public static LocalFileUpload uploadFile(MultipartFile file) {
        return new SpringMVCFileUpload(new MultipartFile[]{file});
    }

    /**
     * Spring MVC 文件上传
     *
     * @param files 文件对象数组
     * @return 返回本地文件上传对象
     */
    public static LocalFileUpload uploadFile(MultipartFile[] files) {
        return new SpringMVCFileUpload(files);
    }

    /**
     * Servlet 文件上传
     *
     * @param request HttpServletRequest
     * @return 返回本地文件上传对象
     */
    public static LocalFileUpload uploadFile(HttpServletRequest request) {
        return new ServletFileUpload(request);
    }

    /**
     * 创建本地文件
     *
     * @param extension 文件扩展名, eg: 'jpg', 'png'...
     * @return 返回本地文件对象
     */
    public static LocalFile createLocalFile(String extension) {
        if (extension == null) {
            throw new NullPointerException();
        }
        String uuidStr = UUID.randomUUID().toString().replace("-", "");
        String fileName = uuidStr + "." + extension;
        return new LocalFile(uuidStr, new File(getLocalFileChildFolder(uuidStr, true), fileName));
    }

    /**
     * 获取本地文件
     *
     * @param fileKey 文件存储 KEY
     * @return 返回 KEY 表示的本地文件
     */
    public static File getLocalFile(String fileKey) {
        File childFoloder = getLocalFileChildFolder(fileKey, false);
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
     * 访问本地文件, 响应到客户端
     *
     * @param response HttpServletResponse
     * @param fileKey  表示本地文件的 Key
     */
    public static void accessLocalFile(HttpServletResponse response, String fileKey) {
        File file = getLocalFile(fileKey);
        if (file == null) {
            throw new LocalFileNotFoundException("找不到 Key 表示的文件: " + fileKey);
        }
        response.setContentType(getContentType(getFileExtension(file)));
        try {
            writeInputStreamToOutputStream(getInputStream(file), response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param file 文件对象
     * @return 返回文件扩展名, eg: 'jpg', 'png'
     */
    public static String getFileExtension(File file) {
        return getFileExtension(file.getName());
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

    /**
     * 获取类路径(classpath)下的文件
     *
     * @param pathname 文件相对于类路径下的路径名, 如: "com/path/file.pdf"
     * @return 返回 classpath 目录下由 pathname 指定的路径的文件对象
     */
    public static File getClassPathFile(String pathname) {
        return new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + pathname);
    }

    /**
     * 获取类路径(classpath)下的文件输入流
     *
     * @param pathname 文件相对于类路径下的路径名, 如: "com/path/file.pdf"
     * @return 返回 classpath 目录下由 pathname 指定的路径的文件输入流对象
     */
    public static InputStream getClassPathFileStream(String pathname) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(pathname);
    }

    /**
     * 获取文件输入流对象
     *
     * @param file 操作的文件对象
     * @return 返回文件输入流对象
     */
    public static InputStream getInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 获取文件输入流对象
     *
     * @param pathname 文件路径的名称
     * @return 返回文件输入流对象
     */
    public static InputStream getInputStream(String pathname) {
        try {
            return new FileInputStream(pathname);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 获取文件输出流对象
     *
     * @param file 操作的文件文件
     * @return 返回文件输出流对象
     */
    public static OutputStream getOutputStream(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 获取文件输出流对象
     *
     * @param pathname 文件路径的名称
     * @return 返回文件输出流对象
     */
    public static OutputStream getOutputStream(String pathname) {
        try {
            return new FileOutputStream(pathname);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 获取文件输出流对象
     *
     * @param file   操作的文件文件
     * @param append 是否追加到文件, true: 追加, false: 覆盖
     * @return 返回文件输出流对象
     */
    public static OutputStream getOutputStream(File file, boolean append) {
        try {
            return new FileOutputStream(file, append);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 获取文件输出流对象
     *
     * @param pathname 文件路径的名称
     * @param append   是否追加到文件, true: 追加, false: 覆盖
     * @return 返回文件输出流对象
     */
    public static OutputStream getOutputStream(String pathname, boolean append) {
        try {
            return new FileOutputStream(pathname, append);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 逐行读取输入流内容
     *
     * @param inputStream 输入流
     * @param consumer    每行的字符内容
     */
    static void readStreamLineByLine(InputStream inputStream, Consumer<String> consumer) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, CHARSET_UTF8))) {
            String read;
            while ((read = reader.readLine()) != null) {
                consumer.accept(read);
            }
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 读取流的内容
     *
     * @param inputStream 输入流对象
     * @return 返回字符串内容
     */
    static String readStreamAsString(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        readStreamLineByLine(inputStream, line -> builder.append(line).append("\n"));
        int length = builder.length();
        return length > 0 ? builder.toString().substring(0, length - 1) : "";
    }

    /**
     * 将输入流写到输出流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     */
    static void writeInputStreamToOutputStream(InputStream inputStream, OutputStream outputStream) {
        try (BufferedInputStream bis = new BufferedInputStream(inputStream);
             BufferedOutputStream bos = new BufferedOutputStream(outputStream)) {
            int read;
            byte[] buffer = new byte[512 * 1024];
            while ((read = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 写文件
     *
     * @param file   操作的文件对象
     * @param append 是否追加到文件. true: 追加到文件, false: 覆盖文件
     * @param text   写出的字符串内容
     */
    private static void writeFile(File file, boolean append, String text) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append)))) {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * Base64 编码图片输入流
     *
     * @param inputStream InputStream
     * @param extension   图片文件的扩展名
     * @return 返回编码的字符串
     */
    private static String encodeImageStreamBase64(InputStream inputStream, String extension) {
        String dataUriScheme = EXTENSION_DATA_URI_SCHEME.get(extension.toLowerCase());
        if (dataUriScheme == null) {
            throw new Base64EncodeImageException("不支持使用 Base64 编码的图片类型: " + extension);
        }
        try (BufferedInputStream bis = new BufferedInputStream(inputStream);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int read;
            byte[] buffer = new byte[512 * 1024];
            while ((read = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }
            return dataUriScheme + new String(Base64.getEncoder().encode(baos.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 获取本地文件的子文件夹目录
     *
     * @param fileKey 表示本地文件的 Key
     * @param create  目录不存在时是否创建
     * @return 返回子文件夹目录
     */
    private static File getLocalFileChildFolder(String fileKey, boolean create) {
        if (fileKey != null && fileKey.length() > LocalFileUploadConfig.childFolderLength) {
            String childFolderName = fileKey.substring(0, LocalFileUploadConfig.childFolderLength);
            File childFoloder = new File(LocalFileUploadConfig.storageRootFolder, childFolderName);
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
    private static String getContentType(String extension) {
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