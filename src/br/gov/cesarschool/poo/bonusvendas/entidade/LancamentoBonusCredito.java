package br.gov.cesarschool.poo.bonusvendas.entidade;

import java.time.LocalDateTime;

public class LancamentoBonusCredito extends LancamentoBonus {
    private static final long serialVersionUID = -8600787050887559618L;

    public LancamentoBonusCredito(long numeroCaixaDeBonus, double valor, LocalDateTime dh1) {
        super(numeroCaixaDeBonus, valor, dh1);
    }

	public LancamentoBonusCredito(long numero, double valor) {
		super(numero, valor);
	}
}
