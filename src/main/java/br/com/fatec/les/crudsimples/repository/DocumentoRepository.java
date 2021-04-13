package br.com.fatec.les.crudsimples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long>  {

	List<Documento> findByCliente(Cliente cliente);

}
