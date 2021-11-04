package org.tei.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tei.form.ConfirmForm;
import org.tei.form.UpdateForm;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/confirm")
@Slf4j
public class ConfirmController {
    @RequestMapping(method = RequestMethod.GET)
    public String init(@ModelAttribute("form") ConfirmForm form, HttpServletRequest request) {
        // @ModelAttribute无法后去保存在session中的值
        UpdateForm updateForm  = (UpdateForm) request.getSession().getAttribute("form");
        // @ModelAttribute 标注后，即使没有传递过来的参数或者Model中没有相应的值，form也不是null。只不过form里面的属性为null
        // BeanUtils.copyProperties 后，updateForm的值填充进form中，Model中的"form"也是指向被填充进值的form
        BeanUtils.copyProperties(updateForm,form);
        System.out.println("ConfirmController.init.form => " + form);
        // redirect:confirm 会丢失保存在model的值
        return "confirm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String register(@ModelAttribute("form") UpdateForm form) {
        System.out.println("form => " + form);
        return "success";
    }

}
