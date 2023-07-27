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
import budgetbuddy.entidades.Investimento;

/**
 * Implementação da interface DAO para a entidade Investimento.
 */
public class InvestimentoDAO implements DAO<Investimento> {

	private DataBase dataBase;

	/**
	 * Construtor da classe InvestimentoDAO.
	 *
	 * @param dataBase O objeto DataBase para realizar operações no banco de dados.
	 */
	public InvestimentoDAO(DataBase dataBase) {
		this.dataBase = dataBase;
	}

	/**
	 * Realiza a inserção de um objeto Investimento na tabela investimento do banco de dados.
	 *
	 * @param investimento O elemento Investimento a ser inserido.
	 * @return O ID do investimento inserido.
	 * @throws SQLException Em caso de erro ao inserir o investimento.
	 */
	@Override
	public int insert(Investimento investimento) throws SQLException {
		int idInvestimento = -1; // Valor padrão para caso ocorra algum erro

		try {
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = dataBase.getPreparedStatement();

			String comandoSql = "INSERT INTO investimento (objetivo, estrategia, nome, valor_investido, posicao, rendimento_bruto, rentabilidade, vencimento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(comandoSql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, investimento.getObjetivo());
			preparedStatement.setString(2, investimento.getEstrategia());
			preparedStatement.setString(3, investimento.getNome());
			preparedStatement.setFloat(4, investimento.getValorInvestido());
			preparedStatement.setFloat(5, investimento.getPosicao());
			preparedStatement.setFloat(6, investimento.getRendimentoBruto());
			preparedStatement.setFloat(7, investimento.getRentabilidade());
			preparedStatement.setDate(8, java.sql.Date.valueOf(investimento.getVencimento()));

			int linhasAfetadas = preparedStatement.executeUpdate();

			if (linhasAfetadas == 0) {
				throw new SQLException("Erro inesperado, nenhuma linha foi afetada.");
			}

			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				idInvestimento = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Erro ao obter o ID do investimento inserido.");
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao inserir Investimento na tabela investimento.", e);
		}

		return idInvestimento;
	}

	/**
	 * Realiza a atualização de um objeto Investimento na tabela investimento do banco de dados.
	 *
	 * @param investimento O elemento Investimento a ser atualizado.
	 * @throws SQLException Em caso de erro ao atualizar o investimento.
	 */
	@Override
	public void update(Investimento investimento) throws SQLException {
		try {
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = dataBase.getPreparedStatement();

			String comandoSql = String.format("%s%s", "UPDATE investimento SET objetivo = ?, estrategia = ?, nome = ?, ",
					"valor_investido = ?, posicao = ?, rendimento_bruto = ?, rentabilidade = ?, vencimento = ? WHERE codigo = ?");

			preparedStatement = connection.prepareStatement(comandoSql);

			preparedStatement.setString(1, investimento.getObjetivo());
			preparedStatement.setString(2, investimento.getEstrategia());
			preparedStatement.setString(3, investimento.getNome());
			preparedStatement.setFloat(4, investimento.getValorInvestido());
			preparedStatement.setFloat(5, investimento.getPosicao());
			preparedStatement.setFloat(6, investimento.getRendimentoBruto());
			preparedStatement.setFloat(7, investimento.getRentabilidade());
			preparedStatement.setDate(8, java.sql.Date.valueOf(investimento.getVencimento()));
			preparedStatement.setInt(9, investimento.getId());

			int linhasAfetadas = preparedStatement.executeUpdate();

			if (linhasAfetadas == 0) {
				throw new SQLException("Erro inesperado, nenhuma linha foi afetada.");
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao atualizar Investimento na tabela investimento.", e);
		}
	}

	/**
	 * Recupera todos os investimentos da tabela investimento do banco de dados.
	 *
	 * @return Uma lista de objetos Investimento representando os investimentos.
	 * @throws SQLException Em caso de erro ao recuperar os investimentos.
	 */
	@Override
	public List<Investimento> findAll() throws SQLException {
		ResultSet resultSet = dataBase.select("SELECT * FROM investimento ");

		List<Investimento> investimentoList = new ArrayList<>();

		while (resultSet.next()) {
			Investimento investimento = new Investimento(resultSet.getString("objetivo"),
					resultSet.getString("estrategia"),
					resultSet.getString("nome"),
					resultSet.getFloat("valor_investido"),
					resultSet.getFloat("posicao"),
					resultSet.getFloat("rendimento_bruto"),
					resultSet.getFloat("rentabilidade"),
					resultSet.getDate("vencimento").toLocalDate());
			investimento.setId(resultSet.getInt("codigo"));
			investimentoList.add(investimento);
		}
		return investimentoList;
	}

}