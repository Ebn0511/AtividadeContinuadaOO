package br.gov.cesarschool.poo.bonusvendas.entidade;

import java.time.LocalDateTime;

public class LancamentoBonusDebito extends LancamentoBonus {
    private static final long serialVersionUID = -8600787050887559618L;

	private TipoResgate tipoResgate;
	
    public LancamentoBonusDebito(long numeroCaixaDeBonus, double valor, TipoResgate tipoResgate) {
        super(numeroCaixaDeBonus, valor);
        this.tipoResgate = tipoResgate;
    }
    
    public LancamentoBonusDebito(long numeroCaixaDeBonus, double valor, LocalDateTime dataHora, TipoResgate tipoResgate) {
        super(numeroCaixaDeBonus, valor, dataHora);
        this.tipoResgate = tipoResgate;
    }

	public TipoResgate getTipoResgate() {
		return tipoResgate;
	}
}