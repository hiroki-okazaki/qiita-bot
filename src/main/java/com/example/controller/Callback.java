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
		System.out.println("3333333333333");
		if (userRepository.findByUserId(userId) == null) {
			User user = new User();
			user.setUserId(userId);
			System.out.println("44444444444");
			user.setRegistrationUrl("https://qiita.com/zakioka_pirori");

			userRepository.insert(user);
		} else {
			System.out.println("登録済み");
		}

	}
}