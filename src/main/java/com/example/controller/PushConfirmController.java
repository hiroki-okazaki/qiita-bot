package com.example.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.QiitaBotApplication;
import com.example.domain.User;
import com.example.repository.UserRepository;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.model.response.BotApiResponse;

//@Slf4j
//@Controller
//@RequestMapping("/")

@Component
public class PushConfirmController {

    @Autowired
    private LineMessagingClient lineMessagingClient;
    
    @Autowired
	private UserRepository userRepository;
    
    //指定したユーザーにメッセージを送信するメソッド
	public void pushMessage() {

		List<User> userList = userRepository.findAll();

		for (User user : userList) {
			String userId = user.getUserId();
			Logger log = LoggerFactory.getLogger(QiitaBotApplication.class);

			try {
				BotApiResponse apiResponse = lineMessagingClient
						.pushMessage(new PushMessage(userId, new TextMessage("こんにちは"))).get();
				log.info("Sent messages: {}", apiResponse);
			} catch (Exception e) {
				System.out.println(e);
				// 送信先ID消失によるエラーの可能性があるため、IDを削除したのちcontinueする
			}
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