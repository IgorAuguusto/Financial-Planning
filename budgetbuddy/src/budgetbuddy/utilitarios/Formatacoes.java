package budgetbuddy.utilitarios;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

/**
 * Interface que contém constantes para formatações de datas e regex para validação de entradas.
 * @author Igor Augusto Alves Silva 
 *
 */
public interface Formatacoes {
	/**
	 * {@code String} que representa regex de uma data no formato dd/MM/yyyy onde dd, mm e yyyy são números.
	 */
	public static final String DATA_REGEX = "^\\d{2}/\\d{2}/\\d{4}$";
	
	/**
	 * {@code String} que representa regex de uma data no formato dd/MM onde dd e mm são números.
	 */
	public static final String DATA_DIA_MES_REGEX = "^\\d{2}/\\d{2}$";
	
	/**
	 * {@code String} que representa regex de um número float. Pode ter um ou mais (números) e zero ou mais (.).
	 */
	public static final String FLOAT_REGEX = "^[\\d.,]+$";
	
	/**
	 * {@code} que representa regex de um número exm porcentagem. Exemplo(39,47%).
	 */
	public static final String PORCENTAGEM_REGEX = "^\\d+(,\\d+)*%?$";
	
	/**
	 * {@code DateTimeFormatter} no formato dd/MM/yyyy.
	 */
	public static final DateTimeFormatter DIA_MES_ANO_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	/**
	 * {@code DateTimeFormatter} no formato dd/MM.
	 */
	public static final DateTimeFormatter DIA_MES_FORMATTER = DateTimeFormatter.ofPattern("dd/MM");
	
	/**
	 * {@code DecimalFormat} define um formata decimal par os números float. Formato definido ("#,##0.00");
	 */
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");
	
}//interface Formatacoes
