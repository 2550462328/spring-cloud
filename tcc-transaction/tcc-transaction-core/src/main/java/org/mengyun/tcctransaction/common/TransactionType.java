

package org.mengyun.tcctransaction.common;

/**
 * Created by changmingxie on 11/15/15.
 */
public enum TransactionType {

    ROOT(1),
    BRANCH(2);

    int id;

    TransactionType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TransactionType parse(int id) {
        for (TransactionType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
}