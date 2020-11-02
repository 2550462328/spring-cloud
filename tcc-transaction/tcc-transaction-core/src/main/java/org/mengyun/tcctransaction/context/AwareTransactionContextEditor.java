package org.mengyun.tcctransaction.context;

import org.mengyun.tcctransaction.api.TransactionContext;
import org.mengyun.tcctransaction.api.TransactionContextAware;
import org.mengyun.tcctransaction.api.TransactionContextEditor;

import java.lang.reflect.Method;

/**
 * @author xiaobzhou
 * date 2019-01-15 23:06
 */
public class AwareTransactionContextEditor implements TransactionContextEditor {

    @Override
    public TransactionContext get(Object target, Method method, Object[] args) {
        TransactionContextAware contextAware = obtainTransactionContextAwareParameter(args);
        if (null != contextAware) {
            return contextAware.getTransactionContext();
        }
        return null;
    }

    @Override
    public void set(TransactionContext transactionContext, Object target, Method method, Object[] args) {
        TransactionContextAware contextAware = obtainTransactionContextAwareParameter(args);
        if (null != contextAware) {
            contextAware.setTransactionContext(transactionContext);
        }
    }

    private TransactionContextAware obtainTransactionContextAwareParameter(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof TransactionContextAware) {
                return (TransactionContextAware) arg;
            }
        }
        return null;
    }
}