package com.yourstechnology.formbuilder.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.yourstechnology.formbuilder.entity.User;
import com.yourstechnology.formbuilder.repository.UserRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                String[][] dummyUsers = {
                    {"User 1", "user1@webtech.id", "password1"},
                    {"User 2", "user2@webtech.id", "password2"},
                    {"User 3", "user3@worldskills.org", "password3"}
                };

                for (String[] data : dummyUsers) {
                    User user = new User();
                    user.setName(data[0]);
                    user.setEmail(data[1]);
                    user.setPassword(passwordEncoder.encode(data[2]));
                    userRepository.save(user);
                }


            }
        };
    }
}
