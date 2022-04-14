package com.skilldrill.registration.service.impl;

import com.skilldrill.registration.dto.TopicDto;
import com.skilldrill.registration.exceptions.NotFoundException;
import com.skilldrill.registration.mapper.TopicMapper;
import com.skilldrill.registration.model.Skills;
import com.skilldrill.registration.model.Topic;
import com.skilldrill.registration.repository.SkillsRepository;
import com.skilldrill.registration.repository.TopicRepository;
import com.skilldrill.registration.repository.UserRepository;
import com.skilldrill.registration.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    @Autowired
    private final TopicRepository topicRepository;
    @Autowired
    private SkillsRepository skillsRepository;
    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;


    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Topic addTopic(TopicDto topicDto) {
        Topic topic = topicMapper.toTopic(topicDto);
        // boolean topicNameExist =topicRepository.findByTopicName(topicDto.getTopicName()).isPresent();
            Skills skill = skillsRepository.findBySkillName(topicDto.getSkillName())
                    .orElseThrow(() -> new NotFoundException("Skill not found"));
            topic.setSkills(skill);
            if (isEmpty(topicDto.getParentTopicName())) {
                topic.setParentTopic(null);
            }
            else {
                Topic parentTopic = topicRepository.findByTopicName(topicDto.getParentTopicName())
                        .orElseThrow(()->new NotFoundException("ParentTopicName not found"));
                topic.setParentTopic(parentTopic);
            }
        return topicRepository.save(topic);
    }

    @Override
    public List<Topic> getAllTopics() {

        return topicRepository.findAll();
    }

    @Override
    public void deleteTopic(String topicName) {
        Topic topic = topicRepository.findByTopicName(topicName)
                .orElseThrow(() -> new NotFoundException("Topic not found"));
        topicRepository.delete(topic);
    }

    @Override
    public List<Topic> getTopicsBySkill(String skillName) {
        Skills skill = skillsRepository.findBySkillName(skillName)
                .orElseThrow(() -> new NotFoundException("SkillName not found"));
        return topicRepository.findTopicBySkills(skill);
    }


    @Override
    public List<Topic> getAllSubTopicsByTopicName(String parentTopicName) {
        Topic parentTopic = getTopicByTopicName(parentTopicName);
        return topicRepository.findTopicByParentTopic(parentTopic);
    }

    @Override
    public Topic getTopicByTopicName(String topicName) {
        Topic topic = topicRepository.findByTopicName(topicName)
                .orElseThrow(() -> new NotFoundException("TopicName not found"));

        return topic;
    }
}
