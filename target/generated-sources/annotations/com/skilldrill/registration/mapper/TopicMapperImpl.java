package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.TopicDto;
import com.skilldrill.registration.model.Topic;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-13T10:46:56+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_311 (Oracle Corporation)"
)
@Component
public class TopicMapperImpl implements TopicMapper {

    @Override
    public TopicDto toDto(Topic topic) {
        if ( topic == null ) {
            return null;
        }

        TopicDto topicDto = new TopicDto();

        topicDto.setId( topic.getId() );
        topicDto.setTopicName( topic.getTopicName() );
        topicDto.setContent( topic.getContent() );
        topicDto.setContentWriter( topic.getContentWriter() );
        topicDto.setCreatedAt( topic.getCreatedAt() );

        return topicDto;
    }

    @Override
    public Topic toTopic(TopicDto topicDto) {
        if ( topicDto == null ) {
            return null;
        }

        Topic topic = new Topic();

        topic.setId( topicDto.getId() );
        topic.setTopicName( topicDto.getTopicName() );
        topic.setContent( topicDto.getContent() );
        topic.setContentWriter( topicDto.getContentWriter() );
        topic.setCreatedAt( topicDto.getCreatedAt() );

        return topic;
    }
}
