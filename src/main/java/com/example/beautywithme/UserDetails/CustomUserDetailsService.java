package com.example.beautywithme.UserDetails;

import com.example.beautywithme.Session.UserSessionDto;
import com.example.beautywithme.User.User;
import com.example.beautywithme.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + email));

        session.setAttribute("user", new UserSessionDto(user));

        return new CustomUserDetails(user);
    }

}
