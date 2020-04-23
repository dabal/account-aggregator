package pl.dabal.accountaggregator.controller;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dabal.accountaggregator.model.User;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping("/get")
    public User getUserDetails(){
        User user=new User(2L,"nazwa","email@testowy.pl","a123H12aa");
    log.error("dfsdfsd");
        return user;
    }
}
