package com.soecode.lyf.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Evan.Li
 */
@RestController
@RequestMapping("/Demo")
public class DemoController {

    @RequestMapping(value = "/lrj",method = RequestMethod.GET)
    private @ResponseBody
    ResponseEntity lrj(){
        return new ResponseEntity("Hello!", HttpStatus.OK);
    }
}
