package mos.reader;

/**
 * Esta classe representa uma linha do arquivo CSV. A linha do arquivo representa um conjunto de dados que consiste em um ou mais valores separados por vírgula (,) ou ponto e vírgula (;).
 * Esta classe considera apenas os dados da linha, sem o caractere separador.
 * 
 * @author Prof. Márlon Oliveira da Silva
 * 
 * @version 0.1
 */
public class Line {
	private String[] line;
	
	/**
	 * Inicializa a linha. 
	 * 
	 * @param line o conteúdo da linha sem o caractere separador.
	 */
	public Line(String... line) {
		this.line = line;	
	}

	/**
	 * Obtém os dados da linha. 
	 * 
	 * @return os dados da linha. A posição de cada dado no vetor corresponde a sua posição na linha do arquivo CSV, ou seja, o primeiro campo da linha ocupa a posição zero do vetor, o segundo campo ocupa a posição um e 
	 * assim por diante.
	 */
	public String[] getLine() {
		return line;
	}
	
	/**
	 * Obtém o dado corresponde a sua posição na linha. 
	 * 
	 * @param position a posição do dado no vetor corresponde a sua posição na linha do arquivo CSV, ou seja, o primeiro campo da linha ocupa a posição zero do vetor, o segundo campo ocupa a posição um e assim por diante.
	 * 
	 * @return retorna o dado da linha na posição indicada. Se o valor de posição for inválido, menor do que zero ou maior do que a quantidade de dados menos um, retorna <code>null</code>.  
	 */
	public String getData(int position) {
		return (position >= 0 && position < quantityOfData()) ? line[position] : null;
	}
	
	/**
	 * Obtém a quantidade de dados da linha.
	 * 
	 * @return o número de dados da linha.
	 */
	public int quantityOfData() {
		return line.length;
	}
} // class Line