package cn.ictt.zhanghui.eureka_consumer.service;

import cn.ictt.zhanghui.eureka_consumer.pojo.User;
import cn.ictt.zhanghui.eureka_consumer.service.impl.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "eureka-provider", fallback = UserServiceImpl.class)
public interface UserService {
    @GetMapping("/user")
    List<User> getUsers();
}
