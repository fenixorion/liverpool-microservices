package com.liverpool.users.service;

import com.liverpool.users.exception.ClienteNotFoundException;
import com.liverpool.users.exception.PedidoNotFoundException;
import com.liverpool.users.model.Pedido;
import com.liverpool.users.repository.ClienteRepository;
import com.liverpool.users.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.liverpool.users.util.NumberUtils.truncateTo2Decimals;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    public Pedido createPedido(Pedido pedido) {
        if (!clienteRepo.existsById(pedido.getClienteId())) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + pedido.getClienteId());
        }
        return pedidoRepo.save(pedido);
    }

    public List<Pedido> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepo.findAll();
        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("No hay pedidos registrados");
        }
        return pedidos;
    }

    public List<Pedido> getPedidosPorCliente(String clienteId) {
        if (!clienteRepo.existsById(clienteId)) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId);
        }
        List<Pedido> pedidos = pedidoRepo.findByClienteId(clienteId);
        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("No se encontraron pedidos para el cliente con ID: " + clienteId);
        }
        return pedidos;
    }

    public Pedido updatePedido(String id, Pedido actualizacion) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con ID: " + id));

        pedido.setCodigoProducto(actualizacion.getCodigoProducto());
        pedido.setCantidad(actualizacion.getCantidad());
        pedido.setPrecio(truncateTo2Decimals(pedido.getPrecio()));
        pedido.setClienteId(actualizacion.getClienteId());

        return pedidoRepo.save(pedido);
    }

    public void deletePedido(String id) {
        if (!pedidoRepo.existsById(id)) {
            throw new PedidoNotFoundException("Pedido no encontrado con ID: " + id);
        }
        pedidoRepo.deleteById(id);
    }
}
