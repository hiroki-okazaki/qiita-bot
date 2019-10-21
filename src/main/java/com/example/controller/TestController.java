package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.repository.UserRepository;
import com.example.service.PushConfirmService;


@Controller
@RequestMapping("")
public class TestController {

	private String strurl = "https://qiita.com";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PushConfirmController pushConfirmController;
	
	
	
	@RequestMapping("/")
	public String test(Model model) throws Exception {
//		List<User> userList = userRepository.findAll();
//		
//		model.addAttribute("user",userList);
//		
//		User user = userRepository.findByUserId(userList.get(0).getUserId());
//		
//		model.addAttribute("serchUser", user);
//		

//    	model.addAttribute("strHtml", html);
		PushConfirmService pushConfirmService = new PushConfirmService();
		String url = pushConfirmService.selectionJsonData("https://qiita.com");
		
    	model.addAttribute("strHtml", url);
          
		return "index";
	}
}
