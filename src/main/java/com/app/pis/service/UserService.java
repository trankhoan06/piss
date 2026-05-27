package com.app.pis.service;

import com.app.pis.dto.request.CreateUserRequest;
import com.app.pis.entity.User;
import com.app.pis.entity.enums.UserRole;
import com.app.pis.entity.enums.UserStatus;
import com.app.pis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Transactional
    public void createUser (CreateUserRequest request) {
        String rawPassword = generateTempPassword();
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phone(request.phone())
                .email(request.email())
                .birthDay(request.birthDay())
                .cccd(request.CCCD())
                .address(request.address())
                .role(UserRole.STAFF.roleName)
                .status(UserStatus.ACTIVE.status)
                .password(passwordEncoder.encode(rawPassword))
                .firstLogin(true)
                .build();
        userRepository.save(user);
        mailService.sendMail(request.email(),
                "Thông báo tạo tài khoản thành công",
                 String.format("Tên đăng nhập: %s\nMật khẩu: %s\nVui lòng đổi mật khẩu trong lần đăng nhập đầu tiên",request.email(), rawPassword));
    }



    private String generateTempPassword() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(letters.charAt(random.nextInt(letters.length())));
        sb.append(digits.charAt(random.nextInt(digits.length())));
        for (int i = 0; i < 6; i++) {
            String all = letters + digits;
            sb.append(all.charAt(random.nextInt(all.length())));
        }
        return sb.toString();
    }


}
