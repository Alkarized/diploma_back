package org.example.diploma_jwt.repositories;

import org.example.diploma_jwt.models.VK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VKRepository extends JpaRepository<VK, Long> {
}
