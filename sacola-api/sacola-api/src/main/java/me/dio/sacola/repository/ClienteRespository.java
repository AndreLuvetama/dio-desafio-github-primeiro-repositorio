package me.dio.sacola.repository;

import me.dio.sacola.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRespository  extends JpaRepository<Cliente, Long> {
}
