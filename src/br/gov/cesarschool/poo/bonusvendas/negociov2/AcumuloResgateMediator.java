package br.gov.cesarschool.poo.bonusvendas.negociov2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.gov.cesarschool.poo.bonusvendas.daov2.CaixaDeBonusDAO;
import br.gov.cesarschool.poo.bonusvendas.daov2.LancamentoBonusDAO;
import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusCredito;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusDebito;
import br.gov.cesarschool.poo.bonusvendas.entidade.TipoResgate;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoValidacao;
import br.gov.cesarschool.poo.bonusvendas.negocio.ComparadorCaixaDeBonusSaldoDec;
import br.gov.cesarschool.poo.bonusvendas.negocio.ComparadorLancamentoBonusDHDec;
import br.gov.cesarschool.poo.bonusvendas.util.Ordenadora;

public class AcumuloResgateMediator {
    private static AcumuloResgateMediator instance;
    private CaixaDeBonusDAO repositorioCaixaDeBonus;
    private LancamentoBonusDAO repositorioLancamento;

    public AcumuloResgateMediator() {
        repositorioCaixaDeBonus = new CaixaDeBonusDAO();
        repositorioLancamento = new LancamentoBonusDAO();
    }

    public static synchronized AcumuloResgateMediator getInstancia() {
        if (instance == null) {
            instance = new AcumuloResgateMediator();
        }
        return instance;
    }

    public long gerarCaixaDeBonus(Vendedor vendedor) throws ExcecaoObjetoJaExistente {
        if (vendedor == null) {
        	throw new ExcecaoObjetoJaExistente("Vendedor nao fornecido");
        }

        LocalDate dataAtual =LocalDate.now();
        String dataFormatada =  dataAtual.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        
        String numeroCaixaDeBonus = vendedor.getCpf().substring(0, vendedor.getCpf().length() - 2) + dataFormatada;

        
        CaixaDeBonus caixaDeBonus = new CaixaDeBonus(Long.parseLong(numeroCaixaDeBonus));
        
        repositorioCaixaDeBonus.incluir(caixaDeBonus);
        
        return Long.parseLong(numeroCaixaDeBonus);
    }

    public void acumularBonus(long numeroCaixaDeBonus, double valor) throws ExcecaoObjetoNaoExistente, ExcecaoValidacao {
        if (valor <= 0) {
        	throw new ExcecaoValidacao("Valor menor ou igual a zeo");
        }

        CaixaDeBonus caixaDeBonus = repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);

        caixaDeBonus.creditar(valor);
        repositorioCaixaDeBonus.alterar(caixaDeBonus);
        
        LancamentoBonusCredito lancamentoBonusCredito = new LancamentoBonusCredito(caixaDeBonus.getNumero(), valor);

        try {
            repositorioLancamento.incluir(lancamentoBonusCredito);
		} catch (ExcecaoObjetoJaExistente e) {
			throw new ExcecaoValidacao("Inconsistencia no cadastro de lancamento");
		}
    }
    
    public void resgatar(long numeroCaixaDeBonus, double valor, TipoResgate tipo) throws ExcecaoObjetoNaoExistente, ExcecaoValidacao {
    	if (valor <= 0) {
    		throw new ExcecaoValidacao("Valor menor ou igual a zero");
    	}
    	
    	CaixaDeBonus caixaDeBonus = repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);
    	
    	if (caixaDeBonus.getSaldo() < valor) {
    		throw new ExcecaoValidacao("Saldo insuficiente");
    	}
    	
    	caixaDeBonus.debitar(valor);
    	
    	repositorioCaixaDeBonus.alterar(caixaDeBonus);
    	
        LancamentoBonusDebito lancamentoDebito = new LancamentoBonusDebito(numeroCaixaDeBonus, valor, tipo);
    	
        try {
            repositorioLancamento.incluir(lancamentoDebito);
		} catch (ExcecaoObjetoJaExistente e) {
			throw new ExcecaoValidacao("Inconsistenai no cadastro de lacamento");
		}
    }
    
    public CaixaDeBonus[] listaCaixaDeBonusPorSaldoMaior(double saldoInicial) {
        CaixaDeBonusDAO caixaDeBonusDAO = new CaixaDeBonusDAO();
        CaixaDeBonus[] todasCaixas = caixaDeBonusDAO.buscarTodos();

        List<CaixaDeBonus> caixasFiltradas = new ArrayList<>();

        for (CaixaDeBonus caixa : todasCaixas) {
            if (caixa.getSaldo() >= saldoInicial) {
                caixasFiltradas.add(caixa);
            }
        }

        CaixaDeBonus[] arrayCaixasFiltradas = caixasFiltradas.toArray(new CaixaDeBonus[0]);
        Ordenadora.ordenar(arrayCaixasFiltradas, ComparadorCaixaDeBonusSaldoDec.getInstance());

        return arrayCaixasFiltradas;
    }
    
    public LancamentoBonus[] listaLancamentosPorFaixaData(LocalDate d1, LocalDate d2) {
        LancamentoBonusDAO lancamentoBonusDAO = new LancamentoBonusDAO();
        LancamentoBonus[] todosLancamentos = lancamentoBonusDAO.buscarTodos();

        List<LancamentoBonus> lancamentosFiltrados = Arrays.stream(todosLancamentos)
                .filter(l -> !l.getDataHoraLancamento().toLocalDate().isBefore(d1) &&
                             !l.getDataHoraLancamento().toLocalDate().isAfter(d2))
                .collect(Collectors.toList());

        Collections.sort(lancamentosFiltrados, ComparadorLancamentoBonusDHDec.getInstance().reversed());

        return lancamentosFiltrados.toArray(new LancamentoBonus[0]);
    }
}
