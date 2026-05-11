package com.fst.bibliotheque.service;

import com.fst.bibliotheque.entity.Membre;
import com.fst.bibliotheque.repository.MembreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MembreService {

    private final MembreRepository membreRepository;

    public MembreService(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    public List<Membre> findAll() {
        return membreRepository.findAll();
    }

    public Page<Membre> findAll(Pageable pageable) {
        return membreRepository.findAll(pageable);
    }

    public Membre findById(Long id) {
        return membreRepository.findById(id).orElseThrow(() -> new RuntimeException("Membre non trouvé"));
    }

    public Membre save(Membre membre) {
        return membreRepository.save(membre);
    }

    public void deleteById(Long id) {
        membreRepository.deleteById(id);
    }
}
