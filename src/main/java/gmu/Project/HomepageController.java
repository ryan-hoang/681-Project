package gmu.Project;

import gmu.Project.Services.UserService;
import gmu.Project.WebSecurity.RegistrationValidator;
import gmu.Project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomepageController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationValidator registrationValidator;

    @GetMapping(value="/")
    public String rootView () {
        return "Welcome";
    }

    @GetMapping(value="/login")
    public String login(Model model){
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
    public String registerPost (User user, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        registrationValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors())
        {
            return "/Register";
        }
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "Successfully Registered!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/login";
    }

    @RequestMapping(value = "/table", method=RequestMethod.GET)
    public String gameView() { return "table"; }

}
