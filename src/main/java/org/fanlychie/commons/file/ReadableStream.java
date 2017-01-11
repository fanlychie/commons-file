package org.fanlychie.commons.file;

import org.fanlychie.commons.file.exception.RuntimeCastException;
import org.fanlychie.commons.file.util.ReaderBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.function.Consumer;

/**
 * 可读的流
 *
 * Created by fanlychie on 2017/1/11.
 */
public class ReadableStream {

    private Reader reader;

    // 默认使用的字符集编码
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 创建一个可读的流对象
     *
     * @param reader Reader
     */
    public ReadableStream(Reader reader) {
        this.reader = reader;
    }

    /**
     * 创建一个可读的流对象
     *
     * @param file 文件对象
     */
    public ReadableStream(File file) {
        this.reader = ReaderBuilder.buildInputStreamReader(file, CHARSET_UTF8);
    }

    /**
     * 创建一个可读的流对象
     *
     * @param file    文件对象
     * @param charset 字符集编码
     */
    public ReadableStream(File file, String charset) {
        this.reader = ReaderBuilder.buildInputStreamReader(file, charset);
    }

    /**
     * 创建一个可读的流对象
     *
     * @param inputStream InputStream
     */
    public ReadableStream(InputStream inputStream) {
        this.reader = ReaderBuilder.buildInputStreamReader(inputStream, CHARSET_UTF8);
    }

    /**
     * 创建一个可读的流对象
     *
     * @param inputStream InputStream
     * @param charset     字符集编码
     */
    public ReadableStream(InputStream inputStream, String charset) {
        this.reader = ReaderBuilder.buildInputStreamReader(inputStream, charset);
    }

    /**
     * 读取全部内容
     *
     * @param consumer 读取到的全部的文本内容
     */
    public void read(Consumer<String> consumer) {
        consumer.accept(read());
    }

    /**
     * 读取全部内容
     *
     * @return 返回读取到的全部的文本内容
     */
    public String read() {
        StringBuilder builder = new StringBuilder();
        readLineByLine((line) -> builder.append(line).append("\n"));
        return builder.length() > 0 ? builder.toString().substring(0, builder.length() - 1) : "";
    }

    /**
     * 逐行读取
     *
     * @param consumer 每行的内容
     */
    public void readLineByLine(Consumer<String> consumer) {
        BufferedReader bufferedReader;
        if (reader instanceof BufferedReader) {
            bufferedReader = (BufferedReader) reader;
        } else {
            bufferedReader = new BufferedReader(reader);
        }
        String read;
        try {
            while ((read = bufferedReader.readLine()) != null) {
                consumer.accept(read);
            }
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeCastException(e);
                }
            }
        }
    }

}