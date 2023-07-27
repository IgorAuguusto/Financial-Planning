package budgetbuddy.entidades;

import static budgetbuddy.entidades.Categoria.converterStringParaCategoria;
import static budgetbuddy.entidades.FormaPagamento.converterStringParaFormaPagamento;
import static budgetbuddy.entidades.Situacao.converterStringParaSituacao;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

import budgetbuddy.utilitarios.Formatacoes;
/**
 * A classe Despesa representa uma despesa financeira.
 * @author Igor Augusto Alves Silva 
 */
public class Despesa implements Formatacoes {
	private Integer id;
	private LocalDate dataDespesa;
    private LocalDate diaPagamento;
    private FormaPagamento formaPagamento;
    private String descricao;
    private Categoria categoria;
    private Float valor;
    private Situacao situacao;

    /**
     * Construtor padrão da classe Despesa.
     * Inicializa os atributos com valores com null.
     */
    public Despesa() {
    	id = null;
    	descricao = null;
    	dataDespesa = null;
    	diaPagamento = null;
    	formaPagamento = null;
    	categoria = null;
    	valor = null;
    	situacao = Situacao.A_PAGAR;
    }
    
    /**
     * Construtor da classe Despesa.
     *
     * @param dataDespesa     a data da despesa
     * @param diaPagamento    o dia do pagamento
     * @param formaPagamento  a forma de pagamento
     * @param descricao       a descrição da despesa
     * @param categoria       a categoria da despesa
     * @param valor           o valor da despesa
     * @param situacao        a situação da despesa
     */
    public Despesa(LocalDate dataDespesa, LocalDate diaPagamento, FormaPagamento formaPagamento, String descricao,
                   Categoria categoria, float valor, Situacao situacao) {
        this.dataDespesa = dataDespesa;
        this.diaPagamento = diaPagamento;
        this.formaPagamento = formaPagamento;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.situacao = situacao;
    }

    /**
     * Construtor da classe Despesa que aceita valores em formato de String.
     *
     * @param dataDespesa     a data da despesa no formato de String
     * @param diaPagamento    o dia do pagamento no formato de String
     * @param formaPagamento  a forma de pagamento no formato de String
     * @param descricao       a descrição da despesa
     * @param categoria       a categoria da despesa no formato de String
     * @param valor           o valor da despesa no formato de String
     * @param situacao        a situação da despesa no formato de String
     * @throws IllegalArgumentException se algum argumento fornecido for inválido
     * @throws DateTimeException se ocorrer um erro ao analisar as datas
     */
    public Despesa(String dataDespesa, String diaPagamento, String formaPagamento, String descricao,
                   String categoria, String valor, String situacao)
            throws IllegalArgumentException, DateTimeException {
        setDataDespesa(dataDespesa);
        setDiaPagamento(diaPagamento);
        setFormaPagamento(formaPagamento);
        setDescricao(descricao);
        setCategoria(categoria);
        setValor(valor);
        setSituacao(situacao);
    }

    
    /**
     * Obtém o id da despesa
     * @return o id da despesa
     */
    public Integer getId() {
		return id;
	}
    
    /**
     * Deine a data da despesa
     * @param id da despesa
     */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
     * Obtém a data da despesa.
     *
     * @return uma String que representa a data da despesa no formato dd/MM/yyyy.
     */
    public String getStringDataDespesa() {
        return DIA_MES_ANO_FORMATTER.format(dataDespesa);
    }
    
    /**
     * A data da despesa.
     * @return a data da despesa.
     */
    public LocalDate getDataDespesa() {
		return dataDespesa;
	}

	/**
     * Define a data da despesa.
     *
     * @param dataDespesa a data da despesa
     */
    public void setDataDespesa(LocalDate dataDespesa) {
        this.dataDespesa = dataDespesa;
    }

    /**
     * Define a data da despesa a partir de uma String no formato "dd/MM/yyyy".
     *
     * @param dataDespesa a data da despesa no formato de String
     * @throws DateTimeException se a String fornecida for inválida
     */
    public void setDataDespesa(String dataDespesa) throws DateTimeException {
        if (!dataDespesa.matches(DATA_REGEX)) {
            throw new DateTimeException("Data Inválida");
        }
        this.dataDespesa = LocalDate.parse(dataDespesa, DIA_MES_ANO_FORMATTER);
    }

    /**
     * Obtém o dia do pagamento.
     *
     * @return o dia do pagamento
     */
    public String getDiaPagamento() {
        int dia = diaPagamento.getDayOfMonth();
        return String.format("%,02d", dia);
    }

    /**
     * Obtém o més do pagamento.
     *
     * @return o mes do pagamento
     */
    public String getMesPagamento() {
        int mes = diaPagamento.getMonthValue();
        return String.format("%,02d", mes);
    }
    
    /**
     * Obtém o més do pagamento.
     *
     * @return um int com o mes do pagamento
     */
    public int getMesIntPagamento() {
        return diaPagamento.getMonthValue();
    }
    
    /**
     * Obtém a data do pagamento.
     *
     * @return a data do pagamento
     */
    public LocalDate getDataPagamento() {
        return diaPagamento;
    }
    
    /**
     * Define o dia do pagamento.
     *
     * @param diaPagamento o dia do pagamento
     */
    public void setDiaPagamento(LocalDate diaPagamento) {
        this.diaPagamento = diaPagamento;
    }

