package org.tei.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tei.form.UpdateForm;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/update")
@Slf4j
public class UpdateController {
    @RequestMapping(method = RequestMethod.GET)
    public String init() {
        log.info("init from :{},written by {},that create at {}", "UpdateController", "Jack", "2021/11/04");
        return "update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String update(@ModelAttribute("form") UpdateForm form, HttpServletRequest request) {
        request.getSession().setAttribute("form", form);
        // Model是保存在request中的，所以如果forward的话，confirm是能够获取form的值。forward继承当前RequestMethod
        // redirect，confirm无法获取form的值，redirect不继承当前RequestMethod，按照get方法转发。
        return "redirect:confirm";
    }


}
