package org.mengyun.tcctransaction.autoconfigure;

import org.mengyun.tcctransaction.TransactionRepository;
import org.mengyun.tcctransaction.interceptor.CompensableTransactionAspect;
import org.mengyun.tcctransaction.interceptor.ResourceCoordinatorAspect;
import org.mengyun.tcctransaction.recover.RecoverConfig;
import org.mengyun.tcctransaction.recover.TransactionRecovery;
import org.mengyun.tcctransaction.serializer.KryoPoolSerializer;
import org.mengyun.tcctransaction.serializer.ObjectSerializer;
import org.mengyun.tcctransaction.spring.ConfigurableCoordinatorAspect;
import org.mengyun.tcctransaction.spring.ConfigurableTransactionAspect;
import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.recover.RecoverScheduledJob;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.mengyun.tcctransaction.spring.support.SpringBeanFactory;
import org.mengyun.tcctransaction.spring.support.SpringTransactionConfigurator;
import org.mengyun.tcctransaction.support.TransactionConfigurator;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author xiaobzhou
 * date 2019-03-06 14:23
 */
@Configuration
@EnableScheduling
@EnableConfigurationProperties(TccConfigProperties.class)
@ConditionalOnProperty(value = "tcc.enabled", havingValue = "true", matchIfMissing = true)
public class TccBootAutoConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(TccBootAutoConfiguration.class);

    @Autowired
    private TccConfigProperties props;

    @Bean
    @ConditionalOnMissingBean(TransactionRepository.class)
    public TransactionRepository transactionRepository(ObjectSerializer<?> serializer) {

        SpringJdbcTransactionRepository transactionRepository = new SpringJdbcTransactionRepository();
        transactionRepository.setDataSource(buildTccDataSource());
        TccConfigProperties.DataSource dataSourceAutoConfig = props.getDatasource();
        if (StringUtils.isEmpty(dataSourceAutoConfig.getDomain())) {
            LOG.warn("Property tcc.data-source-config.domain does not config");
        }
        transactionRepository.setDomain(dataSourceAutoConfig.getDomain());
        if (StringUtils.isEmpty(dataSourceAutoConfig.getTableSuffix())) {
            LOG.warn("Property tcc.data-source-config.table-suffix does not config");
        }
        transactionRepository.setTbSuffix(dataSourceAutoConfig.getTableSuffix());
        transactionRepository.setSerializer(serializer);
        return transactionRepository;
    }

    private javax.sql.DataSource buildTccDataSource() {

        TccConfigProperties.DataSource dataSourceAutoConfig = props.getDatasource();

        Assert.notNull(dataSourceAutoConfig,
                "Properties tcc.data-source-config.* must be config in application.properties/application.yml");
        Assert.notNull(dataSourceAutoConfig.getDataSourceProvider(),
                "Property tcc.data-source-config.data-source-provider must be config in application.properties/application.yml");
        Assert.hasText(dataSourceAutoConfig.getDriverClassName(),
                "Property tcc.data-source-config.driver-class-name must be config in application.properties/application.yml");
        Assert.hasText(dataSourceAutoConfig.getUrl(),
                "Property tcc.data-source-config.url must be config in application.properties/application.yml");
        Assert.hasText(dataSourceAutoConfig.getUsername(),
                "Property tcc.data-source-config.username must be config in application.properties/application.yml");
        Assert.hasText(dataSourceAutoConfig.getPassword(),
                "Property tcc.data-source-config.password must be config in application.properties/application.yml");

        return DataSourceBuilder.create()
                .type(dataSourceAutoConfig.getDataSourceProvider())
                .driverClassName(dataSourceAutoConfig.getDriverClassName())
                .url(dataSourceAutoConfig.getUrl())
                .username(dataSourceAutoConfig.getUsername())
                .password(dataSourceAutoConfig.getPassword())
                .build();
    }

    @Bean
    public SpringBeanFactory springBeanFactory() {
        return new SpringBeanFactory();
    }

    @Bean
    public TransactionConfigurator transactionConfigurator(RecoverConfig recoverConfig,
                                                           TransactionRepository transactionRepository) {
        SpringTransactionConfigurator transactionConfigurator = new SpringTransactionConfigurator();
        transactionConfigurator.setRecoverConfig(recoverConfig);
        transactionConfigurator.setTransactionRepository(transactionRepository);
        transactionConfigurator.init();
        return transactionConfigurator;
    }


    @Bean
    public CompensableTransactionAspect compensableTransactionAspect(TransactionConfigurator transactionConfigurator) {
        ConfigurableTransactionAspect aspect = new ConfigurableTransactionAspect();
        aspect.setTransactionConfigurator(transactionConfigurator);
        aspect.init();
        return aspect;
    }

    @Bean
    public ResourceCoordinatorAspect resourceCoordinatorAspect(TransactionConfigurator transactionConfigurator) {
        ConfigurableCoordinatorAspect aspect = new ConfigurableCoordinatorAspect();
        aspect.setTransactionConfigurator(transactionConfigurator);
        aspect.init();
        return aspect;
    }

    @Bean
    public TransactionRecovery transactionRecovery(TransactionConfigurator transactionConfigurator) {
        TransactionRecovery transactionRecovery = new TransactionRecovery();
        transactionRecovery.setTransactionConfigurator(transactionConfigurator);
        return transactionRecovery;
    }

    @Bean
    public SchedulerFactoryBean recoverScheduler() {
        return new SchedulerFactoryBean();
    }

    @Bean
    public RecoverScheduledJob recoverScheduledJob(TransactionRecovery transactionRecovery,
                                                   TransactionConfigurator transactionConfigurator,
                                                   Scheduler recoverScheduler) {
        RecoverScheduledJob recoverScheduledJob = new RecoverScheduledJob();
        recoverScheduledJob.setTransactionRecovery(transactionRecovery);
        recoverScheduledJob.setTransactionConfigurator(transactionConfigurator);
        recoverScheduledJob.setScheduler(recoverScheduler);
        recoverScheduledJob.init();
        return recoverScheduledJob;
    }

    @Bean
    @ConditionalOnMissingBean(ObjectSerializer.class)
    public ObjectSerializer<?> objectSerializer() {
        return new KryoPoolSerializer();
    }

    @Bean
    @ConditionalOnMissingBean(RecoverConfig.class)
    public RecoverConfig recoverConfig() {

        TccConfigProperties.Recover recoverAutoConfig = props.getRecover();
        DefaultRecoverConfig recoverConfig = new DefaultRecoverConfig();
        if (recoverAutoConfig.getMaxRetryCount() > 0) {
            recoverConfig.setMaxRetryCount(recoverAutoConfig.getMaxRetryCount());
        }
        if (recoverAutoConfig.getRecoverDuration() > 0) {
            recoverConfig.setRecoverDuration(recoverAutoConfig.getRecoverDuration());
        }
        if (!StringUtils.isEmpty(recoverAutoConfig.getCronExpression())) {
            recoverConfig.setCronExpression(recoverAutoConfig.getCronExpression());
        }
        if (recoverAutoConfig.getAsyncTerminateThreadPoolSize() > 0) {
            recoverConfig.setAsyncTerminateThreadPoolSize(recoverAutoConfig.getAsyncTerminateThreadPoolSize());
        }
        if (!CollectionUtils.isEmpty(recoverAutoConfig.getDelayCancelExceptions())) {
            if (recoverAutoConfig.isAppendDelayCancelException()) {
                for (Class<? extends Exception> exClassType : recoverAutoConfig.getDelayCancelExceptions()) {
                    recoverConfig.getDelayCancelExceptions().add(exClassType);
                }
            } else {
                recoverConfig.getDelayCancelExceptions().clear();
                recoverConfig.getDelayCancelExceptions().addAll(recoverAutoConfig.getDelayCancelExceptions());
            }
        }
        return recoverConfig;
    }
}