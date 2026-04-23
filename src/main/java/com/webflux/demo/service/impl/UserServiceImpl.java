package com.webflux.demo.service.impl;

import com.webflux.demo.dto.request.UserRequest;
import com.webflux.demo.dto.response.UserResponse;
import com.webflux.demo.entity.User;
import com.webflux.demo.exception.NotFoundException;
import com.webflux.demo.mapper.UserMapper;
import com.webflux.demo.repository.UserRepository;
import com.webflux.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;


    @Override
    public Flux<UserResponse> getUsers() {
        return userRepository.findAll().map(user -> userMapper.toResponse(user));
    }

    @Override
    public Mono<UserResponse> getUser(Long userId) {

        String key = "user:" + userId;

        return reactiveRedisTemplate.opsForValue()
                .get(key)
                .map(user -> (User) user)
                .map(user -> userMapper.toResponse(user))
                .switchIfEmpty(
                        userRepository.findById(userId)
                                .switchIfEmpty(Mono.error(new NotFoundException("User Not Found " + userId)))
                                .flatMap(entity -> reactiveRedisTemplate.opsForValue()
                                        .set(key, entity, Duration.ofMinutes(10)).thenReturn(userMapper.toResponse(entity)))
                );

    }

    @Override
    public Mono<UserResponse> createUser(UserRequest userRequest) {
       return userRepository.save(userMapper.toEntity(userRequest))
               .map(user -> userMapper.toResponse(user));
    }

    @Override
    public Mono<UserResponse> updateUser(Long userId, UserRequest userRequest) {
        String key = "user:" + userId;

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("User Not Found " + userId)))
                .flatMap(user -> {
                    user.setName(userRequest.getName());
                    user.setAddress(userRequest.getAddress());
                    user.setAge(userRequest.getAge());
                    return userRepository.save(user)
                            .flatMap(userSave -> reactiveRedisTemplate.opsForValue()
                                    .set(key, userSave, Duration.ofMinutes(10))
                                    .thenReturn(userMapper.toResponse(userSave))
                            );
                });
    }



    @Override
    public Mono<Void> deleteUser(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("User Not Found " + userId)))

                .flatMap(user -> userRepository.deleteById(userId));
    }
}
