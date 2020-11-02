package cn.ictt.zhanghui.eureka_consumer.handler;

import cn.ictt.zhanghui.eureka_consumer.pojo.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author: ZhangHui
 * @description: 自定义HttpMessageConverter
 * @date: 2019/6/17
 */
public class MyMessageConverter extends AbstractHttpMessageConverter<List<User>> {

    public MyMessageConverter() {
        // 新建一个我们自定义的媒体类型application/xxx-junlin
        super(new MediaType("application", "json", Charset.forName("UTF-8")));
    }

    @Override
    protected List<User> readInternal(Class<? extends List<User>> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        String temp = StreamUtils.copyToString(httpInputMessage.getBody(), Charset.forName("UTF-8"));
        List<User> userList = (List<User>) JSONObject.parse(temp);
        return userList;
    }

    @Override
    protected void writeInternal(List<User> userList, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        String outMessage = JSONObject.toJSONString(userList);
        httpOutputMessage.getBody().write(outMessage.getBytes());
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return List.class.isAssignableFrom(aClass);
    }
}
