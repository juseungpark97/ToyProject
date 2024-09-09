package com.js.bookforum.config;

import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Configuration
public class EnvConfig {

    @PostConstruct
    public void setSystemProperties() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        // 환경 변수를 시스템 속성으로 설정
        System.setProperty("AWS_ACCESS_KEY_ID", dotenv.get("AWS_ACCESS_KEY_ID"));
        System.setProperty("AWS_SECRET_ACCESS_KEY", dotenv.get("AWS_SECRET_ACCESS_KEY"));
        System.setProperty("AWS_S3_BUCKET_NAME", dotenv.get("AWS_S3_BUCKET_NAME"));
        System.setProperty("AWS_REGION", dotenv.get("AWS_REGION"));
    }
}