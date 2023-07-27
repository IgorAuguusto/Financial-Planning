package budgetbuddy.entidades;

import java.time.DateTimeException;
import java.time.LocalDate;

import budgetbuddy.utilitarios.Formatacoes;

/**
 * Classe que representa um Investimento.
 * @author Igor Augusto Alves Silva 
 */
public class Investimento implements Formatacoes {
	private Integer id;
    private String objetivo;
    private String estrategia;
    private String nome;
    private Float valorInvestido;
    private Float posicao;
    private Float rendimentoBruto;
    private Float rentabilidade;
    private LocalDate vencimento;
    
    /**
     * Construtor padrão da classe Investimento.
     * Inicializa os atributos com valores null.
     */
    public Investimento() {
        id = null;
    	objetivo = estrategia = nome = null;
    	valorInvestido = posicao = rendimentoBruto  = null;
    	vencimento = null;
    }
    
    /**
     * Construtor da classe Investimento que recebe todos os atributos como parâmetros.
     * @param objetivo o objetivo do investimento
     * @param estrategia a estratégia do investimento
     * @param nome o nome do investimento
     * @param valorInvestido o valor investido no investimento
     * @param posicao a posição atual do investimento
     * @param rendimentoBruto o rendimento bruto do investimento
     * @param rentabilidade a rentabilidade do investimento
     * @param vencimento a data de vencimento do investimento
     */
    public Investimento(String objetivo, String estrategia, String nome, Float valorInvestido, Float posicao,
            Float rendimentoBruto, Float rentabilidade, LocalDate vencimento) {
        this.objetivo = objetivo;
        this.estrategia = estrategia;
        this.nome = nome;
        this.valorInvestido = valorInvestido;
        this.posicao = posicao;
        this.rendimentoBruto = rendimentoBruto;
        this.rentabilidade = rentabilidade / 100;
        this.vencimento = vencimento;
    }

    /**
     * Construtor da classe Investimento que recebe os atributos como strings e realiza as devidas conversões.
     * @param objetivo o objetivo do investimento
     * @param estrategia a estratégia do investimento
     * @param nome o nome do investimento
     * @param valorInvestido o valor investido no investimento (como string)
     * @param posicao a posição atual do investimento (como string)
     * @param rendimentoBruto o rendimento bruto do investimento (como string)
     * @param rentabilidade a rentabilidade do investimento (como string)
     * @param vencimento a data de vencimento do investimento (como string)
     * @throws IllegalArgumentException se algum dos parâmetros não estiver no formato correto
     * @throws DateTimeException se a data de vencimento não estiver no formato correto
     */
    public Investimento(String objetivo, String estrategia, String nome, String valorInvestido, String posicao,
            String rendimentoBruto, String rentabilidade, String vencimento)
            throws IllegalArgumentException, DateTimeException {
        setObjetivo(objetivo);
        setEstrategia(estrategia);
        setNome(nome);
        setValorInvestido(valorInvestido);
        setPosicao(posicao);
        setRendimentoBruto(rendimentoBruto);
        setRentabilidade(rentabilidade);
        setVencimento(vencimento);
    }

	/**
     * Obtém o objetivo do investimento.
     * @return o objetivo do investimento
     */
    public String getObjetivo() {
        return objetivo;
    }

    /**
     * Define o objetivo do investimento.
     * @param objetivo o objetivo do investimento
     * @throws IllegalArgumentException se o objetivo for nulo ou uma string vazia
     */
    public void setObjetivo(String objetivo) throws IllegalArgumentException {
        if (objetivo == null || objetivo.isBlank()) {
            throw new IllegalArgumentException("Campo objetivo inválido");
        }
        this.objetivo = objetivo;
    }

    /**
     * Obtém a estratégia do investimento.
     * @return a estratégia do investimento
     */
    public String getEstrategia() {
        return estrategia;
    }

    /**
     * Define a estratégia do investimento.
     * @param estrategia a estratégia do investimento
     * @throws IllegalArgumentException se a estratégia for nula ou uma string vazia
     */
    public void setEstrategia(String estrategia) throws IllegalArgumentException {
        if (estrategia == null || estrategia.isBlank()) {
            throw new IllegalArgumentException("Campo estrategia inválido");
        }
        this.estrategia = estrategia;
    }

