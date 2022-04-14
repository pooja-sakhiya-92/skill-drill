package com.skilldrill.registration.service;

import com.skilldrill.registration.model.Categories;
import com.skilldrill.registration.model.Skills;
import com.skilldrill.registration.model.Topic;
import com.skilldrill.registration.repository.TopicRepository;
import com.skilldrill.registration.service.impl.TopicServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Autowired
    private TopicService topicService;

    @BeforeEach
    void initUseCase() {
        topicService = new TopicServiceImpl(topicRepository);
    }


    public static Topic dummyTopic() {
        Skills skill = new Skills("1", new Categories("11", null, "Backend", "Software", "Null"), "Java", "Backend Development Language");

        Topic topic = new Topic();
        topic.setTopicName("The summary of clean code");
        topic.setContent("blah  blah blah....");
        topic.setContentWriter("Bernard Schultz");
        topic.setCreatedAt("05/04/2022");
        topic.setSkills(skill);
        return topic;
    }

    @Test
    public void saveTopicTest() {
        when(topicRepository.save(any(Topic.class))).thenReturn(dummyTopic());

        assertThat(dummyTopic().getTopicName()).isNotNull();
    }


    @Test
    public void fetchAllTopicsTest() {
        List<Topic> topicList = new ArrayList<>();
        topicList.add(dummyTopic());

        when(topicRepository.findAll()).thenReturn(topicList);

        assertThat(topicRepository.findAll().size()).isGreaterThan(0);
    }

    @Test
    public void archiveTopicTest() {
        Topic topic = dummyTopic();
        topicRepository.delete(topic);
        verify(topicRepository, times(1)).delete(topic);

    }

}

