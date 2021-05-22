package br.com.fatec.les.crudsimples.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class CompraProdutoId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long compraId;
	private Long produtoId;
	
	public CompraProdutoId() {
		
	}
	
	public CompraProdutoId(Long compraId, Long produtoId) {
		super();
		this.compraId = compraId;
		this.produtoId = produtoId;
	}

	public Long getCompraId() {
		return compraId;
	}

	public void setCompraId(Long compraId) {
		this.compraId = compraId;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((compraId == null) ? 0 : compraId.hashCode());
        result = prime * result
                + ((produtoId == null) ? 0 : produtoId.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompraProdutoId other = (CompraProdutoId) obj;
        return Objects.equals(getCompraId(), other.getCompraId()) && Objects.equals(getProdutoId(), other.getProdutoId());
    }

}
