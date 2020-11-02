package cn.ictt.zhanghui.eurekaconfigclient.web.controller;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author: ZhangHui
 * @description: 这是描述信息
 * @date: 2019/6/20
 */
@RestController
@RefreshScope
public class HelloController {

    @Value(value = "${myName}")
    String myName;

    @GetMapping("/hello")
    public void hello(HttpServletResponse response){
        System.out.println("myName = " + myName);
        try {
            Writer writer =response.getWriter();
            writer.write("hello everyone myname is " + myName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