    /**
     * Define o dia do pagamento a partir de uma String no formato "dd/MM".
     * Se o mês da data de despesa for menor ou igual ao mês fornecido, o ano será o mesmo da data de despesa.
     * Caso contrário, o ano será o próximo ano.
     *
     * @param diaPagamento o dia do pagamento no formato de String no padrão "dd/MM"
     * @throws DateTimeException se a String fornecida não estiver no formato correto
     */
    public void setDiaPagamento(String diaPagamento) throws DateTimeException {
        if (!diaPagamento.matches(DATA_DIA_MES_REGEX)) {
            throw new DateTimeException("Dia Inválido");
        }
        int diaPagamentoInt = Integer.parseInt(diaPagamento.substring(0, 2));
        int mesPagamentoInt = Integer.parseInt(diaPagamento.substring(3, 4));
        int mesDespesaInt = dataDespesa.getMonthValue();

        if (mesDespesaInt < mesPagamentoInt) {
            this.diaPagamento = LocalDate.parse(String.format("%s/%s", diaPagamento, dataDespesa.getYear()), DIA_MES_ANO_FORMATTER);
        }
        else if (mesPagamentoInt == mesDespesaInt && diaPagamentoInt < dataDespesa.getDayOfMonth()) {
            this.diaPagamento = LocalDate.parse(String.format("%s/%s", diaPagamento, dataDespesa.getYear() + 1), DIA_MES_ANO_FORMATTER);
        }
        else {
            this.diaPagamento = LocalDate.parse(String.format("%s/%s", diaPagamento, dataDespesa.getYear()), DIA_MES_ANO_FORMATTER);
        }
    }

    /**
     * Obtém a forma de pagamento.
     *
     * @return a forma de pagamento
     */
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * Define a forma de pagamento.
     *
     * @param formaPagamento a forma de pagamento
     */
    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    /**
     * Define a forma de pagamento a partir de uma String.
     *
     * @param formaPagamento a forma de pagamento no formato de String
     * @throws IllegalArgumentException se a String fornecida for inválida
     */
    public void setFormaPagamento(String formaPagamento) throws IllegalArgumentException {
        FormaPagamento formaAux = converterStringParaFormaPagamento(formaPagamento);
        if (formaAux == null) {
            throw new IllegalArgumentException("Tipo de Pagamento Inválido");
        }
        this.formaPagamento = formaAux;
    }

    /**
     * Obtém a descrição da despesa.
     *
     * @return a descrição da despesa
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da despesa.
     *
     * @param descricao a descrição da despesa
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a categoria da despesa.
     *
     * @return a categoria da despesa em forma de String.
     */
    public String getStrCategoria() {
        return categoria.getCategoria();
    }
    
    /**
     * Obtém a categoria da despesa.
     *
     * @return a categoria da despesa.
     */
    public Categoria getCategoria() {
		return categoria;
	}

	/**
     * Define a categoria da despesa.
     *
     * @param categoria a categoria da despesa
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Define a categoria da despesa a partir de uma String.
     *
     * @param categoria a categoria da despesa no formato de String
     * @throws IllegalArgumentException se a String fornecida for inválida
     */
    public void setCategoria(String categoria) throws IllegalArgumentException {
        Categoria categoriaAux = converterStringParaCategoria(categoria);
        if (categoriaAux == null) {
            throw new IllegalArgumentException("Campo categoria inválido");
        }
        this.categoria = categoriaAux;
    }

    /**
     * Obtém o valor da despesa.
     *
     * @return o valor da despesa
     */
    public float getValor() {
        return valor;
    }

    /**
     * Define o valor da despesa.
     *
     * @param valor o valor da despesa
     */
    public void setValor(float valor) {
        this.valor = valor;
    }

    /**
     * Define o valor da despesa a partir de uma String.
     *
     * @param valor o valor da despesa no formato de String
     * @throws IllegalArgumentException se a String fornecida for inválida
     */
    public void setValor(String valor) throws IllegalArgumentException {
        if (!valor.matches(FLOAT_REGEX)) {
            throw new IllegalArgumentException("valor inválido");
        }
        this.valor = Float.valueOf(valor.replace(".", "").replace(",", "."));
    }

    /**
     * Obtém a situação da despesa.
     *
     * @return a situação da despesa
     */
    public Situacao getSituacao() {
        return situacao;
    }

    /**
     * Define a situação da despesa.
     *
     * @param situacao a situação da despesa
     */
    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    /**
     * Define a situação da despesa a partir de uma String.
     *
     * @param situacao a situação da despesa no formato de String
     */
    public void setSituacao(String situacao) {
        this.situacao = converterStringParaSituacao(situacao);
    }
    
    
    
    @Override
	public int hashCode() {
		return Objects.hash(categoria, dataDespesa, descricao, diaPagamento, formaPagamento, id, situacao, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Despesa other = (Despesa) obj;
		return categoria == other.categoria && Objects.equals(dataDespesa, other.dataDespesa)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(diaPagamento, other.diaPagamento)
				&& formaPagamento == other.formaPagamento && Objects.equals(id, other.id) && situacao == other.situacao
				&& Float.floatToIntBits(valor) == Float.floatToIntBits(other.valor);
	}

	/**
     * Retorna uma representação em String da despesa.
     *
     * @return uma representação em String da despesa
     */
    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("Data despesa: %s, dia pagamento: %s, forma de pagamento: %s, ");
        stringBuilder.append("descrição: %s, Categoria: %s, valor: %1.2f, situacao: %s");

        return String.format(stringBuilder.toString(), dataDespesa.format(DIA_MES_ANO_FORMATTER), diaPagamento.format(DIA_MES_FORMATTER),
                formaPagamento, descricao, categoria, valor, situacao.getDescricao());
    }
    
    /**
     * Método para validar uma despesa.
     * @return true se a despesa for válida, false caso contrário.
     */
    public boolean validarDespesa() {
    	if(dataDespesa == null || diaPagamento == null || formaPagamento == null
    		||descricao == null || categoria == null || valor == null || situacao == null) {
    		return false;
    	}
    	return true;
    }
}//class Despesa
