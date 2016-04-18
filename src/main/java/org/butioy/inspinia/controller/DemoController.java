package org.butioy.inspinia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by butioy on 2016/3/30.
 */
@Controller
public class DemoController {

    @RequestMapping("/demo")
    public String demo() {
        System.out.println("demo tiles!!");
        return "demoView";
    }

}
