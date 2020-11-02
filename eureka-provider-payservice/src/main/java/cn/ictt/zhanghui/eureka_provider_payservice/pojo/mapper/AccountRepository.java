package cn.ictt.zhanghui.eureka_provider_payservice.pojo.mapper;

import cn.ictt.zhanghui.eureka_provider_payservice.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName AccountRepository
 * @Description: 这是描述信息
 * @Author: ZhangHui
 * @Date: 2019/11/18
 * @Version：1.0
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
