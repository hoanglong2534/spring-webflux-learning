package com.webflux.demo.dto.request;

import lombok.Data;

@Data
public class UserRequest {

    private String name;

    private Long age;

    private String address;
}
