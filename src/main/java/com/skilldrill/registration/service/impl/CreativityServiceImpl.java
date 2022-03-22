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
import com.skilldrill.registration.service.CreativityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CreativityServiceImpl implements CreativityService {

    @Autowired
    private CreativeAreaRepository creativeAreaRepository;

    @Autowired
    private CreativeAreaMapper creativityMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public CreativeArea addCreativity(CreativeAreaDto creativityDto) {

        CreativeArea creativity = creativityMapper.toCreativeArea(creativityDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).get();
        creativity.setUser(user);
        creativity.setCreativeSkill(CreativeSkills.valueOf(creativityDto.getCreativeSkill()));
        System.out.println(username);
        return creativeAreaRepository.save(creativity);
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
    public List<CreativeAreaDto> findAllCreativeAreas(String userName) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        return creativeAreaRepository.findByUser(user);
            /*.orElseThrow(() -> new NotFoundException("No ratings present"));
    return creativeAreaMapper.toDto(creativeAreas);*/
    /*return creativeAreaList.stream()
            .filter(creativeArea -> creativeArea.getUser().getEmail().equals(userName))
            .collect(Collectors.toList());*/
    }


}
