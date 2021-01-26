package com.example.datn;

import com.example.datn.model.Role;
import com.example.datn.model.User;
import com.example.datn.service.RoleService;
import com.example.datn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class DatnApplication {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostConstruct
    public void init(){
        List<Role> roles = (List<Role>) roleService.findAll();
        if(roles.isEmpty()){
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleService.save(roleAdmin);
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleService.save(roleUser);
        }
        List<User> users = (List<User>) userService.findAll();
        if(users.isEmpty()){
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            Role role = new Role();
            role.setId(1L);
            Role roleUser = new Role();
            roleUser.setId(2L);
            Set<Role> roles1 = new HashSet<>();
            roles1.add(role);
            roles1.add(roleUser);
            admin.setRoles(roles1);
            userService.save(admin);
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("123456"));
            role = new Role();
            role.setId(2L);
            roles1 = new HashSet<>();
            roles1.add(role);
            user.setRoles(roles1);
            userService.save(user);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(DatnApplication.class, args);
    }

}
