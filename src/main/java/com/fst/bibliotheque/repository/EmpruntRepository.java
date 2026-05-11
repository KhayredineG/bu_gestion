package com.fst.bibliotheque.repository;

import com.fst.bibliotheque.entity.Emprunt;
import com.fst.bibliotheque.entity.StatutEmprunt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    Page<Emprunt> findByStatut(StatutEmprunt statut, Pageable pageable);
    List<Emprunt> findByStatut(StatutEmprunt statut);
}
