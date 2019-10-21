package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.domain.User;
import com.example.repository.UserRepository;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

@Component
public class Callback {

	@Autowired
	private UserRepository userRepository;

	public void registrationUser(MessageEvent<TextMessageContent> event) {
		String userId = event.getSource().getUserId();

		//ユーザーからメッセージが送られてきた時、ユーザーのidが登録されていない時に行う処理
		if (userRepository.findByUserId(userId) == null) {
			User user = new User();
			user.setUserId(userId);
			user.setRegistrationUrl("https://qiita.com");

			//ユーザーidとキータのトップページを登録する
			userRepository.insert(user);
		} else {
			System.out.println("登録済み");
		}

	}
}