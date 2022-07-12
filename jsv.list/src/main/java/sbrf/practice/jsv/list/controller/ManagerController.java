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
@SessionAttributes({"page", "search", "criteria", "direction"})
public class ManagerController {

    private final UserService userService;
    private final FileService fileService;

    @GetMapping("/")
    public String index() {
        return "redirect:/manager";
    }

    @GetMapping("/manager")
    public String startPage(@RequestParam(name = "size", defaultValue = "10") Integer size, Model model) {
        model.addAttribute("search", null);
        model.addAttribute("criteria", null);
        model.addAttribute("direction", "ASC");
        return pageable(1, size, model);
    }

    @GetMapping("/manager/page/{id}")
    public String pageable(@PathVariable(name = "id") Integer pageNumber,
                           @RequestParam(name = "size", defaultValue = "10") Integer size,
                           Model model) {
        pageNumber = Math.max(pageNumber, 1);

        if (model.getAttribute("criteria") != null) {
            return pageableWithSorting(pageNumber, size, (String)model.getAttribute("criteria"),
                    Sort.Direction.fromString(((String)model.getAttribute("direction"))), model);
        }

        if (model.getAttribute("search") != null) {
            return pageableWithFiltering(pageNumber, size, (String)model.getAttribute("search"), model);
        }

        if (model.getAttribute("criteria") != null && model.getAttribute("search") != null && model.getAttribute("search") != null) {
            return pageableWithSortingAndFiltering(pageNumber, size, (String)model.getAttribute("search"),
                    (String)model.getAttribute("criteria"),
                    Sort.Direction.fromString(((String)model.getAttribute("direction"))),model);
        }

        model.addAttribute("page", fileService.findFilesByAuthor(user().getId(), PageRequest.of(pageNumber - 1, size)));
        return "index";
    }

    @RequestMapping(value = "/manager/page/{id}", params = {"criteria", "direction"})
    public String pageableWithSorting(@PathVariable(name = "id") Integer pageNumber,
                                      @RequestParam(name = "size", defaultValue = "10") Integer size,
                                      @RequestParam(name = "criteria") String criteria,
                                      @RequestParam(name = "direction") Sort.Direction direction,
                                      Model model) {
        model.addAttribute("page", fileService.findFilesByAuthor(user().getId(),
                PageRequest.of(pageNumber - 1, size, Sort.by(direction, criteria))));
        model.addAttribute("criteria", criteria);
        model.addAttribute("direction", direction.toString());
        return "index";
    }

    @RequestMapping(value = "/manager/page/{id}", params = {"search"})
    public String pageableWithFiltering(@PathVariable(name = "id") Integer pageNumber,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                                        @RequestParam(name = "search") String search,
                                        Model model) {
        if (search.isBlank()) {
            model.addAttribute("search", null);
            return pageable(pageNumber, size, model);
        }
        model.addAttribute("page", fileService.findByAuthorIdAndFilenameContains(user().getId(), search,
                PageRequest.of(pageNumber - 1, size)));
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
            model.addAttribute("search", null);
        }
        model.addAttribute("page", fileService.findByAuthorIdAndFilenameContains(user().getId(), search,
                PageRequest.of(pageNumber - 1, size, Sort.by(direction, criteria))));
        model.addAttribute("search", search);
        model.addAttribute("criteria", criteria);
        model.addAttribute("direction", direction.toString());
        return "index";
    }

    @RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "action=create")
    public String create(@ModelAttribute @Valid CreateFileDto dto, BindingResult bindingResult,
                         @ModelAttribute Page<?> page, Model model) {
        model.addAttribute("search", null);
        if (bindingResult.hasErrors()) {
            model.addAttribute("isntJSON", true);
            return "index";
        }
        fileService.create(dto);
        if (page.isLast() && page.getNumberOfElements() == page.getSize()) {
            return pageable(Math.max(page.getTotalPages() + 1, 1), page.getSize(), model);
        }
        
        return pageable(page.getTotalPages(), page.getSize(), model);
    }

    @RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "action=read")
    public void read(@RequestParam("id") UUID id, HttpServletResponse response) {
        try {
            FileDto file = fileService.findFileById(id);
            byte[] content = fileService.downloadFileById(id);
            IOUtils.copy(new ByteArrayInputStream(content), response.getOutputStream());
            response.setHeader("Content-disposition", "attachment;filename=" + file.getFilename());
        } catch (IOException e) {
            throw new UncheckedIOException("Can't download file with id", e);
        }
    }

    @RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "action=update")
    public String update(@RequestParam("id") String uuid, @ModelAttribute @Valid UpdateFileDto dto,
                         BindingResult bindingResult,  @ModelAttribute Page<?> page, Model model) {
        model.addAttribute("search", null);
        if (bindingResult.hasErrors()) {
            model.addAttribute("isntJSON", true);
            return "index";
        }
        try {
            UUID id = UUID.fromString(uuid);
            fileService.update(id, dto);
            return pageable(page.getNumber() + 1, page.getSize(), model);
        } catch (IllegalArgumentException e) {
            model.addAttribute("isntUUID", true);
            return "index";
        }
    }

    @RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "action=delete")
    public String delete(@RequestParam("id") String uuid, @ModelAttribute Page<?> page, Model model) {
        try {
            UUID id = UUID.fromString(uuid);
            fileService.deleteById(id);
            if (!page.isFirst() && page.getNumberOfElements() == 1) {
                return pageable(page.getNumber() - 1, page.getSize(), model);
            }
            return pageable(page.getNumber() + 1, page.getSize(), model);
        } catch (IllegalArgumentException e) {
            model.addAttribute("isntUUID", true);
            return "index";
        }
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
