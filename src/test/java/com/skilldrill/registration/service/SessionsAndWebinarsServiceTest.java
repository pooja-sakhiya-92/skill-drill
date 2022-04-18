package com.skilldrill.registration.service;

import com.skilldrill.registration.mapper.SessionsAndWebinarsMapper;
import com.skilldrill.registration.model.SessionsAndWebinars;
import com.skilldrill.registration.model.Topic;
import com.skilldrill.registration.repository.SessionsAndWebinarsRepository;
import com.skilldrill.registration.repository.TopicRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class SessionsAndWebinarsServiceTest {

    @MockBean
    TopicRepository topicRepository;

    @MockBean
    SessionsAndWebinarsRepository sessionsAndWebinarsRepository;

    @Autowired
    SessionsAndWebinarsMapper sessionsAndWebinarsMapper;

    @Autowired
    SessionsAndWebinarsService sessionsAndWebinarsService;

    @Autowired
    MessageSource messageSource;

    public static SessionsAndWebinars dummyData() {
        SessionsAndWebinars sessionsAndWebinars =
                new SessionsAndWebinars
                        ("12abcd","https://meet.google.com/psz-csua-fgh","please join the call to learn the topic","9 am","1 hour","14-04-2022",dummyTopicDetails());
        return sessionsAndWebinars;
    }

    public static Topic dummyTopicDetails() {
        Topic topic = new Topic();
        topic.setTopicName("The summary of clean code");
        topic.setContent("blah  blah blah....");
        topic.setContentWriter("Bernard Schultz");
        topic.setCreatedAt("05/04/2022");
        return topic;
    }
    @Before
    public void setUp() {
        when(sessionsAndWebinarsRepository.save(Mockito.any())).thenReturn(dummyData());
        when(sessionsAndWebinarsRepository.findById(Mockito.any())).thenReturn(Optional.of(dummyData()));
        when(topicRepository.findByTopicName(Mockito.any())).thenReturn(Optional.of(dummyTopicDetails()));

    }
    @Test
    public void testAddSessionsAndWebinars() {
        SessionsAndWebinars sessionsAndWebinars = sessionsAndWebinarsService.add(sessionsAndWebinarsMapper.toDto(dummyData()));
        assertEquals("9 am",sessionsAndWebinars.getStartTime());
    }

    @Test
    public void testDeleteSession() {
        SessionsAndWebinars sessionsAndWebinars = dummyData();
        sessionsAndWebinarsService.delete("12abcd");
        verify(sessionsAndWebinarsRepository, times(1)).delete(sessionsAndWebinars);
    }

    @Test
    public void testGetAllSessionsOfTopic() {
        List<SessionsAndWebinars> sessionsList = sessionsAndWebinarsService.findAllByTopic("The summary of clean code");
        sessionsList.add(dummyData());
        when(sessionsAndWebinarsRepository.findAll()).thenReturn(sessionsList);
        assertFalse(sessionsList.isEmpty());
        assertEquals(1,sessionsList.size());

    }

}
