package com.example.sqch14ex1.controllers;

import com.example.sqch14ex1.entities.Account;
import com.example.sqch14ex1.entities.TransferRequest;
import com.example.sqch14ex1.services.TransferService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final TransferService transferService;

    public AccountController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public void transferMoney(@RequestBody TransferRequest request){
        transferService.transfer(
                request.getSenderAccountId(),
                request.getReceiverAccountId(),
                request.getAmount());
    }

    @GetMapping("/accounts")
    public Iterable<Account> getAccounts(@RequestParam(required = false) String name){
        if(name == null){
            return transferService.getAllAccounts();
        }else{
            return transferService.findAccountsByName(name);
        }
    }

}
