//package com.example.service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//import com.linecorp.bot.model.event.Event;
//import com.linecorp.bot.model.event.message.TextMessageContent;
//import com.linecorp.bot.model.message.StickerMessage;
//import com.linecorp.bot.model.message.TextMessage;
//
//public class WebhookService {
//
//	public void handleTextContent(String replyToken, Event event, TextMessageContent content) throws Exception {
//		// 入力されたテキストを取得
//		String text = content.getText();
////		log.info("Got text message from {}: {}", replyToken, text);
//
//		switch (text) {
//		case "はい": {
//			List<Map.Entry<String, String>> stickyList = new ArrayList<>(goodStickyList().entries());
//			int stickyIndex = getIndex(stickyList.size());
//			this.reply(replyToken, Arrays.asList(new StickerMessage("1", "13"), new TextMessage("さっすがー！")));
//			break;
//		}
//		case "いいえ": {
//			List<Map.Entry<String, String>> stickyList = new ArrayList<>(badStickyList().entries());
//			int stickyIndex = getIndex(stickyList.size());
//			this.reply(replyToken, Arrays.asList(new StickerMessage("1", "123"), new TextMessage("捨てにいきましょー！")));
//			break;
//		}
//		default:
////			log.info("Returns echo message {}: {}", replyToken, text);
//			this.replyText(replyToken, text);
//			break;
//		}
//	}
//}