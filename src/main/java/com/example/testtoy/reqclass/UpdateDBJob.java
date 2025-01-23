package com.example.testtoy.reqclass;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.testtoy.service.*;

@Component
public class UpdateDBJob implements Job {

    @Autowired
    private LostArkScheduleService LostArkScheduleService; // DB 업데이트를 수행할 서비스

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            LostArkScheduleService.updateDatabase(); // DB 업데이트 로직 호출
            System.out.println("DB 업데이트 완료 - " + java.time.LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
            throw new JobExecutionException(e);
        }
    }
}

