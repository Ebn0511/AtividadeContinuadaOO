package br.gov.cesarschool.poo.bonusvendas.daov2;

import java.io.Serializable;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;

public class DAOGenerico {

    private static final String BRANCO = "";
    private CadastroObjetos cadastro;
    private String nomeEntidade;

    public DAOGenerico(Class<?> tipo, String nomeEntidade) {
        this.cadastro = new CadastroObjetos(tipo);
        this.nomeEntidade = nomeEntidade;
    }

    public void incluir(Registro reg) throws ExcecaoObjetoJaExistente {
        try {
			Registro registro = buscar(reg.getIdUnico());
			
			if (registro != null) {
				throw new ExcecaoObjetoJaExistente(nomeEntidade + " ja existente");
			} else {
				cadastro.incluir(reg, BRANCO + reg.getIdUnico());
			}
		} catch (ExcecaoObjetoNaoExistente e) {
			
		}
    }

    public void alterar(Registro reg) throws ExcecaoObjetoNaoExistente {
        Registro registro = buscar(reg.getIdUnico());

        if (registro == null) {
            throw new ExcecaoObjetoNaoExistente(nomeEntidade + " nao existente");
        } else {
            cadastro.alterar(reg, BRANCO + reg.getIdUnico());
        }
    }

    public Registro buscar(String id) throws ExcecaoObjetoNaoExistente {
        Registro registro = (Registro) cadastro.buscar(BRANCO + id);

        if (registro == null) {
            throw new ExcecaoObjetoNaoExistente(nomeEntidade + " nao existente");
        }
        return registro;
    }

    public Registro[] buscarTodos() {
        Serializable[] rets = cadastro.buscarTodos(Registro.class);
        Registro[] registros = new Registro[rets.length];

        for (int i = 0; i < registros.length; i++) {
            registros[i] = (Registro) rets[i];
        }

        return registros;
    }
}