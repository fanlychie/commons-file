package org.fanlychie.commons.file;

import org.fanlychie.commons.file.exception.RuntimeCastException;
import org.fanlychie.commons.file.util.InputStreamBuilder;
import org.fanlychie.commons.file.util.OutputStreamBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 可写的流
 *
 * Created by fanlychie on 2017/1/10.
 */
public class WritableStream {

    private String content;

    private BufferedInputStream bufferedInputStream;

    // 512KB
    private static final byte[] BUFFER = new byte[512 * 1028];

    /**
     * 创建一个可写的流对象
     *
     * @param str 字符串内容
     */
    public WritableStream(String str) {
        this.content = str;
    }

    /**
     * 创建一个可写的流对象
     *
     * @param src 源文件对象
     */
    public WritableStream(File src) {
        this.bufferedInputStream = InputStreamBuilder.buildBufferedInputStream(src);
    }

    /**
     * 创建一个可写的流对象
     *
     * @param inputStream InputStream
     */
    public WritableStream(InputStream inputStream) {
        this.bufferedInputStream = InputStreamBuilder.buildBufferedInputStream(inputStream);
    }

    /**
     * 写出到目标文件
     *
     * @param dest 目标文件
     */
    public void toFile(File dest) {
        toOutputStream(OutputStreamBuilder.buildFileOutputStream(dest));
    }

    /**
     * 写出到目标文件
     *
     * @param fileName 文件绝对路径的名称
     */
    public void toFile(String fileName) {
        toOutputStream(OutputStreamBuilder.buildFileOutputStream(fileName));
    }

    /**
     * 追加到目标文件
     *
     * @param dest 目标文件
     */
    public void toAppendFile(File dest) {
        toOutputStream(OutputStreamBuilder.buildFileOutputStream(dest, true));
    }

    /**
     * 追加到目标文件
     *
     * @param fileName 文件绝对路径的名称
     */
    public void toAppendFile(String fileName) {
        toOutputStream(OutputStreamBuilder.buildFileOutputStream(fileName, true));
    }

    /**
     * 写出到输出流对象
     *
     * @param outputStream OutputStream
     */
    public void toOutputStream(OutputStream outputStream) {
        if (content != null && bufferedInputStream == null) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                bufferedWriter.write(content);
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeCastException(e);
            }
            return ;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            if (outputStream instanceof BufferedOutputStream) {
                bufferedOutputStream = (BufferedOutputStream) outputStream;
            } else {
                bufferedOutputStream = new BufferedOutputStream(outputStream);
            }
            int read;
            while ((read = bufferedInputStream.read(BUFFER)) != -1) {
                bufferedOutputStream.write(BUFFER, 0, read);
            }
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
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeCastException(e);
                }
            }
        }
    }

    /**
     * 写出到响应, 以供客户端浏览器下载
     *
     * @param response HttpServletResponse
     * @param filename 客户端下载文件的名称
     */
    public void toResponse(HttpServletResponse response, String filename) {
        try {
            filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
            response.setContentType("application/octet-stream; charset=iso-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            toOutputStream(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

}
