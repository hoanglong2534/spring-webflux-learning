package com.webflux.demo.repository;

import com.webflux.demo.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
