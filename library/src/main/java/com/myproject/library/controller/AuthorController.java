package com.myproject.library.controller;

import com.myproject.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public String getAuthorsView(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors";
    }
}