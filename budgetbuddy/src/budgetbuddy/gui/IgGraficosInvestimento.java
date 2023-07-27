package budgetbuddy.gui;

import static budgetbuddy.gui.IgGraficoBarras.gerarGráficosInvestimento;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import budgetbuddy.entidades.Investimento;

/**
 * Classe IgGraficosInvestimento
 *
 * Uma classe que representa uma janela gráfica para exibir gráficos de investimentos.
 * A classe exibe os gráficos em forma de barras ou colunas, dependendo da orientação fornecida.
 *
 * Atributos:
 * - contentPanel: JPanel que contém os componentes da janela.
 *
 * Métodos:
 * - construtor IgGraficosInvestimento: inicializa a janela gráfica e exibe os gráficos de investimentos.
 */

public class IgGraficosInvestimento extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Construtor IgGraficosInvestimento
	 *
	 * Inicializa a janela gráfica e exibe os gráficos de investimentos.
	 *
	 * @param janelaPai Componente pai da janela.
	 * @param investimentoList Lista de investimentos a serem exibidos nos gráficos.
	 * @param horientacao Orientação dos gráficos (true para barras, false para colunas).
	 */
	public IgGraficosInvestimento(Component janelaPai, List<Investimento> investimentoList, boolean horientacao) {

		setIconImage(Toolkit.getDefaultToolkit().getImage(IgGraficosInvestimento.class.getResource("/budgetbuddy/gui/icon/cifrao .png")));
		setTitle("Investimentos");

		setResizable(false);
		setBackground(new Color(255, 255, 255));
		setBounds(100, 100, 1150, 510);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.add(gerarGráficosInvestimento(investimentoList, horientacao));

		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(janelaPai);
		setVisible(true);
	}

}
