package com.webflux.demo.service.impl;

import com.webflux.demo.dto.request.UserRequest;
import com.webflux.demo.dto.response.UserResponse;
import com.webflux.demo.exception.NotFoundException;
import com.webflux.demo.mapper.UserMapper;
import com.webflux.demo.repository.UserRepository;
import com.webflux.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public Flux<UserResponse> getUsers() {
        return userRepository.findAll().map(user -> userMapper.toResponse(user));
    }

    @Override
    public Mono<UserResponse> getUser(Long userId) {
        return userRepository.findById(userId).map(user -> userMapper.toResponse(user));
    }

    @Override
    public Mono<UserResponse> createUser(UserRequest userRequest) {
       return userRepository.save(userMapper.toEntity(userRequest))
               .map(user -> userMapper.toResponse(user));
    }

    @Override
    public Mono<UserResponse> updateUser(Long userId, UserRequest userRequest) {

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("User Not Found " + userId)))

                .flatMap(user -> {
                    user.setAddress(userRequest.getAddress());
                    user.setAge(userRequest.getAge());
                    user.setName(userRequest.getName());

                    return userRepository.save(user);
                })

                .map(user -> userMapper.toResponse(user));
    }

    @Override
    public Mono<Void> deleteUser(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("User Not Found " + userId)))

                .flatMap(user -> userRepository.deleteById(userId));
    }
}
