package com.example.controller;


import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.model.response.BotApiResponse;

//@Slf4j
//@Controller
//@RequestMapping("/")

@Component
public class PushConfirmController {

    private LineMessagingClient lineMessagingClient;

    void ConfirmService(LineMessagingClient lineMessagingClient) {
        this.lineMessagingClient = lineMessagingClient;
    }

    //リマインドをプッシュ
//    @RequestMapping("")
    public String pushAlarm(MessageEvent<TextMessageContent> event) throws URISyntaxException {

        try {
            BotApiResponse response = lineMessagingClient.pushMessage(new PushMessage("Udd89ec41ae851f75bc33dc4c331d56fb",
                                                         new TemplateMessage("明日は燃えるごみの日だよ！",
                                                                 new ConfirmTemplate("ごみ捨ては終わった？",
                                                                         new MessageAction("はい", "はい"),
                                                                         new MessageAction("いいえ", "いいえ")
                                                                 )
                                                         )))
                                            .get();
            	return "/index";
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}