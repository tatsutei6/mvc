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

import javax.servlet.http.HttpServletRequest;

@Controller
public class ValidateController {
    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public String init(@ModelAttribute("userInfo") UserForm user, HttpServletRequest request) {
        request.getSession().setAttribute("SESSION_INFO", new SessionForm("the msg from session"));
        return "form";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public String checkForm(@ModelAttribute("userInfo") @Validated UserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            return "form";
        }
        return "form";
    }
}
