package org.mengyun.tcctransaction.api;

import java.io.Serializable;

/**
 * @author xiaobzhou
 * date 2019-01-15 23:04
 */
public interface TransactionContextAware extends Serializable {

    TransactionContext getTransactionContext();

    void setTransactionContext(TransactionContext transactionContext);
}