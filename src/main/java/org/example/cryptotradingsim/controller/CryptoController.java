package org.example.cryptotradingsim.controller;

import org.example.cryptotradingsim.model.dto.CryptoCurrencyDto;
import org.example.cryptotradingsim.model.dto.TransactionHistoryDto;
import org.example.cryptotradingsim.model.dto.UserDto;
import org.example.cryptotradingsim.service.TransactionHistoryService;
import org.example.cryptotradingsim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class CryptoController {

    private final UserService userService;

    private final TransactionHistoryService transactionHistoryService;

    @Autowired
    public CryptoController(UserService userService, TransactionHistoryService transactionHistoryService) {
        this.userService = userService;
        this.transactionHistoryService = transactionHistoryService;
    }

    @GetMapping("/crypto")
    public String showCryptoPage(Model model) {
        if (!model.containsAttribute("cryptoCurrencyDto")) {
            model.addAttribute("cryptoCurrencyDto", new CryptoCurrencyDto());
        }
        return "crypto";
    }

    @GetMapping("/account")
    public String showAccountPage(Model model) {
        if (!model.containsAttribute("cryptoCurrencyDto")) {
            model.addAttribute("cryptoCurrencyDto", new CryptoCurrencyDto());
        }
        List<TransactionHistoryDto> transactionHistoryDtos = transactionHistoryService.getAllTransactionHistory();
        String balanceState = transactionHistoryService.getCurrentBalanceState();
        UserDto userDto = userService.getUser();
        model.addAttribute("transactionHistoryDtos", transactionHistoryDtos);
        DecimalFormat df = new DecimalFormat("#.####");
        String formattedBalance = df.format(userDto.getBalance());
        model.addAttribute("formattedBalance", formattedBalance);
        model.addAttribute("balanceState", balanceState);

        return "account";
    }
}
