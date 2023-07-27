package budgetbuddy.entidades;

import java.time.DateTimeException;
import java.time.LocalDate;

import budgetbuddy.utilitarios.Formatacoes;

/**
 * Classe que representa uma receita financeira.
 * @author Igor Augusto Alves Silva 
 */
public class Receita implements Formatacoes {
    private String tipo;
    private LocalDate data;
    private Float valor;
    
    /**
     * Construtor default da classe Receita.
     */
    public Receita() {
    	tipo = "";
    }

	/**
     * Construtor da classe Receita.
     *
     * @param tipo o tipo da receita
     * @param data a data da receita no formato dd/MM/yyyy
     * @param valor o valor da receita
     * @throws IllegalArgumentException caso o tipo seja inválido (nulo ou vazio)
     * @throws DateTimeException caso a data seja inválida
     */
    public Receita(String tipo, String data, String valor) throws IllegalArgumentException, DateTimeException {
        setTipo(tipo);
        setData(data);
        setValor(valor);
    }//construtor

    /**
     * Construtor da classe Receita.
     *
     * @param tipo o tipo da receita
     * @param data a data da receita
     * @param valor o valor da receita
     */
    public Receita(String tipo, LocalDate data, Float valor) {
        this.tipo = tipo;
        this.data = data;
        this.valor = valor;
    }//construtor

    /**
     * Obtém o tipo da receita.
     *
     * @return o tipo da receita
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo da receita.
     *
     * @param tipo o tipo da receita
     * @throws IllegalArgumentException caso o tipo seja inválido (nulo ou vazio)
     */
    public void setTipo(String tipo) throws IllegalArgumentException {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("Campo Tipo inválido");
        }
        this.tipo = tipo;
    }

    /**
     * Obtém a data da receita.
     *
     * @return a data da receita
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Define a data da receita.
     *
     * @param data a data da receita no formato dd/MM/yyyy
     * @throws DateTimeException caso a data seja inválida
     */
    public void setData(String data) throws DateTimeException {
        if (!data.matches(DATA_REGEX)) {
            throw new DateTimeException("Campo data inválido");
        }
        this.data = LocalDate.parse(data, DIA_MES_ANO_FORMATTER);
    }

    /**
     * Define a data da receita.
     *
     * @param data a data da receita
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Obtém o valor da receita.
     *
     * @return o valor da receita
     */
    public Float getValor() {
        return valor;
    }

    /**
     * Define o valor da receita.
     *
     * @param valor o valor da receita
     * @throws IllegalArgumentException caso o valor seja inválido
     */
    public void setValor(String valor) throws IllegalArgumentException {
        if (!valor.matches(FLOAT_REGEX)) {
            throw new IllegalArgumentException("Campo valor inválido");
        }
        this.valor = Float.parseFloat(valor.replace(".", ""));
    }

    /**
     * Define o valor da receita.
     *
     * @param valor o valor da receita
     */
    public void setValor(Float valor) {
        this.valor = valor;
    }

    /**
     * Retorna uma representação em String da Receita.
     *
     * @return uma representação em String da Receita
     */
    @Override
    public String toString() {
        return String.format("Tipo: %s, data: %s, valor: %1.2f", tipo, data.format(DIA_MES_ANO_FORMATTER), valor);
    }
}//class Receita

