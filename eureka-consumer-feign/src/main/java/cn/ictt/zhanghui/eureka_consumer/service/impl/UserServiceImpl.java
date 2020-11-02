package cn.ictt.zhanghui.eureka_consumer.service.impl;

import cn.ictt.zhanghui.eureka_consumer.pojo.User;
import cn.ictt.zhanghui.eureka_consumer.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: ZhangHui
 * @description: 这是UserService的实现类
 * @date: 2019/6/17
 */
@Component
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getUsers() {
        return null;
    }
}
