package cn.ictt.zhanghui.eureka_zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: ZhangHui
 * @description: 这里zuul的服务过滤
 * @date: 2019/6/20
 */
@Component
public class MyFilter extends ZuulFilter {
    /**
     * 返回过滤的类型 "pre"：路由之前 "routing"：路由之时 "post"：路由之后 "error"：发送错误调用
     * @author ZhangHui
     * @date 2019/6/20
     * @return java.lang.String
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 返回过滤的顺序
     * @author ZhangHui
     * @date 2019/6/20
     * @return int
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 返回是否过滤
     * @author ZhangHui
     * @date 2019/6/20
     * @return boolean
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Object accessToken = request.getParameter("token");
        if (accessToken == null) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            } catch (Exception e) {
            }
            return null;
        }
        return null;
    }
}
