package cn.ictt.zhanghui.eureka_provider_payservice.service.impl;

import cn.ictt.zhanghui.eureka_provider_payservice.pojo.Account;
import cn.ictt.zhanghui.eureka_provider_payservice.pojo.mapper.AccountRepository;
import cn.ictt.zhanghui.eureka_provider_payservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName
 * @Description: 这是描述信息
 * @Author: ZhangHui
 * @Date: 2019/11/18
 * @Version：1.0
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void addAccount(Account account) {
        System.out.println("i am add a new account");
        accountRepository.save(account);
    }
}
