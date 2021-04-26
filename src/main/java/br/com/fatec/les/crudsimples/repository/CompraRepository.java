package br.com.fatec.les.crudsimples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.crudsimples.model.Compra;
import br.com.fatec.les.crudsimples.model.CompraStatus;

public interface CompraRepository extends JpaRepository<Compra, Long>  {

	List<Compra> findByClienteId(Long id);
	List<Compra> findByCompraStatus(CompraStatus status);
}
