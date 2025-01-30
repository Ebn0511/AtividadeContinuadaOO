package br.gov.cesarschool.poo.bonusvendas.negociov2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.gov.cesarschool.poo.bonusvendas.dao.DAOGenerico;
import br.gov.cesarschool.poo.bonusvendas.daov2.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ErroValidacao;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoValidacao;
import br.gov.cesarschool.poo.bonusvendas.negocio.ComparadorVendedorRenda;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.StringUtil;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.ValidadorCPF;
import br.gov.cesarschool.poo.bonusvendas.util.Ordenadora;

public class VendedorMediator {
	private static VendedorMediator instance;
	private VendedorDAO repositorioVendedor;
	private AcumuloResgateMediator caixaDeBonusMediator;

	public VendedorMediator() {
		repositorioVendedor = new VendedorDAO();
		caixaDeBonusMediator = new AcumuloResgateMediator();
	}

	public static synchronized VendedorMediator getInstancia() {
		if (instance == null) {
			instance = new VendedorMediator();
		}
		return instance;
	}

	public long incluir(Vendedor vendedor) throws ExcecaoObjetoJaExistente, ExcecaoValidacao {
		validar(vendedor);
		repositorioVendedor.incluir(vendedor);
		return caixaDeBonusMediator.gerarCaixaDeBonus(vendedor);
	}

	public void alterar(Vendedor vendedor) throws ExcecaoObjetoNaoExistente, ExcecaoValidacao {
		validar(vendedor);
		repositorioVendedor.alterar(vendedor);
	}

	public Vendedor buscar(String cpf) throws ExcecaoObjetoNaoExistente {
		return repositorioVendedor.buscar(cpf);
	}

	private void validar(Vendedor vendedor) throws ExcecaoValidacao {
		List<ErroValidacao> erros = new ArrayList<>();

		if (StringUtil.ehNuloOuBranco(vendedor.getCpf())) {
			erros.add(new ErroValidacao(1, "CPF nao informado"));
		} else if (!ValidadorCPF.ehCpfValido(vendedor.getCpf())) {
			erros.add(new ErroValidacao(2, "CPF invalido"));
		}

		if (StringUtil.ehNuloOuBranco(vendedor.getNomeCompleto())) {
			erros.add(new ErroValidacao(3, "Nome completo nao informado"));
		}

		if (vendedor.getSexo() == null) {
			erros.add(new ErroValidacao(4, "Sexo nao informado"));
		}

		if (vendedor.getDataNascimento() == null) {
			erros.add(new ErroValidacao(5, "Data de nascimento nao informada"));
		} else if (vendedor.calcularIdade() < 18) {
			erros.add(new ErroValidacao(6, "Data de nascimento invalida"));
		}

		if (vendedor.getRenda() < 0) {
			erros.add(new ErroValidacao(7, "Renda menor que zero"));
		}

		if (vendedor.getEndereco() == null) {
			erros.add(new ErroValidacao(8, "Endereco nao informado"));
		} else {
			if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getLogradouro())) {
				erros.add(new ErroValidacao(9, "Logradouro nao informado"));
			} else if (vendedor.getEndereco().getLogradouro().length() < 4) {
				erros.add(new ErroValidacao(10, "Logradouro tem menos de 04 caracteres"));
			}

			if (vendedor.getEndereco().getNumero() < 0) {
				erros.add(new ErroValidacao(11, "Numero menor que zero"));
			}

			if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getCidade())) {
				erros.add(new ErroValidacao(12, "Cidade nao informada"));
			}

			if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getEstado())) {
				erros.add(new ErroValidacao(13, "Estado nao informado"));
			}

			if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getPais())) {
				erros.add(new ErroValidacao(14, "Pais nao informado"));
			}
		}

		if (!erros.isEmpty()) {
			throw new ExcecaoValidacao(erros);
		}
	}

	public Vendedor[] gerarListagemClienteOrdenadaPorNome() {

		DAOGenerico dao = new DAOGenerico(Vendedor.class);

		Registro[] registros = dao.buscarTodos();

		List<Vendedor> vendedores = new ArrayList<>();

		for (Registro registro : registros) {
			if (registro instanceof Vendedor) {
				vendedores.add((Vendedor) registro);
			}
		}

		vendedores.sort(Comparator.comparing(Vendedor::getNomeCompleto));
		Vendedor[] vendedoresOrdenados = vendedores.toArray(new Vendedor[0]);

		return vendedoresOrdenados;
	}

	public Vendedor[] gerarListagemClienteOrdenadaPorRenda() {
		DAOGenerico dao = new DAOGenerico(Vendedor.class);
		Registro[] registros = dao.buscarTodos();

		List<Vendedor> vendedores = new ArrayList<>();

		for (Registro registro : registros) {
			if (registro instanceof Vendedor) {
				vendedores.add((Vendedor) registro);
			}
		}

		Ordenadora.ordenar(vendedores.toArray(new Vendedor[0]), ComparadorVendedorRenda.getInstance());

		return vendedores.toArray(new Vendedor[0]);
	}
}
