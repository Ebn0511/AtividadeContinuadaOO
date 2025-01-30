package br.gov.cesarschool.poo.bonusvendas.dao;

import java.io.Serializable;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;

public class LancamentoBonusDAO {
    private static final String BRANCO = "";
	private DAOGenerico dao;
	
	public LancamentoBonusDAO() {
		this.dao = new DAOGenerico(LancamentoBonus.class);
	}

    public boolean incluir(LancamentoBonus lancamentoBonus) {
        LancamentoBonus lancamentoBonusBusca = buscar(lancamentoBonus.getIdUnico());

        if (lancamentoBonusBusca != null) {
            return false;
        } else {
        	dao.incluir(lancamentoBonus);
            return true;
        }
    }

    public LancamentoBonus buscar(String codigo) {
        return (LancamentoBonus)dao.buscar(BRANCO + codigo);
    }
    
    public boolean alterar(LancamentoBonus lancamento) {
    	Registro registro = buscar(lancamento.getIdUnico());

		if (registro == null) {
			return false;
		} else {
			dao.alterar(lancamento);
			return true;
		}
    }
    
    public LancamentoBonus[] buscarTodos() {
        Serializable[] rets = dao.buscarTodos();
        LancamentoBonus[] prods = new LancamentoBonus[rets.length];
        
        for(int i = 0; i < rets.length; i++) {
            prods[i] = (LancamentoBonus)rets[i];
        }
        return prods;
    }
}
