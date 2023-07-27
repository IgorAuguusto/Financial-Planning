package budgetbuddy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import budgetbuddy.entidades.Despesa;

/**
 * Classe responsável pela interface gráfica de pesquisa de despesas.
 */
public class IgPesquisarDespesa extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JTextField itemDespesaTextField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton dataJRadioButton;
	private JRadioButton descriacaoJRadioButton;
	private JRadioButton valorJRadioButton;
	private Component janelaPai;
	private JTable tabela;
	List<Despesa> despesaList;
	private int indice;

	/**
	 * Cria uma nova janela de pesquisa de despesa.
	 *
	 * @param janelaPai    A janela pai à qual esta janela está vinculada.
	 * @param tabela       A tabela de despesas onde a pesquisa será realizada.
	 * @param despesaList  A lista de despesas a serem pesquisadas.
	 */
	public IgPesquisarDespesa(Component janelaPai, JTable tabela, List<Despesa> despesaList) {
		this.janelaPai = janelaPai;
		this.tabela = tabela;
		this.despesaList = despesaList;

		setTitle("Pesquisar Despesa");
		setIconImage(Toolkit.getDefaultToolkit().getImage(IgPesquisarDespesa.class.getResource("/budgetbuddy/gui/icon/cifrao .png")));

		final Font ESTILO_FONTES = new Font("SansSerif", Font.PLAIN, 12);

		setBounds(100, 100, 416, 146);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel itemDeDespesaLabel = new JLabel("Item de despesa: ");
		itemDeDespesaLabel.setDisplayedMnemonic(KeyEvent.VK_I);
		itemDeDespesaLabel.setFont(ESTILO_FONTES);
		itemDeDespesaLabel.setBounds(10, 11, 109, 24);
		contentPanel.add(itemDeDespesaLabel);

		itemDespesaTextField = new JTextField();
		itemDeDespesaLabel.setLabelFor(itemDespesaTextField);
		itemDespesaTextField.setBounds(110, 11, 176, 24);
		contentPanel.add(itemDespesaTextField);
		itemDespesaTextField.setColumns(10);

		JLabel procurarDespesaLabel = new JLabel("Procurar por: ");
		procurarDespesaLabel.setFont(ESTILO_FONTES);
		procurarDespesaLabel.setBounds(10, 45, 74, 14);
		contentPanel.add(procurarDespesaLabel);

		dataJRadioButton = new JRadioButton("Data");
		buttonGroup.add(dataJRadioButton);
		dataJRadioButton.setMnemonic(KeyEvent.VK_D);
		dataJRadioButton.setFont(ESTILO_FONTES);
		dataJRadioButton.setBackground(Color.WHITE);
		dataJRadioButton.setBounds(88, 42, 54, 23);
		contentPanel.add(dataJRadioButton);

		descriacaoJRadioButton = new JRadioButton("Descrição");
		buttonGroup.add(descriacaoJRadioButton);
		descriacaoJRadioButton.setMnemonic(KeyEvent.VK_E);
		descriacaoJRadioButton.setFont(ESTILO_FONTES);
		descriacaoJRadioButton.setBackground(Color.WHITE);
		descriacaoJRadioButton.setBounds(142, 42, 81, 23);
		contentPanel.add(descriacaoJRadioButton);

		valorJRadioButton = new JRadioButton("Valor");
		buttonGroup.add(valorJRadioButton);
		valorJRadioButton.setMnemonic(KeyEvent.VK_V);
		valorJRadioButton.setFont(ESTILO_FONTES);
		valorJRadioButton.setBackground(Color.WHITE);
		valorJRadioButton.setBounds(225, 42, 54, 23);
		contentPanel.add(valorJRadioButton);

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton proximaDespesaButton = new JButton("Próxima despesa");

		proximaDespesaButton.setBackground(Color.WHITE);
		proximaDespesaButton.setMnemonic(KeyEvent.VK_P);
		proximaDespesaButton.setActionCommand("");
		buttonPane.add(proximaDespesaButton);
		getRootPane().setDefaultButton(proximaDespesaButton);

		JButton cancelButton = new JButton("Fechar");

		descriacaoJRadioButton.setSelected(true);

		cancelButton.addActionListener((e) -> dispose());

		indice = 0;

		itemDespesaTextField.addActionListener((e) -> pesquisarDespesas(true));

		proximaDespesaButton.addActionListener((e) -> pesquisarDespesas(false));

		cancelButton.setBackground(Color.WHITE);
		cancelButton.setMnemonic(KeyEvent.VK_F);
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		setModal(true);
		setResizable(false);
		setLocationRelativeTo(janelaPai);
		setVisible(true);
	}

	/**
	 * Realiza a pesquisa de despesas com base nos critérios fornecidos.
	 *
	 * @param primeiraVez Indica se é a primeira vez que a pesquisa está sendo realizada.
	 */
	private void pesquisarDespesas(boolean primeiraVez) {
		String itemASerPesquisado = itemDespesaTextField.getText();
		if (primeiraVez) {
			indice = 0;
		}

		int numeroDeLinhas = tabela.getRowCount();

		if (dataJRadioButton.isSelected()) {
			for (int i = indice; i < numeroDeLinhas; i++) {
				Object objeto = tabela.getValueAt(i, 0);
				if (objeto != null) {
					if (objeto.toString().equalsIgnoreCase(itemASerPesquisado)) {
						janelaPai.requestFocus();
						tabela.changeSelection(i, 3, false, false);
						tabela.requestFocusInWindow();
						indice = i + 1;
						return;
					}
				}
			}
		} else if (descriacaoJRadioButton.isSelected()) {

			for (int i = indice; i < numeroDeLinhas; i++) {
				Object objeto = tabela.getValueAt(i, 3);
				if (objeto != null) {
					if (objeto.toString().equalsIgnoreCase(itemASerPesquisado)) {
						janelaPai.requestFocus();
						tabela.changeSelection(i, 3, false, false);
						tabela.requestFocusInWindow();
						indice = i +1;
						return;
					}
				}

			}
		} else if (valorJRadioButton.isSelected()) {
			for (int i = indice; i < numeroDeLinhas; i++) {
				Object objeto = tabela.getValueAt(i, 4);
				if (objeto != null) {
					if (objeto.toString().replace("R$: ", "").equalsIgnoreCase(itemASerPesquisado)) {
						janelaPai.requestFocus();
						tabela.changeSelection(i, 3, false, false);
						tabela.requestFocusInWindow();
						indice = i + 1;
						return;
					}
				}
			}
		}
		if (indice == 0) {
			JOptionPane.showMessageDialog(this, "Nenhuma Despesa foi encontrada", "Pesquesa Despesa", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
