package org.example.cryptotradingsim.repository;

import org.example.cryptotradingsim.model.entity.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends JpaRepository<CryptoCurrency, Long> {

    CryptoCurrency findByPair(String pair);
}
