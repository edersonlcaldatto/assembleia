package com.dbc.assembleia.config;

import com.dbc.assembleia.datasource.invertexto.InvertextoClient;
import com.dbc.assembleia.datasource.invertexto.fallback.InvertextoClientFallback;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class InvertextoClientConfig {

    @Value("${invertexto.api}")
    private String url;
    private final String SERVICE_NAME = "Invertexto";

    @Bean
    public InvertextoClient getInvertextoClientConfig(ObjectFactory<HttpMessageConverters> messageConverters) {

        var decorators = FeignDecorators.builder()
                .withFallbackFactory(InvertextoClientFallback::new)
                .build();

        return Resilience4jFeign
                .builder(decorators)
                .client(new OkHttpClient())
                .contract(new SpringMvcContract())
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters))
                .logLevel(Logger.Level.FULL)
                .retryer(Retryer.NEVER_RETRY)
                .logger(new Slf4jLogger())
                .options(
                        new Request.Options(
                                20, TimeUnit.SECONDS,
                                20, TimeUnit.SECONDS, false))
                .target(InvertextoClient.class, url);
    }




}
