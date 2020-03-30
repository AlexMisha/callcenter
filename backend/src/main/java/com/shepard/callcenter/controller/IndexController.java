package com.shepard.callcenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alex Misha
 */
@Controller
public class IndexController {
    @RequestMapping(value = { "/", "/index", "/login" }, method = RequestMethod.GET)
    public String index() {
        return "/";
    }
}
