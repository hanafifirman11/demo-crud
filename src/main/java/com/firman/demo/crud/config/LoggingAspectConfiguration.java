package com.firman.demo.crud.config;

import com.firman.demo.crud.aop.logging.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
//    @Profile({Constant.SPRING_PROFILE_LOCAL, Constant.SPRING_PROFILE_DEVELOPMENT, Constant.SPRING_PROFILE_HEROKU, Constant.SPRING_PROFILE_UAT, Constant.SPRING_PROFILE_SANDBOX})
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
