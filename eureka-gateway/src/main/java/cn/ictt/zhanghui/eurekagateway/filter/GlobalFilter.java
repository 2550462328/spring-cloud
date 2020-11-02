package cn.ictt.zhanghui.eurekagateway.filter;

import com.fasterxml.jackson.core.filter.TokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: ZhangHui
 * @description: 这是一个全局globalFilter 不用任何配置，通过@bean注入到spring中即可生效，作用在所有route
 * @date: 2019/7/5
 */
public class GlobalFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {
    Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String token = exchange.getRequest().getQueryParams().getFirst("token");
        // 可以使用token做权限验证
//        if (token == null || token.isEmpty()) {
//            logger.info("token is empty...");
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
