package br.gov.cesarschool.poo.bonusvendas.entidade;

import java.time.LocalDateTime;

import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;

public class CaixaDeBonus extends Registro{
    private long numero;
    private double saldo;
    private java.time.LocalDateTime dataHoraAtualizacao = java.time.LocalDateTime.now();
    

    public CaixaDeBonus(long numero) {
        this.numero = numero;
    }

    public long getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public LocalDateTime getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

    public void creditar(double valor) {
        this.saldo = this.saldo + valor;
        this.dataHoraAtualizacao = LocalDateTime.now();
    }

    public void debitar(double valor) {
        this.saldo = this.saldo - valor;
        this.dataHoraAtualizacao = LocalDateTime.now();
    }

	@Override
	public String getIdUnico() {
		return String.valueOf(numero);
	}
}
