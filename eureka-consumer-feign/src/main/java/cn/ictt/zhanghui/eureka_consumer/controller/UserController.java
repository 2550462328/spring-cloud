package cn.ictt.zhanghui.eureka_consumer.controller;

import java.util.List;

import cn.ictt.zhanghui.eureka_consumer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ictt.zhanghui.eureka_consumer.pojo.User;

@RestController
@Slf4j
public class UserController {
	@Autowired 
	private UserService userService;

	@GetMapping("/consumer")
	public List<User> getUsers(){
		log.info("hello tourist  here is consumer-b");
		return this.userService.getUsers();
	}
}
