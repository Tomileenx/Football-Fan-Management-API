package com.example.Football_Fan.fanProfile;

import com.example.Football_Fan.fan.Person;
import com.example.Football_Fan.users.AppUser;
import com.example.Football_Fan.users.AppUserRepo;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FanProfileService {

    private final FanProfileRepository fanProfileRepository;
    private final FanProfileMapper fanProfileMapper;
    private final AppUserRepo appUserRepo;

    public FanProfileService(FanProfileRepository fanProfileRepository, FanProfileMapper fanProfileMapper, AppUserRepo appUserRepo) {
        this.fanProfileRepository = fanProfileRepository;
        this.fanProfileMapper = fanProfileMapper;
        this.appUserRepo = appUserRepo;
    }

    public FanProfileResponseDto postFanProfile(
            FanProfileDto dto,
            AppUser user
    ) {
        if (user.getFanProfile() != null) {
            throw new EntityExistsException("User already has a fan profile");
        }

        Person person = user.getPerson();

        if (person == null) {
            throw new EntityNotFoundException("Person not found");
        }

        // Map DTO to Person
        FanProfile fanProfile = fanProfileMapper.toFan(dto);

        // Link fanprofile to user-person
//        fanProfile.setAppUser(user);
        fanProfile.setPerson(person);
        user.setFanProfile(fanProfile);

        // Save fan profile
        fanProfileRepository.save(fanProfile);
        appUserRepo.save(user);

        return fanProfileMapper.toFanProfileResponseDto(fanProfile);
    }

    public FanProfileResponseDto getMyFanProfile(AppUser user) {
        FanProfile fanProfile = user.getFanProfile();

        if (fanProfile == null) {
            throw new EntityNotFoundException("fan profile not found");
        }

        return fanProfileMapper.toFanProfileResponseDto(fanProfile);
    }

    public FanProfileResponseDto updateMyFanProfile(
            FanProfileDto dto,
            AppUser user
    ) {
        FanProfile fanProfile = user.getFanProfile();

        if (fanProfile == null) {
            throw new EntityNotFoundException("fan profile not found");
        }

        fanProfileMapper.updateFanProfileFromDto(dto, fanProfile);

        return  fanProfileMapper.toFanProfileResponseDto(fanProfile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<FanProfileResponseDto> getAllFansProfile() {
        return fanProfileRepository.findAll()
                .stream()
                .map(fanProfileMapper::toFanProfileResponseDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public FanProfileResponseDto getFanProfileByPersonId(Integer personId) {
        FanProfile fanProfile = fanProfileRepository.findFanProfileByPersonId(personId)
                .orElseThrow(() -> new EntityNotFoundException("FanProfile not found"));

        return fanProfileMapper.toFanProfileResponseDto(fanProfile);
    }
}
