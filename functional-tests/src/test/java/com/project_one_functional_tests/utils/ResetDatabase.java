package com.project_one_functional_tests.utils;

import java.io.IOException;

public class ResetDatabase {
    public static void run() {
        try {
            HttpUtil.sendPostRequest(
                    "http://localhost:8080/api/testing/reset");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
