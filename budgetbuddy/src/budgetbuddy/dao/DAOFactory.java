package budgetbuddy.dao;

import budgetbuddy.bd.DataBase;
import budgetbuddy.dao.implementacao.DespesaDAO;
import budgetbuddy.dao.implementacao.InvestimentoDAO;
import budgetbuddy.dao.implementacao.ReceitaDAO;
import budgetbuddy.entidades.Despesa;
import budgetbuddy.entidades.Investimento;
import budgetbuddy.entidades.Receita;

/**
 * Uma fábrica para criar instâncias de DAO para as entidades Receita, Despesas e Investimento.
 */
public interface DAOFactory {

    /**
     * Cria uma instância de DAO para a entidade Receita.
     *
     * @param dataBase A instância do banco de dados a ser utilizada pelo DAO.
     * @return Uma instância de DAO para a entidade Receita.
     */
    public static DAO<Receita> createReceitaDao(DataBase dataBase) {
        return new ReceitaDAO(dataBase);
    }

    /**
     * Cria uma instância de DAO para a entidade Investimento.
     *
     * @param dataBase A instância do banco de dados a ser utilizada pelo DAO.
     * @return Uma instância de DAO para a entidade Investimento.
     */
    public static DAO<Investimento> createInvestimentoDao(DataBase dataBase) {
        return new InvestimentoDAO(dataBase);
    }
    
    /**
     * Cria uma instância de DAO para a entidade Despesa.
     *
     * @param dataBase A instância do banco de dados a ser utilizada pelo DAO.
     * @return Uma instância de DAO para a entidade Despesa.
     */
    public static DAO<Despesa> createDespesaDao(DataBase dataBase) {
        return new DespesaDAO(dataBase);
    }
}
