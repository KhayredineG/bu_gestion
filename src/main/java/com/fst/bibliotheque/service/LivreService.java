package com.fst.bibliotheque.service;

import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.repository.LivreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LivreService {

    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public List<Livre> findAll() {
        return livreRepository.findAll();
    }

    public Page<Livre> findAll(Pageable pageable) {
        return livreRepository.findAll(pageable);
    }

    public Page<Livre> search(String keyword, Pageable pageable) {
        return livreRepository.search(keyword, pageable);
    }

    public Livre findById(Long id) {
        return livreRepository.findById(id).orElseThrow(() -> new RuntimeException("Livre non trouvé"));
    }

    public Livre save(Livre livre) {
        return livreRepository.save(livre);
    }

    public void deleteById(Long id) {
        livreRepository.deleteById(id);
    }
}
