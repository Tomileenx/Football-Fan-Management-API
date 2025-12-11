package com.example.Football_Fan.fanProfile;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FanProfileService {

    private final FanProfileRepository fanProfileRepository;
    private final FanProfileMapper fanProfileMapper;

    public FanProfileService(FanProfileRepository fanProfileRepository, FanProfileMapper fanProfileMapper) {
        this.fanProfileRepository = fanProfileRepository;
        this.fanProfileMapper = fanProfileMapper;
    }

    public FanProfileResponseDto postFanProfile(FanProfileDto dto) {
        var fanprofile = fanProfileMapper.toFan(dto);
        var savedFanProfile =  fanProfileRepository.save(fanprofile);
        return fanProfileMapper.toFanProfileResponseDto(savedFanProfile);
    }

    public List<FanProfileResponseDto> getAllFansProfile() {
        return fanProfileRepository.findAll()
                .stream()
                .map(fanProfileMapper::toFanProfileResponseDto)
                .collect(Collectors.toList());
    }

    public FanProfileResponseDto getFanProfileByPersonId(Integer personId) {
        FanProfile fanProfile = fanProfileRepository.findFanProfileByPersonId(personId)
                .orElseThrow(() -> new RuntimeException("FanProfile not found"));

        return fanProfileMapper.toFanProfileResponseDto(fanProfile);
    }
}
