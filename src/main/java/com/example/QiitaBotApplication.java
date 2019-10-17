package com.example;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.controller.PushConfirmController;
import com.example.service.LineService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
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
    
    @Autowired
    private LineMessagingClient lineMessagingClient;
	
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
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws URISyntaxException, InterruptedException, ExecutionException {
        System.out.println("event: ");
        System.out.println(event.getSource().getUserId());
        		
//        BotApiResponse response = lineMessagingClient.pushMessage((new PushMessage(event.getReplyToken(),
//                new TemplateMessage("明日は燃えるごみの日だよ！",new ConfirmTemplate("ごみ捨ては終わった？",
//                                                                                new MessageAction("はい", "はい"),
//                                                                                new MessageAction("いいえ", "いいえ")
//                                                                          ) ))) ).get();
        pushMessage(event);
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
    
//    @Scheduled(cron = "0 * * * * *", zone = "Asia/Tokyo")
    public void pushMessage(MessageEvent<TextMessageContent> event) {
        String userId = "Udd89ec41ae851f75bc33dc4c331d56fb";
        Logger log = LoggerFactory.getLogger(QiitaBotApplication.class);
        
            try {
            	System.out.println("処理実行");
            	BotApiResponse apiResponse = lineMessagingClient.pushMessage(new PushMessage(event.getSource().getUserId(), new TextMessage("こんにちは"))).get();
                System.out.println("成功");
                log.info("Sent messages: {}", apiResponse);
            } catch (Exception e) {
            	System.out.println("失敗");
            	System.out.println(e);
                // 送信先ID消失によるエラーの可能性があるため、IDを削除したのちcontinueする
            }
        }
}
