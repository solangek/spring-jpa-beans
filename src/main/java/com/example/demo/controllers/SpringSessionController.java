package com.example.demo.controllers;

import com.example.demo.beans.Label;
import com.example.demo.beans.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SpringSessionController {

    // check the corresponding code in BeanConfiguration.java
    @Resource(name="autowiredFieldSingletonScope")
    private Label singletonLabel;

    // injection by ctor: match by name
    @Resource(name = "sessionBeanExample")
    private Messages sessionMessages;

    // injection of component by ctor : match by name
    // check the corresponding code in Label.java
    @Resource(name="autowiredLabelDependency")
    private Label label;

    // injection by ctor : match by qualifier (variable name)
    // @Autowired ONLY does not work since there are 4 possibilities
    // check the corresponding code in BeanConfiguration.java
    @Resource(name="autowiredFieldApplicationScope")
    private Label applicationLabel;

    // injecting sesssion scope bean
    // check the corresponding code in BeanConfiguration.java
    @Resource(name="sessionScopeBeanExample")
    private Label sessionLabel;

    @GetMapping("/session")
    public String process(Model model) {
        model.addAttribute("sessionMessages", sessionMessages.getMessages());

        model.addAttribute("mylabel", label);
        model.addAttribute("singletonLabel", singletonLabel);
        model.addAttribute("applicationLabel", applicationLabel);
        return "session";
    }

    /* without SPRING injection  it would look like this:
    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
        List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
        if (messages == null) {
            messages = new ArrayList<>();
            request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
        }
        messages.add(msg);
        request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
        return "redirect:/session";
    }
    */

    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg) {

        sessionMessages.add(msg);
        return "redirect:/session";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/session";
    }
}