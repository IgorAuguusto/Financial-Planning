package budgetbuddy;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import budgetbuddy.bd.DataBase;
import budgetbuddy.dao.DAO;
import budgetbuddy.dao.DAOFactory;
import budgetbuddy.entidades.Despesa;
import budgetbuddy.entidades.Investimento;
import budgetbuddy.entidades.Receita;
import budgetbuddy.gui.IgJanelaPrincipal;
/**
 * Classe résponsavel por executar o programa principal.
 * @author Igor Augusto Alves Silva
 *
 */
public class BudgetBuddy {

	public static void main(String[] args) {
		budgetBuddy();
	}
	
	public static void budgetBuddy() {
		try {
			DataBase dataBase = new DataBase();
			DAO<Receita> receitaDao = DAOFactory.createReceitaDao(dataBase);
			DAO<Despesa> despesaDao = DAOFactory.createDespesaDao(dataBase);
			DAO<Investimento> investimentoDao = DAOFactory.createInvestimentoDao(dataBase);
			
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			new IgJanelaPrincipal(dataBase, receitaDao, despesaDao, investimentoDao);
			
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados", "Error", JOptionPane.ERROR_MESSAGE);
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro inesperado", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

	


