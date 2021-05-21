package cn.metaq.resource.server;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zantang
 * @version 1.0
 * @description TODO
 * @date 2021/5/21 10:03 上午
 */
@WebFilter(filterName = "resourceOauthFilter", urlPatterns = {"/*"})
@Log4j2
public class ResourceOauthFilter implements Filter {

    private static final String ACCESS_TOKEN = "accessToken";
    private static final String AUTHORIZATION_CODE = "code";

    private static final String GET_TOKEN = "http://localhost:8080/getToken";
    private static final String CLIENT_ID = "client1";
    private static final String CLIENT_SECRET = "client1";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String accessToken = request.getParameter(ACCESS_TOKEN);

        ChangeRequestWrapper requestWrapper=new ChangeRequestWrapper(request);

        if (ObjectUtils.isEmpty(accessToken)) {
            accessToken = requestWrapper.getHeader(ACCESS_TOKEN);
        }

        StringBuffer requestUrl = requestWrapper.getRequestURL();
        String qs = requestWrapper.getQueryString();

        if (!ObjectUtils.isEmpty(qs)) {
            requestUrl.append("?").append(qs);
        }

        String requestUri = requestUrl.toString();
        String authorizationCode = requestWrapper.getParameter(AUTHORIZATION_CODE);

        if (ObjectUtils.isEmpty(accessToken)) {

            if (ObjectUtils.isEmpty(authorizationCode)) {
                log.info("重定向授权");
                response.sendRedirect("http://127.0.0.1:8080/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + requestUri + "&response_type=code&state=abc");
                log.info("================");
                return;
            } else {

                log.info("获取token");
                Map params = new HashMap();
                params.put("grant_type", "authorization_code");
                params.put("client_id", CLIENT_ID);
                params.put("client_secret", CLIENT_SECRET);
                params.put("code", authorizationCode);

                AccessTokenResponse res = restTemplate.getForObject(GET_TOKEN, AccessTokenResponse.class, params);

                Map<String, String[]> parameterMap = new HashMap<>(requestWrapper.getParameterMap());

                parameterMap.put(ACCESS_TOKEN, new String[]{res.getAccess_token()});
                requestWrapper.setParameterMap(parameterMap);

                log.info("token:{}", requestWrapper.getParameter(ACCESS_TOKEN));
            }
        }

        filterChain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }
}
