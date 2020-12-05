package com.my.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/12/05 15:19
 */
@Configuration
public class RestHighLevelConfig extends AbstractElasticsearchConfiguration {
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        ClientConfiguration clientConfiguration= ClientConfiguration.builder()
                .connectedTo("192.168.41.155")//es 主机或端口
                .build();
    return RestClients.create(clientConfiguration).rest();
    }
}
