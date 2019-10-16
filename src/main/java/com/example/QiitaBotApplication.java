package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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

    // LINEサービス
    @Autowired
    private LineService lineService;
	
    public static void main(String[] args) {
        SpringApplication.run(QiitaBotApplication.class, args);
    }

//    @EventMapping
//    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
//        System.out.println("event: " + event);
//        return new TextMessage(event.getMessage().getText());
//    }
    
    @EventMapping
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Tokyo")
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        System.out.println("event: " + event);
//        return new TextMessage(lineService.createResponseMessage(event.getMessage().getText()));
        return new TextMessage(lineService.createResponseMessage());
    }
    
//    @EventMapping
//    @Scheduled(cron = "0 * * * * *", zone = "Asia/Tokyo")
//    public TextMessage handleTextMessageEvent() {
//        System.out.println("event: ");
////        return new TextMessage(lineService.createResponseMessage(event.getMessage().getText()));
//        return new TextMessage(lineService.createResponseMessage());
//    }
    
    
    public void doSomething() {
      //...
    }
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
