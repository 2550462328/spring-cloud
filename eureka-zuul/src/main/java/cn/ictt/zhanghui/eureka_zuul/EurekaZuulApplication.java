package cn.ictt.zhanghui.eureka_zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import javax.servlet.annotation.WebFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableDiscoveryClient
public class EurekaZuulApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekaZuulApplication.class, args);
	}
}
