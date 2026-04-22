package com.webflux.demo.service;

import com.webflux.demo.dto.request.UserRequest;
import com.webflux.demo.dto.response.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserResponse> getUsers();

    Mono<UserResponse> getUser(Long userId);

    Mono<UserResponse> createUser(UserRequest userRequest);

    Mono<UserResponse> updateUser(Long userId, UserRequest userRequest);

    Mono<Void> deleteUser(Long userId);
}
