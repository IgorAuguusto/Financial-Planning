package budgetbuddy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;

import budgetbuddy.entidades.Categoria;
import budgetbuddy.entidades.Despesa;
import budgetbuddy.entidades.Investimento;

/**
 * Classe responsável por gerar um gráfico de barras com base nas despesas do mês e no valor total das receitas do mes.
 */
public class IgGraficoBarras implements PropriedadesGrafico {
	
	/**
     * Gera um grádico de barras com base nas despesas do mês e no valor total das receitas do mes.
     *
     * @param despesaMesList     Lista de despesas do mês
     * @param valorTotalReceitasMes Valor total das receitas
     * @return O painel de gráfico de barras gerado ou uma painel informativo quando não foi possível gere-lo.
     */
	public static JPanel gerarGraficoBarrasDespesas(List<Despesa> despesaMesList, float valorTotalReceitasMes) {
		if (valorTotalReceitasMes == 0) {
       	 final JPanel contentPanel = new JPanel();
       	    contentPanel.setBackground(new Color(255, 255, 255));
       	    contentPanel.setBounds(100, 100, 450, 300);
       	    contentPanel.setLayout(new BorderLayout());

       	    JTextPane textPane = new JTextPane();
       	    textPane.setText("Para exibir o gráfico de barras faça \nimportação de um arquivo de receita.");
       	    textPane.setFont(new Font("SansSerif", Font.PLAIN, 16));
       	    textPane.setEditable(false);
       	    textPane.setOpaque(false);

       	    StyledDocument doc = textPane.getStyledDocument();
       	    SimpleAttributeSet centerAlignment = new SimpleAttributeSet();
       	    StyleConstants.setAlignment(centerAlignment, StyleConstants.ALIGN_CENTER);
       	    doc.setParagraphAttributes(0, doc.getLength(), centerAlignment, false);

       	    contentPanel.add(textPane, BorderLayout.CENTER);

       	    JLabel imagemLabel = new JLabel("");
       	    imagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
       	    imagemLabel.setIcon(new ImageIcon(IgGraficoPizza.class.getResource("/budgetbuddy/gui/icon/budgetBuddy.png")));

       	    contentPanel.add(imagemLabel, BorderLayout.NORTH);

       	    return contentPanel;
       }
		
		// Criação do conjunto de dados
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		float valorAlimentacao = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, ALIMENTACAO);
		float valorEducacao = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, EDUCACAO);
		float valorEnergiaEletrica = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, ENERGIA_ELETRICA);
		float valorEsporte = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, ESPORTE);
		float valorLazer = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, LAZER);
		float valorHabitacao = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, HABITACAO);
		float valorPlanoDeSaude = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, PLANO_DE_SAUDE);
		float valorOutrosGastos = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, Categoria.OUTRAS_CATEGORIAS);

		Locale.setDefault(Locale.US);

		dataset.addValue(valorAlimentacao / valorTotalReceitasMes * 100, ALIMENTACAO, "");
		dataset.setValue(valorEducacao / valorTotalReceitasMes * 100, EDUCACAO, "");
		dataset.setValue(valorEnergiaEletrica / valorTotalReceitasMes * 100, ENERGIA_ELETRICA, "");
		dataset.setValue(valorEsporte / valorTotalReceitasMes * 100, ESPORTE, "");
		dataset.setValue(valorHabitacao / valorTotalReceitasMes * 100, HABITACAO, "");
		dataset.setValue(valorLazer / valorTotalReceitasMes * 100, LAZER, "");
		dataset.setValue(valorPlanoDeSaude / valorTotalReceitasMes * 100, PLANO_DE_SAUDE, "");
		dataset.setValue(valorOutrosGastos / valorTotalReceitasMes * 100, OUTROS, "");

		// Criação do gráfico de barras
		JFreeChart chart = ChartFactory.createBarChart(
				"",  // Título do gráfico
				"Porcentagens das despesas em relação a receita", // Rótulo do eixo x
				"",            // Rótulo do eixo y
				dataset,              // Dados
				PlotOrientation.VERTICAL,
				true,
				false,
				false
				);

		// Personalização do estilo das barras
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setSeriesPaint(0, COLOR_ALIMENTACAO);   // Cor da primeira barra
		renderer.setSeriesPaint(1, COLOR_EDUCACAO);   // Cor da segunda barra
		renderer.setSeriesPaint(2, COLOR_ENERGIA_ELETRICA);   // Cor da terceira barra
		renderer.setSeriesPaint(3, COLOR_ESPORTE); // Cor da quarta barra
		renderer.setSeriesPaint(4, COLOR_HABITACAO); // Cor da quinta barra
		renderer.setSeriesPaint(5, COLOR_LAZER); // Cor da quinta barra
		renderer.setSeriesPaint(6, COLOR_PLANO_DE_SAUDE); // Cor da quinta barra
		renderer.setSeriesPaint(7,  COLOR_OUTROS); // Cor da quinta barra
		
		// Definir largura máxima para as barras
		double maxBarWidth = .1; // Defina o valor desejado para a largura máxima das barras
		double barMargin = (1.0 - maxBarWidth) / 2.0;
		renderer.setMaximumBarWidth(maxBarWidth);
		renderer.setItemMargin(barMargin);

		// Personalização do fundo e das linhas do gráfico
		plot.setBackgroundPaint(Color.WHITE); // Fundo branco
		plot.setRangeGridlinePaint(Color.GRAY); // Linhas tracejadas cinza
		
		// Ajustar posição das legendas
		LegendTitle legend = chart.getLegend();
		legend.setPosition(RectangleEdge.RIGHT);

		// Personalização do rótulo do eixo y com formato personalizado
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		DecimalFormat decimalFormat = new DecimalFormat("0.00'%'");
		rangeAxis.setNumberFormatOverride(decimalFormat);
		double maxValue = rangeAxis.getUpperBound(); // Valor máximo atual do eixo y
		rangeAxis.setRange(0, maxValue + 10); // Ajuste da escala com 10 unidades a mais

		// Personalização dos rótulos dos valores acima das barras com formato personalizado
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalFormat));
		renderer.setDefaultItemLabelsVisible(true);


		// Criação do painel do gráfico
		ChartPanel chartPanel = new ChartPanel(chart);
		// Desativar funcionalidade de zoom
		chartPanel.setDomainZoomable(false);
		chartPanel.setRangeZoomable(false);

		// Retorno do ChartPanel
		return chartPanel;
	}
	
	/**
	 * Gera uma painel com os dois gráficos do investimento.
	 * 
	 * @param investimentoList lista de investimento
	 * @param  horientação true para em vertical e false para horizontal
	 * @return JPanel com os dois gráficos do investimento
	 */
	public static JPanel gerarGráficosInvestimento(List<Investimento> investimentoList, boolean horientação) {
		// Criação do painel para os gráficos
		JPanel jPanel = new JPanel(new GridLayout(1, 2));
		jPanel.setPreferredSize(new Dimension(1000, 450)); // Definindo o tamanho preferencial do painel

		// Adicionando os gráficos ao painel
		jPanel.add(gerarGraficoInvestimentoValorPosicao(investimentoList, horientação));
		jPanel.add(gerarGraficoInvestimentoRentabilidade(investimentoList, horientação));

		return jPanel;
	}
	
	/**
     * Gera um painel de um grafico de barras com base no investimento seu valor investido e a posição.
     *
     * @param investimentoList     Lista de investimento
     * @param  horientação true para em vertical e false para horizontal
     * @return O painel de gráfico de barras gerado ou uma painel informativo quando não foi possível gere-lo.
     */
	public static JPanel gerarGraficoInvestimentoValorPosicao(List<Investimento> investimentoList,  boolean horientação) {
		// Criação do conjunto de dados
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Investimento investimento : investimentoList) {
			dataset.addValue(investimento.getValorInvestido(), "Valor Investido", investimento.getNome());
			dataset.addValue(investimento.getPosicao(), "Posição", investimento.getNome());
		}
		JFreeChart chart = null;
		if (horientação) {
			// Criação do gráfico de barras
			chart = ChartFactory.createBarChart(
					"Valor Investido x Posição", // Título do gráfico
					"Investimento", // Rótulo do eixo x
					"Valor em Reais (R$)", // Rótulo do eixo y
					dataset, // Conjunto de dados
					PlotOrientation.VERTICAL, // Orientação do gráfico
					true, // Exibir legenda
					true, // Exibir tooltips
					false // URLs
					);
		}
		else {
			// Criação do gráfico em colunas
			chart = ChartFactory.createBarChart(
					"Valor Investido x Posição", // Título do gráfico
					"Investimento", // Rótulo do eixo x
					"Valor em Reais (R$)", // Rótulo do eixo y
					dataset, // Conjunto de dados
					PlotOrientation.HORIZONTAL, // Orientação do gráfico
					true, // Exibir legenda
					true, // Exibir tooltips
					false // URLs
					);
		}

		// Personalização do estilo das barras
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());

		// Definindo a cor das barras do Valor Investido para verde
		Color valorInvestidoColor = new Color(0, 153, 51); // Verde escuro
		renderer.setSeriesPaint(0, valorInvestidoColor);

		// Definindo a cor das barras da Posição para azul
		Color posicaoColor = new Color(0, 102, 204); // Azul médio
		renderer.setSeriesPaint(1, posicaoColor);

		// Personalização do fundo e das linhas do gráfico
		plot.setBackgroundPaint(Color.WHITE); // Fundo branco
		plot.setRangeGridlinePaint(Color.GRAY); // Linhas tracejadas cinza

		// Personalização dos rótulos dos valores acima das barras com formato personalizado
		DecimalFormat decimalFormat = new DecimalFormat("'R$:' 0.00");
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalFormat));
		renderer.setDefaultItemLabelsVisible(true);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		double maxValue = rangeAxis.getUpperBound(); // Valor máximo atual do eixo y
		rangeAxis.setRange(0, maxValue + 2500); // Ajuste da escala com 2500 unidades a mais


		// Configurando o eixo x para exibir as séries agrupadas
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryMargin(0.15);
		domainAxis.setUpperMargin(0.1);
		domainAxis.setLowerMargin(0.1);

		// Criação do painel do gráfico
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 400)); // Definindo o tamanho preferencial do painel

		return chartPanel;
	}
	
	/**
     * Gera um painel de um grafico de barras com base no investimento sua rentabilidade.
     *
     * @param investimentoList     Lista de investimento
     * @param  horientação true para em vertical e false para horizontal
     * @return O painel de gráfico de barras gerado ou uma painel informativo quando não foi possível gere-lo.
     */
	public static JPanel gerarGraficoInvestimentoRentabilidade(List<Investimento> investimentoList,  boolean horientação) {
		// Criação do conjunto de dados
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Investimento investimento : investimentoList) {
			dataset.addValue(investimento.getRentabilidade(), "Rentabilidade", investimento.getNome());
		}
		
		JFreeChart chart = null;
		
		if (horientação) {
			// Criação do gráfico de barras
			 chart = ChartFactory.createBarChart(
					"Rentabilidade", // Título do gráfico
					"Investimento", // Rótulo do eixo x
					"Percentual de Rentabilidade", // Rótulo do eixo y
					dataset, // Conjunto de dados
					PlotOrientation.VERTICAL, // Orientação do gráfico
					true, // Exibir legenda
					true, // Exibir tooltips
					false // URLs
					);
		}
		else {
			// Criação do gráfico em colunas
			 chart = ChartFactory.createBarChart(
					"Rentabilidade", // Título do gráfico
					"Investimento", // Rótulo do eixo x
					"Percentual de Rentabilidade", // Rótulo do eixo y
					dataset, // Conjunto de dados
					PlotOrientation.HORIZONTAL, // Orientação do gráfico
					true, // Exibir legenda
					true, // Exibir tooltips
					false // URLs
					);
		}
		// Personalização do estilo das barras
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());

		Color rentabilidadeColor = new Color(0, 128, 128); // Verde-azulado (Teal)
		renderer.setSeriesPaint(0, rentabilidadeColor);

		// Personalização do fundo e das linhas do gráfico
		plot.setBackgroundPaint(Color.WHITE); // Fundo branco
		plot.setRangeGridlinePaint(Color.GRAY); // Linhas tracejadas cinza

		// Personalização do rótulo do eixo y com formato personalizado
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		DecimalFormat decimalFormat = new DecimalFormat("0.00'%'");
		rangeAxis.setNumberFormatOverride(decimalFormat);
		double maxValue = rangeAxis.getUpperBound(); // Valor máximo atual do eixo y
		rangeAxis.setRange(0, maxValue + 15); // Ajuste da escala com 15 unidades a mais

		// Personalização dos rótulos dos valores acima das barras com formato personalizado
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalFormat));
		renderer.setDefaultItemLabelsVisible(true);

		// Configurando o eixo x para exibir as séries agrupadas
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryMargin(0.15);
		domainAxis.setUpperMargin(0.1);
		domainAxis.setLowerMargin(0.1);

		// Criação do painel do gráfico
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 400)); // Definindo o tamanho preferencial do painel

		return chartPanel;
	}
}