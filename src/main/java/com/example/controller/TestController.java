package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.JsonData;
import com.example.repository.UserRepository;


@Controller
@RequestMapping("")
public class TestController {

	private String strurl = "https://qiita.com";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PushConfirmController pushConfirmController;
	
	
	
	@RequestMapping("/")
	public String test() throws Exception {
//		List<User> userList = userRepository.findAll();
//		
//		model.addAttribute("user",userList);
//		
//		User user = userRepository.findByUserId(userList.get(0).getUserId());
//		
//		model.addAttribute("serchUser", user);
//		

//    	model.addAttribute("strHtml", html);

          
		return "index";
	}
}
