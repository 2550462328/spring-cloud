package cn.ictt.zhanghui.eureka_provider.service;

import cn.ictt.zhanghui.eureka_provider.annotation.MyAnnotation;
import sun.applet.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: ZhangHui
 * @description: 这是一个自定义注释测试类
 * @date: 2019/6/25
 */
public class ValiadService {
    @MyAnnotation(id = "1", name = "hello")
    public boolean validatePassword(String password) {
        return (password.matches("a(\\d)b"));
    }

    @MyAnnotation(id = "1")
    public String encryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }

    /**
     * 遍历指定cl中的method，获取MyAnnotation注释信息
     * @author ZhangHui
     * @date 2019/6/25
     * @param caseList
     * @param cl
     * @return void
     */
    public static void trackCases(List<Integer> caseList, Class<?> cl) {
        for (Method method : cl.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MyAnnotation.class)) {
                MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
                System.out.println("this method has myAnnotation and id = " + myAnnotation.id() + "name = "
                    + myAnnotation.name());
                caseList.remove(myAnnotation.id());
            }
        }
        caseList.stream().forEach(i -> {
            System.out.println("I don`t have myAnnotation : " + i);
        });
    }

    public static void main(String[] args) {
        List<Integer> caseList = new ArrayList<>();
        Collections.addAll(caseList, 1, 2, 3);
        trackCases(caseList, ValiadService.class);

        try {
            Method method =ValiadService.class.getMethod("encryptPassword", String.class);
            String result = (String)method.invoke(new ValiadService(), "123456");
            System.out.println("execute finished and result = " + result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
