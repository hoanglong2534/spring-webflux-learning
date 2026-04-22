package com.webflux.demo.router;

import com.webflux.demo.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@RequiredArgsConstructor
public class UserRouter {

    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler){
        return RouterFunctions
                .route(GET("/users"), userHandler::getUsers)
                .andRoute(GET("/users/{id}"), userHandler::getUser)
                .andRoute(POST("/users"), userHandler::createUser)
                .andRoute(PUT("/users/{id}"), userHandler::updateUser)
                .andRoute(DELETE("/users/{id}"), userHandler::deleteUser);
    }
}
