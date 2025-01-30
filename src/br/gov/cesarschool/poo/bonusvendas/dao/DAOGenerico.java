package br.gov.cesarschool.poo.bonusvendas.dao;

import java.io.Serializable;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;

public class DAOGenerico {
	
    private static final String BRANCO = "";
	private CadastroObjetos cadastro;

	public DAOGenerico(Class<?> tipo) {
		this.cadastro = new CadastroObjetos(tipo);
	}
	
	public boolean incluir(Registro reg) {
		Registro registro = buscar(reg.getIdUnico());

        if (registro != null) {
            return false;
        } else {
            cadastro.incluir(reg, BRANCO + reg.getIdUnico());
            return true;
        }
    }

    public boolean alterar(Registro reg) {
    	Registro registro = buscar(reg.getIdUnico());
    	
    	if (registro == null) {
			return false;
		} else {
			cadastro.alterar(reg, BRANCO + reg.getIdUnico());
			return true;
		}
    }

	public Registro buscar(String id) {
        return (Registro) cadastro.buscar(BRANCO + id);
    }

    public Registro[] buscarTodos() {
    	Serializable[] rets = cadastro.buscarTodos(Registro.class);
    	Registro[] registros = new Registro[rets.length];
    	
    	for (int i = 0; i < registros.length; i++) {
			registros[i] = (Registro)rets[i];
		}
    	
    	return registros;
    }
}
