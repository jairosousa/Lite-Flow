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
@Component("4")
@Slf4j
public class Test4 extends ComponentAbstract{
    @Override
    public void doHandler(Contxt contxt) throws JsonProcessingException {
        log.info("Test3-Order 1-Context content: {}", new ObjectMapper().writeValueAsString(contxt));
        contxt.setName("Test4");
        contxt.setAge("Test4");
        contxt.setAdrss("Test4");
        contxt.setUserid("Test4");
    }
}
