package com.project_one_functional_tests.utils;

import java.io.IOException;

public class ResetDatabase {
    private static final String serverApiUrl = System.getProperty("serverApiUrl", "http://localhost:8080/api");

    public static void run() {
        try {
            HttpUtil.sendPostRequest(
                    serverApiUrl + "/testing/reset");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void clear() {
        try {
            HttpUtil.sendPostRequest(
                    serverApiUrl + "/testing/clear");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void seed() {
        try {
            HttpUtil.sendPostRequest(
                    serverApiUrl + "/testing/seed");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
