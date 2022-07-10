package sbrf.practice.jsv.list.controller;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
import java.util.UUID;

@Controller
@AllArgsConstructor
public class ManagerController {

    private final UserService userService;
    private final FileService fileService;

    @GetMapping("/page/{id}")
    public String page(@PathVariable(name = "id") Integer pageNumber,
                       @RequestParam(name = "filename", defaultValue = "") String filename,
                       @RequestParam(name = "criteria", defaultValue = "id") String criteria,
                       @RequestParam(name = "direction", defaultValue = "asc") String direction,
                       Model model) {
        final int size = 16;
        boolean isAscending = direction.equalsIgnoreCase("asc");
        UserDto user = (UserDto)model.getAttribute("currentUser");
        Page<FileDto> page;
        if (filename.isBlank()) {
            page = fileService.findFilesByAuthor(user.getId(), PageRequest.of(pageNumber - 1, size, Sort.by(isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, criteria)));
        } else {
            page = fileService.findByAuthorIdAndFilenameContains(user.getId(), filename, PageRequest.of(pageNumber - 1, size, Sort.by(isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, criteria)));
        }
        model.addAttribute("searchingPattern", filename);
        model.addAttribute("sortingCriteria", criteria);
        model.addAttribute("files", page.getContent());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("maxElementsPerPage", size);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("isAscending", isAscending);
        return "index";
    }

    @GetMapping("/")
    public String index(Model model) {
        return page(1, "", "id", "asc", model);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=create")
    public String create(@Valid @ModelAttribute("createFileDto") CreateFileDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return index(model);
        }
        fileService.create(dto);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=read")
    public void read(@RequestParam("id") @Valid UUID id, HttpServletResponse response) throws IOException {
        FileDto file = fileService.findFileById(id);
        byte[] content = fileService.downloadFileById(id);
        IOUtils.copy(new ByteArrayInputStream(content), response.getOutputStream());
        response.setHeader("Content-disposition", "attachment;filename=" + file.getFilename());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=update")
    public String update(@RequestParam("id") @Valid UUID id, @Valid @ModelAttribute("updateFileDto") UpdateFileDto dto, BindingResult result, Model model) {
        fileService.update(id, dto);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=delete")
    public String delete(@RequestParam("id") @Valid UUID id, BindingResult result, Model model) {
        return index(model);
    }

    @ModelAttribute
    CreateFileDto createFileDto() {
        return new CreateFileDto();
    }

    @ModelAttribute
    UpdateFileDto updateFileDto() {
        return new UpdateFileDto();
    }

    @ModelAttribute("currentUser")
    UserDto currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName());
    }

}
