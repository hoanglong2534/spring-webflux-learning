package com.webflux.demo.handler;

import com.webflux.demo.dto.request.UserRequest;
import com.webflux.demo.dto.response.UserResponse;
import com.webflux.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;

    public Mono<ServerResponse> getUsers(ServerRequest serverRequest){
        return ServerResponse
                .ok()
                .body(userService.getUsers(), UserResponse.class);
    }

    public Mono<ServerResponse> getUser(ServerRequest serverRequest){
        Long userId = Long.valueOf(serverRequest.pathVariable("id"));

        return ServerResponse
                .ok()
                .body(userService.getUser(userId), UserResponse.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UserRequest.class)
                .flatMap(user -> userService.createUser(user))
                .flatMap(userResponse -> ServerResponse.ok().bodyValue(userResponse));
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest){
        Long userId = Long.valueOf(serverRequest.pathVariable("id"));
        return serverRequest.bodyToMono(UserRequest.class)
                .flatMap(user-> userService.updateUser(userId, user))
                .flatMap(userResponse -> ServerResponse.ok().bodyValue(userResponse));
    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest){
        Long userId = Long.valueOf(serverRequest.pathVariable("id"));
        return ServerResponse
                .ok()
                .body(userService.deleteUser(userId), Void.class);

    }
}
