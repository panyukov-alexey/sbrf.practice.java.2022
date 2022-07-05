package sbrf.practice.jsv.list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.users.UserDto;
import sbrf.practice.jsv.list.service.FileService;
import sbrf.practice.jsv.list.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    @GetMapping("/")
    public String homepage(Principal principal, Model model) throws IOException {
        String username = principal.getName();
        UserDto user = userService.findByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("files", fileService.findFilesByAuthor(user.getId()));
        return "index";
    }

    @PostMapping("/upload")
    public String upload(CreateFileDto dto, Principal principal, Model model) throws IOException {
        String username = principal.getName();
        UserDto user = userService.findByUsername(username);
        dto.setAuthorId(user.getId());
        fileService.create(dto);
        return "redirect:/";
    }

    @GetMapping("/download/{id}")
    public String download(@PathVariable("id") UUID id) {
        return "redirect:/";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable("id") UUID id) {
        fileService.deleteById(id);
        return "redirect:/";
    }
}
