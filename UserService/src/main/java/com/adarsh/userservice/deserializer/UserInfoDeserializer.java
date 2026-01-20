package com.adarsh.userservice.deserializer;

import com.adarsh.userservice.entities.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Arrays;

public class UserInfoDeserializer implements Deserializer<UserInfoDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserInfoDto deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                throw new SerializationException("Null data received for topic: " + topic);
            }
            UserInfoDto dto = objectMapper.readValue(data, UserInfoDto.class);
            return dto;
        } catch (Exception e) {
            throw new SerializationException("Error deserializing message from topic: " + topic, e);
        }
    }
}
