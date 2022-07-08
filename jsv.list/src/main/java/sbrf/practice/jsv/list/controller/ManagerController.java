package sbrf.practice.jsv.list.controller;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.dto.users.UserDto;
import sbrf.practice.jsv.list.service.FileService;
import sbrf.practice.jsv.list.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class ManagerController {

    private final UserService userService;
    private final FileService fileService;

    @GetMapping("/page/{id}")
    public String pageable(
            @PathVariable(name = "id") Integer page,
            @RequestParam(name = "criteria") String criteria,
            @RequestParam(name = "direction") String direction,
            Principal principal,
            Model model) throws IOException {
        final int size = 10;
        UserDto user = userService.findByUsername(principal.getName());
        boolean isAscending = direction.equalsIgnoreCase("ASC");
        model.addAttribute("user", user);
        Page<FileDto> pageable = fileService.findFilesByAuthor(user.getId(),
                Sort.by( isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, criteria),
        page - 1, size);
        model.addAttribute("files", pageable.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageable.getTotalPages());
        model.addAttribute("isAscending", isAscending);
        System.out.println(pageable.getTotalElements());
        return "index";
    }

    @GetMapping("/")
    public String index(Principal principal, Model model) throws IOException {
        return pageable(1,"id", "ASC" , principal, model);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=create")
    public String create(@Valid CreateFileDto dto, HttpServletRequest request, Model model) throws IOException {
        fileService.create(dto);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=read")
    public void read(@RequestParam("id") UUID id, HttpServletResponse response, Model model) throws IOException {
        FileDto file = fileService.findFileById(id);
        byte[] content = fileService.downloadFileById(id);
        IOUtils.copy(new ByteArrayInputStream(content), response.getOutputStream());
        response.setHeader("Content-disposition", "attachment;filename=" + file.getFilename());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=update")
    public String update(@RequestParam("id") UUID id, @Valid UpdateFileDto dto, HttpServletRequest request, Model model) throws IOException {
        fileService.update(id, dto);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=delete")
    public String delete(@RequestParam("id") UUID id, HttpServletRequest request, Model model) {
        fileService.deleteById(id);
        return "redirect:/";
    }
}
