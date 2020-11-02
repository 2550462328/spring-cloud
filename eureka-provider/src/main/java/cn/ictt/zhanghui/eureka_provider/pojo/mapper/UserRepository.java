package cn.ictt.zhanghui.eureka_provider.pojo.mapper;

import cn.ictt.zhanghui.eureka_provider.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UserRepository
 * @Description: 这是描述信息
 * @Author: ZhangHui
 * @Date: 2019/11/18
 * @Version：1.0
 */
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUserName(String username);
}
