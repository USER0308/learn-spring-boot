package com.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduleTask {


    @Scheduled(cron = "0 0/1 * * * *")
    public void task() {
      log.info("task start...");
    }
}
