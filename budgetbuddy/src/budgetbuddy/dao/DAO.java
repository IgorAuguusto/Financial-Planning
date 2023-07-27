package budgetbuddy.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface que define os métodos de manipulação ao banco de dados.
 * 
 * @author Igor Augusto Alves Silva
 *
 * @param <E> Objeto que será usado nas operçãos.
 */
public interface DAO<E> {
	
	/**
	 * Faz uma inserção na tabela do banco de dados.
	 * 
	 * @param {@code <E>} elemento a ser inserido.
	 * @return o id do elemento a ser  Inserido.
	 */
	public int insert(E Objeto) throws SQLException;
	
	/**
	 * Faz uma atualização na tabela do banco de dados.
	 * @param objeto a ser atualizado.
	 * @throws SQLException
	 */
	void update(E objeto) throws SQLException;
	
	/**
	 * Realiza uma consulta que recupera todos os registros de uma coleção de dados.
	 * Essa operação retorna todos os registros disponíveis sem aplicar qualquer
	 * filtro ou restrição específica.
	 * 
	 * @return {@code List<E>} contendo os registros encontrados.
	 * @throws SQLException 
	 */
	public List<E> findAll() throws SQLException;

}//interface Dao<E>
