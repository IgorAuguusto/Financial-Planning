package budgetbuddy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
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
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DefaultPieDataset;

import budgetbuddy.entidades.Categoria;
import budgetbuddy.entidades.Despesa;

/**
 * Classe responsável por gerar um gráfico de pizza com base nas despesas do mês e no valor total das receitas do mes.
 * @author dio
 */
public class IgGraficoPizza implements PropriedadesGrafico {
    /**
     * Gera um painel de gráfico de pizza com base nas despesas do mês e no valor total das receitas do mes.
     *
     * @param despesaMesList     Lista de despesas do mês
     * @param valorTotalReceitasMes Valor total das receitas
     * @return O painel de gráfico de pizza gerado ou uma painel informativo quando não foi possível gere-lo.
     */
    public static JPanel gerarGraficoPizza(List<Despesa> despesaMesList, float valorTotalReceitasMes) {
        if (despesaMesList.size() < 1 || valorTotalReceitasMes == 0) {
        	 final JPanel contentPanel = new JPanel();
        	    contentPanel.setBackground(new Color(255, 255, 255));
        	    contentPanel.setBounds(100, 100, 450, 300);
        	    contentPanel.setLayout(new BorderLayout());

        	    JTextPane textPane = new JTextPane();
        	    textPane.setText("Para exibir o gráfico de pizza, certifique-se \nque tenha despesas e receitas importadas.");
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
        
        
    	DefaultPieDataset dataset = new DefaultPieDataset();

        float valorAlimentacao = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, ALIMENTACAO);
        float valorEducacao = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, EDUCACAO);
        float valorEnergiaEletrica = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, ENERGIA_ELETRICA);
        float valorEsporte = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, ESPORTE);
        float valorLazer = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, LAZER);
        float valorHabitacao = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, HABITACAO);
        float valorPlanoDeSaude = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, PLANO_DE_SAUDE);
        float valorOutrosGastos = PropriedadesGrafico.calcularValorDespesaPorCategoria(despesaMesList, Categoria.OUTRAS_CATEGORIAS);
        
        Locale.setDefault(Locale.US);
        
        if (valorAlimentacao > 0) {
            dataset.setValue(ALIMENTACAO, Float.parseFloat(String.format("%.2f", (valorAlimentacao / valorTotalReceitasMes * 100))));
        }
        if (valorEducacao > 0) {
            dataset.setValue(EDUCACAO, Float.parseFloat(String.format("%.2f", (valorEducacao / valorTotalReceitasMes * 100))));
        }
        if (valorEnergiaEletrica > 0) {
            dataset.setValue(ENERGIA_ELETRICA, Float.parseFloat(String.format("%.2f", (valorEnergiaEletrica / valorTotalReceitasMes * 100))));
        }
        if (valorEsporte > 0) {
            dataset.setValue(ESPORTE, Float.parseFloat(String.format("%.2f", (valorEsporte / valorTotalReceitasMes * 100))));
        }
        if (valorLazer > 0) {
            dataset.setValue(LAZER, Float.parseFloat(String.format("%.2f", (valorLazer / valorTotalReceitasMes * 100))));
        }
        if (valorHabitacao > 0) {
            dataset.setValue(HABITACAO, Float.parseFloat(String.format("%.2f", (valorHabitacao / valorTotalReceitasMes * 100))));
        }
        if (valorPlanoDeSaude > 0) {
            dataset.setValue(PLANO_DE_SAUDE, Float.parseFloat(String.format("%.2f", (valorPlanoDeSaude / valorTotalReceitasMes * 100))));
        }
        if (valorOutrosGastos > 0) {
            dataset.setValue(OUTROS, Float.parseFloat(String.format("%.2f", (valorOutrosGastos / valorTotalReceitasMes * 100))));
        }
        
        float valorReceitasMes = valorTotalReceitasMes - valorAlimentacao - valorEducacao - valorEnergiaEletrica
        		- valorEsporte -valorLazer -valorHabitacao - valorPlanoDeSaude - valorOutrosGastos;
        
        if (valorTotalReceitasMes > 0) {
        	dataset.setValue(SALDO, Float.parseFloat(String.format("%.2f", (valorReceitasMes / valorTotalReceitasMes * 100))));
        }
        	
        // Cria o gráfico de pizza 3D
        JFreeChart chart = ChartFactory.createPieChart3D(
                null,  // Título do gráfico
                dataset,  // Conjunto de dados
                false,     // Incluir legenda
                false,     // Incluir dicas de ferramentas
                false     // Não gerar URLs
        );

        // Cria uma instância de LegendTitle e adiciona ao gráfico
        LegendTitle legend = new LegendTitle(chart.getPlot());
        chart.addLegend(legend);

        // Define a posição da legenda à esquerda do gráfico
        chart.getLegend().setPosition(RectangleEdge.LEFT);

        // Obtém o objeto de plotagem do gráfico
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        
        // Define as cores para cada categoria
        plot.setSectionPaint(ALIMENTACAO, COLOR_ALIMENTACAO);
        plot.setSectionPaint(EDUCACAO, COLOR_EDUCACAO);
        plot.setSectionPaint(ENERGIA_ELETRICA, COLOR_ENERGIA_ELETRICA);
        plot.setSectionPaint(ESPORTE, COLOR_ESPORTE);
        plot.setSectionPaint(LAZER, COLOR_LAZER);
        plot.setSectionPaint(HABITACAO, COLOR_HABITACAO);
        plot.setSectionPaint(PLANO_DE_SAUDE, COLOR_PLANO_DE_SAUDE);
        plot.setSectionPaint(OUTROS, COLOR_OUTROS);
        // Define a cor para o saldo (transparente)
        plot.setSectionPaint(SALDO, COLOR_SALDO);

        // Define a transparência das cores
        plot.setForegroundAlpha(0.7f); // 0.0f (totalmente transparente) a 1.0f (opaco)

        // Define o fundo do gráfico como branco
        plot.setBackgroundPaint(Color.WHITE);

        // Define o formato das labels das seções como porcentagem
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}%"));

        // Remove a borda do gráfico
        plot.setOutlineVisible(false);

        // Remove as linhas de ligação entre as seções e as porcentagens
        plot.setLabelLinksVisible(false);

        // Define o backgroud color das porcentagens
        plot.setLabelBackgroundPaint(Color.WHITE);

        // Define a cor da borda das porcentagens como transparente
        plot.setLabelOutlinePaint(new Color(0, 0, 0, 0));

        // Define a cor da sombra das porcentagens como transparente
        plot.setShadowPaint(new Color(0, 0, 0, 0));

        // Altera o ícone das legendas para quadrado
        plot.setLegendItemShape(new Rectangle2D.Double(-4, -4, 8, 8));

        // Cria o painel de gráfico interativo
        ChartPanel chartPanel = new ChartPanel(chart);

        // Permite girar o gráfico com a roda do mouse
        chartPanel.setMouseWheelEnabled(true);

        return chartPanel;
    }

}
