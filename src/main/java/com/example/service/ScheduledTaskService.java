package com.example.service;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.controller.PushConfirmController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduledTaskService {

	@Autowired
	private PushConfirmController pushConfirmController;
	
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Tokyo")
    public void executeAlarm() {
        try {
            //プッシュする処理を呼び出す
        	pushConfirmController.pushAlarm();
        } catch (URISyntaxException e) {
//            log.error("{}", e);
        }
    }
}