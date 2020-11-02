package cn.ictt.zhanghui.eureka_provider_payservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
@EnableFeignClients
//@EnableHystrix
//@EnableHystrixDashboard
//@EnableCircuitBreaker
//@EnableTurbine
public class EurekaProviderPayApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekaProviderPayApplication.class, args);
	}
}
