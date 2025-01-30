package br.gov.cesarschool.poo.bonusvendas.daov2;

import java.io.Serializable;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;

public class LancamentoBonusDAO {
    private static final String BRANCO = "";
	private DAOGenerico dao;
	
	public LancamentoBonusDAO() {
		this.dao = new DAOGenerico(LancamentoBonus.class, "Lancamento");
	}

    public void incluir(LancamentoBonus lancamentoBonus) throws ExcecaoObjetoJaExistente {		
		try {
	    	LancamentoBonus lancamentoBonusBusca = buscar(lancamentoBonus.getIdUnico());

			if (lancamentoBonusBusca != null) {
				throw new ExcecaoObjetoJaExistente();
			} else {
				dao.incluir(lancamentoBonus);
			}
		} catch (Exception e) {
			
		}
    }

    public LancamentoBonus buscar(String codigo) throws ExcecaoObjetoNaoExistente {
        return (LancamentoBonus)dao.buscar(BRANCO + codigo);
    }
    
    public void alterar(LancamentoBonus lancamento) throws ExcecaoObjetoNaoExistente {
    	Registro registro = buscar(lancamento.getIdUnico());

		if (registro == null) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			dao.alterar(lancamento);
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
