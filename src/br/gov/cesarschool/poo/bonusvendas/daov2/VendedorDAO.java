package br.gov.cesarschool.poo.bonusvendas.daov2;

import java.io.Serializable;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;

public class VendedorDAO {
    private static final String BRANCO = "";
	private DAOGenerico dao;
	
	public VendedorDAO() {
		this.dao = new DAOGenerico(Vendedor.class, "Vendedor");
	}

    public void incluir(Vendedor vendedor) throws ExcecaoObjetoJaExistente {
    	try {
    		Vendedor vendedorBusca = buscar(vendedor.getCpf());

            if (vendedorBusca != null) {
                throw new ExcecaoObjetoJaExistente("Vendedor ja existente");
            } else {
            	dao.incluir(vendedor);
			}
		} catch (Exception e) {
			
		}  
    }

    public Vendedor buscar(String cpf) throws ExcecaoObjetoNaoExistente{
        return (Vendedor)dao.buscar(BRANCO + cpf);
    }
    
    public void alterar(Vendedor vendedor) throws ExcecaoObjetoNaoExistente {
    	Registro registro = buscar(vendedor.getIdUnico());

		if (registro == null) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			dao.alterar(vendedor);
		}  
	}  
    
    public Vendedor[] buscarTodos() {
		Serializable[] rets = dao.buscarTodos();
		Vendedor[] prods = new Vendedor[rets.length];
		
        for(int i = 0; i < rets.length; i++) {
            prods[i] = (Vendedor)rets[i];
        }
        return prods;
    }
}
