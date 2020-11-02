package cn.ictt.zhanghui.eureka_provider;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.ictt.zhanghui.eureka_provider.pojo.User;
import cn.ictt.zhanghui.eureka_provider.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EurekaProviderApplicationTests {

    @Test
    public void contextLoads() {

    }

    /**
     * mock 模拟 userService 接口返回数据 当前版本只能对接口进行模拟 原理 是生成代理类 对类进行模拟 可以下载扩展包 EasyMock Class Extension 用
     * org.easymock.classextension.EasyMock 类 生成Mock对象
     * 
     * @author ZhangHui
     * @date 2019/7/11
     * @return
     */
    @Test
    public void mockTest() {
        List<User> userList = new ArrayList<User>();
        userList.add(new User.Builder(0, "李四").builderAge(16).builderDesc("张三").build());
        // 创建一个mock对象
        UserService userService = EasyMock.createMock(UserService.class);
        // 生成多个mock对象时使用IMocksControl类生成
        IMocksControl control = EasyMock.createControl();
        // UserService userService = control.createMock(UserService.class);
        // 当前mock对象状态为record 可以预设对象的输出结果
        userService.getUsers();
        // expectLastCall 获取上一次方法调用所对应的 IExpectationSetters 实例
        // andStubReturn 默认一直返回userList
        // EasyMock.expectLastCall().andStubReturn(userList);
        // andReturn 默认执行一次返回userList
        // times(2) 方法需要执行两次 且返回userList
        EasyMock.expectLastCall().andReturn(userList).times(2);
        // andThrow 方法抛出异常
        // EasyMock.expectLastCall().andThrow(new RuntimeException("数据库异常！")).times(1);
        // replay 更改mock对象状态为 replay 可以对对象方法操作 输出预设结果
        EasyMock.replay(userService);
        // 可以将control下的mock对象状态都改成replay
        // control.replay();
        System.out.println(userService.getUsers());
        System.out.println(userService.getUsers());
        // 对 Mock 对象的方法调用的次数进行验证
        EasyMock.verify(userService);
        // 将mock对象的状态改成record 可以重新预设对象输出结果
        EasyMock.reset(userService);
        userService.getUsers();
        EasyMock.expectLastCall().andReturn(userList).times(1);
        EasyMock.replay(userService);
        System.out.println(userService.getUsers());
        EasyMock.verify(userService);
    }
}
