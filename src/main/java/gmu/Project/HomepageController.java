package gmu.Project;

import gmu.Project.Services.UserService;
import gmu.Project.WebSecurity.RegistrationValidator;
import gmu.Project.model.Game;
import gmu.Project.model.User;
import gmu.Project.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

@Controller
public class HomepageController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationValidator registrationValidator;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping(value="/")
    public String rootView () {
        return "Welcome";
    }

    @GetMapping(value="/login")
    public ModelAndView login(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated())
        {
            Collection<Game> games = gameRepository.getPendingGames();
            HomepageBean hb = new HomepageBean();
            hb.setGames(games);

            String username = "";
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal).getUsername();
            } else
            {
                username = principal.toString();
            }

            ModelAndView mv = new ModelAndView("homepage");
            mv.addObject("username",username);
            mv.addObject("homepagebean",hb);
            return mv;
        }
        else {
            return new ModelAndView("login");
        }
    }

    @GetMapping(value="/register")
    public String register(ModelMap model){
        model.put("user", new User());
        return "Register";
    }

    @GetMapping(value="/lobby")
    public String pregame() {
        return "lobby";
    }

    @GetMapping(value="/homepage")
    public ModelAndView homepage(){
        Collection<Game> games = gameRepository.getPendingGames();
        HomepageBean hb = new HomepageBean();
        hb.setGames(games);

        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else
        {
            username = principal.toString();
        }

        ModelAndView mv = new ModelAndView("homepage");
        mv.addObject("username",username);
        mv.addObject("homepagebean",hb);
        return mv;
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

    @RequestMapping(value = "/table")
    public String gameView() { return "table"; }

}
