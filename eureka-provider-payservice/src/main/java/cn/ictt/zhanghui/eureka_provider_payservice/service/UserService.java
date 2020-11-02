package cn.ictt.zhanghui.eureka_provider_payservice.service;

import cn.ictt.zhanghui.eureka_provider_payservice.pojo.User;
import org.mengyun.tcctransaction.api.TransactionContext;

/**
 * @ClassName UserService
 * @Description: 这是描述信息
 * @Author: ZhangHui
 * @Date: 2019/11/18
 * @Version：1.0
 */
public interface UserService {
    void addUser(TransactionContext transactionContext, User user);

    void confirmMethod(TransactionContext transactionContext,User user);

    void cancelMethod(TransactionContext transactionContext,User user);
}
