package budgetbuddy.gui;

import static budgetbuddy.gui.IgGraficoBarras.gerarGraficoBarrasDespesas;
import static budgetbuddy.gui.IgGraficoPizza.gerarGraficoPizza;
import static budgetbuddy.utilitarios.Formatacoes.DECIMAL_FORMAT;
import static mos.reader.Reader.SEMICOLON;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import budgetbuddy.bd.DataBase;
import budgetbuddy.dao.DAO;
import budgetbuddy.entidades.Categoria;
import budgetbuddy.entidades.Despesa;
import budgetbuddy.entidades.Investimento;
import budgetbuddy.entidades.Receita;
import mos.reader.Line;
import mos.reader.Reader;

/**
 * Esta classe representa a janela principal da aplicação BudgetBuddy.
 * Ela exibe informações sobre as receitas, despesas, investimentos e saldo
 * do orçamento, além de permitir a interação com os dados através de tabelas e gráficos.
 */
public class IgJanelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel valorReceitasLabel;
	private JLabel valorDespesasLabel;
	private JLabel valorSaldoLabel;
	private JLabel valorTotalPagoLabel;
	private JLabel valorTotaAPagarLabel;
	private JLabel valorInvestimentosLabel;
	private JComboBox<String> mesComboBox;
	private JComboBox<String> categoriaComboBox;
	private JPanel graficoPanel;
	private JTable tabelaDespesa;
	private JPanel tabelaPanel;
	private boolean estaNograficoPizza;

	private DataBase dataBase;
	private DAO<Receita> receitaDao;
	private DAO<Despesa> despesaDao;
	private DAO<Investimento> investimentoDao;

	private List<Receita> receitaList;
	private List<Despesa> despesaList;
	private List<Investimento> investimentoList;
	private final JPanel receitaPanel = new JPanel();

	/**
	 * Construtor da classe IgJanelaPrincipal.
	 *
	 * @param dataBase         O objeto de conexão com o banco de dados.
	 * @param receitaDao       O DAO para acessar as receitas no banco de dados.
	 * @param despesaDao       O DAO para acessar as despesas no banco de dados.
	 * @param investimentoDao  O DAO para acessar os investimentos no banco de dados.
	 * @throws SQLException   Exceção lançada em caso de problemas com o banco de dados.
	 */
	public IgJanelaPrincipal(DataBase dataBase, DAO<Receita> receitaDao, DAO<Despesa> despesaDao, DAO<Investimento> investimentoDao) throws SQLException {
		setTitle("Budget Buddy™");
		setIconImage(Toolkit.getDefaultToolkit().getImage(IgJanelaPrincipal.class.getResource("/budgetbuddy/gui/icon/cifrao .png")));

		this.dataBase = dataBase;
		this.receitaDao = receitaDao;
		this.despesaDao = despesaDao;
		this.investimentoDao = investimentoDao;
		receitaList = receitaDao.findAll();
		despesaList = despesaDao.findAll();
		investimentoList = investimentoDao.findAll();

		final Font FONTE_DESCRICOES = new Font("SansSerif", Font.BOLD, 16);
		final Font FONTE_VALORES = new Font("SansSerif", Font.BOLD, 18);
		final Color COR_DE_FUNDO = Color.WHITE;
		final Color COR_DESCRICOES = Color.BLUE;
		final Font TAMANHO_BOTAO = new Font("SansSerif", Font.PLAIN, 11);

		String[] meses = Meses.getAbreviacoes();
		String[] categorias = Categoria.getCategorias();
		Arrays.sort(categorias, (c, c1) -> c.compareTo(c1));

		//Define o tamanho da janela
		setBounds(100, 100, 1207, 521);
		contentPane = new JPanel();
		contentPane.setBackground(COR_DE_FUNDO);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel superiorPanel = new JPanel();
		superiorPanel.setBackground(COR_DE_FUNDO);
		superiorPanel.setBounds(6, 6, 1153, 61);
		contentPane.add(superiorPanel);
		superiorPanel.setLayout(null);
		receitaPanel.setBackground(new Color(255, 255, 255));
		receitaPanel.setBounds(27, -3, 144, 59);
		superiorPanel.add(receitaPanel);

		JLabel receitasLabel = new JLabel("Receitas");
		receitaPanel.add(receitasLabel);
		receitasLabel.setForeground(COR_DESCRICOES);
		receitasLabel.setFont(FONTE_DESCRICOES);

		valorReceitasLabel = new JLabel();
		receitaPanel.add(valorReceitasLabel);
		valorReceitasLabel.setFont(FONTE_VALORES);

		JPanel DespesasPanel = new JPanel();
		DespesasPanel.setBackground(new Color(255, 255, 255));
		DespesasPanel.setBounds(200, -3, 144, 59);
		superiorPanel.add(DespesasPanel);

		JLabel despesasLabel = new JLabel("Despesas");
		DespesasPanel.add(despesasLabel);
		despesasLabel.setForeground(COR_DESCRICOES);
		despesasLabel.setFont(FONTE_DESCRICOES);

		valorDespesasLabel = new JLabel();
		DespesasPanel.add(valorDespesasLabel);
		valorDespesasLabel.setFont(FONTE_VALORES);

		JPanel totalPagoPanel = new JPanel();
		totalPagoPanel.setBackground(new Color(255, 255, 255));
		totalPagoPanel.setBounds(593, -3, 144, 59);
		superiorPanel.add(totalPagoPanel);

		JLabel totalPagoLabel = new JLabel("Total Pago");
		totalPagoPanel.add(totalPagoLabel);
		totalPagoLabel.setForeground(COR_DESCRICOES);
		totalPagoLabel.setFont(FONTE_DESCRICOES);

		valorTotalPagoLabel = new JLabel();
		totalPagoPanel.add(valorTotalPagoLabel);
		valorTotalPagoLabel.setFont(FONTE_VALORES);

		JPanel InvestimentosPanel = new JPanel();
		InvestimentosPanel.setBackground(new Color(255, 255, 255));
		InvestimentosPanel.setBounds(1003, -3, 144, 59);
		superiorPanel.add(InvestimentosPanel);

		JLabel investimentosLabel = new JLabel("Investimentos");
		InvestimentosPanel.add(investimentosLabel);
		investimentosLabel.setForeground(COR_DESCRICOES);
		investimentosLabel.setFont(FONTE_DESCRICOES);

		valorInvestimentosLabel = new JLabel();
		InvestimentosPanel.add(valorInvestimentosLabel);
		valorInvestimentosLabel.setFont(FONTE_VALORES);

		JPanel totalPagarPanel = new JPanel();
		totalPagarPanel.setBackground(new Color(255, 255, 255));
		totalPagarPanel.setBounds(749, -3, 144, 59);
		superiorPanel.add(totalPagarPanel);

		JLabel totalAPagarLabel = new JLabel("Total A Pagar");
		totalPagarPanel.add(totalAPagarLabel);
		totalAPagarLabel.setFont(FONTE_DESCRICOES);
		totalAPagarLabel.setForeground(COR_DESCRICOES);

		valorTotaAPagarLabel = new JLabel();
		totalPagarPanel.add(valorTotaAPagarLabel);
		valorTotaAPagarLabel.setFont(FONTE_VALORES);

		JPanel saldoPanel = new JPanel();
		saldoPanel.setBackground(new Color(255, 255, 255));
		saldoPanel.setBounds(356, -3, 144, 59);
		superiorPanel.add(saldoPanel);

		JLabel saldoLabel = new JLabel("        Saldo       ");
		saldoPanel.add(saldoLabel);
		saldoLabel.setFont(FONTE_DESCRICOES);
		saldoLabel.setForeground(COR_DESCRICOES);

		valorSaldoLabel = new JLabel();
		saldoPanel.add(valorSaldoLabel);
		valorSaldoLabel.setFont(FONTE_VALORES);

		JPanel centralPanel = new JPanel();
		centralPanel.setBackground(COR_DE_FUNDO);
		centralPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Or\u00E7amento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centralPanel.setBounds(6, 66, 1179, 385);
		contentPane.add(centralPanel);
		centralPanel.setLayout(null);

		tabelaPanel = new JPanel();
		tabelaPanel.setBackground(COR_DE_FUNDO);
		tabelaPanel.setBorder(null);
		tabelaPanel.setBounds(17, 54, 595, 272);
		centralPanel.add(tabelaPanel);
		tabelaPanel.setLayout(new BorderLayout(0, 0));

		tabelaDespesa = criarTabela();
		tabelaPanel.add(criarTabelaDespesa(despesaList));

		JButton pesquisarDespesaButton = new JButton("Pesquisar Despesa...");
		pesquisarDespesaButton.setMnemonic(KeyEvent.VK_P);
		pesquisarDespesaButton.setFont(TAMANHO_BOTAO);
		pesquisarDespesaButton.setBackground(Color.WHITE);
		pesquisarDespesaButton.setBounds(17, 330, 138, 25);
		centralPanel.add(pesquisarDespesaButton);

		JButton graficoEmBarrasButton = new JButton("Gráfico em Barras");
		graficoEmBarrasButton.setMnemonic(KeyEvent.VK_B);
		graficoEmBarrasButton.setFont(TAMANHO_BOTAO);
		graficoEmBarrasButton.setBackground(Color.WHITE);
		graficoEmBarrasButton.setBounds(156, 330, 123, 25);
		centralPanel.add(graficoEmBarrasButton);

		JButton graficoEmPizzaButton = new JButton("Gráfico em Pizza");

		graficoEmPizzaButton.setMnemonic(KeyEvent.VK_G);
		graficoEmPizzaButton.setFont(TAMANHO_BOTAO);
		graficoEmPizzaButton.setBackground(Color.WHITE);
		graficoEmPizzaButton.setBounds(280, 330, 110, 25);
		centralPanel.add(graficoEmPizzaButton);

		JLabel mesLabel = new JLabel("Mês:");
		mesLabel.setDisplayedMnemonic(KeyEvent.VK_M);
		mesLabel.setBounds(16, 26, 34, 16);
		centralPanel.add(mesLabel);


		mesComboBox = new JComboBox<String>();
		mesLabel.setLabelFor(mesComboBox);
		mesComboBox.setModel(new DefaultComboBoxModel<>(meses));
		mesComboBox.setMaximumRowCount(6);
		mesComboBox.setSelectedIndex(mesAtual(meses));
		mesComboBox.setBackground(COR_DE_FUNDO);
		mesComboBox.setBorder(null);
		mesComboBox.setBounds(46, 24, 88, 21);
		centralPanel.add(mesComboBox);

		JLabel categoriaLabel = new JLabel("Categoria:");
		categoriaLabel.setDisplayedMnemonic(KeyEvent.VK_C);
		categoriaLabel.setBounds(142, 26, 64, 16);
		centralPanel.add(categoriaLabel);

		categoriaComboBox = new JComboBox<>();
		categoriaLabel.setLabelFor(categoriaComboBox);
		categoriaComboBox.setModel(new DefaultComboBoxModel<>(categorias));
		categoriaComboBox.setMaximumRowCount(6);
		categoriaComboBox.setSelectedIndex(categorias.length - 1);
		categoriaComboBox.setBorder(null);
		categoriaComboBox.setBackground(Color.WHITE);
		categoriaComboBox.setBounds(202, 24, 123, 21);
		centralPanel.add(categoriaComboBox);

		graficoPanel = new JPanel();
		graficoPanel.setBounds(624, 52, 549, 272);
		centralPanel.add(graficoPanel);
		graficoPanel.setLayout(new BorderLayout(0, 0));
		//Adiciona o gráfico de pizza
		estaNograficoPizza = true;
		graficoPanel.add(gerarGraficoPizza(despesaList, valorTotalReceitaMes()));

		JPanel inferiorPanel = new JPanel();
		inferiorPanel.setBackground(COR_DE_FUNDO);
		inferiorPanel.setBounds(0, 453, 1191, 29);
		contentPane.add(inferiorPanel);
		inferiorPanel.setLayout(null);

		JButton investimentoButton = new JButton("Investimentos...");
	
		investimentoButton.setMnemonic(KeyEvent.VK_I);
		investimentoButton.setBounds(1074, 0, 111, 25);
		investimentoButton.setBackground(COR_DE_FUNDO);
		investimentoButton.setFont(TAMANHO_BOTAO);
		inferiorPanel.add(investimentoButton);

		JButton importarButton = new JButton("Importar...");

		importarButton.setMnemonic(KeyEvent.VK_O);
		importarButton.setBackground(COR_DE_FUNDO);
		importarButton.setFont(TAMANHO_BOTAO);
		importarButton.setBounds(987, 0, 83, 25);
		inferiorPanel.add(importarButton);

		//Atualiza os valores dos componentes exibidos na GUI.
		atualizarComponentes();

		//Atualiza os componentes de acordo com o mes selecionado no JComboBox.
		mesComboBox.addItemListener((itemEvent) -> atualizarComponentes(itemEvent));

		//Atualiza os componentes de acordo com a categoria selecionada no JComboBox.
		categoriaComboBox.addItemListener((itemEvent) -> atualizarComponentes(itemEvent));

		//Abre uma janela para a importações dos arquivos.
		importarButton.addActionListener((e) -> importarArquivos());

		//Abre a janela résponsavel pela pesquisa
		pesquisarDespesaButton.addActionListener((e) -> new IgPesquisarDespesa(IgJanelaPrincipal.this, tabelaDespesa, obterListaDespesaMes()));
		
		//Abre a janela do investimento
		investimentoButton.addActionListener((e) -> new IgInvestimentos(IgJanelaPrincipal.this, investimentoList, investimentoDao));

		//Altera o grafico exibido para o grafico de barras.
		graficoEmBarrasButton.addActionListener((e) -> graficoEmBarras());

		//Altera o grafico exibido para o grafico de pizza.
		graficoEmPizzaButton.addActionListener((e) -> graficoEmPizza());

		DefaultTableModel model = (DefaultTableModel) tabelaDespesa.getModel();

		/**
		 * Trata os eventos relacionados a tabela e banco de dados, inserção e alteração. 
		 */
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent evento) {
				if (evento.getType() == TableModelEvent.UPDATE) {
					// Obter a linha selecionada
					int linhaSelecionada = tabelaDespesa.getSelectedRow();
					int colunaAlterada = tabelaDespesa.getSelectedColumn();

					if(linhaSelecionada == tabelaDespesa.getRowCount() - 1) {
						try {
							Despesa despesa = getDespesaDaTabela(linhaSelecionada);
							if (despesa.validarDespesa()) {
								try {
									if (categoriaComboBox.getSelectedItem().toString().equals("Todas")) {
										String categoria = JOptionPane.showInputDialog(IgJanelaPrincipal.this, 
												"Entre com a categoria", "Buget Buddy", JOptionPane.INFORMATION_MESSAGE);
										despesa.setCategoria(categoria);
									}else {
										despesa.setCategoria(categoriaComboBox.getSelectedItem().toString());
									}
									int codDespesa = despesaDao.insert(despesa);
									despesa.setId(codDespesa);
									despesaList.add(despesa);
									atualizarComponentes();
								} catch (SQLException exception) {
									JOptionPane.showMessageDialog(IgJanelaPrincipal.this, "Erro ao Inserir Despesa no banco de dados",
											"Erro", JOptionPane.ERROR_MESSAGE);
								}
							}

						} catch (IllegalArgumentException exception) {
							JOptionPane.showMessageDialog(IgJanelaPrincipal.this, exception.getMessage(), "Valor Inválido",
									JOptionPane.ERROR_MESSAGE);
							tabelaDespesa.setValueAt(null, linhaSelecionada, colunaAlterada);
						}
					}
					else {
						// Recuperar a Despesa da linha selecionada
						try {
							Despesa despesa = getDespesaDaTabela(linhaSelecionada);
							try {
								despesaDao.update(despesa);
								for (int i = 0; i < despesaList.size(); i++) {
									Despesa d = despesaList.get(i);
									if (d.getId().equals(despesa.getId())) {
										despesaList.set(i, despesa);
										break;
									}
								}
							} catch (SQLException exception) {
								JOptionPane.showMessageDialog(IgJanelaPrincipal.this, "Erro ao atualizar no banco de dados",
										"Erro", JOptionPane.ERROR_MESSAGE);
							}
						} catch (IllegalArgumentException exception) {
							JOptionPane.showMessageDialog(IgJanelaPrincipal.this, exception.getMessage(), "Valor Inválido",
									JOptionPane.ERROR_MESSAGE);
						}
						atualizarComponentes();
					}
					tabelaDespesa.requestFocus();
				}
			}
		});

		//Fecha a conexão com banco de dados
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					dataBase.close();
				} catch (Exception e1) {
					JOptionPane.showInternalConfirmDialog(IgJanelaPrincipal.this, "Error ao fechar conexão com banco de dados",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				System.exit(0);
			}
		});

		setResizable(false);
		//Define a propriedade da janela como visivel.
		setVisible(true);
	}

	/**
	 * Altera para o Gráfico de pizza, caso ele já estiver sendo exibido atualiza seus valores.
	 */
	private void graficoEmPizza() {
		if (!estaNograficoPizza) {
			estaNograficoPizza = true;

		}
		graficoPanel.remove(0);
		graficoPanel.add(gerarGraficoPizza(obterListaDespesaMes(), valorTotalReceitaMes()));
		tabelaPanel.revalidate();
		tabelaPanel.repaint();
	}

	/**
	 * Altera para o Gráfico de barras, caso ele já estiver sendo exibido atualiza seus valores.
	 */
	private void graficoEmBarras() {
		if (estaNograficoPizza) {
			estaNograficoPizza = false;
		}
		graficoPanel.remove(0);
		graficoPanel.add(gerarGraficoBarrasDespesas(obterListaDespesaMes(), valorTotalReceitaMes()));
		tabelaPanel.revalidate();
		tabelaPanel.repaint();
	}

	/**
	 * Obtém o índice do mês atual em relação a um array de nomes de meses.
	 *
	 * @param meses O array de nomes de meses.
	 * @return O índice do mês atual no array de meses.
	 */
	private int mesAtual(String[] meses) {
		LocalDate dataAtual = LocalDate.now();
		int mesAtual = dataAtual.getMonthValue();

		for (int i = 0; i < meses.length; i++) {
			if (Meses.obterValorPorAbreviacao(meses[i]) == mesAtual) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * Atualiza os valores das labels exibidos na interface gráfica com as informações do banco de dados.
	 *
	 */
	private void atualizarValoresLabels() {
		float valorReceita = valorTotalReceitaMes();
		float valorDespesas = valorTotalDespesMes();
		float totalPago = totalPagoMes();

		valorReceitasLabel.setText(String.format("%s%s","R$: " , DECIMAL_FORMAT.format(valorReceita)));
		valorDespesasLabel.setText(String.format("%s%s","R$: " ,DECIMAL_FORMAT.format(valorDespesas)));

		float valorSaldo = valorReceita - valorDespesas;

		if (valorSaldo < 0) {
			valorSaldoLabel.setForeground(Color.RED);
		} else if (valorSaldo > 0) {
			valorSaldoLabel.setForeground(new Color(0, 100, 0));
		} else {
			valorSaldoLabel.setForeground(Color.black);
		}

		float valorInvestimentos = investimentoList.stream().map((i) -> i.getValorInvestido())
				.reduce(0f,(i, i2) -> i + i2);

		valorSaldoLabel.setText(String.format("%s%s","R$: " , DECIMAL_FORMAT.format(valorSaldo)));
		valorTotalPagoLabel.setText(String.format("%s%s","R$: " ,DECIMAL_FORMAT.format(totalPago)));
		valorTotaAPagarLabel.setText(String.format("%s%s","R$: " , DECIMAL_FORMAT.format(valorDespesas - totalPago)));
		valorInvestimentosLabel.setText(String.format("%s%s","R$: " , DECIMAL_FORMAT.format(valorInvestimentos)));

	}

	/**
	 * Calcula o valor total dos valores das despesas pagas no més selecionado no JComboBox;
	 * @return o valor total das despesas pegas naquele més.
	 */  
	private float totalPagoMes() {
		if (despesaList == null) return 0;

		return  obterListaDespesaMes().stream().filter((d) ->  d.getSituacao().isStatus() == true)
				.map((d) -> d.getValor()).reduce(0f, (d, d1) -> d + d1);
	}

	/**
	 * Calcula o valor total das despesas no més selecionado no JComboBox;
	 * @return o valor total das despesas no més.
	 */  
	private float valorTotalDespesMes() {
		if (despesaList == null) return 0;

		if (mesComboBox.getSelectedItem().toString().equals("Todos")) {
			return despesaList.stream().map((d) -> d.getValor()).reduce(0f, (d, d1) -> d + d1);
		}

		return obterListaDespesaMes().stream().map((d) -> d.getValor()).reduce(0f, (d, d1) -> d + d1);
	}

	/**
	 * Calcula o valor total das receitas no més selecionado no JComboBox;
	 * @return o valor total das receitas no més.
	 */
	private float valorTotalReceitaMes() {
		if (receitaList == null) return 0;

		if (mesComboBox.getSelectedItem().toString().equals("Todos")) {
			return receitaList.stream().map((r) -> r.getValor()).reduce(0f, (r, r1) -> r + r1);
		}

		return receitaList.stream().filter((r) -> r.getData().getMonthValue() == 
				Meses.obterValorPorAbreviacao(mesComboBox.getSelectedItem().toString()))
				.map((r) -> r.getValor()).reduce(0f, (r, r1) -> r + r1);
	}

	/**
	 * Cria uma nova tabela de despesas.
	 *
	 * @return A JTable configurada para exibir as despesas.
	 */
	private JTable criarTabela() {
		// Criar os nomes das colunas
		String[] colunaNomes = {"Data", "Dia", "Tipo", "Descrição", "Valor", "Paga"};

		// Criar o modelo da tabela
		DefaultTableModel model = new DefaultTableModel(colunaNomes, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 5) {
					return Boolean.class; 
				}
				return super.getColumnClass(columnIndex);
			}

		};

		// Criar a tabela com o modelo criado
		JTable tabela = new JTable(model);

		// Impedir reordenação das colunas
		tabela.getTableHeader().setReorderingAllowed(false);

		// Configurar o estilo da tabela
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Preencher toda a altura da tabela
		tabela.setFillsViewportHeight(true);

		// Definir cor de fundo da tabela
		tabela.setBackground(Color.WHITE);

		// Remover sombra da linha quando uma célula é selecionada
		tabela.setRowSelectionAllowed(true);

		// Definir cor de fundo do cabeçalho da tabela
		tabela.getTableHeader().setBackground(Color.WHITE);

		// Adicionar bordas entre as colunas e linhas da tabela
		tabela.setShowGrid(true);
		tabela.setGridColor(Color.LIGHT_GRAY);

		// Definir a renderização do JCheckBox na coluna "Paga"
		TableColumn checkBoxColumn = tabela.getColumnModel().getColumn(5);
		checkBoxColumn.setCellRenderer(tabela.getDefaultRenderer(Boolean.class));
		checkBoxColumn.setCellEditor(tabela.getDefaultEditor(Boolean.class));

		tabela.getColumnModel().getColumn(0).setPreferredWidth(90);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(36);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(68);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(302);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(120);

		return tabela;
	}

	/**
	 * Obtém uma despesa da tabela a partir de sua linha.
	 * @param linhaTabela 
	 * @return {@code Despesa} referente a linha da tabela.
	 */
	private Despesa getDespesaDaTabela(int linhaTabela) {
		DefaultTableModel model = (DefaultTableModel) tabelaDespesa.getModel();

		Integer id = null;
		String categoria = null;

		Despesa despesaAntiga = null;
		if (linhaTabela != tabelaDespesa.getRowCount() -1) {
			despesaAntiga = obterListaDespesaMes().get(linhaTabela);
			categoria = despesaAntiga.getStrCategoria();
			id = despesaAntiga.getId();
		}
		String dataDespesa = (String) model.getValueAt(linhaTabela, 0);
		String diaPagamento = (String) model.getValueAt(linhaTabela, 1);
		String formaPagamento = (String) model.getValueAt(linhaTabela, 2);
		String descricao = (String) model.getValueAt(linhaTabela, 3);
		String valor = (String) model.getValueAt(linhaTabela, 4);
		Boolean paga = (Boolean) model.getValueAt(linhaTabela, 5);

		String situacao = "";
		if (paga)
			situacao = "Paga";

		try {
			Despesa despesa = new Despesa();
			if (dataDespesa != null) {
				despesa.setDataDespesa(dataDespesa);
			}

			if (diaPagamento != null) {
				diaPagamento = String.format("%s/%02d", diaPagamento, Meses.obterValorPorAbreviacao(mesComboBox.getSelectedItem().toString()));
				despesa.setDiaPagamento(diaPagamento);
			}
			if (formaPagamento != null) {
				despesa.setFormaPagamento(formaPagamento);
			}
			if (descricao != null) {
				despesa.setDescricao(descricao);
			}
			if (valor != null) {
				valor = valor.replace("R$: ","");
				despesa.setValor(valor);
			}
			if (categoria == null) {
				despesa.setCategoria(categoriaComboBox.getSelectedItem().toString());
			}
			else{
				despesa.setCategoria(categoria);
			}

			despesa.setSituacao(situacao);

			if (id != null) {
				despesa.setId(id);
				for (int i = 0; i < despesaList.size(); i++) {
					if (despesaAntiga != null && despesaAntiga.getId().equals(despesaList.get(i).getId())) {
						despesaList.set(i, despesa);
					}
				}
			}
			return despesa;
		}catch (IllegalArgumentException | DateTimeException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Adiciona os dados das despesas à tabela e cria um JScrollPane para permitir a rolagem.
	 *
	 * @param despesas A lista de despesas a serem exibidas na tabela.
	 * @return Um JScrollPane contendo a tabela de despesas com os dados fornecidos.
	 */
	private JScrollPane atualizarDadosTabela(List<Despesa> despesas) {

		DefaultTableModel model = (DefaultTableModel) tabelaDespesa.getModel();

		// Limpar dados existentes da tabela
		model.setRowCount(0);

		// Preencher o modelo com os dados das despesas
		for (Despesa despesa : despesas) {
			Object[] rowData = {
					despesa.getStringDataDespesa(),
					despesa.getDiaPagamento(),
					despesa.getFormaPagamento().getFormaPagamento(),
					despesa.getDescricao(),
					String.format("R$: %s", DECIMAL_FORMAT.format(despesa.getValor())),
					despesa.getSituacao().isStatus(),
			};
			model.addRow(rowData);

		}

		Object[] despesaASerInserido = {
				null,
				null,
				null,
				null,
				null,
				false,
		};

		model.addRow(despesaASerInserido);

		// Criar um JScrollPane para permitir a rolagem da tabela
		JScrollPane scrollPane = new JScrollPane(tabelaDespesa);
		
		return scrollPane;
	}

	/**
	 * Cria e retorna a tabela de despesas com base na lista de despesas fornecida.
	 *
	 * @param despesas A lista de despesas a serem exibidas na tabela.
	 * @return Um JScrollPane contendo a tabela de despesas com os dados fornecidos.
	 */
	public JScrollPane criarTabelaDespesa(List<Despesa> despesas) {
		JScrollPane scrollPane = atualizarDadosTabela(despesas);
		return scrollPane;
	}

	public List<Despesa> obterListaDespesaMes(){
		return  despesaList.stream()
				.filter((d) -> Integer.parseInt(d.getMesPagamento()) == Meses.
				obterValorPorAbreviacao(mesComboBox.getSelectedItem().toString()))
				.collect(Collectors.toList());
	}

	/**
	 * Atualiza os componentes do GUI.
	 *
	 */
	private void atualizarComponentes() {
		List<Despesa> despesaMesList = despesaList;

		despesaMesList = obterListaDespesaMes();

		if (estaNograficoPizza) {
			graficoEmPizza();
		}
		else {
			graficoEmBarras();
		}

		if (!categoriaComboBox.getSelectedItem().equals("Todas")) {
			despesaMesList = despesaMesList.stream().filter((d) -> d.getCategoria()
					.equals(Categoria.converterStringParaCategoria(categoriaComboBox.getSelectedItem().toString())))
					.collect(Collectors.toList());
		}
		
		
		// Atualizar o modelo da tabela com os novos dados
		atualizarDadosTabela(despesaMesList);
		
		// Remover o JScrollPane antigo da tabelaPanel
		tabelaPanel.remove(0);

		// Adicionar o novo JScrollPane à tabelaPanel
		tabelaPanel.add(atualizarDadosTabela(despesaMesList));
		
		
		// Atualizar a interface
		tabelaPanel.revalidate();
		tabelaPanel.repaint();

		atualizarValoresLabels();
	}


	/**
	 * Atualiza os componentes do GUI com base nos Eventos do JComboBox selecionados pelo usuário.
	 *
	 * @param itemEvent O evento de mudança de seleção no mês ou categoria.
	 */
	private void atualizarComponentes(ItemEvent itemEvent) {
		if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
			if (tabelaDespesa.isEditing()) {
			    tabelaDespesa.getCellEditor().stopCellEditing();
			}
			atualizarComponentes();
		}
	}

	/**
	 * Importa os dados dos arquivos selecionados pelo usuário e exibe um relatório com o status da importação.
	 */
	private void importarArquivos() {
		final String cabecalhoReceita = "[Tipo, Data, Valor]";
		final String cabecalhoDespesa = "[DataDespesa, DiaPagamento, FormaPagamento, Descrição, Categoria, Valor, Situação]";
		final String cabecalhoInvestimento = "[Objetivo, Estratégia, Nome, Valor Investido, Posição, Rendimento Bruto, Rentabilidade, Vencimento]";

		List<String> caminhosArquivoList = IgExploradorDeArquivos.dialogoAbrirArquivos(IgJanelaPrincipal.this, "Importar receitas, despesas e investimentos");

		if (caminhosArquivoList == null) return;

		StringBuilder arquivosImportados = new StringBuilder();
		StringBuilder falhaAoImportarArquivos = new StringBuilder();
		Map<String, Integer[]> arquivoLinhasMap = new HashMap<>();	

		for (String caminhoArquivo : caminhosArquivoList) {
			List<Line> linhasDoArquivoList = Reader.read(caminhoArquivo, SEMICOLON);

			int numeroDelinhasImportadas;
			int numeroDelinhasDoArquivo = linhasDoArquivoList.size() - 1;

			String fileSeparator = System.getProperty("file.separator");
			String nomeArquivo = caminhoArquivo.replaceAll(String.format(".*\\%s", fileSeparator), ""); 

			if (Arrays.toString(linhasDoArquivoList.get(0).getLine()).equals(cabecalhoReceita)) {
				numeroDelinhasImportadas = importarArquivoReceita(linhasDoArquivoList);

			}
			else if (Arrays.toString(linhasDoArquivoList.get(0).getLine()).equals(cabecalhoDespesa)) {
				numeroDelinhasImportadas = importarArquivoDespesa(linhasDoArquivoList);
			}
			else if (Arrays.toString(linhasDoArquivoList.get(0).getLine()).equals(cabecalhoInvestimento)) {
				numeroDelinhasImportadas = importarArquivoInvestimento(linhasDoArquivoList);
			}
			else {
				falhaAoImportarArquivos.append(nomeArquivo).append("\n");
				continue;
			}

			arquivoLinhasMap.put(nomeArquivo, new Integer[] {numeroDelinhasDoArquivo, numeroDelinhasImportadas});
			arquivosImportados.append(String.format("\nArquivo: %s  \nNumero de linhas do arquivo: %d  \nLinhas Importadas: %d\n", 
					nomeArquivo ,arquivoLinhasMap.get(nomeArquivo)[0], arquivoLinhasMap.get(nomeArquivo)[1]));
		}	
		atualizarComponentes();

		exibirStatusArquivos(arquivosImportados.toString(), falhaAoImportarArquivos.toString());
	}

	/**
	 * Importa os dados de receitas contidos no arquivo e adiciona-os à lista de receitas.
	 *
	 * @param linhasDoArquivoList As linhas do arquivo a serem importadas.
	 * @return O número de linhas importadas com sucesso.
	 */
	private int importarArquivoReceita(List<Line> linhasDoArquivoList) {
		linhasDoArquivoList.remove(0);

		int falhaAoImportarLinha = 0;

		for (Line line : linhasDoArquivoList) {
			try {
				Receita receita = new Receita(line.getData(0), line.getData(1), line.getData(2));
				receitaDao.insert(receita);
				receitaList.add(receita);
			} catch (IllegalArgumentException | DateTimeException | SQLException e) {
				falhaAoImportarLinha++;
			}
		}
		return linhasDoArquivoList.size() - falhaAoImportarLinha;
	}

	/**
	 * Importa os dados de despesas contidos no arquivo e adiciona-os à lista de despesas.
	 *
	 * @param linhasDoArquivoList As linhas do arquivo a serem importadas.
	 * @return O número de linhas importadas com sucesso.
	 */
	private int importarArquivoDespesa(List<Line> linhasDoArquivoList) {
		linhasDoArquivoList.remove(0);
		int falhaAoImportarLinha = 0;

		for (Line line : linhasDoArquivoList) {
			try {
				Despesa despesa = new Despesa(line.getData(0), line.getData(1), line.getData(2), line.getData(3),
						line.getData(4), line.getData(5), line.getData(6));
				despesaDao.insert(despesa);
				despesaList.add(despesa);
			} catch (IllegalArgumentException | DateTimeException | SQLException e) {
				falhaAoImportarLinha++;
				e.printStackTrace();
			}
		}
		return linhasDoArquivoList.size() - falhaAoImportarLinha;
	}

	/**
	 * Importa os dados de investimentos contidos no arquivo e adiciona-os à lista de investimentos.
	 *
	 * @param linhasDoArquivoList As linhas do arquivo a serem importadas.
	 * @return O número de linhas importadas com sucesso.
	 */
	private int importarArquivoInvestimento(List<Line> linhasDoArquivoList) {
		linhasDoArquivoList.remove(0);
		int falhaAoImportarLinha = 0;

		for (Line line : linhasDoArquivoList) {
			try {
				Investimento investimento = new Investimento(line.getData(0), line.getData(1), line.getData(2), line.getData(3), 
						line.getData(4), line.getData(5), line.getData(6), line.getData(7));
				investimentoDao.insert(investimento);
				investimentoList.add(investimento);
			} catch (IllegalArgumentException | DateTimeException | SQLException e) {
				falhaAoImportarLinha++;
			}
		}

		return linhasDoArquivoList.size() - falhaAoImportarLinha;
	}

	/**
	 * Exibe um relatório com o status dos arquivos importados e dos arquivos que falharam na importação.
	 *
	 * @param arquivosImportados        O relatório dos arquivos importados com sucesso.
	 * @param falhaAoImportarArquivos   O relatório dos arquivos que falharam na importação.
	 */
	private void exibirStatusArquivos(String arquivosImportados, String falhaAoImportarArquivos) {
		StringBuilder sb = new StringBuilder();
		if (arquivosImportados != null) {
			sb.append(String.format("Arquivos importados com sucesso\n%s", arquivosImportados));
		}
		else if (falhaAoImportarArquivos != null) {
			sb.append(String.format("\nArquivos que falharam em ser importados\n%s", falhaAoImportarArquivos));
		}
		JTextArea jTextArea = createTextArea(10, 20, false, true, true);
		jTextArea.setText(sb.toString());
		JOptionPane.showMessageDialog(this, new JScrollPane(jTextArea), "Importação de Arquivos", JOptionPane.INFORMATION_MESSAGE);	
	}

	/**
	  * Retorna a conexão com banco de dados	
	  * @return o objeto database.
	  */
	public DataBase getDataBase() {
		return dataBase;
	}

	/**
	 * Cria a área de texto com as propriedades especificadas.
	 * 
	 * @param lines número de linhas da área de texto;
	 * @param columns número de colunas da área de texto;
	 * @param editavel se <code>true</code> a área de texto será editável; 
	 * @param lineWrap se <code>true</code>  ativa a quebra automática das linha de texto;
	 * @param toSeparateWord se <code>true</code> define que a quebra automática das linha de texto ocorra entre 
	 * palavras.
	 * 
	 * @return a área de texto ({@link JTextArea}).
	 */
	private JTextArea createTextArea(int lines, int columns, boolean editavel, boolean lineWrap, boolean toSeparateWord) {
		// Cria uma área de texto vazia com o número de linhas e colunas indicado.
		JTextArea textArea = new JTextArea(lines, columns);

		// Define a área de texto como não editável.
		textArea.setEditable(editavel);

		// Define a quebra automática das linha de texto.
		textArea.setLineWrap(lineWrap);

		// Define que a quebra automática das linha de texto ocorra entre palavras.
		textArea.setWrapStyleWord(toSeparateWord);

		return textArea;
	}
}
