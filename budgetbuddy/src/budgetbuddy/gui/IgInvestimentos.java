package budgetbuddy.gui;

import static budgetbuddy.utilitarios.Formatacoes.DECIMAL_FORMAT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import budgetbuddy.dao.DAO;
import budgetbuddy.entidades.Investimento;

/**
 * Classe que representa a janela de investimentos no Budget Buddy.
 */
public class IgInvestimentos extends JDialog {
	private static final long serialVersionUID = 1L;

	private JLabel valorInvestimentoLabel;
	private JLabel valorAcumuladoLabel;
	private JLabel valorRendimentoBrutoLabel;
	private JTable investimentoTable;

	private DAO<Investimento> investimentoDao;
	private List<Investimento> investimentoList;
	private JPanel tabelaPanel;
	private JButton graficoBarrasButton;
	private JButton graficoColunasButton;

	/**
     * Cria uma nova instância da classe IgInvestimentos.
     * 
     * @param janelaPai        A janela pai à qual essa janela de investimentos está vinculada.
     * @param investimentoList A lista de investimentos a ser exibida na tabela.
     * @param investimentoDAO  O objeto DAO para acessar os dados de investimento.
     */
	public IgInvestimentos(Component janelaPai ,List<Investimento> investimentoList, DAO<Investimento> investimentoDAO) {

		this.investimentoDao= investimentoDAO;
		this.investimentoList = investimentoList;

		setIconImage(Toolkit.getDefaultToolkit().getImage(IgInvestimentos.class.getResource("/budgetbuddy/gui/icon/cifrao .png")));
		setTitle("Budget Buddy - Investimentos");
		setBounds(100, 100, 1207, 521);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1191, 482);
		panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(panel);
		panel.setLayout(null);

		JPanel centralPanel = new JPanel();
		centralPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Investimentos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		centralPanel.setBackground(new Color(255, 255, 255));
		centralPanel.setBounds(10, 83, 1171, 358);
		panel.add(centralPanel);
		centralPanel.setLayout(null);

		tabelaPanel = new JPanel();
		tabelaPanel.setBackground(new Color(255, 255, 255));
		tabelaPanel.setBounds(10, 25, 1151, 322);
		centralPanel.add(tabelaPanel);
		tabelaPanel.setLayout(new BorderLayout(0, 0));

		investimentoTable = criarTabela();
		tabelaPanel.add(criarTabelaInvestimento());

		JPanel superiorPanel = new JPanel();
		superiorPanel.setBackground(new Color(255, 255, 255));
		superiorPanel.setBounds(0, 0, 1191, 95);
		panel.add(superiorPanel);
		superiorPanel.setLayout(null);

		JPanel totalInvestimentoLabel = new JPanel();
		totalInvestimentoLabel.setBackground(new Color(255, 255, 255));
		totalInvestimentoLabel.setBounds(19, 6, 220, 82);
		superiorPanel.add(totalInvestimentoLabel);

		JLabel totalInvestidoLabel = new JLabel("Total Investimento");
		totalInvestimentoLabel.add(totalInvestidoLabel);
		totalInvestidoLabel.setForeground(new Color(0, 0, 255));
		totalInvestidoLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

		valorInvestimentoLabel = new JLabel("R$: 00");
		totalInvestimentoLabel.add(valorInvestimentoLabel);
		valorInvestimentoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valorInvestimentoLabel.setForeground(new Color(0, 0, 0));
		valorInvestimentoLabel.setFont(new Font("SansSerif", Font.BOLD, 26));

		JPanel rendimentoBrutoLabel = new JPanel();
		rendimentoBrutoLabel.setBackground(new Color(255, 255, 255));
		rendimentoBrutoLabel.setBounds(461, 6, 220, 82);
		superiorPanel.add(rendimentoBrutoLabel);

		JLabel rendimentoBruto = new JLabel("Rendimento Bruto");
		rendimentoBrutoLabel.add(rendimentoBruto);
		rendimentoBruto.setForeground(Color.BLUE);
		rendimentoBruto.setFont(new Font("SansSerif", Font.BOLD, 20));

		valorRendimentoBrutoLabel = new JLabel("R$: 00");
		rendimentoBrutoLabel.add(valorRendimentoBrutoLabel);
		valorRendimentoBrutoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valorRendimentoBrutoLabel.setForeground(Color.BLACK);
		valorRendimentoBrutoLabel.setFont(new Font("SansSerif", Font.BOLD, 26));

