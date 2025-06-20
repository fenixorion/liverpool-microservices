package com.liverpool.users.controller;

import com.liverpool.users.model.Cliente;
import com.liverpool.users.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //Retorna todos los clientes
    @GetMapping("/search")
    public ResponseEntity<List<Cliente>> findClientes(){
        List<Cliente> clientes = clienteService.getClientes();
        return ResponseEntity.ok(clientes);
    }

    //Retorna busqueda por nombre, apellido paterno y apellido materno
    @GetMapping("/search/nombre/{nombre}/apellidoPaterno/{apellidoPaterno}/apellidoMaterno/{apellidoMaterno}")
    public ResponseEntity<List<Cliente>> findCliente(@PathVariable String nombre, @PathVariable String apellidoPaterno, @PathVariable String apellidoMaterno) {
        List<Cliente> clientes = clienteService.getClientes(nombre, apellidoPaterno, apellidoMaterno);
        return ResponseEntity.ok(clientes);
    }

    //Inserta un nuevo cliente
    @PostMapping("/create")
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody Cliente cliente) {
        Cliente creado = clienteService.createCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    //Actualiza datos del cliente
    @PatchMapping("/update/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String id, @Valid @RequestBody Cliente updates) {
        Cliente actualizado = clienteService.updateCliente(id, updates);
        return ResponseEntity.ok(actualizado);
    }

    //Borra un cliente por id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
