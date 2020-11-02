package org.mengyun.tcctransaction.feign.enhance;

import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaobzhou
 * date 2019-03-07 22:54
 */
@Configuration
@ConditionalOnClass({Feign.class, FeignClient.class})
@EnableConfigurationProperties(FeignEnhanceConfigProperties.class)
@ConditionalOnProperty(value = "tcc.feign.enhance.enabled", havingValue = "true", matchIfMissing = true)
public class FeignEnhanceAutoConfiguration {

    @Autowired
    private ApplicationContext ctx;

    @Bean
    public FeignClientProxyDecoratePostProcessor tccFeignClientProxyDecoratorPostProcessor() {
        return new FeignClientProxyDecoratePostProcessor(ctx);
    }

    @Bean
    @ConditionalOnMissingBean(FeignResponseHandler.class)
    public FeignResponseHandler feignResponseHandler() {
        return new FeignResponseHandler() {
            @Override
            public void handle(Object feignRes) {
                // do noting default
            }
        };
    }
}