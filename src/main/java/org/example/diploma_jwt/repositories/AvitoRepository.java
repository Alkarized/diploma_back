package org.example.diploma_jwt.repositories;

import org.example.diploma_jwt.models.Avito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvitoRepository extends JpaRepository<Avito, Long> {
}
