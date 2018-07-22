package com.spring.example.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by adnenehamdouni on 18/07/2018.
 * Filter used to calculate time passed in services
 */

//TODO works only with one thread !

@Slf4j
@Service
public class TimerFilter implements ClientRequestFilter, ClientResponseFilter {


    private Map<String, MutablePair<Long, Long>> timers = Collections.synchronizedMap(new LinkedHashMap<>());

    @Autowired
    @Qualifier("tracking")
    private ThreadLocal<String> trackingId;

    private String getKey(ClientRequestContext context) {
        return StringUtils.defaultIfBlank(trackingId.get(), "NONE") + "#" + context.getMethod() + " " + context.getUri().toString();
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        String key = getKey(clientRequestContext);
        MutablePair<Long, Long> time = timers.computeIfAbsent(key, k -> new MutablePair<>());
        time.setLeft(System.nanoTime());
        log.debug("CISL start : {}", key);

    }

    @Override
    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) throws IOException {
        String key = getKey(clientRequestContext);
        MutablePair<Long, Long> time = timers.computeIfAbsent(key, k -> new MutablePair<>());
        time.setRight(System.nanoTime());
        log.debug("CISL end : {}", key);
    }

    public void reset() {
        timers.clear();
    }

    public String test() {
        return "Hello World! test from TimerFilter Service Work Fine :) Greet Job";
    }

    public void logTimerbyService() {

        Map<String, Map<String, Double>> result = new TreeMap<>();

        for (Map.Entry<String, MutablePair<Long, Long>> entry : timers.entrySet()) {
            String key = entry.getKey();

            int target = key.indexOf('#');
            String trackingId = key.substring(0, target);
            String call = key.substring(target + 1);

            Map<String, Double> history = result.computeIfAbsent(trackingId, k -> new LinkedHashMap<>());
            if(entry.getValue().getLeft() != null && entry.getValue().getRight() != null) {
                history.put(call, (entry.getValue().getRight() - entry.getValue().getLeft()) / (double) 1_000_000_000);
            }
        }


        double count = 0;

        for (Map.Entry<String, Map<String, Double>> call : result.entrySet()) {
            log.debug("===== Session " + call.getKey() + " =====");
            for (Map.Entry<String, Double> entry : call.getValue().entrySet()) {
                log.debug("Call " + entry.getKey() + " : " + entry.getValue() + " s");
                count += entry.getValue();
            }
            log.debug("Total CISL time : " + count + " s");
            count = 0;
        }
    }
}
