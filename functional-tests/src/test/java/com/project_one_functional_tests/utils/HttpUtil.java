package com.project_one_functional_tests.utils;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class HttpUtil {

    public static void sendPostRequest(String url) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            try (CloseableHttpResponse response = client.execute(post)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    EntityUtils.consume(entity);
                }
            }
        }
    }
}
