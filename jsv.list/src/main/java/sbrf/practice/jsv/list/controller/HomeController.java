package sbrf.practice.jsv.list.controller;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.users.UserDto;
import sbrf.practice.jsv.list.service.FileService;
import sbrf.practice.jsv.list.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        model.addAttribute("user", user);
        model.addAttribute("files", fileService.findFilesByAuthor(user.getId()));
        return "index";
    }

    @PostMapping("/upload")
    public String upload(CreateFileDto dto, Model model) throws IOException {
        fileService.create(dto);
        return "redirect:/";
    }

    @GetMapping("/download/{id}")
    public String download(@PathVariable("id") UUID id, HttpServletResponse response) throws IOException {
        FileDto file = fileService.findFileById(id);
        byte[] content = fileService.downloadFileById(id);
        IOUtils.copy(new ByteArrayInputStream(content), response.getOutputStream());
        response.setContentType("application/json");
        response.setHeader("Content-disposition", "attachment;filename=" + file.getFileName());
        response.flushBuffer();
        return "redirect:/";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable("id") UUID id) {
        fileService.deleteById(id);
        return "redirect:/";
    }
}
