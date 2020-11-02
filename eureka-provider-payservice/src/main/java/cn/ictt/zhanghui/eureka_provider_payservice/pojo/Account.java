package cn.ictt.zhanghui.eureka_provider_payservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName Account
 * @Description: 这是描述信息
 * @Author: ZhangHui
 * @Date: 2019/11/18
 * @Version：1.0
 */
@Entity(name = "tb_account")
@AllArgsConstructor
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private long account;

    @Column
    private int userid;

    @Column
    private int type;
}
