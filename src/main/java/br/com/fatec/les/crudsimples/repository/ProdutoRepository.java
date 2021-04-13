package br.com.fatec.les.crudsimples.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusProduto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByProdStatus(StatusProduto status, Pageable sort);
}
