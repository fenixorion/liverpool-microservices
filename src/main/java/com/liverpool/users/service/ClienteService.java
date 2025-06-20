package com.liverpool.users.service;

import com.liverpool.users.exception.ClienteDuplicadoException;
import com.liverpool.users.exception.ClienteNotFoundException;
import com.liverpool.users.model.Cliente;
import com.liverpool.users.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.liverpool.users.util.TextUtils.normalizar;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> getClientes() {
        List<Cliente> clientes = repo.findAll();
        if (clientes.isEmpty()) {
            throw new ClienteNotFoundException("Sin registros de clientes");
        }
        return clientes;
    }

    public List<Cliente> getClientes(
            String nombre, String apellidoPaterno, String apellidoMaterno) {
        if (nombre.isBlank() || apellidoPaterno.isBlank() || apellidoMaterno.isBlank()) {
            throw new IllegalArgumentException("Nombre, apellido paterno y apellido materno son obligatorios");
        }
        List<Cliente> clientes = repo.findByNombreContainsIgnoreCaseAndApellidoPaternoContainsIgnoreCaseAndApellidoMaternoContainsIgnoreCase(
                normalizar(nombre), normalizar(apellidoPaterno), normalizar(apellidoMaterno));
        if (clientes.isEmpty()) {
            throw new ClienteNotFoundException("No se encontraron clientes con esos datos");
        }
        return clientes;
        }

    public Cliente createCliente(Cliente cliente){

        String normalizadoNombre = normalizar(cliente.getNombre());
        String normalizadoApellidoPaterno = normalizar(cliente.getApellidoPaterno());
        String normalizadoApellidoMaterno = normalizar(cliente.getApellidoMaterno());
        boolean existe = repo.findByNombreContainsIgnoreCaseAndApellidoPaternoContainsIgnoreCaseAndApellidoMaternoContainsIgnoreCase(
                normalizar(cliente.getNombre()),normalizar(cliente.getApellidoPaterno()),normalizar(cliente.getApellidoMaterno()))
                .stream().anyMatch(existing ->
                normalizar(existing.getNombre()).equals(normalizadoNombre)
                    && normalizar(existing.getApellidoPaterno()).equals(normalizadoApellidoPaterno)
                    && normalizar(existing.getApellidoMaterno()).equals(normalizadoApellidoMaterno));
        if (existe) {
            throw new ClienteDuplicadoException("Ya existe un cliente con el mismo nombre y apellidos");
        }
            cliente.setNombre(normalizadoNombre);
            cliente.setApellidoPaterno(normalizadoApellidoPaterno);
            cliente.setApellidoMaterno(normalizadoApellidoMaterno);
            cliente.setEmail(cliente.getEmail().trim().toLowerCase());
            cliente.setDireccion(cliente.getDireccion().trim());
        return repo.save(cliente);
    }

    public Cliente updateCliente(String id, Cliente updates) {

        Cliente cliente = repo.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("No se encontró el cliente con ID: " + id));

        cliente.setNombre(normalizar(updates.getNombre()));
        cliente.setApellidoPaterno(normalizar(updates.getApellidoPaterno()));
        cliente.setApellidoMaterno(normalizar(updates.getApellidoMaterno()));
        cliente.setEmail(updates.getEmail().toLowerCase().trim());
        cliente.setDireccion(updates.getDireccion().trim());

        return repo.save(cliente);
    }

    public void deleteCliente(String id){
        if (id.isBlank()) {
            throw new IllegalArgumentException("El ID es requerido");
        }

        if (!repo.existsById(id)) {
            throw new ClienteNotFoundException("No se encontró el cliente con ID: " + id);
        }

        repo.deleteById(id);
    }
}
