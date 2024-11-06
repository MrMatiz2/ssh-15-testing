package com.example.sqch14ex1;

import com.example.sqch14ex1.entities.Account;
import com.example.sqch14ex1.exceptions.AccountNotFoundException;
import com.example.sqch14ex1.repositories.AccountRepository;
import com.example.sqch14ex1.services.TransferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransferServiceUnitTests {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    @DisplayName("Test the amount is transferred from one account to another if no exception occurs.")
    public void moneyTransferHappyFlow() {
        // AccountRepository accountRepository = mock(AccountRepository.class);
        // TransferService transferService = new TransferService(accountRepository);

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

    @Test
    @DisplayName("Test the method behavior if no account is find")
    public void moneyTransferDestinationAccountNotFoundFlow() {
        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal(1000));

        Account destination = new Account();
        destination.setId(2);
        destination.setAmount(new BigDecimal(1000));

        given(accountRepository.findById(sender.getId())).willReturn(Optional.of(sender));
        given(accountRepository.findById(destination.getId())).willReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transferService.transfer(1, 2, new BigDecimal(100))
        );

        verify(accountRepository, never()).changeAmount(anyLong(), any());

    }

}
