package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.TopicDto;
import com.skilldrill.registration.model.Topic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    TopicDto toDto(Topic topic);

    Topic toTopic(TopicDto topicDto);
}
