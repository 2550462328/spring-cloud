package cn.ictt.zhanghui.eurekagateway.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName HostAddrKeyResolver
 * @Description: 这是实现根据ip、url或其他条件实现spring Cloud GateWay限流的自定义keyResolver
 * @Author: ZhangHui
 * @Date: 2019/7/10
 * @Version：1.0
 */
public class HostAddrKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // 根据ip限流
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
        // 根据url限流
        // return Mono.just(exchange.getRequest().getURI().getPath());
        // 根据传入的user参数限流
        // return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }
}
