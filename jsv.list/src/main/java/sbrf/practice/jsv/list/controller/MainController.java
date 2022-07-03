package sbrf.practice.jsv.list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.users.UserDto;
import sbrf.practice.jsv.list.model.User;
import sbrf.practice.jsv.list.service.FileService;
import sbrf.practice.jsv.list.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    @GetMapping("/home")
    public String homepage(Principal principal, Model model) throws IOException {
        String username = principal.getName();
        UserDto user = userService.findByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("files", fileService.findFilesByAuthor(user.getId()));
        return "home";
    }
    @PostMapping("/upload")
    public String uploadFile(Principal principal, @ModelAttribute CreateFileDto dto, Model model) throws IOException {
        String username = principal.getName();
        UserDto user = userService.findByUsername(username);
        dto.setAuthorId(user.getId());
        fileService.create(dto);
        return "redirect:/home";
    }
}
