package com.maroc_ouvrage.semployee.service.Imp;

import com.maroc_ouvrage.semployee.model.User;
import com.maroc_ouvrage.semployee.repo.UserRepository;
import com.maroc_ouvrage.semployee.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired // optional with constructor injection in modern Spring
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 UserDetailsServiceImpl: loading user -> " + username); // ✅ Add this
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new MyUserDetails(user);
    }
}
