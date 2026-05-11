package com.fst.bibliotheque.service;

import com.fst.bibliotheque.entity.Emprunt;
import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.entity.StatutEmprunt;
import com.fst.bibliotheque.repository.EmpruntRepository;
import com.fst.bibliotheque.repository.LivreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;

    public EmpruntService(EmpruntRepository empruntRepository, LivreRepository livreRepository) {
        this.empruntRepository = empruntRepository;
        this.livreRepository = livreRepository;
    }

    public Page<Emprunt> findAll(Pageable pageable) {
        return empruntRepository.findAll(pageable);
    }

    public Page<Emprunt> findByStatut(StatutEmprunt statut, Pageable pageable) {
        return empruntRepository.findByStatut(statut, pageable);
    }

    public Emprunt findById(Long id) {
        return empruntRepository.findById(id).orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));
    }

    @Transactional
    public Emprunt createEmprunt(Emprunt emprunt) {
        Livre livre = livreRepository.findById(emprunt.getLivre().getId())
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));

        if (livre.getQuantiteDisponible() <= 0) {
            throw new RuntimeException("Le livre n'est plus disponible en stock");
        }

        // Mettre à jour la quantité
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() - 1);
        livreRepository.save(livre);

        // Configurer l'emprunt
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setStatut(StatutEmprunt.EN_COURS);
        
        return empruntRepository.save(emprunt);
    }

    @Transactional
    public Emprunt retournerEmprunt(Long id) {
        Emprunt emprunt = findById(id);
        
        if (emprunt.getStatut() == StatutEmprunt.RENDU) {
            throw new RuntimeException("Cet emprunt a déjà été rendu");
        }

        // Mettre à jour l'emprunt
        emprunt.setDateRetourEffective(LocalDate.now());
        emprunt.setStatut(StatutEmprunt.RENDU);
        empruntRepository.save(emprunt);

        // Mettre à jour la quantité du livre
        Livre livre = emprunt.getLivre();
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() + 1);
        livreRepository.save(livre);

        return emprunt;
    }

    // Tâche planifiée pour identifier les retards (chaque jour à minuit par exemple)
    // Pour cet exercice, on peut l'appeler manuellement ou via une action
    @Scheduled(cron = "0 0 0 * * *")
    public void checkRetards() {
        List<Emprunt> enCours = empruntRepository.findByStatut(StatutEmprunt.EN_COURS);
        LocalDate today = LocalDate.now();
        
        for (Emprunt e : enCours) {
            if (e.getDateRetourPrevue().isBefore(today)) {
                e.setStatut(StatutEmprunt.EN_RETARD);
                empruntRepository.save(e);
            }
        }
    }
}
