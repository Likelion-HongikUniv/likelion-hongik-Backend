package Likelion.service;

import com.recruit.test.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {

    private final UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email) // 이미 db에 있는 이메일이라면 그 유저를 반환
//                .orElseThrow(() -> new customException(ErrorCode.NO_USER)); // 만약 db에 없다면 "해당 유저가 없습니다~" 반환
//        System.out.println("userDetailsServiceImpl user = " + user);
//        return new UserDetailsImpl(user);
//    }

}
