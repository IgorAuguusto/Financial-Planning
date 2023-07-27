package budgetbuddy.gui;

import java.awt.Color;
import java.util.List;

import budgetbuddy.entidades.Categoria;
import budgetbuddy.entidades.Despesa;

/**
 * Interface résponsavel pelas constantes de cores e métodos de calculos sobre as despesas em uma certa categoria.
 * @author dio
 *
 */
public interface PropriedadesGrafico {
	public static final String ALIMENTACAO = "Alimentação";
    public static final String EDUCACAO = "Educação";
    public static final String ENERGIA_ELETRICA = "Energia Elétrica";
    public static final String ESPORTE = "Esporte";
    public static final String LAZER = "Lazer";
    public static final String HABITACAO = "Habitação";
    public static final String PLANO_DE_SAUDE = "Plano de Saúde";
    public static final String OUTROS = "Outros";
    public static final String SALDO = "Receita Restante";
    
    
    // Defina as cores para cada categoria
    public static final Color COLOR_ALIMENTACAO = new Color(220, 20, 60); // Red
    public static final Color COLOR_EDUCACAO = new Color(0, 128, 0); // Green
    public static final Color COLOR_ENERGIA_ELETRICA = new Color(255, 165, 0); // Orange
    public static final Color COLOR_ESPORTE = new Color(65, 105, 225); // Royal Blue
    public static final Color COLOR_LAZER = new Color(255, 192, 203); // Pink
    public static final Color COLOR_HABITACAO = new Color(128, 0, 128); // Purple
    public static final Color COLOR_PLANO_DE_SAUDE = new Color(70, 130, 180); // Steel Blue
    public static final Color COLOR_OUTROS = new Color(150, 75, 0); // Steel Blue
    // Define a cor para o saldo (transparente)
    public static final Color COLOR_SALDO = Color.WHITE;
    
    /**
     * Calcula o valor total das despesas para uma determinada categoria.
     *
     * @param despesaList Lista de despesas
     * @param categoria   Categoria para a qual deseja calcular o valor total
     * @return 
     * @return O valor total das despesas para a categoria especificada
     */
     public static float calcularValorDespesaPorCategoria(List<Despesa> despesaList, String categoria) {
        return despesaList.stream().filter((d) -> d.getStrCategoria().equals(categoria))
                .map((d) -> d.getValor()).reduce(0f, (d, d1) -> d + d1);
    }

    /**
     * Calcula o valor total das despesas para uma determinada categoria.
     *
     * @param despesaList Lista de despesas
     * @param categoria   Enumeração da categoria para a qual deseja calcular o valor total
     * @return O valor total das despesas para a categoria especificada
     */
    public static float calcularValorDespesaPorCategoria(List<Despesa> despesaList, Categoria categoria) {
        return despesaList.stream().filter((d) -> d.getCategoria().equals(categoria))
                .map((d) -> d.getValor()).reduce(0f, (d, d1) -> d + d1);
    }
}
