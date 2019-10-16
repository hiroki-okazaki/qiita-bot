package com.example.controller;


import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("")
public class PushConfirmController {

    private LineMessagingClient lineMessagingClient;

    void ConfirmService(LineMessagingClient lineMessagingClient) {
        this.lineMessagingClient = lineMessagingClient;
    }

    //リマインドをプッシュ
    @RequestMapping("")
    public String pushAlarm() throws URISyntaxException {

        try {
            BotApiResponse response = lineMessagingClient
                                            .pushMessage(new PushMessage("/* プッシュしたい人のuserId */",
                                                         new TemplateMessage("明日は燃えるごみの日だよ！",
                                                                 new ConfirmTemplate("ごみ捨ては終わった？",
                                                                         new MessageAction("はい", "はい"),
                                                                         new MessageAction("いいえ", "いいえ")
                                                                 )
                                                         )))
                                            .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }finally {
        	return "/index";
        }
    }
}