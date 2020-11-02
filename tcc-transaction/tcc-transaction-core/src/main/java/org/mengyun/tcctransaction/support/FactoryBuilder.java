package org.mengyun.tcctransaction.support;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by changming.xie on 2/23/17.
 */
public final class FactoryBuilder {

    private FactoryBuilder() {

    }

    private static List<BeanFactory> beanFactories = new ArrayList<BeanFactory>();

    private static ConcurrentHashMap<Class, SingeltonFactory> classFactoryMap = new ConcurrentHashMap<Class, SingeltonFactory>();

    public static <T> SingeltonFactory<T> factoryOf(Class<T> classType) {

        if (!classFactoryMap.containsKey(classType)) {

            for (BeanFactory beanFactory : beanFactories) {
                if (beanFactory.isFactoryOf(classType)) {
                    classFactoryMap.putIfAbsent(classType, new SingeltonFactory<T>(classType, beanFactory.getBean(classType)));
                }
            }

            if (!classFactoryMap.containsKey(classType)) {
                classFactoryMap.putIfAbsent(classType, new SingeltonFactory<T>(classType));
            }
        }

        return classFactoryMap.get(classType);
    }

    public static void registerBeanFactory(BeanFactory beanFactory) {
        beanFactories.add(beanFactory);
    }

    public static class SingeltonFactory<T> {

        private volatile T instance = null;

        private String className;

        public SingeltonFactory(Class<T> clazz, T instance) {
            this.className = clazz.getName();
            this.instance = instance;
        }

        public SingeltonFactory(Class<T> clazz) {
            this.className = clazz.getName();
        }

        public T getInstance() {

            if (instance == null) {
                synchronized (SingeltonFactory.class) {
                    if (instance == null) {
                        try {
                            ClassLoader loader = Thread.currentThread().getContextClassLoader();

                            Class<?> clazz = loader.loadClass(className);

                            instance = (T) clazz.newInstance();
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to create an instance of " + className, e);
                        }
                    }
                }
            }

            return instance;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;

            SingeltonFactory that = (SingeltonFactory) other;

            if (!className.equals(that.className)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return className.hashCode();
        }
    }
}