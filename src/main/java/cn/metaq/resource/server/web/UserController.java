package cn.metaq.resource.server.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zantang
 * @version 1.0
 * @description TODO
 * @date 2021/5/21 9:56 上午
 */
@RestController
@Log4j2
public class UserController {

    private static final String ACCESS_TOKEN = "accessToken";

    @Autowired
    private HttpServletRequest request;

    @GetMapping("user")
    public String user() {

        String accessToken = request.getParameter(ACCESS_TOKEN);

        if (ObjectUtils.isEmpty(accessToken)) {
            accessToken = request.getHeader(ACCESS_TOKEN);
        }

        log.info("accessToken:{}", accessToken);

        return "admin";
    }
}
