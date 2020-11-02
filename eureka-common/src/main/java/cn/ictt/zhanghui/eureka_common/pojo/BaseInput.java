package cn.ictt.zhanghui.eureka_common.pojo;

import lombok.Data;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.mengyun.tcctransaction.api.TransactionContextAware;

import java.io.Serializable;

/**
 * @ClassName BaseInput
 * @Description: 这是描述信息
 * @Author: ZhangHui
 * @Date: 2019/11/19
 * @Version：1.0
 */
@Data
public class BaseInput<T> implements TransactionContextAware {
    private T data;
    private TransactionContext transactionContext;
}
