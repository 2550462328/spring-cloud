package cn.ictt.zhanghui.eureka_consumer.exception;

/**
 * @author: ZhangHui
 * @description: 这是一个自定义异常
 * @date: 2019/6/17
 */
public class MyException extends RuntimeException {
    public MyException(String message) {
        super(message);
    }

    public MyException() {
        super();
    }
}
