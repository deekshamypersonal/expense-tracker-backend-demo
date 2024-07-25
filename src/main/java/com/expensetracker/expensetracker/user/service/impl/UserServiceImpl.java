//package com.expensetracker.expensetracker.user.service.impl;
//
//
//import com.expensetracker.expensetracker.io.entity.User;
//import com.expensetracker.expensetracker.io.repository.UserRepository;
//import com.expensetracker.expensetracker.model.UserRegister;
//import com.expensetracker.expensetracker.security.Utility;
//import com.expensetracker.expensetracker.user.service.UserService;
//
//import org.modelmapper.ModelMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserServiceImpl implements UserDetailsService, UserService {
//
//    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
//
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    UserRepository userRepository;
//
////    @Autowired
////    ProducerService producerService;
//
//
//
//    public String registerUser(UserRegister userRegister) {
//        logger.info("Method registerUser invoked for user");
//        ModelMapper modelMapper = new ModelMapper();
//        User userEntity = modelMapper.map(userRegister, User.class);
//        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userRegister.getPassword()));
//
//            userRepository.save(userEntity);
//
//
//        return "User Registered Successfully";
//    }
//
////
//
//
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        {
//            User userEntity = userRepository.findByEmail(email);
//            if (userEntity == null)
//                throw new UsernameNotFoundException(email);
//            return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
//                    true,
//                    true, true,
//                    true, new ArrayList<>());
//        }
//    }
//
//
//
//    private static String getLoggedInUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getName();
//    }
//}


package com.expensetracker.expensetracker.user.service.impl;

import com.expensetracker.expensetracker.exception.UserAlreadyExistsException;
import com.expensetracker.expensetracker.exception.UserRegistrationException;
import com.expensetracker.expensetracker.io.entity.User;
import com.expensetracker.expensetracker.io.repository.UserRepository;
import com.expensetracker.expensetracker.model.UserRegister;
import com.expensetracker.expensetracker.user.service.UserService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String registerUser(UserRegister userRegister) {
        logger.info("Method registerUser invoked for user");
        ModelMapper modelMapper = new ModelMapper();
        User userEntity = modelMapper.map(userRegister, User.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userRegister.getPassword()));
        try {
            if (userRepository.findByEmail(userRegister.getEmail()) != null) {
                throw new UserAlreadyExistsException("User already exists with email: " + userRegister.getEmail());
            }
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new UserRegistrationException("Failed to register user", e);
        }
        return "User Registered Successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true, new ArrayList<>());
    }

    private static String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}

