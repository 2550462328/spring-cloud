package cn.ictt.zhanghui.eureka_consumer.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import cn.ictt.zhanghui.eureka_consumer.pojo.User;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import cn.ictt.zhanghui.eureka_consumer.handler.MyMessageConverter;
import cn.ictt.zhanghui.eureka_consumer.handler.ResponseErrorHandler;

/**
 * @author: ZhangHui
 * @description: restTemplate的配置类
 * @date: 2019/6/17
 */
@Configuration
public class RestTemplateConfig {
    /**
     * @LoadBalanced 开启负载均衡功能
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        // 设置自定义错误处理
        restTemplate.setErrorHandler(getMyErrorHandler());
        // 设置自定义消息转换
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(getMyConverter());
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    /**
     * 自定义HttpClient的创建方式
     * @author ZhangHui
     * @date 2019/6/17
     * @return org.springframework.http.client.HttpComponentsClientHttpRequestFactory
     */
    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
            httpClientBuilder.setSSLContext(sslContext);
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            // 注册http和https请求
            Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build();
            // 开始设置连接池
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
                new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 最大连接数200
            poolingHttpClientConnectionManager.setMaxTotal(200);
            // 同路由并发数20
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);
            httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
            // 重试次数
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
            org.apache.http.client.HttpClient httpClient = httpClientBuilder.build();
            // httpClient连接配置
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
            // 连接超时
            clientHttpRequestFactory.setConnectTimeout(20000);
            // 数据读取超时时间
            clientHttpRequestFactory.setReadTimeout(20000);
            // 连接不够用的等待时间
            clientHttpRequestFactory.setConnectionRequestTimeout(200);
            return clientHttpRequestFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 自定义MessageConverter
     * @author ZhangHui
     * @date 2019/6/17
     * @return cn.ictt.zhanghui.eureka_consumer.handler.MyMessageConverter
     */
    @Bean
    public MyMessageConverter getMyConverter(){
        return new MyMessageConverter();
    }

    @Bean
    public ResponseErrorHandler getMyErrorHandler(){
        return new ResponseErrorHandler();
    }
}
