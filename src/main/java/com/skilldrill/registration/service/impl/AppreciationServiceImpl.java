package com.skilldrill.registration.service.impl;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.AppreciationDto;
import com.skilldrill.registration.exceptions.NotFoundException;
import com.skilldrill.registration.mapper.AppreciationMapper;
import com.skilldrill.registration.model.Appreciation;
import com.skilldrill.registration.model.Badges;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.AppreciationRepository;
import com.skilldrill.registration.repository.UserRepository;
import com.skilldrill.registration.service.AppreciationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class AppreciationServiceImpl implements AppreciationService {

    @Autowired
    AppreciationMapper appreciationMapper;

    @Autowired
    AppreciationRepository appreciationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;


    @Override
    public Appreciation giveAppreciation(AppreciationDto appreciationDto, String userName) {
        Appreciation appreciation = appreciationMapper.toAppreciation(appreciationDto);
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        appreciation.setDateOfPraise(String.valueOf(LocalDate.now()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String appreciator_name = authentication.getName();
        appreciation.setAppreciatorName(appreciator_name);
        appreciation.setUser(user);
        return appreciationRepository.save(appreciation);
    }

    @Override
    public AppreciationDto updateAppreciation(String praise, AppreciationDto appreciationDto, String userName) {

        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        Appreciation appreciation = appreciationRepository.findByPraise(praise)
                .orElseThrow(() -> new NotFoundException("No appreciations present"));
        appreciation.setPraise(appreciationDto.getPraise());
        appreciation.setDateOfPraise(String.valueOf(LocalDate.now()));
        appreciation.setUser(user);
        appreciationRepository.save(appreciation);
        return appreciationMapper.toDto(appreciation);
    }

    @Override
    public void deleteAppreciation(String praise, String userName) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        Appreciation appreciation = appreciationRepository.findByUser(user)
                .stream()
                .filter(badgesMock -> praise.equalsIgnoreCase(String.valueOf(badgesMock.getPraise())))
                .findAny()
                .orElseThrow(() -> new NotFoundException("appreciation doesn't exist!"));
        appreciationRepository.delete(appreciation);


    }

    @Override
    public List<Appreciation> findAllAppreciations(String userName) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        return appreciationRepository.findByUser(user);
    }

}
