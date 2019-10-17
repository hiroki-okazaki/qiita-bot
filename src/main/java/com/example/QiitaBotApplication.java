package com.example;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.controller.PushConfirmController;
import com.example.service.LineService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
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
    
    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws URISyntaxException, InterruptedException, ExecutionException {
        System.out.println("event: ");
        System.out.println(event.getSource().getUserId());
        		
        return new TextMessage(lineservice.createResponseMessage(event.getMessage().getText()));
        }
    
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Tokyo")
    public void doSomething() {
    	System.out.println("cron呼ばれてる");
    	pushMessage("test");
    }

    @EventMapping
    public void defaultMessageEvent(Event event) {
      System.out.println("event: " + event);
    }
    
    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
    
    //指定したユーザーにメッセージを送信するメソッド
    public void pushMessage() {
        String userId = "Udd89ec41ae851f75bc33dc4c331d56fb";
        Logger log = LoggerFactory.getLogger(QiitaBotApplication.class);
        
            try {
            	BotApiResponse apiResponse = lineMessagingClient.pushMessage(new PushMessage(userId, new TextMessage("こんにちは"))).get();
                log.info("Sent messages: {}", apiResponse);
            } catch (Exception e) {
            	System.out.println(e);
                // 送信先ID消失によるエラーの可能性があるため、IDを削除したのちcontinueする
            }
        }
    
    public void pushMessage(String challenge) {
        String userId = "Udd89ec41ae851f75bc33dc4c331d56fb";
        Logger log = LoggerFactory.getLogger(QiitaBotApplication.class);
        
            try {
            	BotApiResponse apiResponse = lineMessagingClient.pushMessage(new PushMessage(userId, new TemplateMessage("明日は燃えるごみの日だよ！",
                        new ConfirmTemplate("ごみ捨ては終わった？",
                                new MessageAction("はい", "はい"),
                                new MessageAction("いいえ", "いいえ")
                        )
                ))).get();
                log.info("Sent messages: {}", apiResponse);
            } catch (Exception e) {
            	System.out.println(e);
                // 送信先ID消失によるエラーの可能性があるため、IDを削除したのちcontinueする
            }
        }
    
}
