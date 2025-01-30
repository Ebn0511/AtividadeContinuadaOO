package br.gov.cesarschool.poo.bonusvendas.dao;

import java.io.Serializable;

import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;

public class CaixaDeBonusDAO {
	private static final String BRANCO = "";
	private DAOGenerico dao;
	
	public CaixaDeBonusDAO() {
		this.dao = new DAOGenerico(CaixaDeBonus.class);
	}
	
	public boolean incluir(CaixaDeBonus caixaDeBonus) {
        CaixaDeBonus caixaDeBonusBusca = buscar(caixaDeBonus.getNumero());

        if (caixaDeBonusBusca != null) {
            return false;
        } else {
            dao.incluir(caixaDeBonus);
            return true;
        }
    }

	public CaixaDeBonus buscar(long numero) {
        return (CaixaDeBonus) dao.buscar(BRANCO + numero);
    }

	public boolean alterar(CaixaDeBonus caixaDeBonus) {
		Registro registro = buscar(caixaDeBonus.getNumero());

		if (registro == null) {
			return false;
		} else {
			dao.alterar(caixaDeBonus);
			return true;
		}
	}

	public CaixaDeBonus[] buscarTodos() {
		Serializable[] rets = dao.buscarTodos();
		CaixaDeBonus[] prods = new CaixaDeBonus[rets.length];

		for (int i = 0; i < rets.length; i++) {
			prods[i] = (CaixaDeBonus) rets[i];
		}
		return prods;
	}
}
