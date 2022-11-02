package br.com.medicamento.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.medicamento.model.Medicamento;
import br.com.medicamento.service.MedicamentoService;
import br.com.medicamento.utility.Message;
import br.com.medicamento.utility.NegocioException;

@Named
@ViewScoped
public class MedicamentoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Medicamento medicamento;
	@Inject
	private MedicamentoService service;

	private String apresentacao;
	private String laboratorio;
	private String nome;
	private BigDecimal preco;
	private String registro;

	private List<Medicamento> medicamentos;

	@PostConstruct
	public void init() {
		limpaCampos();
		carregaCampos();
	}

	public void adicionar() {
		try {
			if (medicamento == null) {
				medicamento = new Medicamento();
			}

			carregaEntidade();
			service.salvar(medicamento);
			carregaCampos();
			limpaCampos();
			Message.info("Salvo com sucesso");
		} catch (NegocioException e) {
			Message.erro(e.getMessage());
		}
	}

	private void carregaCampos() {
		medicamentos = service.todosOsMedicamentos();
	}

	private void carregaEntidade() {
		medicamento.setNome(nome);
		medicamento.setApresentacao(apresentacao);
		medicamento.setLaboratorio(laboratorio);
		medicamento.setPreco(preco);
		medicamento.setRegistro(registro);
	}

	public void editar() {
		apresentacao = medicamento.getApresentacao();
		laboratorio = medicamento.getLaboratorio();
		nome = medicamento.getNome();
		preco = medicamento.getPreco();
		registro = medicamento.getRegistro();
	}

	public void excluir() {
		try {
			service.remover(medicamento);
			carregaCampos();
			Message.info(medicamento.getNome() + " foi removido");
		} catch (NegocioException e) {
			Message.erro(e.getMessage());
		}
	}

	public void novo() {
		try {
			medicamento = new Medicamento();
			limpaCampos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void limpaCampos() {
		medicamento = null;
		apresentacao = "";
		laboratorio = "";
		nome = "";
		preco = null;
		registro = "";
	}

	// Getters and Setters
	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public List<Medicamento> getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(List<Medicamento> medicamentos) {
		this.medicamentos = medicamentos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApresentacao() {
		return apresentacao;
	}

	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

}
