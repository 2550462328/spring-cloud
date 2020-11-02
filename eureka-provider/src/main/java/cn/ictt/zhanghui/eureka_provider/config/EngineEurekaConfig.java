//package cn.ictt.zhanghui.eureka_provider.config;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
//import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
//import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
//import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
//import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.ConfigurableEnvironment;
//
///**
// * @author: ZhangHui
// * @description: 将自定义的EurekaAutoServiceRegistration（覆盖了stop方法）注入到环境中
// * @date: 2019/7/8
// */
//@Configuration
//public class EngineEurekaConfig extends EurekaClientAutoConfiguration {
//    public EngineEurekaConfig(ConfigurableEnvironment env) {
//        super(env);
//        // TODO Auto-generated constructor stub
//    }
//
//    @Bean
//    @ConditionalOnBean(AutoServiceRegistrationProperties.class)
//    @ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled", matchIfMissing = true)
//    @Override
//    public EurekaAutoServiceRegistration eurekaAutoServiceRegistration(ApplicationContext context,
//                                                                       EurekaServiceRegistry registry, EurekaRegistration registration) {
//        return new EngineEurekaAutoServiceRegistration(context, registry, registration);
//    }
//}
