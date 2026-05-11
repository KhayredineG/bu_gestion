package com.fst.bibliotheque.config;

import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.entity.Membre;
import com.fst.bibliotheque.repository.LivreRepository;
import com.fst.bibliotheque.repository.MembreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(LivreRepository livreRepository, MembreRepository membreRepository) {
        return args -> {
            if (livreRepository.count() == 0) {
                List<Livre> books = new ArrayList<>();
                String[] categories = {"Informatique", "Mathématiques", "Physique", "Littérature", "Histoire"};
                
                for (int i = 1; i <= 20; i++) {
                    Livre book = new Livre();
                    book.setTitre("Livre " + i);
                    book.setAuteur("Auteur " + (i % 5 + 1));
                    book.setIsbn("ISBN-000-" + i);
                    book.setCategorie(categories[i % 5]);
                    book.setQuantiteTotal(10);
                    book.setQuantiteDisponible(10);
                    books.add(book);
                }
                livreRepository.saveAll(books);
                System.out.println(">> 20 livres insérés.");
            }

            if (membreRepository.count() == 0) {
                List<Membre> membres = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    Membre membre = new Membre();
                    membre.setNom("Nom" + i);
                    membre.setPrenom("Prenom" + i);
                    membre.setEmail("membre" + i + "@university.com");
                    membre.setDateInscription(LocalDate.now().minusMonths(i));
                    membre.setActif(true);
                    membres.add(membre);
                }
                membreRepository.saveAll(membres);
                System.out.println(">> 10 membres insérés.");
            }
        };
    }
}
