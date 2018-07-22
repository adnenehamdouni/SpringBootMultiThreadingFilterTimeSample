package com.spring.example.d1;

import com.spring.example.Utils.Constants;
import com.spring.example.model.User;
import com.spring.example.services.HelloService;
import com.spring.example.services.TimerFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by adnenehamdouni on 18/07/2018.
 */

@Slf4j
@RestController
public class UserResource {


    @Autowired
    @Qualifier("tracking")
    private ThreadLocal<String> trackingId;

    @Autowired
    private CalculateService calculateService;


    @Autowired
    private TimerFilter timerFilter;

    //url -> http://localhost:8080
    @RequestMapping("/calculate")
    public String index() {
        return "Greetings from calculate!";
    }

    //url -> http://localhost:8080/timer
    @RequestMapping("/user")
    public String timerFilter() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date begin = new Date();
        log.debug("CalculateOfferEndpoint Start : {}", sdf.format(new Date()));

        //Define a tracking ID, as there's no HTTP session
        trackingId.set(TrackingUtils.generateKey());

        calculateService.calculateQuotations(1, Constants.getUsersToSubmit());

        return "Hello";

    }

}
