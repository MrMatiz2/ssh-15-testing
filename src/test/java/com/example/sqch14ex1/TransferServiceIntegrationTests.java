package com.example.sqch14ex1;

import com.example.sqch14ex1.entities.Account;
import com.example.sqch14ex1.repositories.AccountRepository;
import com.example.sqch14ex1.services.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TransferServiceIntegrationTests {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private TransferService transferService;

    @Test
    public void transferServiceTransferAmountTest(){
        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal(1000));

        Account destination = new Account();
        destination.setId(2);
        destination.setAmount(new BigDecimal(1000));

        given(accountRepository.findById(sender.getId())).willReturn(Optional.of(sender));
        given(accountRepository.findById(destination.getId())).willReturn(Optional.of(destination));

        transferService.transfer(
                sender.getId(),
                destination.getId(),
                new BigDecimal(100)
        );

        verify(accountRepository).changeAmount(1L, new BigDecimal(900));
        verify(accountRepository).changeAmount(2L, new BigDecimal(1100));
    }

}
