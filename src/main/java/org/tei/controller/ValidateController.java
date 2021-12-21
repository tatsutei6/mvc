package org.tei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tei.form.SessionForm;
import org.tei.form.UserForm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
public class ValidateController {
    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public String init(@ModelAttribute("userInfo") UserForm user, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("Name".trim(), "Jack".trim());
        cookie.setMaxAge(30 * 60);// 设置为30min
        cookie.setPath("/");
        System.out.println("已添加===============");
        response.addCookie(cookie);
        request.getSession().setAttribute("SESSION_INFO", new SessionForm("the msg from session"));
        return "form";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public String checkForm(@ModelAttribute("userInfo") @Validated UserForm form, HttpServletRequest request, BindingResult bindingResult) {
        System.out.println("form = " + form);
        System.out.println("cookies = " + Arrays.toString(request.getCookies()));
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            return "form";
        }
        return "form";
    }
}
