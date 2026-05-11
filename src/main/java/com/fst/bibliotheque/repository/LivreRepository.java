package com.fst.bibliotheque.repository;

import com.fst.bibliotheque.entity.Livre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {

    @Query("SELECT l FROM Livre l WHERE " +
            "LOWER(l.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.auteur) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.categorie) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Livre> search(@Param("keyword") String keyword, Pageable pageable);
}
