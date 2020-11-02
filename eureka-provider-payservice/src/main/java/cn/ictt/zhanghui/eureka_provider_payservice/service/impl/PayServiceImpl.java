package cn.ictt.zhanghui.eureka_provider_payservice.service.impl;

import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ictt.zhanghui.eureka_provider_payservice.pojo.Account;
import cn.ictt.zhanghui.eureka_provider_payservice.pojo.User;
import cn.ictt.zhanghui.eureka_provider_payservice.service.AccountService;
import cn.ictt.zhanghui.eureka_provider_payservice.service.PayService;
import cn.ictt.zhanghui.eureka_provider_payservice.service.UserService;

/**
 * @author: ZhangHui
 * @description: 这是描述信息
 * @date: 2019/6/21
 */
@Service
@Transactional
public class PayServiceImpl implements PayService {
    @Override
    public void executePay() {
        System.out.println("我正在执行支付操作");
    }

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PayService payService;

    /**
     * 如果在这里调用addBoth会出现spring自调用问题，事务不会回滚
     */
    @Override
    public void bothAdd() {
        // 注入自身并调用，解决自调用问题
        payService.addBoth();
        // 下面这种直接调用事务不会回滚
        // addBoth();
    }

    @Override
    public void addBoth() {
        accountService.addAccount(new Account(0, 1000L, 1, 1));

        userService.addUser(null, new User(0, "zhanghui", 18, "i am zhanghui", 0));
    }



}
