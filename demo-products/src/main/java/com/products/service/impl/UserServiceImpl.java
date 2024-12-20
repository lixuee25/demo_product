package com.products.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.products.model.User;
import com.products.repository.UserRepository;
import com.products.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return userRepository.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
			}
		};
	}
	

}

    
    
    
    
    
    
    
    
//    @Override
//    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
//    	User user = userRepository.findByUsername(username)
//    	        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//        return UserDetailsImpl.build(user);  
//    }
//}
//





//@Service  
//public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        return UserDetailsImpl.build(user);  
//    }
//}




















//@Service  
//public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
//
//@Autowired
//private UserRepository userRepository;
//@Override
//public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
//    User user = userRepository.findByUsername(username)
//            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));  
//    Set<GrantedAuthority> authorities = new HashSet<>();
//    for (Role role : user.getRoles()) {
//        authorities.add(new SimpleGrantedAuthority(role.getName()));
//        role.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
//    }
//    return new UserDetailsImpl(user.getUsername(), user.getPassword(), user.getEmail(), authorities);
//}
//
//
//}
