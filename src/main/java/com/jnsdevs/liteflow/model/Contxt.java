package com.jnsdevs.liteflow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Autor Jairo Nascimento
 * @Created 21/10/2024 - 09:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contxt{
    private String name;
    private String age;
    private String adrss;
    private String userid;

}
