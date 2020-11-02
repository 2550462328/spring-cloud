package cn.ictt.zhanghui.eurekagateway;

import java.time.ZonedDateTime;

import cn.ictt.zhanghui.eurekagateway.filter.GlobalFilter;
import cn.ictt.zhanghui.eurekagateway.resolver.HostAddrKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import cn.ictt.zhanghui.eurekagateway.factory.RequestTimeGatewayFilterFactory;
import cn.ictt.zhanghui.eurekagateway.filter.GateWayFilter;

/**
 * 网关路由的作用 1、协议转换，路由转发 2、流量聚合，对流量进行监控，日志输出 3、作为整个系统的前端工程，对流量进行控制，有限流的作用 4、作为系统的前端边界，外部流量只能通过网关才能访问系统 5、可以在网关层做权限的判断
 * 6、可以在网关层做缓存
 * 
 * @author ZhangHui
 * @date 2019/6/27
 */
@SpringBootApplication
@RestController
public class EurekaGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = "http://httpbin.org:80";
        return builder
            .routes()
            // 让请求“/get”请求都转发到“http://httpbin.org/get”
            .route(
                "route1",
                p -> p.after(ZonedDateTime.now()).negate().or().header("hui", "hui").or().cookie("hui", "hui").and()
                    .path("/get").filters(f -> f.filter(new GateWayFilter()).addRequestHeader("Hello", "World"))
                    .uri(httpUri))
            // 对localhost:8777/rewrite/index的请求会被转发到https://blog.csdn.net/index
            .route(
                "route2",
                p -> p.path("/rewrite/**").filters(f -> f.rewritePath("/rewrite/(?<segment>.*)", "/${segment}"))
                    .uri("https://blog.csdn.net"))
            // 当请求的host有"*.hystrix.com"进入该router
            // 使用curl命令测试失败 立马返回404
            // 使用测试工具(soap ui)模拟发送http请求 有时没拦截 返回404
            .route(
                "routei",
                p -> p.host("*.hystrix.com").and().method(HttpMethod.POST).and().query("hello", "world")
                    .filters(f -> f.hystrix(config -> config.setName("myhystrix").setFallbackUri("forward:/fallback")))
                    .uri(httpUri)).build();
    }

    /**
     * 默认失败处理
     */
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("this is a fallback");
    }

    /**
     * 自定义gateFilterFactory
     */
//    @Bean
//    public RequestTimeGatewayFilterFactory elapsedGatewayFilterFactory() {
//        return new RequestTimeGatewayFilterFactory();
//    }

    /**
     * 自定义全局Filter
     */
//    @Bean
//    public GlobalFilter globalFilter() {
//        return new GlobalFilter();
//    }

    /**
     * 注入hotsAddress解析器 实现 网关根据ipAddress限流
     */
    @Bean
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }

}
