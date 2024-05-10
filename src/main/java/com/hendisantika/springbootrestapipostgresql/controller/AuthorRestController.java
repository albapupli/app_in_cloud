package com.hendisantika.springbootrestapipostgresql.controller;

import com.hendisantika.springbootrestapipostgresql.entity.Author;
import com.hendisantika.springbootrestapipostgresql.repository.AuthorRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorRestController.class);

    @Autowired
    private AuthorRepository repository;

    @PostMapping
    public ResponseEntity<?> addAuthor(@RequestBody Author author) {
        logger.info("Adding author: {}", author);
        try {
            Author savedAuthor = repository.save(author);
            logger.info("Author added successfully: {}", savedAuthor);
            return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while adding author", e);
            return new ResponseEntity<>("Failed to add author", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Author>> getAllAuthors() {
        logger.info("Retrieving all authors");
        try {
            Collection<Author> authors = repository.findAll();
            logger.info("Found {} authors", authors.size());
            return new ResponseEntity<>(authors, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving authors", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        logger.info("Retrieving author with id: {}", id);
        try {
            Author author = repository.findById(id).orElse(null);
            if (author != null) {
                logger.info("Author found: {}", author);
                return new ResponseEntity<>(author, HttpStatus.OK);
            } else {
                logger.warn("Author not found with id: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while retrieving author with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<Collection<Author>> findAuthorWithName(@RequestParam(value = "name") String name) {
        logger.info("Retrieving author by name: {}", name);
        try {
            Collection<Author> authors = repository.findByName(name);
            logger.info("Found {} authors with name: {}", authors.size(), name);
            return new ResponseEntity<>(authors, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving author with name: {}", name, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") long id, @RequestBody Author author) {
        logger.info("Updating author with id: {}", id);
        try {
            Optional<Author> currentAuthorOpt = repository.findById(id);
            if (currentAuthorOpt.isPresent()) {
                Author currentAuthor = currentAuthorOpt.get();
                currentAuthor.setName(author.getName());
                Author updatedAuthor = repository.save(currentAuthor);
                logger.info("Author updated successfully: {}", updatedAuthor);
                return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
            } else {
                logger.warn("Author not found with id: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating author with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable Long id) {
        logger.info("Deleting author with id: {}", id);
        try {
            repository.deleteById(id);
            logger.info("Author deleted successfully with id: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error occurred while deleting author with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllAuthors() {
        logger.info("Deleting all authors");
        try {
            repository.deleteAll();
            logger.info("All authors deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error occurred while deleting all authors", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}