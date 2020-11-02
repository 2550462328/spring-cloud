package cn.ictt.zhanghui.eureka_consumer.handler;

import cn.ictt.zhanghui.eureka_consumer.exception.MyException;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

/**
 * @author: ZhangHui
 * @description: 这是描述信息
 * @date: 2019/6/17
 */
public class ResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        super.handleError(url, method, response);
    }

    /**
     * 处理异常信息
     * 思路一：如果异常信息里面带有什么信息就抛出自定义异常，否则由默认处理
     * @author ZhangHui
     * @date 2019/6/17
     * @param clientHttpResponse
     * @return void
     */
    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        Scanner scanner = new Scanner(clientHttpResponse.getBody()).useDelimiter("\\A");
        String stringResponse = scanner.hasNext() ? scanner.next() : "";
        if (stringResponse.matches(".*XXX.*")) {
            throw new MyException(stringResponse);
        } else {
            super.handleError(clientHttpResponse);
        }
    }

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return super.hasError(clientHttpResponse);
    }
}
