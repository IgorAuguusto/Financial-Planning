package mos.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * Possui métodos para lê o conteúdo de um arquivo CSV (Comma Separated Values). 
 * A linha do arquivo representa um conjunto de dados que consiste em um ou mais valores separados por vírgula (,) ou ponto e vírgula (;).
 * 
 * @author Prof. Márlon Oliveira da Silva
 * 
 * @version 0.1
 */
public class Reader {

	/**
	 * Representa o caractere vírgula (',').
	 */
	public static final char COMMA = ',';

	/**
	 * Representa o caractere ponto e vírgula (';').
	 */
	public static final char SEMICOLON = ';';

	private Reader() {
	}

	/**
	 * Lê o arquivo CSV assmindo que o caractere separador padrão em cada linha do arquivo é a vírgula (',') .
	 * 
	 * @param fileName nome do arquivo CSV.
	 * 
	 * @return uma lista com o conteúdo de cada linha do arquivo ou <code>null</code> se ocorrer um erro ao ler o arquivo.
	 */
	public static List<Line> read(String fileName) {
		return read(fileName, COMMA);
	}
	
	/**
	 * Lê o arquivo CSV considerando o caractere separador especificado.  
	 * 
	 * @param fileName nome do arquivo CSV;
	 * @param separatorChar o caractere separador usado no arquivo CSV. Os valores válidos são COMMA (',') e SEMICOLON (';'). Se for especificado um valor inválido ele será ignorado e o 
	 * caractere separador padrão (COMMA (',')) será usado.
	 * 
	 * @return uma lista com o conteúdo de cada linha do arquivo ou <code>null</code> se ocorrer um erro ao ler o arquivo. 
	 */
	public static List<Line> read(String fileName, char separatorChar) {
		List<Line> lineList = new ArrayList<>();
		
		// Cria o scanner para lê o arquivo csv.
		try (Scanner scanner = new Scanner(new File(fileName))) {
			// Define o caractere separador usado pelo arquivo csv.
			scanner.useDelimiter(Character.toString(separatorChar == SEMICOLON ? SEMICOLON : COMMA));
			
			// Percorre as linhas do arquivo csv para obter o conteúdo de cada linha sem o caractere separador.
			while (scanner.hasNext()) 
				lineList.add(new Line(scanner.nextLine().split(Character.toString(separatorChar))));
			
			return lineList;
			
		} catch (FileNotFoundException | NullPointerException  e) {
			return null;
		}
	}
} // class FileReader