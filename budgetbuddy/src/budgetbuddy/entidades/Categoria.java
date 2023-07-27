package budgetbuddy.entidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeração que representa as categorias de uma despesa.
 * @author Igor Augusto Alves Silva 
 */
public enum Categoria {

    /**
     * Categoria de Alimentação.
     */
    ALIMENTACAO("Alimentação"),

    /**
     * Categoria de Educação.
     */
    EDUCACAO("Educação"),

    /**
     * Categoria de Energia Elétrica.
     */
    ENERGIAELETRICA("Energia Elétrica"),

    /**
     * Categoria de Esporte.
     */
    ESPORTE("Esporte"),

    /**
     * Categoria de Lazer.
     */
    LAZER("Lazer"),

    /**
     * Categoria de Habitação.
     */
    HABITACAO("Habitação"),

    /**
     * Categoria de Plano de Saúde.
     */
    PLANO_DE_SAUDE("Plano de Saúde"),
	
	/**
	 * Outras categorias
	 */
	OUTRAS_CATEGORIAS(""),
	
	/**
	 * Representa todas as categorias
	 */
	TODAS("Todas");
	
    private String categoria;

    
    private Categoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtém a categoria.
     *
     * @return A {@code Categoria}
     */
    public String getCategoria() {
        return categoria;
    }
    
    /**
     * Seta a categoria
     * @param categoria
     */
    public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	/**
     * Converte uma string em uma instância do enum Categoria.
     *
     * @param {@code string} A string representando a categoria.
     * @return Uma instância do enum {@code Categoria} correspondente à string fornecida.
     *         Se a string não corresponder a nenhuma categoria conhecida,vai ser definida como outra categoria,
     *         caso a string for null ou espaços em branco retorna null.
     */
    public static Categoria converterStringParaCategoria(String string) {
        if (string.isBlank()) {
        	return null;
        }
    	
    	for (Categoria cat : Categoria.values()) {
            if (cat.getCategoria().equalsIgnoreCase(string)) {
                return cat;
            }
        }
    	Categoria outraCategoria = Categoria.OUTRAS_CATEGORIAS;
    	outraCategoria.setCategoria(string);
        return outraCategoria;
    }
    
    /**
     * Todas as descrições das categorias, excluindo "OUTRAS_CATEGORIAS".
     * @return um {@code String[]} com todas as descrições das categorias, exceto "OUTRAS_CATEGORIAS".
     */
    public static String[] getCategorias() {
        Categoria[] categorias = Categoria.values();
        List<String> nomesCategorias = new ArrayList<>();

        for (Categoria categoria : categorias) {
            if (categoria != Categoria.OUTRAS_CATEGORIAS) {
                nomesCategorias.add(categoria.getCategoria());
            }
        }

        return nomesCategorias.toArray(new String[0]);
    }
    @Override
    public String toString() {
        return getCategoria();
    }
}//enum Categoria
