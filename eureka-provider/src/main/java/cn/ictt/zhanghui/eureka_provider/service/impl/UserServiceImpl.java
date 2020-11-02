package cn.ictt.zhanghui.eureka_provider.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cn.ictt.zhanghui.eureka_common.pojo.BaseInput;
import cn.ictt.zhanghui.eureka_provider.api.IUserService;
import cn.ictt.zhanghui.eureka_provider.pojo.User;
import cn.ictt.zhanghui.eureka_provider.pojo.mapper.UserRepository;
import cn.ictt.zhanghui.eureka_provider.service.UserService;

/**
 * @author: ZhangHui
 * @description: 这是描述信息
 * @date: 2019/6/20
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IUserService iUserService;

    /**
     * 被限流的测试方法 经测试 每秒并发线程大约 = perSecond * 2 + 2 返回人员列表
     * @author ZhangHui
     * @date 2019/7/9
     * @return java.util.List<cn.ictt.zhanghui.eureka_provider.pojo.User>
     */
    // @LxRateLimit(perSecond = 10)
    // @ShLimit(5)
    @Override
    public List<User> getUsers() {
        List<User> userList = new ArrayList<User>();
        userList.add(new User.Builder(0, "张三").builderAge(16).builderDesc("张三").build());
        userList.add(new User.Builder(0, "张三").builderAge(16).builderDesc("张三").build());
        userList.add(new User.Builder(0, "张三").builderAge(16).builderDesc("张三").build());
        return userList;
    }

    @Override
    @Compensable(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public void doSave(User user) {
        BaseInput<User> input = new BaseInput<>();
        input.setData(user);
        String result = iUserService.doSave(input);

        userRepository.save(user);
        System.out.println("try userInfo finish!");

        System.out.println(result);

        // 模拟报错，预计回滚
        System.out.println(1/0);

//      MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
//      params.add("transactionContext", null);
//      params.add("input", input);

        // 远程调用
//      String result = restTemplate.postForObject("http://localhost:8663/addUser", input, String.class);
    }

    @Override
    public void confirmMethod(User user){
        User toAddUser = userRepository.findByUserName(user.getUserName());
        if(toAddUser == null){
            System.out.println("事务已回滚，人员已清除！");
            return;
        }
        // 保证幂等性
        if(user.getType() != 2) {
            user.setType(2);
            System.out.println("confirm userInfo finish!");
            userRepository.save(user);
        }
//        // 模拟报错，预计回滚
//        System.out.println(1/0);
    }

    @Override
    public void cancelMethod(User user){
        User toAddUser = userRepository.findByUserName(user.getUserName());
        if(toAddUser == null){
            System.out.println("事务已回滚，人员已清除！");
            return;
        }
        userRepository.deleteById(toAddUser.getUserId());
        System.out.println("cancel userInfo finish!");
    }
}
