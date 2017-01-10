package org.fanlychie.commons.file;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 可写的流
 *
 * Created by fanlychie on 2017/1/10.
 */
public class WritableStream {

    private Reader reader;

    // 字符
    private static final char[] BUFFERB = new char[65535];

    /**
     * 创建一个可写的流对象
     *
     * @param reader Reader
     */
    public WritableStream(Reader reader) {
        this.reader = reader;
    }

    /**
     * 创建一个可写的流对象
     *
     * @param inputStream InputStream
     * @param charset     字符集编码
     * @throws UnsupportedEncodingException
     */
    public WritableStream(InputStream inputStream, String charset) throws UnsupportedEncodingException {
        this.reader = new InputStreamReader(inputStream, charset);
    }

    /**
     * 写出到目标文件
     *
     * @param dest 目标文件
     */
    public void toFile(File dest) {
        toFile(dest, false);
    }

    /**
     * 追加到目标文件
     *
     * @param dest 目标文件
     */
    public void toAppendFile(File dest) {
        toFile(dest, true);
    }

    /**
     * 写出到输出流对象
     *
     * @param outputStream OutputStream
     */
    public void toOutputStream(OutputStream outputStream) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            if (reader instanceof BufferedReader) {
                bufferedReader = (BufferedReader) reader;
            } else {
                bufferedReader = new BufferedReader(reader);
            }
            int read;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            while ((read = bufferedReader.read(BUFFERB)) != -1) {
                bufferedWriter.write(BUFFERB, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
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
        try (OutputStream os = response.getOutputStream()) {
            filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
            response.setContentType("application/octet-stream; charset=iso-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            toOutputStream(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 写出到目标文件
     *
     * @param dest   目标文件
     * @param append true : 追加到文件末尾, false : 覆盖原文件
     */
    private void toFile(File dest, boolean append) {
        try (OutputStream os = new FileOutputStream(dest, append)) {
            toOutputStream(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
