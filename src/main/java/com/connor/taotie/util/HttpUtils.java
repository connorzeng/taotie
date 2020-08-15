package com.connor.taotie.util;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * HTTP请求管理类
 */
public class HttpUtils {

    private static PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();

    private static ConnectionConfig connectionConfig = null;

    static {
        manager.setMaxTotal(400);
        manager.setDefaultMaxPerRoute(20);
        connectionConfig = ConnectionConfig.custom()

                .setCharset(Charset.forName("UTF-8")).build();
    }

    /**
     * 获取连接,设置连接池
     *
     * @return
     */
    public static CloseableHttpClient getHttpClient() {

        return HttpClients.custom().setDefaultConnectionConfig(connectionConfig)
                .setConnectionManager(manager)
                .setRetryHandler(new SisyphusHandler())
                .build();
    }

    /**
     * 获取一个没有SSL验证的HTTP client
     *
     * @return
     */
    public static CloseableHttpClient getHttpClientNoSSL() throws NoSuchAlgorithmException, KeyManagementException {

        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        X509TrustManager tm = new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] arg0,
                                           String arg1) throws CertificateException {
            }
            public void checkServerTrusted(X509Certificate[] arg0,
                                           String arg1) throws CertificateException {
            }
        };


        ctx.init(null, new TrustManager[] { tm }, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

        return HttpClients.custom().setDefaultConnectionConfig(connectionConfig)
                .setConnectionManager(manager)
                .setRetryHandler(new SisyphusHandler())
                .setSSLSocketFactory(new SSLConnectionSocketFactory(
                        ctx, NoopHostnameVerifier.INSTANCE))
                .build();
    }

    /**
     * 获取连接
     *
     * @return
     */
    public static CloseableHttpClient getHttpClientSingle() {
        return HttpClients.custom().setDefaultConnectionConfig(connectionConfig)
                .build();
    }

    /**
     * 重试handler<br>
     * 西西弗斯处理器
     */
    private static class SisyphusHandler implements HttpRequestRetryHandler {
        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            //重试三次放弃
            if (executionCount > 3) {
                return false;
            }
            return true;
        }
    }
}
