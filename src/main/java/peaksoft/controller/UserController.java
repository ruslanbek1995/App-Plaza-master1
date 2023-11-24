package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.User;
import peaksoft.service.impl.ApplicationService;
import peaksoft.service.impl.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ApplicationService applicationService;

    @Autowired
    public UserController(UserService userService, ApplicationService applicationService) {
        this.userService = userService;
        this.applicationService = applicationService;
    }
    // HTMLге пустой обьекти жонотот
    @GetMapping
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "user/save";
    }
    @GetMapping()
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user/save";
    }

    //  HTML-толтурулган обьекти @ModelAttribute-методу аркылуу алып келип сактайбы!!!
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:find-all";
    }

    //    saveUser - findAll менен чогу иштейт себеп (redirect)-aркылуу
//    findAll коп user-лерди чыгаруу
    @GetMapping("/get-all")
    public String findAll(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "user/get-all";
    }
    @GetMapping("/find-all")
    public String getAllUser(Model model){
        model.addAttribute("users",userService.findAll());
        return "user/find-all";
    }


    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/update";
    }
@GetMapping("/save-update/{id}")
    public String saveUpdate(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "user/main-for-user";
    }

     @GetMapping("/profile")
     public String getProfile(Principal principal, Model model ){
        User user = userService.findByGmail(principal.getName());
        model.addAttribute("user",user);
        return "user/profile";
     }

//    @GetMapping("/profile/{id}")
//    public String getUser(@PathVariable("id")Long id,Model model){
//        User user = userService.findById(id);
//        model.addAttribute("user1",user);
//        return "user/profile";
//    }
//@GetMapping("/find-all")
//    public String getAll(Model model){
//        model.addAttribute("userList", userService.findAll());
//        return "user/get-all";
//    }
//    @PostMapping("{id}")
//    public  String saveUpdate(@PathVariable("id") long id ,@ModelAttribute("user") User user){
//        userService.update(id,user);
//        if (user.getRole().equals(Role.ADMIN)){
//            return "user/main-for-admin";
//        }
//        return "user/main-for-user";
//    }
//    @GetMapping("{id}")
//    public String delete(@PathVariable("id") Long id){
//        userService.deleteById(id);
//        return "redirect:find-all";
//    }
//    public String getUsers(@PathVariable("id") Long id, Model model){
//        User user
//    }

}
