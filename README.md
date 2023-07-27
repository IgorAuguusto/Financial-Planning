# Planejamento Financeiro - Aplicativo

## Descrição do Projeto
Trabalho desenvolvido para a disciplina Tecnologia de Orientação a Objetos do terceiro período do curso Tecnólogo em Sistemas para Internet.

Link para o enunciado: https://drive.google.com/file/d/1Hx8XQl3wOXmOKqlhNFRRHJ5sYYWJ7dzN/view?usp=sharing

O Planejamento Financeiro é um aplicativo desenvolvido em Java com interface gráfica Swing para auxiliar o usuário a gerenciar suas finanças, incluindo importação de receitas, despesas e investimentos, cálculo do balanço mensal, exibição de gráficos e pesquisa de despesas.

## Funcionalidades
1. Importação de Receitas, Despesas e Investimentos:
   * Permite ao usuário importar dados de receitas, despesas e investimentos a partir de arquivos CSV.
   * Os dados de receitas incluem tipo, data e valor.
     * Exemplo de arquivo de receita

       ![Imagem Exemplo](https://github.com/IgorAuguusto/Financial-Planning/assets/82172424/3d971566-02b7-4ae3-8c4c-84f24e619da3)

   * Os dados de despesas incluem data de aquisição, dia de pagamento no mês, tipo de pagamento, descrição, categoria, valor e situação.
      * Exemplo de arquivo de despesa

       ![image](https://github.com/IgorAuguusto/Financial-Planning/assets/82172424/d8891423-2da8-494a-ab79-db36f2e8ab5f)

   * Os dados de investimentos incluem objetivo, estratégia, nome, valor investido, posição, rendimento bruto, rentabilidade e data de vencimento.
      * Exemplo de aruivo de investimento

       ![image](https://github.com/IgorAuguusto/Financial-Planning/assets/82172424/7505eb05-b3bc-47e8-be63-a8a606895f3e)

   * A importação ocorre através do botão "Importar" na interface gráfica.
     * **Os arquivos devem possuir o mesmo cabeçalho dos exemplos a cima.**
       
    ![image](https://github.com/IgorAuguusto/Financial-Planning/assets/82172424/4c220446-713c-4cc1-a1a7-b0f013211e07)
     
1. Balanço Mensal:
   * Calcula e exibe os valores totais de receitas, despesas, saldo, total pago, total a pagar e investimentos.
   * Exibe um gráfico de pizza ou em barras mostrando os valores percentuais de cada categoria de despesa em relação à receita mensal total.
   * Atualiza automaticamente quando o mês e/ou a categoria são alterados nas caixas de combinação da interface gráfica.

2. Gráficos:
   * Permite ao usuário alternar entre gráficos de pizza e gráficos de barras que mostram a categorização das despesas mensais em relação à receita total do mês.
     * Gráfico em pizza.
    
     ![2023-07-27_11h18_50](https://github.com/IgorAuguusto/Financial-Planning/assets/82172424/e2299957-dcea-4108-98b9-231690ae6ef1)

     * Gráfico em barras.
     
     ![2023-07-27_11h18_28](https://github.com/IgorAuguusto/Financial-Planning/assets/82172424/c0e593a4-f9f6-4334-b826-5ead3c545956)

     
3. Pesquisar Despesa:
   * Permite ao usuário pesquisar despesas específicas no orçamento.
   * Os itens de pesquisa válidos são data, descrição e valor da despesa.
   * O programa exibe uma caixa de diálogo para inserir o item de pesquisa e permite pesquisar a próxima despesa correspondente.

     ![2023-07-27_10h51_45](https://github.com/IgorAuguusto/Financial-Planning/assets/82172424/4770eb0b-1881-4bba-bbee-00ea5e088ffa)


4. Investimentos:

    ![image](https://github.com/IgorAuguusto/Financial-Planning/assets/82172424/e652d826-4bb7-4203-ad64-b8ab629018ce)
   
   * Exibe uma caixa de diálogo com informações sobre os investimentos do usuário, como valor total investido, acumulado e rendimento bruto.

     ![image](https://github.com/IgorAuguusto/Financial-Planning/assets/82172424/4e4dc216-b99f-4123-8133-89df13a96d81)

   * Permite alternar entre gráficos de barras e gráficos de colunas que mostram os detalhes de cada investimento (valor investido, posição e rentabilidade).

     ![image](https://github.com/IgorAuguusto/Financial-Planning/assets/82172424/a8fcd0af-027a-4782-a07f-327cc9aec82f)

     ![image](https://github.com/IgorAuguusto/Financial-Planning/assets/82172424/d9b43a68-40b2-4d90-a6e8-3f0d881c6745)





## Pré-requisitos
* Java JDK 19 ou superior
* Eclipse IDE for Java Developers 2022-12 ou superior
* PostgreSQL 14 como sistema gerenciador de banco de dados

## Como Executar o Projeto
* Configure o banco de dados PostgreSQL com o nome do banco "financialplanning", usuário "dba" e senha "fpdb@".
* Faça a restauração do banco de dados com o script budgetbuddy.sql ou com o arquivo de backup  budgetbuddy.backup.
* Clone o repositório do GitHub para o seu ambiente local a pasta referente ao o projeto e a budgetbuddy.
* Importe o projeto no Eclipse IDE.
* Execute o aplicativo a partir da classe principal budgetbuddy.
* Caso prefira pode executar o budgetbuddy.jar no lugar de clonar o projeto.
* Utilize a interface gráfica para importar dados, gerar balanço mensal, exibir gráficos e realizar pesquisas de despesas.

## Observações
O aplicativo foi desenvolvido como um projeto individual para fins educacionais e de aprendizado em Java e JDBC.
Ele utiliza apenas recursos da linguagem Java e componentes GUI Swing para implementar suas funcionalidades.
Não é permitido o uso de frameworks ou bibliotecas que automatizem a persistência e recuperação de objetos em banco de dados relacionais.
O aplicativo pode ser aprimorado e estendido de acordo com as necessidades do usuário.
## Autor
Nome: Igor Augusto Alves Silva

E-mail: igoraugusto36s@gmail.com
