package cn.ictt.zhanghui.eureka_provider_payservice.service;

import cn.ictt.zhanghui.eureka_common.pojo.BaseInput;
import cn.ictt.zhanghui.eureka_provider_payservice.pojo.User;
import org.mengyun.tcctransaction.api.TransactionContext;

public interface PayService {
    void executePay();

    void bothAdd();

    void addBoth();
}
