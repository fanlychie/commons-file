package org.fanlychie.commons.file.util;

import org.fanlychie.commons.file.exception.RuntimeCastException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * InputStream 构建器
 * Created by fanlychie on 2017/1/12.
 */
public final class InputStreamBuilder {

    /**
     * 构建 FileInputStream 对象
     *
     * @param file 文件
     * @return 返回 FileInputStream 对象
     */
    public static FileInputStream buildFileInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 FileInputStream 对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回 FileInputStream 对象
     */
    public static FileInputStream buildFileInputStream(String fileName) {
        try {
            return new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedInputStream 对象
     *
     * @param file 文件
     * @return 返回 BufferedInputStream 对象
     */
    public static BufferedInputStream buildBufferedInputStream(File file) {
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedInputStream 对象
     *
     * @param fileName 文件绝对路径的名称
     * @return 返回 BufferedInputStream 对象
     */
    public static BufferedInputStream buildBufferedInputStream(String fileName) {
        try {
            return new BufferedInputStream(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 构建 BufferedInputStream 对象
     *
     * @param inputStream InputStream
     * @return 返回 BufferedInputStream 对象
     */
    public static BufferedInputStream buildBufferedInputStream(InputStream inputStream) {
        return new BufferedInputStream(inputStream);
    }

    /**
     * 构建 InputStream 对象
     *
     * @param url HTTP 链接地址
     * @return 返回 InputStream 对象
     */
    public static InputStream buildHpptUrlInputStream(String url) {
        return buildHpptUrlInputStream(url, 3 * 60 * 1000, 30 * 1000);
    }

    /**
     * 构建 InputStream 对象
     *
     * @param url            HTTP 链接地址
     * @param readTimeout    读取超时时间, 单位毫秒
     * @param connectTimeout 连接超时时间, 单位毫秒
     * @return 返回 InputStream 对象
     */
    public static InputStream buildHpptUrlInputStream(String url, int readTimeout, int connectTimeout) {
        HttpURLConnection conn = null;
        try {
            URL source = new URL(url);
            if (source.getProtocol().toLowerCase().equals("https")) {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
                    @Override
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                }}, null);
                conn = (HttpsURLConnection) source.openConnection();
                ((HttpsURLConnection) conn).setSSLSocketFactory(sslContext.getSocketFactory());
            } else {
                conn = (HttpURLConnection) source.openConnection();
            }
        } catch (Throwable e) {
            throw new RuntimeCastException(e);
        }
        conn.setReadTimeout(readTimeout);
        conn.setConnectTimeout(connectTimeout);
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0");
        try {
            return conn.getInputStream();
        } catch (IOException e) {
            throw new RuntimeCastException(e);
        }
    }

}