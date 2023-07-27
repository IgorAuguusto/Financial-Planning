package budgetbuddy.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Esta classe fornece métodos para permitir as operações CRUD no banco de dados.
 * O acrônimo CRUD corresponde as seguintes instruções SQL:
 * 
 * Create -> SQL INSERT
 * Retrieve -> SQL SELECT
 * Update -> SQL UPDATE
 * Delete -> SQL DELETE
 * 
 */
public class DataBase implements AutoCloseable{
	
	private String url, user, password;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	
	
	public DataBase() throws SQLException {
		connection = DriverManager.getConnection("jdbc:postgresql:financialplanning", "dba", "fpdb@");
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	}//construtor
	
	/**
	 * Obtém uma conexão com o banco de dados.
	 * 
	 * @param url url do banco de dados exemplos: jdbc:postgresql:coursejdbc ou jdbc:postgresql://LocalHost::5432/coursejdbc
	 * @param user 
	 * @param password
	 * @throws SQLException
	 */
	public DataBase(String url, String user, String password) throws SQLException {
		this.url = url;
		this.user = user;
		this.password = password;
		
		connection = DriverManager.getConnection(url, user, password);
		
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	}//construtor
	
	/**
	 * Obtém a URL do banco de dados.
	 *
	 * @return a URL do banco de dados
	 */
	public String getUrl() {
	    return url;
	}

	/**
	 * Obtém o nome de usuário utilizado para acessar o banco de dados.
	 *
	 * @return o nome de usuário do banco de dados
	 */
	public String getUser() {
	    return user;
	}

	/**
	 * Obtém a senha utilizada para acessar o banco de dados.
	 *
	 * @return a senha do banco de dados
	 */
	public String getPassword() {
	    return password;
	}

	/**
	 * Obtém a conexão atual com o banco de dados.
	 *
	 * @return a conexão com o banco de dados
	 */
	public Connection getConnection() {
	    return connection;
	}

	/**
	 * Obtém o objeto de declaração (statement) atualmente utilizado para enviar comandos SQL para o banco de dados.
	 *
	 * @return o objeto de declaração atual
	 */
	public Statement getStatement() {
	    return statement;
	}

	/**
	 * Obtém o objeto PreparedStatement atualmente utilizado para enviar comandos SQL parametrizados para o banco de dados.
	 *
	 * @return o objeto PreparedStatement atual
	 */
	public PreparedStatement getPreparedStatement() {
	    return preparedStatement;
	}

	/**
	 * Define o objeto PreparedStatement a ser utilizado para enviar comandos SQL parametrizados para o banco de dados.
	 *
	 * @param preparedStatement o objeto PreparedStatement a ser definido
	 */
	public void setPreparedStatement(PreparedStatement preparedStatement) {
	    this.preparedStatement = preparedStatement;
	}

	/**
	 * Obtém o conjunto de resultados (ResultSet) atualmente retornado por uma consulta ao banco de dados.
	 *
	 * @return o conjunto de resultados atual
	 */
	public ResultSet getResultSet() {
	    return resultSet;
	}

	/**
	 * Executa uma consulta SQL do tipo SELECT retornando o resultado da consulta.
	 * 
	 * @param instructionSQL instrução que será executada no banco de dados.
	 * @return {@code ResultSet} contendo o resultado da consulata.
	 * @throws SQLException se ocorrer um erro de acesso ao banco de dados. 
	 */
	public ResultSet select(String instructionSQL) throws SQLException {
		try {
			resultSet = statement.executeQuery(instructionSQL);
			return resultSet;
			
		}
		catch (SQLException e) {
			throw new SQLException("Error ao executar consulta SQL SELECT");
		}
	}//select()
	
	/**
	 * Executa uma consulta SQL do tipo INSERT, UPDATE ou DELETE sobre o banco de dados.
	 * 
	 * @param instructionSQL instrução que será executada no banco de dados.
	 * @return O número de linhas resultande da consulta SQL ou zero para instrução que não retorna nenhum resultado.
	 * @throws SQLException se ocorrer um erro de acesso ao banco de dados.
	 */
	public int query(String instructionSQL) throws SQLException {
		try{
			return statement.executeUpdate(instructionSQL);
		}
		catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}//query()
	
	/**
	 * Executa uma consulta SQL do tipo INSERT retornando a cahve gerada da inserção
	 * 
	 * @param instructionSQL instrução que será executada no banco de dados.
	 * @return {@code ResultSet} com a chave Gerada.
	 * @throws SQLException se ocorrer um erro de acesso ao banco de dados.
	 */
	public ResultSet insert(String instructionSQL) throws SQLException {
		try{
			statement.executeUpdate(instructionSQL, Statement.RETURN_GENERATED_KEYS);
			return statement.getGeneratedKeys();
		}
		catch (SQLException e) {
			throw new SQLException("Error ao executar uma consulta SQL INSERT, UPDATE ou DELETE");
		}
	}//insert()
	
	/**
	 * Fecha os recursos caso eles estejam aberto, dos objetos de acesso ao banco de dados.
	 */
	@Override
	public void close() throws Exception {
		if (statement != null)statement.close();
		if (preparedStatement != null) preparedStatement.close();
		if (connection!= null)connection.close();
		if (resultSet != null)resultSet.close();
	}

}//class DataBase
