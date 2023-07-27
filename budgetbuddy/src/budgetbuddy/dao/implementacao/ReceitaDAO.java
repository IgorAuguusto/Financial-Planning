package budgetbuddy.dao.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import budgetbuddy.bd.DataBase;
import budgetbuddy.dao.DAO;
import budgetbuddy.entidades.Receita;

/**
 * Implementação da interface DAO para a entidade receita.
 */
public class ReceitaDAO implements DAO<Receita> {
	
	private DataBase dataBase;
	
	public ReceitaDAO(DataBase dataBase) {
		this.dataBase = dataBase;
	}
	
	/**
	 * Insere uma receita no banco de dados nas tabelas 'renda' e 'renda_mensal'.
	 *
	 * @param receita A receita a ser inserida.
	 * @return O ID da receita inserida.
	 * @throws SQLException Em caso de erro ao inserir a receita.
	 */
	@Override
	public int insert(Receita receita) throws SQLException {
	    Connection connection = null;
	    int codRenda = -1;
	    try {
	        connection = dataBase.getConnection();
	        connection.setAutoCommit(false);

	        codRenda = insertRenda(connection, receita.getTipo());

	        insertRendaMensal(connection, codRenda, receita.getData(), receita.getValor());

	        connection.commit();
	    } catch (SQLException e) {
	        if (connection != null) {
	            connection.rollback();
	        }
	        throw new SQLException("Erro ao inserir Receita na tabela", e);
	    } finally {
	        if (connection != null) {
	            connection.setAutoCommit(true);
	        }
	    }
	    return codRenda;
	}


	/**
	 * Insere uma renda na tabela 'renda' e retorna o código gerado.
	 * 
	 * @param connection A conexão com o banco de dados.
	 * @param tipo O tipo de renda.
	 * @return O código gerado para a renda.
	 * @throws SQLException Em caso de erro ao inserir a renda.
	 */
	private int insertRenda(Connection connection, String tipo) throws SQLException {
	    String comandoSql = "INSERT INTO renda (descricao) VALUES (?)";
	    PreparedStatement preparedStatement = connection.prepareStatement(comandoSql, Statement.RETURN_GENERATED_KEYS);
	    preparedStatement.setString(1, tipo);

	    int linhasAfetadas = preparedStatement.executeUpdate();

	    if (linhasAfetadas <= 0) {
	        throw new SQLException("Erro ao inserir Receita na tabela");
	    }

	    ResultSet resultSet = preparedStatement.getGeneratedKeys();
	    if (resultSet.next()) {
	        return resultSet.getInt(1);
	    } else {
	        throw new SQLException("Erro ao obter chave gerada para a tabela renda");
	    }
	}

	/**
	 * Insere uma renda mensal na tabela 'renda_mensal'.
	 * 
	 * @param connection A conexão com o banco de dados.
	 * @param codRenda O código da renda associada à renda mensal.
	 * @param data A data da renda mensal.
	 * @param valor O valor da renda mensal.
	 * @throws SQLException Em caso de erro ao inserir a renda mensal.
	 */
	private void insertRendaMensal(Connection connection, int codRenda, LocalDate data, float valor) throws SQLException {
	    String comandoSql = "INSERT INTO renda_mensal (cod_renda, data, valor) VALUES (?, ?, ?)";
	    PreparedStatement preparedStatement = connection.prepareStatement(comandoSql);
	    preparedStatement.setInt(1, codRenda);
	    preparedStatement.setDate(2, java.sql.Date.valueOf(data));
	    preparedStatement.setFloat(3, valor);

	    int linhasAfetadas = preparedStatement.executeUpdate();

	    if (linhasAfetadas <= 0) {
	        throw new SQLException("Erro ao inserir Receita na tabela renda_mensal");
	    }
	}
	
	/**
	 * Retorna uma lista de todas as receitas presentes no banco de dados.
	 * 
	 * @return Uma lista de receitas.
	 * @throws SQLException Em caso de erro ao buscar as receitas.
	 */
	@Override
	public List<Receita> findAll() throws SQLException {
		ResultSet resultSet = dataBase.select(String.format("%s %s %s", 
											   "SELECT * FROM renda r ",
				                               "JOIN renda_mensal rm ON r.codigo = rm.cod_renda ",
											   "ORDER BY rm.data"));

		List<Receita> receitaList = new ArrayList<>();
		
		while (resultSet.next()) {
			receitaList.add(new Receita(resultSet.getString("descricao"),
										resultSet.getDate("data").toLocalDate(),
										resultSet.getFloat("valor")));
		}
		return receitaList;
	}

	@Override
	public void update(Receita objeto) {
		// TODO Auto-generated method stub
		
	}

}//class ReceitaDAO

