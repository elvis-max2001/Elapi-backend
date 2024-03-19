package com.yupi.elapiinterface.controller;


import com.yupi.elapiclientsdk.model.User;
import com.yupi.elapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/")
    public String getNameByGet(String name) {
        return "发送GET请求 你的名字是：" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {

        return "发送POST请求 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        // 从请求头中获取参数
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");

        // todo 实际情况应该是去数据库中查是否已分配给用户
        if (!accessKey.equals("elvis")) {
            throw new RuntimeException("无权限");
        }
        // 校验随机数，模拟一下，直接判断nonce是否大于10000
        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }

        // todo 时间和当前时间不能超过5分钟---->自己实现
        // if (timestamp) {}

        //todo 实际情况是从数据库中查找出secretKey
        String serverSign = SignUtils.genSign(body, "abcdefgh");
        if (!sign.equals(serverSign)) {
            throw new RuntimeException("无权限");
        }


        return "POST 用户名字是" + user.getUsername();
    }

}
