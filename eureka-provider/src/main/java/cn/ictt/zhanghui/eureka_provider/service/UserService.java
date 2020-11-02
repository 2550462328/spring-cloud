package cn.ictt.zhanghui.eureka_provider.service;

import java.util.List;

import cn.ictt.zhanghui.eureka_provider.pojo.User;


public interface UserService {
    List<User> getUsers();

    void doSave(User user);

    void confirmMethod(User user);

    void cancelMethod(User user);
}
