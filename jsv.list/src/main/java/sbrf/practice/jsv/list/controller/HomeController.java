package sbrf.practice.jsv.list.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.dto.users.UserDto;
import sbrf.practice.jsv.list.model.User;
import sbrf.practice.jsv.list.service.FileService;
import sbrf.practice.jsv.list.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;

    @GetMapping("/")
    public String index(@RequestParam(name = "filename", required = false, defaultValue = "") String filename,
                        @RequestParam(name = "sort", required = false, defaultValue = "id") String criteria,
                        @RequestParam(name = "page", required = false, defaultValue="0") int page,
                        @RequestParam(name = "size", required = false, defaultValue="10") int size,
                        Principal principal, Model model) throws IOException {
        if (model.getAttribute("user") == null) {
            String username = principal.getName();
            model.addAttribute("user", userService.findByUsername(username));
            model.addAttribute("from", 0);
            model.addAttribute("to", 10);
        }
        UserDto user = (UserDto)model.getAttribute("user");
        Page<FileDto> files = fileService.findAllSorted(Sort.by(criteria), page, size);
        model.addAttribute("files", files.getContent().stream().
                filter((f) -> f.getAuthorId().equals(user.getId())).
                filter(f -> f.getFileName().contains(filename)).collect(Collectors.toList()));
        return "index";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=create")
    public String create(@Valid CreateFileDto dto, HttpServletRequest request) throws IOException {
        fileService.create(dto);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=read")
    public void read(@RequestParam("id") UUID id, HttpServletResponse response) throws IOException {
        FileDto file = fileService.findFileById(id);
        byte[] content = fileService.downloadFileById(id);
        IOUtils.copy(new ByteArrayInputStream(content), response.getOutputStream());
        response.setHeader("Content-disposition", "attachment;filename=" + file.getFileName());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=update")
    public String update(@RequestParam("id") UUID id, @Valid UpdateFileDto dto, HttpServletRequest request) throws IOException {
        fileService.update(id, dto);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=delete")
    public String delete(@RequestParam("id") UUID id, HttpServletRequest request) {
        fileService.deleteById(id);
        return "redirect:" + request.getHeader("Referer");
    }
}
