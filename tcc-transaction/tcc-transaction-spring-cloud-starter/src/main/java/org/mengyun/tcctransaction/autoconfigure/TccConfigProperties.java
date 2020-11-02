package org.mengyun.tcctransaction.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xiaobzhou
 * date 2019-03-07 12:52
 */
@ConfigurationProperties(prefix = "tcc")
public class TccConfigProperties {
    /**
     * enable tcc auto configuration.
     */
    private boolean enabled = true;
    private DataSource datasource;
    private Recover recover = new Recover();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public DataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }

    public Recover getRecover() {
        return recover;
    }

    public void setRecover(Recover recover) {
        this.recover = recover;
    }

    public static class DataSource {
        /**
         * Fully qualified name of the connection pool implementation to use. By default, it
         * is auto-detected from the classpath.
         */
        private Class<? extends javax.sql.DataSource> dataSourceProvider;
        /**
         * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
         */
        private String driverClassName;

        /**
         * JDBC url of the database.
         */
        private String url;

        /**
         * Login user of the database.
         */
        private String username;

        /**
         * Login password of the database.
         */
        private String password;

        private String domain;

        private String tableSuffix;

        public Class<? extends javax.sql.DataSource> getDataSourceProvider() {
            return dataSourceProvider;
        }

        public void setDataSourceProvider(Class<? extends javax.sql.DataSource> dataSourceProvider) {
            this.dataSourceProvider = dataSourceProvider;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getTableSuffix() {
            return tableSuffix;
        }

        public void setTableSuffix(String tableSuffix) {
            this.tableSuffix = tableSuffix;
        }
    }

    public static class Recover {

        private int maxRetryCount = 30;

        // 120 seconds
        private int recoverDuration = 120;

        private String cronExpression = "0 */1 * * * ?";

        private int asyncTerminateThreadPoolSize = 1024;

        private boolean appendDelayCancelException = true;

        private Set<Class<? extends Exception>> delayCancelExceptions = new HashSet<Class<? extends Exception>>();

        public int getMaxRetryCount() {
            return maxRetryCount;
        }

        public void setMaxRetryCount(int maxRetryCount) {
            this.maxRetryCount = maxRetryCount;
        }

        public int getRecoverDuration() {
            return recoverDuration;
        }

        public void setRecoverDuration(int recoverDuration) {
            this.recoverDuration = recoverDuration;
        }

        public String getCronExpression() {
            return cronExpression;
        }

        public void setCronExpression(String cronExpression) {
            this.cronExpression = cronExpression;
        }

        public int getAsyncTerminateThreadPoolSize() {
            return asyncTerminateThreadPoolSize;
        }

        public void setAsyncTerminateThreadPoolSize(int asyncTerminateThreadPoolSize) {
            this.asyncTerminateThreadPoolSize = asyncTerminateThreadPoolSize;
        }

        public boolean isAppendDelayCancelException() {
            return appendDelayCancelException;
        }

        public void setAppendDelayCancelException(boolean appendDelayCancelException) {
            this.appendDelayCancelException = appendDelayCancelException;
        }

        public Set<Class<? extends Exception>> getDelayCancelExceptions() {
            return delayCancelExceptions;
        }

        public void setDelayCancelExceptions(Set<Class<? extends Exception>> delayCancelExceptions) {
            this.delayCancelExceptions = delayCancelExceptions;
        }
    }
}