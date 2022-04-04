package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.AppreciationDto;
import com.skilldrill.registration.model.Appreciation;

import java.util.List;

public interface AppreciationService {

    Appreciation giveAppreciation(AppreciationDto appreciationDto, String userName);

    AppreciationDto updateAppreciation(String praise,AppreciationDto appreciationDto,String userName);

    void deleteAppreciation(String praise,String userName);

    List<Appreciation> findAllAppreciations(String userName);
}
