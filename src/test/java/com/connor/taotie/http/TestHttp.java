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

    private static int TIMES = 1000;

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
}