		JPanel totalAcumuladoPanel = new JPanel();
		totalAcumuladoPanel.setBackground(new Color(255, 255, 255));
		totalAcumuladoPanel.setBounds(251, 6, 220, 82);
		superiorPanel.add(totalAcumuladoPanel);

		JLabel totalAcumuladoLabel = new JLabel("Total Acumulado");
		totalAcumuladoPanel.add(totalAcumuladoLabel);
		totalAcumuladoLabel.setForeground(Color.BLUE);
		totalAcumuladoLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

		valorAcumuladoLabel = new JLabel("R$: 00");
		totalAcumuladoPanel.add(valorAcumuladoLabel);
		valorAcumuladoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valorAcumuladoLabel.setForeground(Color.BLACK);
		valorAcumuladoLabel.setFont(new Font("SansSerif", Font.BOLD, 26));

		JPanel inferiorPanel = new JPanel();
		inferiorPanel.setLayout(null);
		inferiorPanel.setBackground(Color.WHITE);
		inferiorPanel.setBounds(0, 435, 1191, 41);
		panel.add(inferiorPanel);

		graficoBarrasButton = new JButton("Gráfico em Barras...");
		graficoBarrasButton.setMnemonic(KeyEvent.VK_B);
		graficoBarrasButton.setBackground(Color.WHITE);
		graficoBarrasButton.setBounds(823, 13, 137, 28);
		inferiorPanel.add(graficoBarrasButton);

		JButton fecharButton = new JButton("Fechar");
		fecharButton.setMnemonic(KeyEvent.VK_F);
		fecharButton.setBackground(Color.WHITE);
		fecharButton.setActionCommand("Cancel");
		fecharButton.setBounds(1115, 13, 66, 28);
		inferiorPanel.add(fecharButton);

		graficoColunasButton = new JButton("Gráfico em Colunas...");
		
		graficoColunasButton.setMnemonic(KeyEvent.VK_C);
		graficoColunasButton.setBackground(Color.WHITE);
		graficoColunasButton.setBounds(965, 13, 145, 28);
		inferiorPanel.add(graficoColunasButton);
		
		// Desabilitar botões se a lista de investimentos estiver vazia
	    if (investimentoList.isEmpty()) {
	        graficoBarrasButton.setEnabled(false);
	        graficoColunasButton.setEnabled(false);
	    }

		atualizarComponentes();

		DefaultTableModel model = (DefaultTableModel) investimentoTable.getModel();

