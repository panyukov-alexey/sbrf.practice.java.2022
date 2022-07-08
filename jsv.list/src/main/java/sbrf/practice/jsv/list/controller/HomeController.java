package sbrf.practice.jsv.list.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.dto.users.UserDto;
import sbrf.practice.jsv.list.service.FileService;
import sbrf.practice.jsv.list.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
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
        model.addAttribute("user", user);
        model.addAttribute("files", fileService.findFilesByAuthor(user.getId()));
        return "index";
    }


    @RequestMapping(value="/edit", method=RequestMethod.POST, params="action=create")
    public String create(@Valid CreateFileDto dto, Model model) throws IOException {
        fileService.create(dto);
        return "redirect:/";
    }

    @RequestMapping(value="/edit", method=RequestMethod.POST, params="action=read")
    public void read(@RequestParam("id") UUID id, HttpServletResponse response) throws IOException {
        FileDto file = fileService.findFileById(id);
        byte[] content = fileService.downloadFileById(id);
        IOUtils.copy(new ByteArrayInputStream(content), response.getOutputStream());
        response.setHeader("Content-disposition", "attachment;filename=" + file.getFileName());
    }

    @RequestMapping(value="/edit", method=RequestMethod.POST, params="action=update")
    public String update(@RequestParam("id") UUID id, @Valid UpdateFileDto dto) throws IOException {
        fileService.update(id, dto);
        return "redirect:/";
    }

    @RequestMapping(value="/edit", method=RequestMethod.POST, params="action=delete")
    public String delete(@RequestParam("id") UUID id) {
        fileService.deleteById(id);
        return "redirect:/";
    }
}
