package org.mengyun.tcctransaction.recover;

import java.util.Set;

/**
 * Created by changming.xie on 6/1/16.
 */
public interface RecoverConfig {

    int getMaxRetryCount();

    int getRecoverDuration();

    String getCronExpression();

    Set<Class<? extends Exception>> getDelayCancelExceptions();

    void setDelayCancelExceptions(Set<Class<? extends Exception>> delayRecoverExceptions);

    int getAsyncTerminateThreadPoolSize();
}