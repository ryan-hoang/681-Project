package gmu.Project;

import gmu.Project.Services.UserService;
import gmu.Project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomepageController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/")
    public String rootView () {
        return "Welcome";
    }

    @GetMapping(value="/login")
    public String login(){
        return "login";
    }

    @GetMapping(value="/register")
    public String register(ModelMap model){
        model.put("user", new User());
        return "Register";
    }

    @GetMapping(value="/homepage")
    public String homepage(){
        return "homepage";
    }

    @PostMapping(value = "/register")
    public String registerPost (@ModelAttribute User user){
        System.out.println(user);
        userService.save(user);
        return "redirect:/login";
    }
}
