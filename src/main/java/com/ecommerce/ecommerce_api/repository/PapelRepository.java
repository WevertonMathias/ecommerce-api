package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.NomePapel;
import com.ecommerce.ecommerce_api.entity.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PapelRepository extends JpaRepository<Papel, UUID> {
    Optional<Papel> findByName(NomePapel nome);
}
