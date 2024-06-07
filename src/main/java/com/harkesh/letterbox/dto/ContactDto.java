package com.harkesh.letterbox.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class ContactDto {
    List<UserDto> contacts;
}
