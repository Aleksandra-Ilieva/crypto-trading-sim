package org.example.cryptotradingsim.repository;

import org.example.cryptotradingsim.model.entity.CryptoCurrency;
import org.example.cryptotradingsim.model.entity.User;
import org.example.cryptotradingsim.model.entity.UserCrypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCryptoRepository extends JpaRepository<UserCrypto, Long> {
    UserCrypto findUserCryptoByUserAndCryptoCurrency(User user, CryptoCurrency crypto);
}
