package cn.metaq.resource.server.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zantang
 * @version 1.0
 * @description TODO
 * @date 2021/5/21 9:56 上午
 */
@RestController
@Log4j2
@RequestMapping("users")
public class UserController {

    private static final String ACCESS_TOKEN = "accessToken";

    @GetMapping( "/info")
    public Map<String, Object> getUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "xxx");
        return map;
    }

}
