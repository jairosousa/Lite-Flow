# Lite-Flow [![Maven Package](https://github.com/jairosousa/Lite-Flow/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/jairosousa/Lite-Flow/actions/workflows/maven-publish.yml)
Projeto Spring Boot Implementando Desacoplamento e Orquestração Dinâmica de Interfaces Complexas Baseadas no Padrão de Cadeia de Responsabilidade

## I. Background
1. Há uma interface OpenAPI fornecida aos clientes (sistemas upstream) no projeto.
2. Esta interface inclui mais de uma dúzia de funcionalidades, tais como: validação de entrada, validação de configuração do sistema, armazenamento de dados básicos, armazenamento de dados principais, envio para o centro de mensagens, envio para o MQ, etc.
3. Diferentes clientes têm diferentes requisitos para esta interface; algumas funcionalidades não são necessárias, enquanto outras exigem adições específicas.

## II. Abordagem
1. Com base no contexto acima, consideramos dividir as doze funcionalidades em componentes independentes. Portanto, usamos o padrão Chain of Responsibility para implementar isso.
2. Crie uma classe abstrata (ComponentAbstract.java), e cada funcionalidade dividida herdará dessa classe abstrata para formar subclasses.
3. Ao criar subclasses, o nome da classe precisa ser definido na @Component("1")anotação; se não for definido, será usado o nome padrão (camelCase).
4. A comunicação entre subclasses usa uma classe de contexto personalizada (Contxt.java), que permite que as subclasses modifiquem dados de contexto (desacoplamento de negócios).
5. Por meio de uma ordem de execução predefinida, o Spring ApplicationContext pode ser usado para recuperar objetos de subclasse em um loop com base em nomes de subclasse e executar o handlerRequest()método na classe abstrata.
6. A “ordem de execução predefinida” pode ser armazenada em um banco de dados e carregada na memória quando o projeto inicia, ou mantida diretamente no Redis. Aqui, uma interface é usada para demonstração: http://localhost:8080/test/chain?index=2,1,3,4 .

## III. Código
1. Dependências do Maven, sem dependências especiais; FastJSON é usado para testes e registros.
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.76</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.12</version>
</dependency>
```
2. ComponentAbstract.java : Classe abstrata que implementa a base da cadeia de responsabilidade.
```java
import com.liran.middle.liteflow.slot.Contxt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
​
/**
 * Component abstract class
 */
