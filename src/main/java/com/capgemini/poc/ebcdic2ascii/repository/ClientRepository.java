package com.capgemini.poc.ebcdic2ascii.repository;

import com.capgemini.poc.ebcdic2ascii.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
