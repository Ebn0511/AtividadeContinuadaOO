package br.gov.cesarschool.poo.bonusvendas.daov2;

import java.io.Serializable;

import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;

public class CaixaDeBonusDAO {
	private static final String BRANCO = "";
	private DAOGenerico dao;
	
	public CaixaDeBonusDAO() {
		this.dao = new DAOGenerico(CaixaDeBonus.class, "Caixa");
	}
	
	public void incluir(CaixaDeBonus caixaDeBonus) throws ExcecaoObjetoJaExistente {
		try {
    		CaixaDeBonus caixaDeBonusBusca = buscar(caixaDeBonus.getNumero());

            if (caixaDeBonusBusca != null) {
                throw new ExcecaoObjetoJaExistente("Vendedor ja existente");
            }else {
            	dao.incluir(caixaDeBonus);
			}
		} catch (Exception e) {
			
		}
    }

	public CaixaDeBonus buscar(long numero) throws ExcecaoObjetoNaoExistente {
        return (CaixaDeBonus) dao.buscar(BRANCO + numero);
    }

	public void alterar(CaixaDeBonus caixaDeBonus) throws ExcecaoObjetoNaoExistente {
		Registro registro = buscar(caixaDeBonus.getNumero());

		if (registro == null) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			dao.alterar(caixaDeBonus);
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
