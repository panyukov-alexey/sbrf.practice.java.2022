package sbrf.practice.jsv.list.controller;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.header.Header;
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
import java.io.UncheckedIOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class ManagerController {

    private final UserService userService;
    private final FileService fileService;

    @GetMapping("/")
    public String index() {
        return "redirect:/manager";
    }

    @GetMapping("/manager")
    public String startPage(@RequestParam(name = "size", defaultValue = "10") Integer size, Model model) {
        return pageable(1, size, model);
    }

    @GetMapping("/manager/page/{id}")
    public String pageable(@PathVariable(name = "id") Integer pageNumber,
                           @RequestParam(name = "size", defaultValue = "10") Integer size,
                           Model model) {
        UserDto user = (UserDto) model.getAttribute("user");
        Page<FileDto> page = fileService.findFilesByAuthor(user.getId(), PageRequest.of(pageNumber - 1, size));
        model.addAttribute("page", page);
        return "index";
    }

    @RequestMapping(value = "/manager/page/{id}", params = {"criteria", "direction"})
    public String pageableWithSorting(@PathVariable(name = "id") Integer pageNumber,
                                      @RequestParam(name = "size", defaultValue = "10") Integer size,
                                      @RequestParam(name = "criteria") String criteria,
                                      @RequestParam(name = "direction") Sort.Direction direction,
                                      Model model) {
        Sort sort = Sort.by(direction, criteria);
        Page<FileDto> page = fileService.findFilesByAuthor(user().getId(), PageRequest.of(pageNumber - 1, size, sort));
        model.addAttribute("page", page);
        model.addAttribute("criteria", criteria);
        model.addAttribute("ascending", direction.isAscending());
        return "index";
    }

    @RequestMapping(value = "/manager/page/{id}", params = {"search"})
    public String pageableWithFiltering(@PathVariable(name = "id") Integer pageNumber,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                                        @RequestParam(name = "search") String search,
                                        Model model) {
        if (search.isBlank()) {
            return "redirect:/manager";
        }
        Page<FileDto> page = fileService.findByAuthorIdAndFilenameContains(user().getId(), search, PageRequest.of(pageNumber - 1, size));
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        return "index";
    }

    @RequestMapping(value = "/manager/page/{id}", params = {"criteria", "direction", "search"})
    public String pageableWithSortingAndFiltering(@PathVariable(name = "id") Integer pageNumber,
                                                  @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                  @RequestParam(name = "search") String search,
                                                  @RequestParam(name = "criteria") String criteria,
                                                  @RequestParam(name = "direction") Sort.Direction direction,
                                                  Model model) {
        if (search.isBlank()) {
            return pageable(pageNumber, size, model);
        }
        Sort sort = Sort.by(direction, criteria);
        Page<FileDto> page = fileService.findByAuthorIdAndFilenameContains(user().getId(), search,
                PageRequest.of(pageNumber - 1, size, sort));
        model.addAttribute("page", page);
        model.addAttribute("criteria", criteria);
        model.addAttribute("search", search);
        model.addAttribute("ascending", direction.isAscending());
        return "index";
    }


    @RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "action=create")
    public String create(@Valid @ModelAttribute CreateFileDto dto) {
        fileService.create(dto);
        return "redirect:/manager";
    }

    @RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "action=read")
    public void read(@RequestParam("id") @Valid UUID id, HttpServletResponse response) {
        try {
            FileDto file = fileService.findFileById(id);
            byte[] content = fileService.downloadFileById(id);
            IOUtils.copy(new ByteArrayInputStream(content), response.getOutputStream());
            response.setHeader("Content-disposition", "attachment;filename=" + file.getFilename());
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot download file with id", e);
        }
    }

    @RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "action=update")
    public String update(@RequestParam("id") UUID id, @Valid @ModelAttribute UpdateFileDto dto, @RequestHeader(name = "Referer") String referer){
        fileService.update(id, dto);
        System.out.println(referer);
        return "redirect:" + referer;
    }

    @RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "action=delete")
    public String delete(@RequestParam("id") @Valid UUID id, @RequestHeader(name = "Referer") String referer) {
        fileService.deleteById(id);
        return "redirect:" + referer;
    }

    @ModelAttribute("user")
    UserDto user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName());
    }

    @ModelAttribute("criteries")
    Map<String, String> criteries() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("id", "Уникальный идентификатор файла");
        map.put("filename", "Название файла");
        map.put("length", "Размер файла");
        map.put("createdAt", "Дата создания");
        map.put("updatedAt", "Дата изменения");
        return map;
    }

}