@Slf4j
public abstract class ComponentAbstract {
​
    public void handlerRequest(Contxt contxt) {
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
    abstract public void doHandler(Contxt contxt);
}
```
3. Test1.java : Classe de negócio 1, herda a classe abstrata e implementa o `doHandler()` método, definindo o nome da classe como 1 em `@Component`.
```java
import com.alibaba.fastjson.JSON;
import com.liran.middle.liteflow.slot.Contxt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
​
@Component("1")
@Slf4j
public class Test1 extends ComponentAbstract {
    @Override
    public void doHandler(Contxt contxt) {
        log.info("Test1-Order 1-Context content: {}", JSON.toJSONString(contxt));
        contxt.setName("Test1");
        contxt.setAge("Test1");
        contxt.setAdrss("Test1");
        contxt.setUserid("Test1");
    }
}
```
4. Test2.java : Classe de negócio 2, herda a classe abstrata e implementa o `doHandler()` método, definindo o nome da classe como 2 em `@Component`.
```java
import com.alibaba.fastjson.JSON;
import com.liran.middle.liteflow.slot.Contxt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
​
@Component("2")
@Slf4j
public class Test2 extends ComponentAbstract {
​
    @Override
    public void doHandler(Contxt contxt) {
        log.info("Test2-Order 2-Context content: {}", JSON.toJSONString(contxt));
        contxt.setName("Test2");
        contxt.setAge("Test2");
        contxt.setAdrss("Test2");
        contxt.setUserid("Test2");
    }
}
```
5. Test3.java : Classe de negócio 3, herda a classe abstrata e implementa o `doHandler()` método, definindo o nome da classe como 3 em `@Component`.
```java
import com.alibaba.fastjson.JSON;
import com.liran.middle.liteflow.slot.Contxt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
​
@Component("3")
@Slf4j
public class Test3 extends ComponentAbstract {
    @Override
    public void doHandler(Contxt contxt) {
        log.info("Test3-Order 3-Context content: {}", JSON.toJSONString(contxt));
        contxt.setName("Test3");
        contxt.setAge("Test3");
        contxt.setAdrss("Test3");
        contxt.setUserid("Test3");
    }
}
```
6. Test4.java : Classe de negócio 4, herda a classe abstrata e implementa o `doHandler()` método, definindo o nome da classe como 4 em `@Component`.
```java
import com.alibaba.fastjson.JSON;
import com.liran.middle.liteflow.slot.Contxt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
​
@Component("4")
@Slf4j
public class Test4 extends ComponentAbstract {
​
    @Override
    public void doHandler(Contxt contxt) {
        log.info("Test4-Order 4-Context content: {}", JSON.toJSONString(contxt));
        contxt.setName("Test4");
        contxt.setAge("Test4");
        contxt.setAdrss("Test4");
        contxt.setUserid("Test4");
    }
}
```
7. **Contxt.java** : Contexto de negócios, usado para comunicação de dados entre subclasses (pontos funcionais). Campos adicionais podem ser adicionados a esta classe para escrever dados, que podem ser lidos posteriormente por classes em execução.
```java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
​
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contxt {
    private String name;
    private String age;
    private String adrss;
    private String userid;
}
```
8. **AopProxyUtils.java** : Contexto gerenciado pelo Spring para recuperar entidades de classe por nome.
```java
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
​
@Component
public class AopProxyUtils implements ApplicationContextAware {
​
    private static ApplicationContext applicationContext;
​
    /**
     * Implements the setApplicationContext method of ApplicationContextAware interface,
     * used to inject ApplicationContext.
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }
​
    /**
     * Gets the proxy object of the specified class, suitable for scenarios requiring transactions or other AOP enhancements.
     *
     * @param clazz Class of the object to be retrieved
     * @param <T>   Generic type indicator
     * @return Proxy object instance
     */
    public static <T> T getProxyBean(Class<T> clazz) {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext not initialized.");
        }
        return applicationContext.getBean(clazz);
    }
​
    public static Object getProxyBean(String name) {
        return applicationContext.getBean(name);
    }
}
```
9. LiteFlowController.java : Usado para testes, demonstrando como orquestrar dinamicamente. Chame a interface http://localhost:8082/test/chain?index=2,1,3,4 para passar diferentes ordens de índice, afetando a ordem de execução da lógica de negócios.

```java
import com.alibaba.fastjson.JSON;
import com.liran.middle.liteflow.component.pattern.chain.ComponentAbstract;
import com.liran.middle.liteflow.slot.Contxt;
import com.liran.middle.liteflow.utils.Aop
​
ProxyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
​
import java.util.List;
import java.util.Map;
​
@RestController
@Slf4j
public class LiteFlowController {
​
    @GetMapping("/test/chain")
    public String test(@RequestParam Map<String, String> requestParams) {
        // Get index
        String index = requestParams.get("index");
        // Split the incoming index string into a list
        String[] split = index.split(",");
        Contxt contxt = new Contxt();
        for (String s : split) {
            ComponentAbstract component = (ComponentAbstract) AopProxyUtils.getProxyBean(s);
            component.handlerRequest(contxt);
        }
        return JSON.toJSONString(contxt);
    }
}
```
## Executando o Projeto
1. Inicie o projeto Spring Boot.
2. Use o navegador para acessar a URL http://localhost:8082/test/chain?index=2,1,3,4para testar a funcionalidade e verificar os logs.

## V. Conclusão
Esta implementação desvincula com sucesso a funcionalidade por meio do padrão Cadeia de Responsabilidade, permitindo uma orquestração dinâmica com base nos diferentes requisitos do cliente.

Cada lógica de negócios é encapsulada dentro de sua classe, permitindo fácil manutenção e escalabilidade.
