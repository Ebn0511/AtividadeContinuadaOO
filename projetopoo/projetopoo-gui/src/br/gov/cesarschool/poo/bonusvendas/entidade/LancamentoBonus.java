package br.gov.cesarschool.poo.bonusvendas.entidade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;

public class LancamentoBonus extends Registro{
    protected long numeroCaixaDeBonus;
    protected double valor;
    private java.time.LocalDateTime dataHoraLancamento;

    public LancamentoBonus(long numeroCaixaDeBonus, double valor) {
        this.numeroCaixaDeBonus = numeroCaixaDeBonus;
        this.valor = valor;
        this.dataHoraLancamento = LocalDateTime.now();
    }

    public LancamentoBonus(long numeroCaixaDeBonus2, double valor2, LocalDateTime dataHora) {
    	this.numeroCaixaDeBonus = numeroCaixaDeBonus2;
    	this.valor = valor2;
    	this.dataHoraLancamento = dataHora;
	}

	public long getNumeroCaixaDeBonus() {
        return numeroCaixaDeBonus;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHoraLancamento() {
        return dataHoraLancamento;
    }

	@Override
	public String getIdUnico() {
		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LancamentoBonus lancamento = null;
		return lancamento.getNumeroCaixaDeBonus() + 
                lancamento.getDataHoraLancamento().format(customFormatter);
	}
}
