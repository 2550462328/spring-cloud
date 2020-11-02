package cn.ictt.zhanghui.eureka_provider.api;

import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.context.AwareTransactionContextEditor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cn.ictt.zhanghui.eureka_common.pojo.BaseInput;
import cn.ictt.zhanghui.eureka_provider.pojo.User;

/**
 * @ClassName
 * @Description: feign请求接口
 * @Author: ZhangHui
 * @Date: 2019/11/20
 * @Version：1.0
 */
@FeignClient("eureka-provider-payservice")
public interface IUserService {
    @Compensable(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod",transactionContextEditor = AwareTransactionContextEditor.class)
    @PostMapping(path = "/doSave", produces = "application/json;charset=UTF-8")
    String doSave(@RequestBody  BaseInput<User> user);

    @PostMapping(path = "/doConfirm", produces = "application/json;charset=UTF-8")
    String confirmMethod(@RequestBody  BaseInput<User> user);

    @PostMapping(path = "/doCancel", produces = "application/json;charset=UTF-8")
    String cancelMethod(@RequestBody  BaseInput<User> user);
}
