package com.my.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author:ljn
 * @Description:解决Ajax的跨域问题,为响应设置响应头,Access-Control-Allow-Origin ---> 设置访问白名单
 * @Date:2020/11/25 16:12
 */
@Component
public class CorsFilter implements Filter {

    final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CorsFilter.class);


    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;

        String originHeader=((HttpServletRequest) request).getHeader("Origin");

        ArrayList<String> domainList = new ArrayList<>();
        domainList.add("http://192.168.41.1:9090"); //添加允许访问的域名


        //判断该域名是否在白名单中
        if(domainList.contains(originHeader)){
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        chain.doFilter(request, res);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
