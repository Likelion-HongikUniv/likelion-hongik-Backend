package Likelion.service;

import Likelion.model.Profile;
import Likelion.model.User;
import Likelion.model.dto.dto.ProfileDto;

public interface ProfileService {

    public void insertDetail(User user, ProfileDto profileDto);

    public Profile haveUser(int userId);
}
