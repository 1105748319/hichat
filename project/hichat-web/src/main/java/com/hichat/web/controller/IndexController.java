package com.hichat.web.controller;


import com.google.code.kaptcha.Producer;
import com.hichat.web.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.UUID;

@Controller
@RequestMapping("index")
public class IndexController {

    @Autowired
    private Producer captchaProducer;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/captcha-image")
    public ModelAndView getKaptchaImage(HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();
        System.out.println("capText: " + capText);
        try {
            String uuid = UUID.randomUUID().toString();
            redisUtil.set(uuid + "_code", capText);
            Cookie cookie = new Cookie("captchaCode", uuid + "_code");
            cookie.setMaxAge(7200);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }


}
