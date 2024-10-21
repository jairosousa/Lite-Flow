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
@Component("3")
@Slf4j
public class Test3 extends ComponentAbstract{
    @Override
    public void doHandler(Contxt contxt) throws JsonProcessingException {
        log.info("Test3-Order 1-Context content: {}", new ObjectMapper().writeValueAsString(contxt));
        contxt.setName("Test3");
        contxt.setAge("Test3");
        contxt.setAdrss("Test3");
        contxt.setUserid("Test3");
    }
}
