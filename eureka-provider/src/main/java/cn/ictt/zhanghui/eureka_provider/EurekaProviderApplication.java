package cn.ictt.zhanghui.eureka_provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableTransactionManagement
//@EnableHystrix
//@EnableHystrixDashboard
//@EnableCircuitBreaker //开启断路器功能
public class EurekaProviderApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekaProviderApplication.class, args);
	}
}
