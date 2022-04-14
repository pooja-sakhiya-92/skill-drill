package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.TopicDto;
import com.skilldrill.registration.model.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TopicService {

    List<Topic> getAllTopics();

    Topic addTopic(TopicDto topicDto);

    void deleteTopic(String topicName);

    List<Topic> getTopicsBySkill(String skillName);

    List<Topic> getAllSubTopicsByTopicName(String parentTopicName);

    public Topic getTopicByTopicName(String topicName);
}
