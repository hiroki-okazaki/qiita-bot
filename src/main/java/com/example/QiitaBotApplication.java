package com.example;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.controller.Callback;
import com.example.controller.PushConfirmController;
import com.example.service.LineService;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@LineMessageHandler
@EnableScheduling
public class QiitaBotApplication {

	@Autowired
    private LineService lineservice;
	
    // LINEサービス
    @Autowired
    private PushConfirmController pushConfirmController;
    
    //オウム返し、ユーザーidの登録を行うコントローラー
    @Autowired
    private Callback callback;
    
//    @Autowired
//    private LineMessagingClient lineMessagingClient;
	
    public static void main(String[] args) {
        SpringApplication.run(QiitaBotApplication.class, args);
    }
    
    //オウム返しを行うメソッド
    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws URISyntaxException, InterruptedException, ExecutionException {
    	callback.registrationUser(event);
        TextMessage text = new TextMessage(lineservice.createResponseMessage());
        return text;
        }
    
    @Scheduled(cron = "0 0 8,18 * * *", zone = "Asia/Tokyo")
    public void doSomething() throws Exception {
    	pushConfirmController.pushMessage();
    }

//    @EventMapping
//    public void defaultMessageEvent(Event event) {
//      System.out.println("event: " + event);
//    }
//    
//    @EventMapping
//    public void handleDefaultMessageEvent(Event event) {
//        System.out.println("event: " + event);
//    }
    
}
