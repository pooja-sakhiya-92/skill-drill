package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.CreativeAreaDto;
import com.skilldrill.registration.model.CreativeArea;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CreativityService {

    CreativeArea addCreativity(CreativeAreaDto creativityDto);

    CreativeAreaDto updateCreativity(String creativeSkill,CreativeAreaDto creativityDto);

    void deleteCreativeArea(String creativeSkill,String userName);

    List<CreativeAreaDto> findAllCreativeAreas(String userName);
}
