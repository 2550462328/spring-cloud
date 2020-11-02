package cn.ictt.zhanghui.eureka_provider.controller;

import java.util.List;

import org.mengyun.tcctransaction.api.TransactionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import brave.sampler.Sampler;
import cn.ictt.zhanghui.eureka_common.pojo.BaseInput;
import cn.ictt.zhanghui.eureka_provider.pojo.User;
import cn.ictt.zhanghui.eureka_provider.service.UserService;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class UserController {
    @Value("${server.port}")
    private String port;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/initPay")
    public String initPay(){
        return restTemplate.getForObject("http://localhost:8663/payApi",String.class);
    }

    @GetMapping("/user")
    @HystrixCommand(fallbackMethod = "defaultError")
    public List<User> getUsers() {
        System.out.println("i am from port:" + port);
        return userService.getUsers();
    }

    @RequestMapping("/addUser")
    public String addUser(){

        User user = new User(1,"zhanghui",18,"hi i am zhanghui", 1);
        userService.doSave(user);

        return "successful!";
    }

    /**
     * getUsers接口超时返回的结果
     */
    public List<User> defaultError(){
        return null;
    }

    /**
     * 设置断路器断路模式
     * @author ZhangHui
     * @date 2019/11/19
     * @return brave.sampler.Sampler
     */
    @Bean
    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }
}
