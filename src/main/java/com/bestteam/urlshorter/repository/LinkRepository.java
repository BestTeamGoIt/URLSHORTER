package com.bestteam.urlshorter.repository;
import com.bestteam.urlshorter.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, String> {
}
