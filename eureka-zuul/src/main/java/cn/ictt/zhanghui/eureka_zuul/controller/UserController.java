package cn.ictt.zhanghui.eureka_zuul.controller;

import java.util.ArrayList;
import java.util.List;

import cn.ictt.zhanghui.eureka_zuul.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Value("${server.port}")
    private String port;

    @RequestMapping("/user")
	public List<User> getUsers(){
		System.out.println("i am from port:" + port);
		List<User> userList = new ArrayList<User>();
		userList.add(new User(0,"张辉",16));
		userList.add(new User(1,"张三",18));
		userList.add(new User(2,"张麻子",23));
		return userList;
	}
}
