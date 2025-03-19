package org.example.cryptotradingsim.controller;

import org.example.cryptotradingsim.model.dto.CryptoCurrencyDto;
import org.example.cryptotradingsim.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/buy")
    public String buyCrypto(@RequestParam String pair,
                            @RequestParam BigDecimal ask,
                            @RequestParam BigDecimal quantity,
                            RedirectAttributes redirectAttributes) {
        CryptoCurrencyDto cryptoCurrencyDto = new CryptoCurrencyDto(pair, ask, quantity);

        String message = transactionService.buyCryptoCurrency(cryptoCurrencyDto);

        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("messageType", "success"); // или "failed" за грешка

        return "redirect:/crypto";
    }


    @PostMapping("/sell")
    public String sellCrypto(@RequestParam String pair,
                             @RequestParam BigDecimal bid,
                             @RequestParam BigDecimal quantity,
                             RedirectAttributes redirectAttributes) {
        CryptoCurrencyDto cryptoCurrencyDto = new CryptoCurrencyDto(pair, bid, quantity);

        String message = transactionService.sellCryptoCurrency(cryptoCurrencyDto);

        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("messageType", "success");

        return "redirect:/crypto";
    }


}
