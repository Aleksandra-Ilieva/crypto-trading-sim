package org.example.cryptotradingsim.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.cryptotradingsim.model.dto.UserDto;
import org.example.cryptotradingsim.model.entity.User;
import org.example.cryptotradingsim.repository.TransactionHistoryRepository;
import org.example.cryptotradingsim.repository.UserCryptoRepository;
import org.example.cryptotradingsim.repository.UserRepository;
import org.example.cryptotradingsim.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserCryptoRepository userCryptoRepository;

    private final ModelMapper modelMapper;

    private final TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserCryptoRepository userCryptoRepository, ModelMapper modelMapper, TransactionHistoryRepository transactionHistoryRepository) {
        this.userRepository = userRepository;
        this.userCryptoRepository = userCryptoRepository;
        this.modelMapper = modelMapper;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @PostConstruct
    public void registerUser() {
        User user = new User();
        user.setId(1L);
        user.setBalance(BigDecimal.valueOf(10000));
        userRepository.save(user);
    }

    @Override
    public void resetAccount() {
        userCryptoRepository.deleteAll();
        userRepository.deleteAll();
        transactionHistoryRepository.deleteAll();
        registerUser();
    }

    @Override
    public UserDto getUser() {
        User user = userRepository.getReferenceById(1L);
        return modelMapper.map(user, UserDto.class);

    }
}
