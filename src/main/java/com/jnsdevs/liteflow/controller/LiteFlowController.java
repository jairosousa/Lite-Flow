package com.jnsdevs.liteflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnsdevs.liteflow.model.Contxt;
import com.jnsdevs.liteflow.service.AopProxyUtils;
import com.jnsdevs.liteflow.service.ComponentAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Autor Jairo Nascimento
 * @Created 21/10/2024 - 09:42
 */
@RestController
@Slf4j
public class LiteFlowController {
    @GetMapping("/test/chain")
    public String test(@RequestParam Map<String, String> requestParams) throws JsonProcessingException {
        // Get index
        String index = requestParams.get("index");
        // Split the incoming index string into a list
        String[] split = index.split(",");
        Contxt contxt = new Contxt();
        for (String s : split) {
            ComponentAbstract component = (ComponentAbstract) AopProxyUtils.getProxyBean(s);
            component.handlerRequest(contxt);
        }
        return new ObjectMapper().writeValueAsString(contxt);
    }
}
