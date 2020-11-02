tcc transaction：https://github.com/changmingxie/tcc-transaction

基于tcc transaction1.2.x基础上实现tcc transaction与springboot2.x的自动配置与springcloud feign自动集成
        
使用指南

    第一步引入maven依赖
        <dependency>
            <groupId>org.mengyun</groupId>
            <artifactId>tcc-transaction-spring-cloud-starter</artifactId>
            <version>1.2.x</version>
        </dependency>
    第二步在yml配置文件加入配置
        tcc:
          enabled: true #开启springboot自动配置
          data-source-config:
            data-source-provider: com.alibaba.druid.pool.DruidDataSource #连接池 必须配置项
            driver-class-name: com.mysql.jdbc.Driver #必须配置项
            url: jdbc:mysql://localhost:3306/tcc?useSSL=false #必须配置项
            username: root #必须配置项
            password: 123456 #必须配置项
            domain: xxx #非必须项
            table-suffix: xxx #非必须项
          feign:
            enhance:
                enabled: true #启用feign增强功能
    第三步
        FeignClient接口方法添加@Compensable注解
        详情参考tcc-transaction-springcloud-sample