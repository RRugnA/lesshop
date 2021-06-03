package br.com.fatec.les.crudsimples.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.crudsimples.model.LogTransacao;

public interface LogTransacaoRepository extends JpaRepository<LogTransacao, Long>  {
	
}
