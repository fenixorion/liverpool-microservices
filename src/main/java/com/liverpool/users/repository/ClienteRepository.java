package com.liverpool.users.repository;

import com.liverpool.users.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
    List<Cliente> findByNombreContainsIgnoreCaseAndApellidoPaternoContainsIgnoreCaseAndApellidoMaternoContainsIgnoreCase(String nombre, String apellidoPaterno, String apellidoMaterno);
}
