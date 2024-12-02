package com.lion.demo.controller;

import com.lion.demo.entity.User;
import com.lion.demo.service.UserService;
import java.time.LocalDate;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(){
        return "user/register";
    }

    @PostMapping("/register")
    public String registerProc(String uid, String pwd, String pwd2, String uname, String email){
        if(userService.findByUid(uid) == null && pwd.equals(pwd2) && pwd.length() >= 4){
            String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
            User user = User.builder()
                    .uid(uid)
                    .uname(uname)
                    .email(email)
                    .pwd(hashedPwd)
                    .regDate(LocalDate.now())
                    .role("ROLE_USER")
                    .build();
            userService.registerUser(user);
        }
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model){
        List<User> userList = userService.getUsers();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    @GetMapping("/delete/{uid}")
    public String delete(@PathVariable("uid") String uid){
        userService.deleteUser(uid);
        return "redirect:/user/list";
    }


    @GetMapping("/update/{uid}")
    public String updateForm(@PathVariable("uid") String uid, Model model){
        User user = userService.findByUid(uid);
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/update")
    public String updateProc(String uid, String pwd, String pwd2, String uname, String email, String role){
        User user = userService.findByUid(uid);
        if(pwd != null && pwd.equals(pwd2) && pwd.length() >= 4) {
            String hashPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
            user.setPwd(hashPwd);
        }
        user.setRole(role);
        user.setEmail(email);
        user.setUname(uname);
        userService.updateUser(user);
        return "redirect:/user/list";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "user/login";
    }
}
