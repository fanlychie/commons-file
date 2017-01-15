package org.fanlychie.commons.file;

import org.fanlychie.commons.file.util.InputStreamBuilder;

/**
 * Http URL 流
 * Created by fanlychie on 2017/1/12.
 */
public class HttpURLStream {

    /**
     * 超链接地址
     */
    private String url;

    /**
     * 读取超时时间, 默认3分钟
     */
    private int readTimeout = 3 * 60 * 1000;

    /**
     * 连接超时时间, 默认30秒
     */
    private int connectTimeout = 30 * 1000;

    /**
     * Http URL 流
     *
     * @param url 超链接地址
     */
    public HttpURLStream(String url) {
        this.url = url;
    }

    /**
     * 设置读取超时时间, 默认为 3分钟
     *
     * @param readTimeout 超时时间, 毫秒单位
     * @return
     */
    public HttpURLStream setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * 设置连接超时时间, 默认为 30秒
     *
     * @param connectTimeout 超时时间, 毫秒单位
     * @return
     */
    public HttpURLStream setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * 转化为可读的流
     *
     * @return 返回可读的流对象
     */
    public ReadableStream turnToReadableStream() {
        return new ReadableStream(InputStreamBuilder.buildHpptUrlInputStream(url, readTimeout, connectTimeout));
    }

    /**
     * 转化为可写的流
     *
     * @return 返回可写的流对象
     */
    public WritableStream turnToWritableStream() {
        return new WritableStream(InputStreamBuilder.buildHpptUrlInputStream(url, readTimeout, connectTimeout));
    }

}