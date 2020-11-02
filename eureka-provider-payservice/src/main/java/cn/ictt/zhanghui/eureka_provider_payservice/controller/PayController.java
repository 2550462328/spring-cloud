package cn.ictt.zhanghui.eureka_provider_payservice.controller;

import java.util.List;

import cn.ictt.zhanghui.eureka_provider_payservice.service.UserService;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import brave.sampler.Sampler;
import cn.ictt.zhanghui.eureka_common.pojo.BaseInput;
import cn.ictt.zhanghui.eureka_provider_payservice.pojo.User;
import cn.ictt.zhanghui.eureka_provider_payservice.service.PayService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class PayController {
    @Autowired
    private PayService payService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    /**
     * 设置断路器断路模式
     * @author ZhangHui
     * @date 2019/11/19
     * @return brave.sampler.Sampler
     */
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    /**
     * 测试远程服务调用
     * @author ZhangHui
     * @date 2019/11/19
     * @return java.lang.String
     */

    @GetMapping("/payApi")
    public String payApi() {
        List<User> userList = restTemplate.getForObject("http://localhost:8662/user", List.class);
        if (userList != null && userList.size() > 0) {
            payService.executePay();
            return "execute success!";
        }
        return "execute failure";
    }

    /**
     * 测试本地事务
     * @author ZhangHui
     * @date 2019/11/20
     * @return java.lang.String
     */
    @GetMapping("/testTrans")
    public String testTrans() {
        payService.bothAdd();
        return "successful!";
    }

    /**
     * 测试TCC
     */
    @PostMapping("/doSave")
    public String addUser(@RequestBody BaseInput<User> input) {
        userService.addUser(input.getTransactionContext(), input.getData());
        return "try is ok!";
    }

    @PostMapping("/doConfirm")
    public String confirmMethod(@RequestBody BaseInput<User> input){
        userService.confirmMethod(input.getTransactionContext(), input.getData());
        return "confirm is ok!";
    }

    @PostMapping("/doCancel")
    public String cancelMethod(@RequestBody BaseInput<User> input){
        userService.cancelMethod(input.getTransactionContext(), input.getData());
        return "cancel is ok!";
    }
}
