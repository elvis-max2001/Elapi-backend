package com.yupi.elapiclientsdk;

import com.yupi.elapiclientsdk.client.ElApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//告诉Spring Boot从配置文件（application.yml）中读取以"elapi.client"为前缀的属性，并将这些属性的值自动注入到ElApiClientConfig类的对应属性中。
@ConfigurationProperties("elapi.client")
@Data
@ComponentScan
public class ElApiClientConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public ElApiClient elApiClient(){
        //创建实例
        return new ElApiClient(accessKey,secretKey);
    }
}
