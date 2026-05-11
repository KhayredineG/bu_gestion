package com.fst.bibliotheque.repository;

import com.fst.bibliotheque.entity.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {
}
