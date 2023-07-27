package budgetbuddy.dao.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import budgetbuddy.bd.DataBase;
import budgetbuddy.dao.DAO;
import budgetbuddy.entidades.Categoria;
import budgetbuddy.entidades.Despesa;
import budgetbuddy.entidades.FormaPagamento;
import budgetbuddy.entidades.Situacao;
/**
 * Implementação da interface DAO para a entidade Despesa.
 */
public class DespesaDAO implements DAO<Despesa> {

	private DataBase dataBase;

	public DespesaDAO(DataBase dataBase) {
		this.dataBase = dataBase;
	}

	/**
	 * Insere uma despesa no banco de dados, juntamente com suas informações relacionadas, como forma de pagamento, categoria e orçamento.
	 * 
	 * @param despesa A despesa a ser inserida.
	 * @return O ID da despesa inserida.
	 * @throws SQLException Em caso de erro ao inserir a despesa.
	 */
	@Override
	public int insert(Despesa despesa) throws SQLException {
		int codDespesa = -1; // Valor padrão para caso ocorra algum erro

		try {
			Connection connection = dataBase.getConnection();
			connection.setAutoCommit(false);

			int codFormaPagamento = insertFormaPagamento(connection, despesa.getFormaPagamento().getFormaPagamento());
			int codCategoria = insertCategoria(connection, despesa.getStrCategoria());
			codDespesa = insertDespesa(connection, despesa.getDescricao(), codCategoria);
			insertOrcamento(connection, despesa, codFormaPagamento, codDespesa);
			despesa.setId(codDespesa);

			connection.commit();
		} catch (SQLException e) {
			dataBase.getConnection().rollback();
			throw new SQLException("Erro ao inserir Despesa na tabela", e);
		} finally {
			dataBase.getConnection().setAutoCommit(true);
		}

		return codDespesa;
	}

	/**
	 * Insere uma forma de pagamento no banco de dados e retorna o código gerado.
	 * 
	 * @param connection A conexão com o banco de dados.
	 * @param descricao  A descrição da forma de pagamento.
	 * @return O código gerado para a forma de pagamento.
	 * @throws SQLException Em caso de erro ao inserir a forma de pagamento.
	 */
	private int insertFormaPagamento(Connection connection, String descricao) throws SQLException {
		String comandoSql = "INSERT INTO forma_pagamento (descricao) VALUES (?)";

		PreparedStatement preparedStatement = connection.prepareStatement(comandoSql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, descricao);

		int codFormaPagamento;

		if (preparedStatement.executeUpdate() > 0) {
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				codFormaPagamento = resultSet.getInt(1);
			} else {
				throw new SQLException("Erro ao obter chave gerada para forma de pagamento");
			}
		} else {
			throw new SQLException("Erro ao inserir forma de pagamento na tabela");
		}