    /**
     * Obtém o nome do investimento.
     * @return o nome do investimento
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do investimento.
     * @param nome o nome do investimento
     * @throws IllegalArgumentException se o nome for nulo ou uma string vazia
     */
    public void setNome(String nome) throws IllegalArgumentException {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Campo nome inválido");
        }
        this.nome = nome;
    }

    /**
     * Obtém o valor investido no investimento.
     * @return o valor investido no investimento
     */
    public Float getValorInvestido() {
        return valorInvestido;
    }
    
    /**
     * Define o valor investido no investimento.
     * @param valorInvestido o valor investido no investimento
     */
    public void setValorInvestido(Float valorInvestido) {
        this.valorInvestido = valorInvestido;
    }
    
    /**
     * Define o valor investido no investimento a partir de uma string.
     * @param valorInvestido o valor investido no investimento (como string)
     * @throws IllegalArgumentException se a string não corresponder ao formato válido
     */
    public void setValorInvestido(String valorInvestido) throws IllegalArgumentException {
         if (!valorInvestido.matches(FLOAT_REGEX)) {
            throw new IllegalArgumentException("valor Investido inválido");
         }
        this.valorInvestido = Float.parseFloat(valorInvestido.replace(".", "").replace(",", "."));
    }

    /**
     * Obtém a posição atual do investimento.
     * @return a posição atual do investimento
     */
    public Float getPosicao() {
        return posicao;
    }

    /**
     * Define a posição atual do investimento.
     * @param posicao a posição atual do investimento
     */
    public void setPosicao(Float posicao) {
        this.posicao = posicao;
    }
    
    /**
     * Define a posição atual do investimento a partir de uma string.
     * @param posicao a posição atual do investimento (como string)
     * @throws IllegalArgumentException se a string não corresponder ao formato válido
     */
    public void setPosicao(String posicao) throws IllegalArgumentException {
         if (!posicao.matches(FLOAT_REGEX)) {
            throw new IllegalArgumentException("Posição inválida");
         }
         this.posicao = Float.parseFloat(posicao.replace(".", "").replace(",", "."));
    }

    /**
     * Obtém o rendimento bruto do investimento.
     * @return o rendimento bruto do investimento
     */
    public Float getRendimentoBruto() {
        return rendimentoBruto;
    }

    /**
     * Define o rendimento bruto do investimento.
     * @param rendimentoBruto o rendimento bruto do investimento
     */
    public void setRendimentoBruto(Float rendimentoBruto) {
        this.rendimentoBruto = rendimentoBruto;
    }
    
    /**
     * Define o rendimento bruto do investimento a partir de uma string.
     * @param rendimentoBruto o rendimento bruto do investimento (como string)
     * @throws IllegalArgumentException se a string não corresponder ao formato válido
     */
    public void setRendimentoBruto(String rendimentoBruto) throws IllegalArgumentException {
        if (!rendimentoBruto.matches(FLOAT_REGEX)) {
            throw new IllegalArgumentException("Rendimento Bruto inválido");
        }
        this.rendimentoBruto = Float.parseFloat(rendimentoBruto.replace(".", "").replace(",", "."));
    }

    /**
     * Obtém a rentabilidade do investimento.
     * @return a rentabilidade do investimento
     */
    public Float getRentabilidade() {
        return rentabilidade * 100;
    }
    
    /**
     * Define a rentabilidade do investimento.
     * @param rentabilidade a rentabilidade do investimento
     */
    public void setRentabilidade(Float rentabilidade) {
        this.rentabilidade = rentabilidade / 100;
    }

    /**
     * Define a rentabilidade do investimento a partir de uma string.
     * @param rentabilidade a rentabilidade do investimento (como string)
     * @throws IllegalArgumentException se a string não corresponder ao formato válido
     */
    public void setRentabilidade(String rentabilidade) throws IllegalArgumentException {
        if (!rentabilidade.matches(PORCENTAGEM_REGEX)) {
            throw new IllegalArgumentException("Campo rentabilidade inválido");
        }
        this.rentabilidade = Float.parseFloat(rentabilidade.replaceAll(",",".").replace("%", "")) / 100;
    }

    /**
     * Obtém a data de vencimento do investimento.
     * @return a data de vencimento do investimento
     */
    public LocalDate getVencimento() {
        return vencimento;
    }
    
    /**
     * Obtém a data de vencimento do investimento.
     * @return uma String data de vencimento do investimento
     */
    public String getStringVencimento() {
        return vencimento.format(DIA_MES_ANO_FORMATTER);
    }
    
    /**
     * Define a data de vencimento do investimento.
     * @param vencimento a data de vencimento do investimento
     */
    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    /**
     * Define a data de vencimento do investimento a partir de uma string.
     * @param vencimento a data de vencimento do investimento (como string)
     * @throws DateTimeException se a string não corresponder ao formato válido
     */
    public void setVencimento(String vencimento) throws DateTimeException {
        if (!vencimento.matches(DATA_REGEX)) {
            throw new DateTimeException("Campo data inválido");
        }
        this.vencimento = LocalDate.parse(vencimento, DIA_MES_ANO_FORMATTER);
    }
    
    /**
     * O id do investimento
     * @return id do investimento
     */
    public Integer getId() {
		return id;
	}

    /**
     * Define o id do investimento
     * @param  o id
     */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
     * Retorna uma representação em string do investimento.
     * @return uma representação em string do investimento
     */
	@Override
	public String toString() {
	    return String.format("Objetivo: %s, estratégia: %s, nome: %s, Valor Investido: %.2f, Posição: %.2f, Rendimento Bruto: %.2f, Rentabilidade: %.2f%%, Vencimento: %s",
	            objetivo, estrategia, nome, valorInvestido, posicao, rendimentoBruto, rentabilidade * 100, vencimento.format(DIA_MES_ANO_FORMATTER));
	}

	/**
	 * valida o investimente com base em todos os seus campos estiverem preenchido.
	 * @return true caso todos os campos estiverem preenchido, false caso contrario
	 */
	public boolean validarInvestimento() {
		if (objetivo == null || estrategia == null || nome == null || valorInvestido == null || posicao == null || rendimentoBruto == null || rentabilidade == null || vencimento == null ) {
			return false;
		}
		return true;
	}	
}//class Investimento
