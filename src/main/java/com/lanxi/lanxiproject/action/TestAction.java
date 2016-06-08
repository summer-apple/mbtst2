package com.lanxi.lanxiproject.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by summer on 16/4/8.
 */
@RestController
@RequestMapping("/test")
public class TestAction {



    @RequestMapping("/t")
    public String test(){
        return "妈的智障...";
    }


    @RequestMapping("/t2")
    public ModelAndView test2(ModelAndView mv){
        mv.setViewName("/content/test");
        return mv;
    }

}
