package br.gov.cesarschool.poo.bonusvendas.negocio;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.util.Comparador;

public class ComparadorVendedorNome implements Comparador{
	
	private static ComparadorVendedorNome instance;

    private ComparadorVendedorNome() {
    }

    public static synchronized ComparadorVendedorNome getInstance() {
        if (instance == null) {
            instance = new ComparadorVendedorNome();
        }
        return instance;
    }

    @Override
    public int comparar(Object o1, Object o2) {
        if (!(o1 instanceof Vendedor) || !(o2 instanceof Vendedor)) {
            throw new IllegalArgumentException("Os objetos devem ser do tipo Vendedor");
        }
        Vendedor v1 = (Vendedor) o1;
        Vendedor v2 = (Vendedor) o2;
        return v1.getNomeCompleto().compareTo(v2.getNomeCompleto());
    }
}
