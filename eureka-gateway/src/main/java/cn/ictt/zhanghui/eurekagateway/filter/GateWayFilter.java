package cn.ictt.zhanghui.eurekagateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * @author: ZhangHui
 * @description: 这是一个自定义gateWayFilter
 *                 只能作用在单独得route或通过spring.cloud.default-filters配置在全局，作用在所有路由上
 * @date: 2019/7/2
 */
public class GateWayFilter implements GatewayFilter, Ordered {

    private static final String REQUEST_BEGIN_TIME = "requestTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //此处相当于zuul中的"pre"阶段
        exchange.getAttributes().put(REQUEST_BEGIN_TIME, System.currentTimeMillis());

        return chain.filter(exchange).then(
            Mono.fromRunnable(() -> {
                //此处相当于zuul中的"post"阶段
                Long startTime = exchange.getAttribute(REQUEST_BEGIN_TIME);
                if (startTime != null) {
                    System.out.println("执行" + exchange.getRequest().getURI().getRawPath() + "耗时："
                        + (System.currentTimeMillis() - startTime) + "ms");
                }
            }));
    }

    // 值越大则优先级越低
    @Override
    public int getOrder() {
        return 0;
    }
}
