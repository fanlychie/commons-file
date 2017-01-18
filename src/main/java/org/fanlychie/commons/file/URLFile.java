package org.fanlychie.commons.file;

import org.fanlychie.commons.file.exception.RuntimeCastException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * URL 表示的文件
 * Created by fanlychie on 2017/1/12.
 */
public class URLFile {

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
     * 创建一个 URL 表示的文件
     *
     * @param url 超链接地址
     */
    public URLFile(String url) {
        this.url = url;
    }

    /**
     * 设置读取超时时间, 默认为 3分钟
     *
     * @param readTimeout 超时时间, 毫秒单位
     * @return
     */
    public URLFile setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * 设置连接超时时间, 默认为 30秒
     *
     * @param connectTimeout 超时时间, 毫秒单位
     * @return
     */
    public URLFile setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * 读取字符串内容
     *
     * @return 返回字符串内容
     */
    public String readString() {
        return FileUtils.readStreamAsString(getInputStream());
    }

    /**
     * 写到文件
     *
     * @param file 目标文件
     */
    public void writeToFile(File file) {
        FileUtils.writeInputStreamToOutputStream(getInputStream(), FileUtils.getOutputStream(file));
    }

    /**
     * 写到文件
     *
     * @param pathname 目标文件路径名称
     */
    public void writeToFile(String pathname) {
        FileUtils.writeInputStreamToOutputStream(getInputStream(), FileUtils.getOutputStream(pathname));
    }

    /**
     * 写到目录
     *
     * @param dir 文件目录
     */
    public void writeToDirectory(File dir) {
        FileUtils.writeInputStreamToOutputStream(getInputStream(), FileUtils.getOutputStream(new File(dir, FileUtils.getUrlFileName(url))));
    }

    /**
     * 写到目录
     *
     * @param pathname 文件目录路径名称
     */
    public void writeToDirectory(String pathname) {
        FileUtils.writeInputStreamToOutputStream(getInputStream(), FileUtils.getOutputStream(new File(pathname, FileUtils.getUrlFileName(url))));
    }

    /**
     * 获取输入流对象
     *
     * @return 返回 URL 表示的 InputStream 对象
     */
    public InputStream getInputStream() {
        try {
            URL source = new URL(url);
            HttpURLConnection conn = null;
            if (source.getProtocol().toLowerCase().equals("https")) {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }}, null);
                conn = (HttpsURLConnection) source.openConnection();
                ((HttpsURLConnection) conn).setSSLSocketFactory(sslContext.getSocketFactory());
            } else {
                conn = (HttpURLConnection) source.openConnection();
            }
            conn.setReadTimeout(readTimeout);
            conn.setConnectTimeout(connectTimeout);
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0");
            return conn.getInputStream();
        } catch (Throwable e) {
            throw new RuntimeCastException(e);
        }
    }

}