package com.controller;

import com.task.flowable.Trigger1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@EnableTransactionManagement
@RequestMapping("/business/task")
public class TaskController {
    @Autowired
    private Trigger1 trigger1;

    @PostMapping("/start")
    public void start() {
        trigger1.triggerStart();
    }

    @GetMapping("/status")
    public void status() {

    }

    @PostMapping("/stop")
    public void stop() {
        trigger1.triggerStop();
    }

    @PostMapping("/restart")
    public void restart() {
        trigger1.triggerRetart();
    }

}
