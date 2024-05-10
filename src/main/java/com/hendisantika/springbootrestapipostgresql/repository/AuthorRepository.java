package com.hendisantika.springbootrestapipostgresql.repository;

import com.hendisantika.springbootrestapipostgresql.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // You can add custom query methods here if needed
    List<Author> findByName(String name);

}