package br.com.fatec.les.crudsimples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.crudsimples.model.CompraProduto;

public interface CompraProdutoRepository extends JpaRepository<CompraProduto, Long>  {
	List<CompraProduto> findByCompraCompraId(Long compraId);
}