		return codFormaPagamento;
	}

	/**
	 * Insere uma categoria no banco de dados e retorna o código gerado.
	 * 
	 * @param connection A conexão com o banco de dados.
	 * @param descricao A descrição da categoria.
	 * @return O código gerado para a categoria.
	 * @throws SQLException Em caso de erro ao inserir a categoria.
	 */
	private int insertCategoria(Connection connection, String descricao) throws SQLException {
		String comandoSql = "INSERT INTO categoria (descricao) VALUES (?)";

		PreparedStatement preparedStatement = connection.prepareStatement(comandoSql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, descricao);

		int codCategoria;

		if (preparedStatement.executeUpdate() > 0) {
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {codCategoria = resultSet.getInt(1);
			} 
			else {
				throw new SQLException("Erro ao obter chave gerada para categoria");
			}
		} 
		else {
			throw new SQLException("Erro ao inserir categoria na tabela");
		}

		return codCategoria;
	}

	/**
	 * Insere uma despesa no banco de dados e retorna o código gerado.
	 * 
	 * @param connection A conexão com o banco de dados.
	 * @param descricao A descrição da despesa.
	 * @param codCategoria O código da categoria associada à despesa.
	 * @return O código gerado para a despesa.
	 * @throws SQLException Em caso de erro ao inserir a despesa.
	 */
	private int insertDespesa(Connection connection, String descricao, int codCategoria) throws SQLException {
		String comandoSql = "INSERT INTO despesa (descricao, cod_categoria) VALUES (?, ?)";

		PreparedStatement preparedStatement = connection.prepareStatement(comandoSql, Statement.RETURN_GENERATED_KEYS);

		preparedStatement.setString(1, descricao);
		preparedStatement.setInt(2, codCategoria);

		int codDespesa;

		if (preparedStatement.executeUpdate() > 0) {
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				codDespesa = resultSet.getInt(1);
			} else {
				throw new SQLException("Erro ao obter chave gerada para despesa");
			}
		} else {
			throw new SQLException("Erro ao inserir despesa na tabela");
		}

		return codDespesa;
	}

	/**
	 * Insere os dados da despesa no orcamento no banco de dados.
	 * 
	 * @param connection A conexão com o banco de dados.
	 * @param despesa A despesa a ser inserida no orcamento.
	 * @param codFormaPagamento O código da forma de pagamento associada à despesa.
	 * @param codDespesa O código da despesa.
	 * @throws SQLException Em caso de erro ao inserir a despesa no orcamento.
	 */
	private void insertOrcamento(Connection connection, Despesa despesa, int codFormaPagamento, int codDespesa) throws SQLException {
		String comandoSql = "INSERT INTO orcamento (mes_ano, cod_despesa, data_despesa, data_pagamento, cod_forma_pagamento, valor, situacao) VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement = connection.prepareStatement(comandoSql);

		preparedStatement.setDate(1, java.sql.Date.valueOf(despesa.getDataPagamento()));
		preparedStatement.setInt(2, codDespesa);
		preparedStatement.setDate(3, java.sql.Date.valueOf(despesa.getDataDespesa()));
		preparedStatement.setDate(4, java.sql.Date.valueOf(despesa.getDataPagamento()));
		preparedStatement.setInt(5, codFormaPagamento);
		preparedStatement.setFloat(6, despesa.getValor());
		preparedStatement.setBoolean(7, despesa.getSituacao().isStatus());

		if (preparedStatement.executeUpdate() <= 0) {
			throw new SQLException("Erro ao inserir despesa no orcamento");
		}
	}

	@Override
	public void update(Despesa despesa) throws SQLException {
		try {
			Connection connection = dataBase.getConnection();
			connection.setAutoCommit(false);

			int codFormaPagamento = insertFormaPagamento(connection, despesa.getFormaPagamento().getFormaPagamento());
			int codCategoria = insertCategoria(connection, despesa.getStrCategoria());
			updateDespesa(connection, despesa, codCategoria);
			updateOrcamento(connection, despesa, codFormaPagamento);

			connection.commit();
		} catch (SQLException e) {
			dataBase.getConnection().rollback();
			throw new SQLException("Erro ao atualizar Despesa na tabela", e);
		} finally {
			dataBase.getConnection().setAutoCommit(true);
		}
	}

	/**
	 * Atualiza os dados da despesa no banco de dados.
	 *
	 * @param connection   A conexão com o banco de dados.
	 * @param despesa      A despesa a ser atualizada.
	 * @param codCategoria O código da categoria associada à despesa.
	 * @throws SQLException Em caso de erro ao atualizar a despesa.
	 */
	private void updateDespesa(Connection connection, Despesa despesa, int codCategoria) throws SQLException {
		String comandoSql = "UPDATE despesa SET descricao = ?, cod_categoria = ? WHERE codigo = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(comandoSql);
		preparedStatement.setString(1, despesa.getDescricao());
		preparedStatement.setInt(2, codCategoria);
		preparedStatement.setInt(3, despesa.getId());

		if (preparedStatement.executeUpdate() <= 0) {
			throw new SQLException("Erro ao atualizar despesa no banco de dados");
		}
	}

	/**
	 * Atualiza os dados da despesa no orcamento no banco de dados.
	 *
	 * @param connection        A conexão com o banco de dados.
	 * @param despesa           A despesa a ser atualizada no orcamento.
	 * @param codFormaPagamento O código da forma de pagamento associada à despesa.
	 * @throws SQLException Em caso de erro ao atualizar a despesa no orcamento.
	 */
	private void updateOrcamento(Connection connection, Despesa despesa, int codFormaPagamento) throws SQLException {
		String comandoSql = "UPDATE orcamento SET mes_ano = ?, data_despesa = ?, data_pagamento = ?, cod_forma_pagamento = ?, valor = ?, situacao = ? WHERE cod_despesa = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(comandoSql);
		preparedStatement.setDate(1, java.sql.Date.valueOf(despesa.getDataPagamento()));
		preparedStatement.setDate(2, java.sql.Date.valueOf(despesa.getDataDespesa()));
		preparedStatement.setDate(3, java.sql.Date.valueOf(despesa.getDataPagamento()));
		preparedStatement.setInt(4, codFormaPagamento);
		preparedStatement.setFloat(5, despesa.getValor());
		preparedStatement.setBoolean(6, despesa.getSituacao().isStatus());
		preparedStatement.setInt(7, despesa.getId());

		if (preparedStatement.executeUpdate() <= 0) {
			throw new SQLException("Erro ao atualizar despesa no orcamento no banco de dados");
		}
	}

	/**
	 * Recupera todas as despesas do banco de dados.
	 *
	 * @return Uma lista de objetos Despesa representando as despesas.
	 * @throws SQLException se ocorrer um erro ao acessar o banco de dados.
	 */
	@Override
	public List<Despesa> findAll() throws SQLException {
		List<Despesa> despesaList = new ArrayList<>();

		try {

			StringBuilder comandoSql = new StringBuilder();
			comandoSql.append("SELECT * FROM orcamento o ");
			comandoSql.append("INNER JOIN forma_pagamento fp ON fp.codigo = o.cod_forma_pagamento ");
			comandoSql.append("INNER JOIN despesa d ON d.codigo = o.cod_despesa ");
			comandoSql.append("INNER JOIN categoria c ON d.cod_categoria = c.codigo ");
			comandoSql.append("ORDER BY data_pagamento");

			ResultSet resultSet = dataBase.select(comandoSql.toString());

			while (resultSet.next()) {
				Despesa despesa = new Despesa(
						resultSet.getDate(3).toLocalDate(),
						resultSet.getDate(1).toLocalDate(),
						FormaPagamento.converterStringParaFormaPagamento(resultSet.getString(9)),
						resultSet.getString(11),
						Categoria.converterStringParaCategoria(resultSet.getString(14)),
						resultSet.getFloat(6),
						resultSet.getBoolean(7) ? Situacao.PAGA : Situacao.A_PAGAR
						);
				despesa.setId(resultSet.getInt(2));
				despesaList.add(despesa);
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao buscar despesas no banco de dados", e);
		}

		return despesaList;
	}



}//class DespesaDao
