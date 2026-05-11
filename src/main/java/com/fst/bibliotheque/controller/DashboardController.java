package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.entity.StatutEmprunt;
import com.fst.bibliotheque.repository.EmpruntRepository;
import com.fst.bibliotheque.repository.LivreRepository;
import com.fst.bibliotheque.repository.MembreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final LivreRepository livreRepository;
    private final MembreRepository membreRepository;
    private final EmpruntRepository empruntRepository;

    public DashboardController(LivreRepository livreRepository, MembreRepository membreRepository, EmpruntRepository empruntRepository) {
        this.livreRepository = livreRepository;
        this.membreRepository = membreRepository;
        this.empruntRepository = empruntRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalBooks", livreRepository.count());
        model.addAttribute("activeMembers", membreRepository.count()); // Simplifié
        model.addAttribute("currentLoans", empruntRepository.findByStatut(StatutEmprunt.EN_COURS).size());
        model.addAttribute("overdueLoans", empruntRepository.findByStatut(StatutEmprunt.EN_RETARD).size());
        
        return "index";
    }
}
