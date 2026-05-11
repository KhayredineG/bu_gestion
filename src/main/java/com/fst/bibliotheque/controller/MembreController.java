package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.entity.Membre;
import com.fst.bibliotheque.service.MembreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/membres")
public class MembreController {

    private final MembreService membreService;

    public MembreController(MembreService membreService) {
        this.membreService = membreService;
    }

    @GetMapping
    public String listMembres(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Membre> membrePage = membreService.findAll(pageable);

        model.addAttribute("membres", membrePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", membrePage.getTotalPages());
        
        return "membres/list";
    }

    @GetMapping("/nouveau")
    public String showCreateForm(Model model) {
        model.addAttribute("membre", new Membre());
        return "membres/form";
    }

    @PostMapping("/enregistrer")
    public String saveMembre(@Valid @ModelAttribute("membre") Membre membre, BindingResult result) {
        if (result.hasErrors()) {
            return "membres/form";
        }
        
        if (membre.getId() == null) {
            membre.setDateInscription(LocalDate.now());
            membre.setActif(true);
        }
        
        membreService.save(membre);
        return "redirect:/membres";
    }

    @GetMapping("/modifier/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("membre", membreService.findById(id));
        return "membres/form";
    }

    @GetMapping("/supprimer/{id}")
    public String deleteMembre(@PathVariable Long id) {
        membreService.deleteById(id);
        return "redirect:/membres";
    }
}