		/**
		 * Trata os eventos relacionados a tabela e banco de dados, inserção e alteração. 
		 */
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent evento) {
				if (evento.getType() == TableModelEvent.UPDATE) {
					// Obter a linha selecionada
					int linhaSelecionada = investimentoTable.getSelectedRow();
					int colunaAlterada = investimentoTable.getSelectedColumn();

					if(linhaSelecionada == investimentoTable.getRowCount() - 1) {
						try {
							Investimento investimento = getInvestimentoDaTabela(linhaSelecionada);
							if (investimento.validarInvestimento()) {
								try {
									int codDespesa = investimentoDao.insert(investimento);
									investimento.setId(codDespesa);
									investimentoList.add(investimento);
								} catch (SQLException exception) {
									JOptionPane.showMessageDialog(IgInvestimentos.this, "Erro ao Inserir investimento no banco de dados",
											"Erro", JOptionPane.ERROR_MESSAGE);
								}
								atualizarComponentes();
							}
						} catch (IllegalArgumentException exception) {
							JOptionPane.showMessageDialog(IgInvestimentos.this, exception.getMessage(), "Valor Inválido",
									JOptionPane.ERROR_MESSAGE);
							investimentoTable.setValueAt(null, linhaSelecionada, colunaAlterada);
						}
					}
					else {
						// Recuperar a Investimento da linha selecionada
						try {
							Investimento investimento = getInvestimentoDaTabela(linhaSelecionada);
							try {
								investimentoDao.update(investimento);
								for (int i = 0; i < investimentoList.size(); i++) {
									Investimento inv = investimentoList.get(i);
									if (inv.getId().equals(investimento.getId())) {
										investimentoList.set(i, investimento);
										break;
									}
								}
							} catch (SQLException exception) {
								JOptionPane.showMessageDialog(IgInvestimentos.this, "Erro ao atualizar no banco de dados",
										"Erro", JOptionPane.ERROR_MESSAGE);
							}
						} catch (IllegalArgumentException exception) {
							JOptionPane.showMessageDialog(IgInvestimentos.this, exception.getMessage(), "Valor Inválido",
									JOptionPane.ERROR_MESSAGE);
						}
						atualizarComponentes();
					}
					investimentoTable.requestFocus();
				}
			}		
		});
		
		//Abre a janela para exibir o gráfico em barras.
		graficoBarrasButton.addActionListener((e) -> new IgGraficosInvestimento(IgInvestimentos.this,  investimentoList,  true));
		
		//Abre a janela para exibir o gráfico em colunas.
		graficoColunasButton.addActionListener((e) -> new IgGraficosInvestimento(IgInvestimentos.this,  investimentoList,  false));
		
		// fecha a janela quando o botão fechar for presionado
		fecharButton.addActionListener((e) -> dispose());
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setLocationRelativeTo(janelaPai);  
		setVisible(true);

	}
	
	/**
	 * Atualiza os valores da tabela e das labels.
	 */
	private void atualizarComponentes() {
		// habilita botões se a lista de investimentos estiver vazia
	    if (!investimentoList.isEmpty()) {
	        graficoBarrasButton.setEnabled(true);
	        graficoColunasButton.setEnabled(true);
	    }
		// Atualizar o modelo da tabela com os novos dados
		atualizarDadosTabela();

		// Remover o JScrollPane antigo da tabelaPanel
		tabelaPanel.remove(0);

		// Adicionar o novo JScrollPane à tabelaPanel
		tabelaPanel.add(atualizarDadosTabela());

		// Atualizar a interface
		tabelaPanel.revalidate();
		tabelaPanel.repaint();

		atualizarValoresLabels();
	}

	/**
	 * Atualiza os valores das labels exibidos na interface gráfica com as informações do banco de dados.
	 *
	 */
	private void atualizarValoresLabels() {
		float totalInvestido = totalInvestido();
		float totalAcumulado = totalAcumulado();
		float rendimentoBruto = rendimentoBruto();

		valorInvestimentoLabel.setText(String.format("%s%s","R$: " , DECIMAL_FORMAT.format(totalInvestido)));
		valorAcumuladoLabel.setText(String.format("%s%s","R$: " ,DECIMAL_FORMAT.format(totalAcumulado)));
		valorRendimentoBrutoLabel.setText(String.format("%s%s","R$: " ,DECIMAL_FORMAT.format(rendimentoBruto)));
	}

	/**
	 * Calcula o total investido somando os valores de investimento de todos os elementos da lista de investimentos.
	 *
	 * @return O valor total investido.
	 */
	private float totalInvestido() {
		return investimentoList.stream().map((i) -> i.getValorInvestido()).reduce(0f, (i, i2) -> i + i2);
	}

	/**
	 * Calcula o total acumulado somando as posições de todos os elementos da lista de investimentos.
	 *
	 * @return O valor total acumulado.
	 */
	private float totalAcumulado() {
		return investimentoList.stream().map((i) -> i.getPosicao()).reduce(0f, (i, i2) -> i + i2);
	}

	/**
	 * Calcula o rendimento bruto somando os rendimentos brutos de todos os elementos da lista de investimentos.
	 *
	 * @return O valor total do rendimento bruto.
	 */
	private float rendimentoBruto() {
		return investimentoList.stream().map((i) -> i.getRendimentoBruto()).reduce(0f, (i, i2) -> i + i2);
	}

	/**
	 * Cria uma nova tabela de investimento.
	 *
	 * @return A JTable configurada para exibir as despesas.
	 */
	private JTable criarTabela() {
		// Criar os nomes das colunas
		String[] colunaNomes = {"Objetivo", "Estrátegia", "Nome", "Valor Investido", "Posição", "Redimento Bruto", "Rentabilidade", "Vencimento"};

		// Criar o modelo da tabela
		DefaultTableModel model = new DefaultTableModel(colunaNomes, 0) {
			private static final long serialVersionUID = 1L;

		};

		// Criar a tabela com o modelo criado
		JTable tabela = new JTable(model) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				// Definir as colunas "Rendimento Bruto" e "Rentabilidade" como não editáveis
				return !(column == 5 || column == 6);
			}
		};

		// Impedir reordenação das colunas
		tabela.getTableHeader().setReorderingAllowed(false);

		// Configurar o estilo da tabela
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Preencher toda a altura da tabela
		tabela.setFillsViewportHeight(true);

		// Adicionar bordas entre as colunas e linhas da tabela
		tabela.setShowGrid(true);
		tabela.setGridColor(Color.LIGHT_GRAY);

		// Remover sombra da linha quando uma célula é selecionada
		tabela.setRowSelectionAllowed(true);

		// Definir cor de fundo do cabeçalho da tabela
		tabela.getTableHeader().setBackground(Color.WHITE);

		return tabela;
	}

	/**
	 * Adiciona os dados dos investimentos à tabela e cria um JScrollPane para permitir a rolagem.
	 *
	 * @param investimentoList A lista de investimento a serem exibidas na tabela.
	 * @return Um JScrollPane contendo a tabela de despesas com os dados fornecidos.
	 */
	private JScrollPane atualizarDadosTabela() {

		DefaultTableModel model = (DefaultTableModel) investimentoTable.getModel();

		// Limpar dados existentes da tabela
		model.setRowCount(0);

		// Preencher o modelo com os dados do Investimento.
		for (Investimento investimento : investimentoList) {
			Object[] rowData = {
					investimento.getObjetivo(),
					investimento.getEstrategia(),
					investimento.getNome(),
					String.format("R$: %s", DECIMAL_FORMAT.format(investimento.getValorInvestido())),
					String.format("R$: %s", DECIMAL_FORMAT.format(investimento.getPosicao())),
					String.format("R$: %s", DECIMAL_FORMAT.format(investimento.getRendimentoBruto())),
					String.format("%s%%", DECIMAL_FORMAT.format(investimento.getRentabilidade())),
					investimento.getStringVencimento(),
			};
			model.addRow(rowData);

		}

		Object[] investimentoASerInserido = {
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
		};

		model.addRow(investimentoASerInserido);

		// Criar um JScrollPane para permitir a rolagem da tabela
		JScrollPane scrollPane = new JScrollPane(investimentoTable);

		return scrollPane;
	}

	/**
	 * Cria e retorna a tabela de investimento com base na lista de investimento fornecida.
	 *
	 * @param investimentosList A lista de investimentos a serem exibidas na tabela.
	 * @return Um JScrollPane contendo a tabela de Investimento com os dados fornecidos.
	 */
	public JScrollPane criarTabelaInvestimento() {
		// Dentro do método criarTabelaInvestimento()
		JScrollPane scrollPane = atualizarDadosTabela();
		return scrollPane;
	}

	/**
	 * Obtém uma despesa da tabela a partir de sua linha.
	 * @param linhaTabela 
	 * @return {@code Investimento} referente a linha da tabela.
	 */
	private Investimento getInvestimentoDaTabela(int linhaTabela) {
		DefaultTableModel model = (DefaultTableModel) investimentoTable.getModel();

		Integer id = null;

		Investimento investimentoAntigo = null;
		if (linhaTabela != investimentoTable.getRowCount() -1) {
			investimentoAntigo= investimentoList.get(linhaTabela);
			id = investimentoAntigo.getId();
		}

		String objetivo = (String) model.getValueAt(linhaTabela, 0);
		String estrategia = (String) model.getValueAt(linhaTabela, 1);
		String nome = (String) model.getValueAt(linhaTabela, 2);
		String valoresInvestido = (String) model.getValueAt(linhaTabela, 3);
		String posicao = (String) model.getValueAt(linhaTabela, 4);
		String vencimento = ((String) model.getValueAt(linhaTabela, 7));

		try {
			Investimento investimento = new Investimento();
			if (objetivo != null) {
				investimento.setObjetivo(objetivo);
			}

			if (estrategia != null) {
				investimento.setEstrategia(estrategia);
			}
			if (nome != null) {
				investimento.setNome(nome);
			}
			if (valoresInvestido != null) {
				valoresInvestido = valoresInvestido.replace("R$: ", "");
				investimento.setValorInvestido(valoresInvestido);
			}
			if (posicao != null) {
				posicao = posicao.replace("R$: ", "");
				investimento.setPosicao(posicao);
			}
			if (posicao != null && valoresInvestido != null) {
				float rendimento =   investimento.getPosicao() - investimento.getValorInvestido();
				investimento.setRendimentoBruto(rendimento);
				float rentabi =   ((investimento.getPosicao() - investimento.getValorInvestido() ) * 100) / investimento.getValorInvestido();
				investimento.setRentabilidade(rentabi);
			}
			if (vencimento != null) {
				investimento.setVencimento(vencimento);
			}

			if (id != null) {
				investimento.setId(id);
				for (int i = 0; i < investimentoList.size(); i++) {
					if (investimentoAntigo != null && investimentoAntigo.getId().equals(investimentoList.get(i).getId())) {
						investimentoList.set(i, investimento);
					}
				}
			}
			return investimento;
		}catch (IllegalArgumentException | DateTimeException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

}
