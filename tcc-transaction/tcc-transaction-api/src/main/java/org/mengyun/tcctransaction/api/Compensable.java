package org.mengyun.tcctransaction.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * Created by changmingxie on 10/25/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Compensable {

    Propagation propagation() default Propagation.REQUIRED;

    String confirmMethod() default "";

    String cancelMethod() default "";

    Class<? extends TransactionContextEditor> transactionContextEditor() default DefaultTransactionContextEditor.class;

    boolean asyncConfirm() default false;

    boolean asyncCancel() default false;

    class NullableTransactionContextEditor implements TransactionContextEditor {

        @Override
        public TransactionContext get(Object target, Method method, Object[] args) {
            return null;
        }

        @Override
        public void set(TransactionContext transactionContext, Object target, Method method, Object[] args) {

        }
    }

    class DefaultTransactionContextEditor implements TransactionContextEditor {

        @Override
        public TransactionContext get(Object target, Method method, Object[] args) {
            int index = getTransactionContextParamIndex(method.getParameterTypes());
            if (index >= 0) {
                return (TransactionContext) args[index];
            }
            return null;
        }

        @Override
        public void set(TransactionContext transactionContext, Object target, Method method, Object[] args) {
            int index = getTransactionContextParamIndex(method.getParameterTypes());
            if (index >= 0) {
                args[index] = transactionContext;
            }
        }

        static int getTransactionContextParamIndex(Class<?>[] parameterTypes) {
            for (int index = 0; index < parameterTypes.length; index++) {
                if (parameterTypes[index].equals(org.mengyun.tcctransaction.api.TransactionContext.class)) {
                    return index;
                }
            }
            return -1;
        }

        public static TransactionContext getTransactionContextFromArgs(Object[] args) {
            for (Object arg : args) {
                if (null != arg && arg instanceof org.mengyun.tcctransaction.api.TransactionContext) {
                    return (org.mengyun.tcctransaction.api.TransactionContext) arg;
                }
            }
            return null;
        }
    }
}