package com.example.testtoy.config;

import com.example.testtoy.reqclass.UpdateDBJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    // 1. JobDetail 정의
    @Bean
    public JobDetail updateDBJobDetail() {
        return JobBuilder.newJob(UpdateDBJob.class) // UpdateDBJob은 작업을 정의한 클래스
                .withIdentity("updateDBJob") // Job의 이름
                .storeDurably() // Durable Job으로 설정 (트리거 없이도 유지)
                .build();
    }

    // 2. Trigger 정의 (매일 00:05 실행)
    @Bean
    public Trigger updateDBTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(updateDBJobDetail()) // 연결된 JobDetail
                .withIdentity("updateDBTrigger") // Trigger 이름
                .withSchedule(CronScheduleBuilder.cronSchedule("0 5 * * * ?")) // 매시 05분
                .build();
    }
}
