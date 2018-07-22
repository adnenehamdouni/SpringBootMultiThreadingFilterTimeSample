package com.spring.example.resource;

import com.spring.example.model.User;
import com.spring.example.services.HelloService;
import com.spring.example.services.TimerFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by adnenehamdouni on 18/07/2018.
 */

@Slf4j
@RestController
public class HelloResource {

    @Autowired
    private TimerFilter timerFilterService;

    @Autowired
    private HelloService helloService;


    @Autowired
    private TimerFilter timerFilter;

    //url -> http://localhost:8080
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    //url -> http://localhost:8080/sayHello
    @RequestMapping("/sayHello")
    public String sayHello() {
        return helloService.sayHello();
    }

    //url -> http://localhost:8080/timer
    @RequestMapping("/timer")
    public String timerFilter() {

        log.debug("One quotation managed by thread " + Thread.currentThread().getName());

        timerFilter.reset();

        HashMap<Long, User> userList = helloService.getUsers();

        timerFilter.logTimerbyService();


        return timerFilterService.test();
    }

}
