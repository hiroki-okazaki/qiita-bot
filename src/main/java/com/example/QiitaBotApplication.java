package com.example;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.controller.PushConfirmController;
import com.example.service.LineService;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@SpringBootApplication
@LineMessageHandler
@EnableScheduling
public class QiitaBotApplication {

	@Autowired
    private LineService lineservice;
	
    // LINEサービス
    @Autowired
    private PushConfirmController pushConfirmController;
	
    public static void main(String[] args) {
        SpringApplication.run(QiitaBotApplication.class, args);
    }

//    @EventMapping
//    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
//        System.out.println("event: " + event);
//        return new TextMessage(event.getMessage().getText());
//    }
    
//    @EventMapping
//    @Scheduled(cron = "0 * * * * *", zone = "Asia/Tokyo")
//    public TextMessage handleTextMessage(MessageEvent<TextMessageContent> event) {
//        System.out.println("event: " + event);
//        return new TextMessage(lineService.createResponseMessage(event.getMessage().getText()));
////        return new TextMessage(lineService.createResponseMessage());
//    }
    
    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws URISyntaxException {
        System.out.println("event: ");
        System.out.println(event.getSource().getUserId());
        pushConfirmController.pushAlarm(event);
        return new TextMessage(lineservice.createResponseMessage(event.getMessage().getText()));
    }
    
//    @Scheduled(cron = "0 * * * * *", zone = "Asia/Tokyo")
//    public TextMessage doSomething() {
//    	System.out.println("cron呼ばれてる");
//    	return new TextMessage("cron成功");
//    }
//    
//    @EventMapping
//    @Scheduled(initialDelay = 60000, fixedRate = 5000)
//    public TextMessage doSomething() {
//    	return new TextMessage(lineService.createResponseMessage());
//    }
    
    
//    @Autowired
//    private LineMessagingService lineMessagingService;

//    @EventMapping
//    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
//      System.out.println("event: " + event);
//      final BotApiResponse apiResponse = lineMessagingService
//          .replyMessage(new ReplyMessage(event.getReplyToken(),
//                                         Collections.singletonList(new TextMessage(event.getSource().getUserId()))))
//          .execute().body();
//      System.out.println("Sent messages: " + apiResponse);
//    }

    @EventMapping
    public void defaultMessageEvent(Event event) {
      System.out.println("event: " + event);
    }
    
    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
}
