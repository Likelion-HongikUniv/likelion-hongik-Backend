package Likelion.service;

import Likelion.model.entity.Profile;
import Likelion.model.entity.User;
import Likelion.model.repository.UserRepository;
import Likelion.model.dto.ProfileDto;
import Likelion.model.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;


    @Override
    public void insertDetail(User user, ProfileDto profileDto) {

        Profile profile = profileDto.toEntity(user);
        profileRepository.save(profile);
        System.out.println("profile save 완료");
    }

    @Override
    public Profile haveUser(int userId) {
        return profileRepository.findByUser_Id(userId);
    }

    @Override
    public Profile viewProfile(User user){
        Profile profile = profileRepository.findByUser_Id(user.getId());
        System.out.println("profile = " + profile.getMajor());
        return profile;
    }


}