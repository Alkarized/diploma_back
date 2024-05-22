package org.example.diploma_jwt.repositories;

import org.example.diploma_jwt.models.Ozon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OzonRepository extends JpaRepository<Ozon, Long> {
}
