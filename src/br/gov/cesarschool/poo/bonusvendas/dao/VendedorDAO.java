package br.gov.cesarschool.poo.bonusvendas.dao;

import java.io.Serializable;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;

public class VendedorDAO {
    private static final String BRANCO = "";
	private DAOGenerico dao;
	
	public VendedorDAO() {
		this.dao = new DAOGenerico(Vendedor.class);
	}

    public boolean incluir(Vendedor vendedor) {
        Vendedor vendedorBusca = buscar(vendedor.getCpf());

        if (vendedorBusca != null) {
            return false;
        } else {
            dao.incluir(vendedor);
            return true;
        }
    }

    public Vendedor buscar(String cpf) {
        return (Vendedor)dao.buscar(BRANCO + cpf);
    }
    
    public boolean alterar(Vendedor vendedor) {
    	Registro registro = buscar(vendedor.getIdUnico());

		if (registro == null) {
			return false;
		} else {
			dao.alterar(vendedor);
			return true;
		}    }
    
    public Vendedor[] buscarTodos() {
		Serializable[] rets = dao.buscarTodos();
		Vendedor[] prods = new Vendedor[rets.length];
		
        for(int i = 0; i < rets.length; i++) {
            prods[i] = (Vendedor)rets[i];
        }
        return prods;
    }
}
