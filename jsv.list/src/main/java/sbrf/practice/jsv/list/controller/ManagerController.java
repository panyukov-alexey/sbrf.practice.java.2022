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
            @RequestParam(name = "criteria", defaultValue = "id") String criteria,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            Principal principal,
            Model model) {
        final int size = 12;
        UserDto user = userService.findByUsername(principal.getName());
        boolean isAscending = direction.equalsIgnoreCase("asc");
        model.addAttribute("user", user);
        Page<FileDto> pageable = fileService.findFilesByAuthor(user.getId(),
                Sort.by(isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, criteria),
                page - 1, size);
        model.addAttribute("files", pageable.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageable.getTotalPages());
        System.out.println(pageable.getTotalElements());
        model.addAttribute("isAscending", isAscending);
        return "index";
    }

    @GetMapping("/")
    public String index(Principal principal, Model model) {
        return pageable(1, "id", "asc", principal, model);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=create")
    public String create(@Valid @ModelAttribute CreateFileDto dto) {
        fileService.create(dto);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=read")
    public void read(@RequestParam("id") UUID id, HttpServletResponse response) throws IOException {
        FileDto file = fileService.findFileById(id);
        byte[] content = fileService.downloadFileById(id);
        IOUtils.copy(new ByteArrayInputStream(content), response.getOutputStream());
        response.setHeader("Content-disposition", "attachment;filename=" + file.getFilename());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=update")
    public String update(@RequestParam("id") UUID id, @Valid @ModelAttribute UpdateFileDto dto) {
        fileService.update(id, dto);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=delete")
    public String delete(@RequestParam("id") UUID id) {
        fileService.deleteById(id);
        return "redirect:/";
    }
}
