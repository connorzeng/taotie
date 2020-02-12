package com.connor.taotie.util;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.nio.charset.Charset;

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
