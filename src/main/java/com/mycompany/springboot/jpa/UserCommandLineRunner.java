package com.mycompany.springboot.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(UserCommandLineRunner.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("Erik", "Admin"));
        userRepository.save(new User("Adan", "User"));
        userRepository.save(new User("Juan", "User"));

        for(User user: userRepository.findAll()) {
            log.info(user.toString());
        }

        log.info("Admin users are ...");
        for(User user: userRepository.findByRole("Admin")) {
            log.info(user.toString());
        }
    }
    
}
