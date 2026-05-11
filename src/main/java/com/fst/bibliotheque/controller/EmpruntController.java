package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.entity.Emprunt;
import com.fst.bibliotheque.entity.StatutEmprunt;
import com.fst.bibliotheque.service.EmpruntService;
import com.fst.bibliotheque.service.LivreService;
import com.fst.bibliotheque.service.MembreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;
    private final LivreService livreService;
    private final MembreService membreService;

    public EmpruntController(EmpruntService empruntService, LivreService livreService, MembreService membreService) {
        this.empruntService = empruntService;
        this.livreService = livreService;
        this.membreService = membreService;
    }

    @GetMapping
    public String listEmprunts(
            @RequestParam(name = "statut", required = false) StatutEmprunt statut,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Emprunt> empruntPage;

        if (statut != null) {
            empruntPage = empruntService.findByStatut(statut, pageable);
        } else {
            empruntPage = empruntService.findAll(pageable);
        }

        model.addAttribute("emprunts", empruntPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", empruntPage.getTotalPages());
        model.addAttribute("statut", statut);
        model.addAttribute("statuts", StatutEmprunt.values());
        
        return "emprunts/list";
    }

    @GetMapping("/nouveau")
    public String showCreateForm(Model model) {
        Emprunt emprunt = new Emprunt();
        emprunt.setDateRetourPrevue(LocalDate.now().plusWeeks(2)); // Par défaut 2 semaines
        
        model.addAttribute("emprunt", emprunt);
        model.addAttribute("livres", livreService.findAll()); // Idéalement filtrer par dispo
        model.addAttribute("membres", membreService.findAll());
        return "emprunts/form";
    }

    @PostMapping("/enregistrer")
    public String saveEmprunt(@ModelAttribute("emprunt") Emprunt emprunt, Model model) {
        try {
            empruntService.createEmprunt(emprunt);
            return "redirect:/emprunts";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("livres", livreService.findAll());
            model.addAttribute("membres", membreService.findAll());
            return "emprunts/form";
        }
    }

    @GetMapping("/retourner/{id}")
    public String retournerEmprunt(@PathVariable Long id) {
        empruntService.retournerEmprunt(id);
        return "redirect:/emprunts";
    }
}
