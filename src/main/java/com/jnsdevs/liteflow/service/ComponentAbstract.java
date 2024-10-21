package com.jnsdevs.liteflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jnsdevs.liteflow.model.Contxt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

/**
 * @Autor Jairo Nascimento
 * @Created 21/10/2024 - 09:24
 */
@Slf4j
public abstract class ComponentAbstract {
    public void handlerRequest(Contxt contxt) throws JsonProcessingException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // Execute subclass business logic
        this.doHandler(contxt);
        stopWatch.stop();
        long cost = stopWatch.getTotalTimeMillis();
        if (cost <= 10) {
            log.info("-----------Monitoring method execution time, executed {} method, time is excellent: {} ms -----------", getClass(), cost);
        } else if (cost <= 50) {
            log.info("-----------Monitoring method execution time, executed {} method, time is average: {} ms -----------", getClass(), cost);
        } else if (cost <= 500) {
            log.info("-----------Monitoring method execution time, executed {} method, time is delayed: {} ms -----------", getClass(), cost);
        } else if (cost <= 1000) {
            log.info("-----------Monitoring method execution time, executed {} method, time is slow: {} ms -----------", getClass(), cost);
        } else {
            log.info("-----------Monitoring method execution time, executed {} method, time is stalled: {} ms -----------", getClass(), cost);
        }
    }
     public abstract void doHandler(Contxt contxt) throws JsonProcessingException;
}
