package sbrf.practice.jsv.list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    
    @PostMapping("/login_failure_handler")
    public String loginFailureHandler(Model model) {
    model.addAttribute("errorLogin", "Неверный логин или пароль");
    return "login";
    }

    @GetMapping("/logout_seccesful_handler")
    public String logoutSeccesfulHandler(Model model) {
    model.addAttribute("seccesLogout", "Вы успешно вышли");
    return "login";
    } 
}
