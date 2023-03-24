package com.king.yori.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.king.yori.repository.dto.UserDto;
import com.king.yori.repository.entity.User;

//@Configuration
public class EntityMapper {
    private final ModelMapper modelMapper = new ModelMapper();
//    @Bean
    public ModelMapper loginMapper() {
        // 매핑 전략 설정
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(User.class, UserDto.LoginRequest.class)
                .addMapping(User::getLoginId, UserDto.LoginRequest::setLoginId)
                .addMapping(User::getPassword, UserDto.LoginRequest::setPassword);
        return modelMapper;
    }
    
}

