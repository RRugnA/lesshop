package br.com.fatec.les.crudsimples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.crudsimples.model.DadosProduto;

public interface DadosProdutoRepository extends JpaRepository<DadosProduto, Long>  {
	List<DadosProduto> findByProdutoProdutoId(Long id);
}
