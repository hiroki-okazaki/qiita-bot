package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.repository.UserRepository;

@Controller
@RequestMapping("")
public class TestController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PushConfirmController pushConfirmController;
	
	
	
	@RequestMapping("/")
	public String test(Model model) throws Exception {
		List<User> userList = userRepository.findAll();
		
		model.addAttribute("user",userList);
		
		User user = userRepository.findByUserId(userList.get(0).getUserId());
		
		model.addAttribute("serchUser", user);
		
//    	String html = pushConfirmController.loadHtml();
//        System.out.println(html);
		
//		List<String> html = pushConfirmController.read("https://qiita.com","UTF-8");
//		System.out.println(html.size());
		
		String html = pushConfirmController.test();
    	model.addAttribute("strHtml", html);
		
		return "index";
	}
}
