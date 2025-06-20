package com.liverpool.users.controller;

import com.liverpool.users.model.Pedido;
import com.liverpool.users.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@Valid @RequestBody Pedido pedido) {
        Pedido creado = pedidoService.createPedido(pedido);
        return ResponseEntity.status(201).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.getAllPedidos());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable String clienteId) {
        return ResponseEntity.ok(pedidoService.getPedidosPorCliente(clienteId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable String id, @Valid @RequestBody Pedido actualizacion) {
        Pedido actualizado = pedidoService.updatePedido(id, actualizacion);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable String id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}
