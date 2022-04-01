package com.skilldrill.registration.service.impl;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.CreativeAreaDto;
import com.skilldrill.registration.enums.CreativeSkills;
import com.skilldrill.registration.exceptions.NotFoundException;
import com.skilldrill.registration.mapper.CreativeAreaMapper;
import com.skilldrill.registration.model.CreativeArea;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.CreativeAreaRepository;
import com.skilldrill.registration.repository.UserRepository;
import com.skilldrill.registration.service.CreativeAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CreativeAreaServiceImpl implements CreativeAreaService {

    @Autowired
    private CreativeAreaRepository creativeAreaRepository;

    @Autowired
    private CreativeAreaMapper creativityMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public CreativeArea addCreativity(CreativeAreaDto creativityDto,String userName) {

        CreativeArea creativiteArea = creativityMapper.toCreativeArea(creativityDto);
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        creativiteArea.setUser(user);
        return creativeAreaRepository.save(creativiteArea);
    }

    @Override
    public CreativeAreaDto updateCreativity(String creativeSkill, CreativeAreaDto creativityDto) {
        CreativeArea creativity = creativeAreaRepository.findByCreativeSkill(creativeSkill)
                .orElseThrow(() -> new NotFoundException("No Creativity present"));
        creativity.setCreativeSkill(CreativeSkills.valueOf(creativityDto.getCreativeSkill()));
        creativity.setAchievements(creativityDto.getAchievements());
        creativity.setSpecialization(creativityDto.getSpecialization());
        creativeAreaRepository.save(creativity);

        return creativityMapper.toDto(creativity);
    }

    @Override
    public void deleteCreativeArea(String creativeSkill,String userName) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        CreativeArea creativeArea = creativeAreaRepository.findByCreativeSkill(creativeSkill)
                .orElseThrow(() -> new NotFoundException("No creativeAreas present"));
        creativeAreaRepository.delete(creativeArea);

    }

    @Override
    public List<CreativeArea> findAllCreativeAreas(String userName) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        return creativeAreaRepository.findByUser(user);

    }


}
