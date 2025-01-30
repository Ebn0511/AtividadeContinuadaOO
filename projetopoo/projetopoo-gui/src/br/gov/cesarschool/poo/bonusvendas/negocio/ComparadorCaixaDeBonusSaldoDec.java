package br.gov.cesarschool.poo.bonusvendas.negocio;

import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.util.Comparador;

public class ComparadorCaixaDeBonusSaldoDec implements Comparador {

	private static ComparadorCaixaDeBonusSaldoDec instance;

	private ComparadorCaixaDeBonusSaldoDec() {
	}

	public static synchronized ComparadorCaixaDeBonusSaldoDec getInstance() {
		if (instance == null) {
			instance = new ComparadorCaixaDeBonusSaldoDec();
		}
		return instance;
	}

	@Override
	public int comparar(Object o1, Object o2) {
		if (!(o1 instanceof CaixaDeBonus) || !(o2 instanceof CaixaDeBonus)) {
			throw new IllegalArgumentException("Os objetos devem ser do tipo CaixaDeBonus");
		}
		CaixaDeBonus c1 = (CaixaDeBonus) o1;
		CaixaDeBonus c2 = (CaixaDeBonus) o2;

		return Double.compare(c2.getSaldo(), c1.getSaldo());
	}
}
