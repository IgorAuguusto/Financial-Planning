package budgetbuddy.entidades;

/**
 * Enumeração que representa a situação de pagamento de uma despesa.
 * @author Igor Augusto Alves Silva 
 */
public enum Situacao {

    /**
     * Despesa paga.
     */
    PAGA("Paga", true),

    /**
     * Despesa a pagar.
     */
    A_PAGAR("A pagar", false);

    private String descricao;
    private boolean status;

    /**
     * Construtor privado para a enumeração Situacao.
     *
     * @param descricao A descrição da situação.
     * @param status O status da situação (paga ou a pagar).
     */
    private Situacao(String descricao, boolean status) {
        this.descricao = descricao;
        this.status = status;
    }

    /**
     * Obtém a descrição da situação.
     *
     * @return A descrição da situação.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Verifica se a situação é de pagamento efetuado (paga) ou não.
     *
     * @return {@code true} se a situação for paga, {@code false} caso contrário.
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Converte uma string em uma instância do enum Situacao.
     *
     * @param {@code String} A string representando a situação.
     * @return Uma instância do enum {@code Situacao} correspondente à string fornecida.
     *         Se a string não corresponder a nenhuma situação conhecida, retorna a situação "A pagar" por padrão.
     */
    public static Situacao converterStringParaSituacao(String string) {
        if (PAGA.getDescricao().equalsIgnoreCase(string)) {
            return PAGA;
        }
        return A_PAGAR;
    }
}//enum Situacao
