package org.example.cryptotradingsim.service.impl;

import org.example.cryptotradingsim.model.dto.UserDto;
import org.example.cryptotradingsim.model.entity.User;
import org.example.cryptotradingsim.repository.TransactionHistoryRepository;
import org.example.cryptotradingsim.repository.UserCryptoRepository;
import org.example.cryptotradingsim.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCryptoRepository userCryptoRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setBalance(BigDecimal.valueOf(10000.0));
    }



    @Test
    void testResetAccount() {

        doNothing().when(userCryptoRepository).deleteAll();
        doNothing().when(userRepository).deleteAll();
        doNothing().when(transactionHistoryRepository).deleteAll();

        userService.resetAccount();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId());
        assertEquals(10000.0, savedUser.getBalance());
    }

    @Test
    void testGetUser() {

        when(userRepository.getReferenceById(1L)).thenReturn(user);

        UserDto userDto = new UserDto();
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.getUser();

        assertNotNull(result);
        verify(userRepository, times(1)).getReferenceById(1L);  // Проверяваме дали методът за взимане на потребител е бил извикан
    }
}
