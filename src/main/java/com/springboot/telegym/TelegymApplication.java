package com.springboot.telegym;

import com.springboot.telegym.entity.User;
import com.springboot.telegym.repository.RoleRepository;
import com.springboot.telegym.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Optional;

@SpringBootApplication
public class TelegymApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(TelegymApplication.class, args);
    }

    @Bean
    InitializingBean sendDatabase() {
        Optional<User> a = userRepository.findById("88888888-8888-8888-8888-888888888888");
        if (a.isEmpty()) {
            return () -> {
                Calendar ca = Calendar.getInstance();
                ca.set(1970, Calendar.JANUARY, 1, 0, 0, 0);

                userRepository.save(new User(ca.getTime(), "88888888-8888-8888-8888-888888888888",
                        ca.getTime(), "88888888-8888-8888-8888-888888888888",
                        "88888888-8888-8888-8888-888888888888", "Admin tổng", "loc3120",
                        encoder.encode("12345"), false, "",
                        roleRepository.getById("11111111-1111-1111-1111-111111111111")));
            };
        }
        else
            return null;
    }

//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//
//        mailSender.setUsername(Constant.MY_USERNAME);
//        mailSender.setPassword(Constant.MY_PASSWORD);
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
}
