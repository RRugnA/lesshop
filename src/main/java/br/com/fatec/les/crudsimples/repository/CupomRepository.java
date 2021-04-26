package br.com.fatec.les.crudsimples.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.crudsimples.model.Cupom;

public interface CupomRepository extends JpaRepository<Cupom, String>  {
	
}
