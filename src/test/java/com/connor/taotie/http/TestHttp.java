package com.connor.taotie.http;


import com.connor.taotie.util.HttpUtils;
import com.connor.taotie.util.SkipHttpsUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class TestHttp {

    private static int TIMES = 1000;


    @Test
    public void doRequestURL() throws IOException, NoSuchAlgorithmException, KeyManagementException {

        //doRequest("https://test-b-fat.pingan.com.cn/mp_signature_info_a0331866b5.json");

        //doRequest("https://test-b-fat.pingan.com.cn/mp_signature_info_a0331866b5.json");


        //doRequest("https://14.17.22.56:7029/app/v2.0/wxf_stage_callback.fcgi");


        //doRequestWithOutHttps("https://113.98.240.41/obp/api/gateway.do");
        //doRequest("https://124.74.46.118:7012/business/service");
        doRequest("https://login.cloud.huawei.com/oauth2/v2/token");


        //doRequestWithOutHttps("https://124.74.46.118:7012/business/service");
        //doRequestWithOutHttpsPool("https://124.74.46.118:7012/business/service");
    }


    /**
     * 测试不使用http pool耗时,1000次请求;
     * http://www.baidu.com     25s,25197ms
     * https://www.baidu.com    5m:17s
     * @throws IOException
     */
    @Test
    public void testHttpRequest() throws IOException {
        long start = System.currentTimeMillis();
        for (int time = 0; time < TIMES; time++) {
            CloseableHttpClient closeableHttpClient = HttpUtils.getHttpClientSingle();
            doRequest(closeableHttpClient);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        //25197 25s
    }

    /**
     * 测试不使用http pool耗时,1000次请求;
     * http://www.baidu.com     10s
     * https://www.baidu.com    1m:1s
     * @throws IOException
     */
    @Test
    public void testHttpRequestPool() throws IOException {
        long start = System.currentTimeMillis();
        for (int time = 0; time < TIMES; time++) {
            CloseableHttpClient closeableHttpClient = HttpUtils.getHttpClient();
            doRequest(closeableHttpClient);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        //10543 10s
    }

    private void doRequest(CloseableHttpClient closeableHttpClient) throws IOException {
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);
        InputStream in = httpResponse.getEntity().getContent();
        InputStreamReader read = new InputStreamReader(in, "UTF-8");
        char[] buffer = new char[1024];
        int i = 0;
        while ((i = read.read(buffer, 0, 1024)) != -1) {
            //System.out.println(new String(buffer));
        }
        read.close();
        httpResponse.close();
    }

    private void doRequestWithOutHttpsPool(String url) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        CloseableHttpClient httpClient = createSSLClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream in = httpResponse.getEntity().getContent();
        InputStreamReader read = new InputStreamReader(in, "UTF-8");
        char[] buffer = new char[1024];
        int i = 0;
        while ((i = read.read(buffer, 0, 1024)) != -1) {
            //System.out.println(new String(buffer));
        }
        read.close();

    }

    private CloseableHttpClient createSSLClient() {

        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http",plainSF);
        try{
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            LayeredConnectionSocketFactory sslSF = new SSLSocketFactory(sslContext,null,null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https",sslSF);

            Registry<ConnectionSocketFactory> build = registryBuilder.build();

            PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager(build,null,null,null,-1, TimeUnit.MILLISECONDS);


            return HttpClients.custom().setConnectionManager(pool).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void doRequestWithOutHttps(String url) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        HttpClient httpClient = SkipHttpsUtil.wrapClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream in = httpResponse.getEntity().getContent();
        InputStreamReader read = new InputStreamReader(in, "UTF-8");
        char[] buffer = new char[1024];
        int i = 0;
        while ((i = read.read(buffer, 0, 1024)) != -1) {
            //System.out.println(new String(buffer));
        }
        read.close();

    }

    private void doRequest(String url) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpUtils.getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);
        InputStream in = httpResponse.getEntity().getContent();
        InputStreamReader read = new InputStreamReader(in, "UTF-8");
        char[] buffer = new char[1024];
        int i = 0;
        while ((i = read.read(buffer, 0, 1024)) != -1) {
            //System.out.println(new String(buffer));
        }
        read.close();
        httpResponse.close();
    }
}
