package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.service.LivreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping
    public String listLivres(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Livre> livrePage;

        if (keyword != null && !keyword.isEmpty()) {
            livrePage = livreService.search(keyword, pageable);
        } else {
            livrePage = livreService.findAll(pageable);
        }

        model.addAttribute("livres", livrePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", livrePage.getTotalPages());
        model.addAttribute("keyword", keyword);
        
        return "livres/list";
    }

    @GetMapping("/nouveau")
    public String showCreateForm(Model model) {
        model.addAttribute("livre", new Livre());
        return "livres/form";
    }

    @PostMapping("/enregistrer")
    public String saveLivre(@Valid @ModelAttribute("livre") Livre livre, BindingResult result) {
        if (result.hasErrors()) {
            return "livres/form";
        }
        
        // Initialiser quantiteDisponible pour un nouveau livre
        if (livre.getId() == null) {
            livre.setQuantiteDisponible(livre.getQuantiteTotal());
        }
        
        livreService.save(livre);
        return "redirect:/livres";
    }

    @GetMapping("/modifier/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Livre livre = livreService.findById(id);
        model.addAttribute("livre", livre);
        return "livres/form";
    }

    @GetMapping("/supprimer/{id}")
    public String deleteLivre(@PathVariable Long id) {
        livreService.deleteById(id);
        return "redirect:/livres";
    }
}
