package cn.ictt.zhanghui.eureka_provider_payservice.service.impl;

import cn.ictt.zhanghui.eureka_provider_payservice.pojo.mapper.UserRepository;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ictt.zhanghui.eureka_provider_payservice.pojo.User;
import cn.ictt.zhanghui.eureka_provider_payservice.service.UserService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName
 * @Description: 这是描述信息
 * @Author: ZhangHui
 * @Date: 2019/11/18
 * @Version：1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Compensable(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public void addUser(TransactionContext transactionContext, User user) {
        if (user != null) {
            user.setType(1);
            userRepository.save(user);
        }
        System.out.println("try userInfo finish!");
    }
    @Override
    public void confirmMethod(TransactionContext transactionContext, User user) {
        User toAddUser = userRepository.findByUserName(user.getUserName());
        if (toAddUser == null) {
            System.out.println("事务已回滚，人员已清除！");
            return;
        }
        // 保证幂等性
        if (user.getType() != 2) {
            user.setType(2);
            userRepository.save(user);
            System.out.println("confirm userInfo finish!");
        }
    }

    @Override
    public void cancelMethod(TransactionContext transactionContext,User user) {
        userRepository.deleteById(user.getUserId());
        System.out.println("cancel userInfo finish!");
    }

}
