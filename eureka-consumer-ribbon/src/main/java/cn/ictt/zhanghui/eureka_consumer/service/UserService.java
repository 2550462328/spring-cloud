package cn.ictt.zhanghui.eureka_consumer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.ictt.zhanghui.eureka_consumer.pojo.User;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @HystrixCommand对该方法创建了熔断器的功能
     * Hystric的阀值是5秒20次
     * @author ZhangHui
     * @date 2019/6/17
     * @return java.util.List<cn.ictt.zhanghui.eureka_consumer.pojo.User>
     */
    @HystrixCommand(fallbackMethod = "defaultError")
    public List<User> getUsers() {
        List<User> users = (List<User>) restTemplate.getForObject("http://eureka-provider/user", List.class);
        return users;
    }

    /**
     * 熔断后替代方法返回值需要和原方法保持一致，否则会报错
     * @author ZhangHui
     * @date 2019/6/17
     * @return java.util.List<cn.ictt.zhanghui.eureka_consumer.pojo.User>
     */
    public List<User> defaultError(){
        return null;
    }
}
