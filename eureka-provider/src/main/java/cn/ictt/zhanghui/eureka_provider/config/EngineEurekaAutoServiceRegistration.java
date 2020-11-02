//package cn.ictt.zhanghui.eureka_provider.config;
//
//import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
//import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
//import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
//import org.springframework.context.ApplicationContext;
//
///**
// * @author: ZhangHui
// * @description: 覆盖eureka注册源码中EurekaAutoServiceRegistration的stop方法，即取消注册 防止取消注册时间过长引起的端口占用问题
// * @date: 2019/7/8
// */
//public class EngineEurekaAutoServiceRegistration extends EurekaAutoServiceRegistration {
//    public EngineEurekaAutoServiceRegistration(ApplicationContext context, EurekaServiceRegistry serviceRegistry,
//        EurekaRegistration registration) {
//        super(context, serviceRegistry, registration);
//        // TODO Auto-generated constructor stub
//    }
//
//    /** * 上下文关闭时会调用此方法, 在另一个线程中取消注册, 防止超时 */
//    @Override
//    public void stop() {
//        System.out.println("取消注册：unregsiter eureka in another thread");
//        Thread stopThread = new Thread(() -> super.stop());
//        stopThread.start();
//        try {
//            stopThread.join(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("unregister done");
//    }
//}
