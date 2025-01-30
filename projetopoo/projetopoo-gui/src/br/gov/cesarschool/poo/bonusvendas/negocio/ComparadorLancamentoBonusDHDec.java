package br.gov.cesarschool.poo.bonusvendas.negocio;

import java.util.Comparator;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;

public class ComparadorLancamentoBonusDHDec implements Comparator {

    private static ComparadorLancamentoBonusDHDec instance;

    private ComparadorLancamentoBonusDHDec() {
    }

    public static synchronized ComparadorLancamentoBonusDHDec getInstance() {
        if (instance == null) {
            instance = new ComparadorLancamentoBonusDHDec();
        }
        return instance;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (!(o1 instanceof LancamentoBonus) || !(o2 instanceof LancamentoBonus)) {
            throw new IllegalArgumentException("Os objetos devem ser do tipo LancamentoBonus");
        }
        LancamentoBonus l1 = (LancamentoBonus) o1;
        LancamentoBonus l2 = (LancamentoBonus) o2;

        return l2.getDataHoraLancamento().compareTo(l1.getDataHoraLancamento());
    }
}