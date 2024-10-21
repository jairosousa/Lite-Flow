package com.jnsdevs.liteflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnsdevs.liteflow.model.Contxt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Autor Jairo Nascimento
 * @Created 21/10/2024 - 09:28
 */
@Component("1")
@Slf4j
public class Test1 extends ComponentAbstract{
    @Override
    public void doHandler(Contxt contxt) throws JsonProcessingException {
        log.info("Test1-Order 1-Context content: {}", new ObjectMapper().writeValueAsString(contxt));
        contxt.setName("Test1");
        contxt.setAge("Test1");
        contxt.setAdrss("Test1");
        contxt.setUserid("Test1");
    }
}
