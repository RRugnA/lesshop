//package br.com.fatec.les.crudsimples.model;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//
//@Entity
//public class LogTransacao {
//
//	@Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
//    private Long id;
//	
//	private String operacao;
//	private LocalDateTime dataCadastro;
//
//	@OneToOne
//	@JoinColumn(name = "usuario_id", referencedColumnName = "login")
//	private Usuario usuario;
//
//	public LogTransacao() {
//		
//	}
//	public LogTransacao(Usuario usuario, String operacao) {
//		this.usuario = usuario;
//		this.operacao = operacao;
//		this.dataCadastro = LocalDateTime.now();
//	}
//	
//	public Long getId() {
//		return id;
//	}
//
//	public String getOperacao() {
//		return operacao;
//	}
//
//	public void setOperacao(String operacao) {
//		this.operacao = operacao;
//	}
//
//	public LocalDateTime getDataCadastro() {
//		return dataCadastro;
//	}
//	public void setDataCadastro(LocalDateTime dataCadastro) {
//		this.dataCadastro = dataCadastro;
//	}
//	public Usuario getUsuario() {
//		return usuario;
//	}
//
//	public void setUsuario(Usuario usuario) {
//		this.usuario = usuario;
//	}
//
//}
