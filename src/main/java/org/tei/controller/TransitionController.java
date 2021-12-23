package org.tei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TransitionController {
    @GetMapping(value = "/menu")
    public String moveToMenu(HttpServletRequest request) {
        this.removeAllTransitionToken(request);
        return "menu";
    }

    @GetMapping(value = "/terms")
    public String moveToTerms(HttpServletRequest request, String flag, Model model) {
        if (!StringUtils.hasText(flag)) {
            return "error";
        }
        if ("1".equals(flag)) {
            model.addAttribute("flag", "所有者変更");
            model.addAttribute("dest", "/owner_info_change1_init");
        }
        if ("2".equals(flag)) {
            model.addAttribute("flag", "マイクロチップ情報変更");
            model.addAttribute("dest", "/mcinfo_change");
        }
        if ("3".equals(flag)) {
            model.addAttribute("flag", "情報変更");
            model.addAttribute("dest", "/info_change");
        }
        this.generateAndSetTransitionToken(request);
        return "terms";
    }

    @RequestMapping(value = "/owner_info_change1_init")
    public String ownerChange1Init(HttpServletRequest request, String transitionToken) {
        boolean isValid = this.checkTransitionToken(request, transitionToken);
        if (!isValid) {
            return "access_error";
        }
        return "owner_change_1";
    }

    @RequestMapping(value = "/owner_info_change2_init")
    public String ownerChange2Init(HttpServletRequest request, String transitionToken) {
        boolean isValid = this.checkTransitionToken(request, transitionToken);
        if (!isValid) {
            return "access_error";
        }
        return "owner_change_2";
    }

    @RequestMapping(value = "/owner_info_change3_init")
    public String ownerChange3Init(HttpServletRequest request, String transitionToken) {
        boolean isValid = this.checkTransitionToken(request, transitionToken);
        if (!isValid) {
            return "access_error";
        }
        return "owner_change_3";
    }

    @RequestMapping(value = "/owner_info_change4_init")
    public String ownerChange4Init(HttpServletRequest request, String transitionToken) {
        boolean isValid = this.checkTransitionToken(request, transitionToken);
        if (!isValid) {
            return "access_error";
        }
        return "owner_change_4";
    }

    @RequestMapping(value = "/success_init")
    public String ownerChangeRegister(HttpServletRequest request, String transitionToken) {
        boolean isValid = this.checkTransitionToken(request, transitionToken);
        if (!isValid) {
            return "access_error";
        }
        return "success";
    }

    private boolean checkTransitionToken(HttpServletRequest request, String transitionToken) {
        if (!StringUtils.hasText(transitionToken)) {
            return false;
        }
        String transitionTokenInSession = (String) request.getSession().getAttribute("transition_token");
        if (!StringUtils.hasText(transitionTokenInSession)) {
            return false;
        }
        if (!transitionTokenInSession.equals(transitionToken)) {
            return false;
        }
        this.generateAndSetTransitionToken(request);
        return true;
    }

    private void generateAndSetTransitionToken(HttpServletRequest request) {
//        byte[] values = new byte[128];
//        SecureRandom random = new SecureRandom();
//        random.nextBytes(values);
//        return new String(values);
        final long l = System.currentTimeMillis();
        request.getSession().setAttribute("transition_token", Long.toString(l));
    }

    private void removeAllTransitionToken(HttpServletRequest request) {
        request.getSession().removeAttribute("transition_token");
    }
}
