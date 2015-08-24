package com.weshare.thunder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lishaoyan on 2015/4/22.
 *
* just for test!!!!  Don't put any production function here.
 */
@Controller
public class TestController {
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test/hello")
    public String helloSpring(Model model) {
        logger.debug("enter hello");
        model.addAttribute("name", "test");
        return "hello";
    }

}


