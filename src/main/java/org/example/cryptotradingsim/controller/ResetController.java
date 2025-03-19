package org.example.cryptotradingsim.controller;

import org.example.cryptotradingsim.model.dto.CryptoCurrencyDto;
import org.example.cryptotradingsim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ResetController {

    private final UserService userService;

    @Autowired
    public ResetController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/reset")
    public String showCryptoPage(Model model) {
        if (!model.containsAttribute("cryptoCurrencyDto")) {
            model.addAttribute("cryptoCurrencyDto", new CryptoCurrencyDto());
        }

        userService.resetAccount();

        return "redirect:/crypto";
    }
}
