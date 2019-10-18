package com.example.controller;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@LineMessageHandler
public class WebhookController {

	// テキストが送られてきた際の応答
	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {

		// 入力されたテキストの取得
		String text = event.getMessage().toString();

		switch (text) {
		case "はい": {
			new TextMessage("さっすがー！");
//			this.reply(event.getMessage().getText(), new TextMessage("さっすがー！"));
			break;
		}
		case "いいえ": {
			new TextMessage("早くしなさい");
//			this.reply(replyToken, new TextMessage("捨てにいきましょー！"));
			break;
		}
		default:
			new TextMessage("はいでもいいえでもない");
//			log.info("Returns echo message {}: {}", replyToken, text);
//			this.replyText(replyToken, text);
			break;
		}
	}
}