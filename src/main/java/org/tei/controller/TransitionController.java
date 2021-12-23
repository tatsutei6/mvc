package org.tei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
            request.getSession().setAttribute("owner_change_transition_token", this.generateTransitionToken());
            model.addAttribute("dest", "/owner_info_change");
        }
        if ("2".equals(flag)) {
            model.addAttribute("flag", "マイクロチップ情報変更");
            request.getSession().setAttribute("mcinfo_change_transition_token", this.generateTransitionToken());
            model.addAttribute("dest", "/mcinfo_change");
        }
        if ("3".equals(flag)) {
            model.addAttribute("flag", "情報変更");
            model.addAttribute("dest", "/info_change");
            request.getSession().setAttribute("info_change_transition_token", this.generateTransitionToken());
        }
        return "terms";
    }

    @PostMapping(value = "/owner_info_change1_init")
    public String ownerChange1Init(HttpServletRequest request, String transitionToken) {
        boolean isValid = this.checkTransitionToken(request, transitionToken);
        if (!isValid) {
            return "error";
        }
        return "owner_change_1";
    }

    @PostMapping(value = "/owner_info_change2_init")
    public String ownerChange2Init(HttpServletRequest request, String transitionToken) {
        boolean isValid = this.checkTransitionToken(request, transitionToken);
        if (!isValid) {
            return "error";
        }
        return "owner_change_2";
    }

    @PostMapping(value = "/owner_info_change3_init")
    public String ownerChange3Init(HttpServletRequest request, String transitionToken) {
        boolean isValid = this.checkTransitionToken(request, transitionToken);
        if (!isValid) {
            return "error";
        }
        return "owner_change_3";
    }

    @PostMapping(value = "/owner_info_change4_init")
    public String ownerChange4Init(HttpServletRequest request, String transitionToken) {
        boolean isValid = this.checkTransitionToken(request, transitionToken);
        if (!isValid) {
            return "error";
        }
        return "owner_change_4";
    }

    @PostMapping(value = "/success_init")
    public String ownerChangeRegister(HttpServletRequest request, String transitionToken) {
        boolean isValid = this.checkTransitionToken(request, transitionToken);
        if (!isValid) {
            return "error";
        }
        return "success";
    }

    private boolean checkTransitionToken(HttpServletRequest request, String transitionToken) {
        if (!StringUtils.hasText(transitionToken)) {
            return false;
        }
        String transitionTokenInSession = (String) request.getSession().getAttribute("owner_change_transition_token");
        if (!StringUtils.hasText(transitionTokenInSession)) {
            return false;
        }
        if (!transitionTokenInSession.equals(transitionToken)) {
            return false;
        }
        return true;
    }

    private String generateTransitionToken() {
//        byte[] values = new byte[128];
//        SecureRandom random = new SecureRandom();
//        random.nextBytes(values);
//        return new String(values);
        final long l = System.currentTimeMillis();
        return Long.toString(l);
    }

    private void removeAllTransitionToken(HttpServletRequest request) {
        request.getSession().removeAttribute("owner_change_transition_token");
        request.getSession().removeAttribute("mcinfo_change_transition_token");
        request.getSession().removeAttribute("info_change_transition_token");
    }
}
