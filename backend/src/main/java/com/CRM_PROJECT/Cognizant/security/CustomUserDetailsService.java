package com.CRM_PROJECT.Cognizant.security;

import com.CRM_PROJECT.Cognizant.entity.Admin;
import com.CRM_PROJECT.Cognizant.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminName(username.toLowerCase()) // Normalize username to lowercase
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        System.out.println("Loaded Admin: " + admin);
    
        return new User(
                admin.getAdminName().toLowerCase(), // Normalize username to lowercase
                admin.getAdminPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + admin.getRole())) // Prefix role with "ROLE_"
        );
    

}
}