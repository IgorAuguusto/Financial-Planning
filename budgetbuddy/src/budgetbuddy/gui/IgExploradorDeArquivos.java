package budgetbuddy.gui;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Fornece serviços de interface gráfica que dão ao usuário acesso aos arquivos
 * do sistema de arquivos.
 * 
 */
public class IgExploradorDeArquivos {
	private static JFileChooser fileChooser;

	/**
	 * Obtém a refêrencia única do objeto {@code JFileChooser}.
	 */
	private static JFileChooser getFileChooser() {
		return (fileChooser == null) ? fileChooser = new JFileChooser() : fileChooser;
	}

	/**
	 * Exibe uma caixa de diálogo {@code JFileChooser} para o usuário navegar pelo
	 * sistema de arquivos e selecionar os arquivos que serão abertos.
	 * 
	 * @param janela Objeto {@code Component} que identifica a janela sobre a qual o
	 *               {@code JFileChooser} será exibido. Use {@code null} se não
	 *               existir uma janela ou para centralizar o {@code JFileChooser}
	 *               na tela.
	 * 
	 * @param titulo {@code String} com o nome da barra de título da caixa de
	 *               diálogo.
	 * 
	 * @return uma lista de {@code String} com os nomes dos arquivos a serem
	 *         abertos. Será retornado {@code null} se o usuário cancelar a operação
	 *         (clicar no botão Cancelar ou Fechar) ou ocorrer algum erro ao tentar
	 *         acessar os nomes dos arquivos no sistema de arquivos.
	 * 
	 * @see javax.swing.JFileChooser
	 */
	public static List<String> dialogoAbrirArquivos(Component janela, String titulo) {
		definirPropriedades(titulo, "Importar Arquivos CSV", KeyEvent.VK_I);

		// Habilita a seleção múltipla de arquivos
		fileChooser.setMultiSelectionEnabled(true);

		// Exibe a caixa de diálogo abrir arquivo.
		int opcao = fileChooser.showDialog(janela, "Importar Arquivos");

		// Verifica se o usuário cancelou a operação; se não, obtém os nomes dos
		// arquivos digitados ou selecionados pelo usuário na caixa de diálogo.
		try {
			if (opcao == JFileChooser.APPROVE_OPTION) {
				File[] selectedFiles = fileChooser.getSelectedFiles();
				List<String> filePaths = new ArrayList<>();
				for (File file : selectedFiles) {
					filePaths.add(file.getCanonicalPath());
				}
				return filePaths;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Define as propriedades do JFileChooser, incluindo título, descrição do botão, o diretório atual e
	 * os filtros de extensão de arquivo.
	 */
	private static void definirPropriedades(String titulo, String textoBotao, int letraMnemonica) {
		final String DIRETORIO_ATUAL = ".", ARQUIVOS_CSV = "Arquivos de csv (*.csv)", EXTENSAO_CSV = "csv";

		if (fileChooser == null) {
			fileChooser = getFileChooser();

			// Indica que o usuário poderá selecionar apenas nomes de arquivos.
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			// Define qual é o diretório default.
			fileChooser.setCurrentDirectory(new File(DIRETORIO_ATUAL));

			// Define um filtro de extensão que será usado pelo JFileChooser para filtrar o
			// tipo de arquivo que será exibido na janela.
			FileNameExtensionFilter csvExtensionFilter = new FileNameExtensionFilter(ARQUIVOS_CSV, EXTENSAO_CSV);

			// O ultímo tipo de arquivo adicionado ao JFileChooser é considerado o filtro
			// (ou tipo) default quando se usa o método a baixo.
			fileChooser.setFileFilter(csvExtensionFilter);
		}
		// Define o título na caixa de diálogo.
		fileChooser.setDialogTitle(titulo);
		
		// Define um texto de ajuda para o botão.
		fileChooser.setApproveButtonToolTipText(textoBotao);
		
		// Define uma letra mnemônica para o botão principal. Esse recurso depende do Look e feel usado, não funciona com o Look e fell metal.
		fileChooser.setApproveButtonMnemonic(letraMnemonica);
	}

}// class IgExploradorArquivo