package com.connor.taotie.http;


import com.connor.taotie.util.HttpUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestHttp {

    private static int TIMES = 1;


    /**
     * 测试请求自签名HTTPS网站.
     * 本地需要先部署NG服务器,配置自签名https证书和key.
     */
    @Test
    public void testHttps(){

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
    }

    private void doRequest(CloseableHttpClient closeableHttpClient) throws IOException {
        HttpGet httpGet = new HttpGet("https://localhost:8888");//https://localhost:8888 https://www.baidu.com
        //httpGet.setConfig();
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
