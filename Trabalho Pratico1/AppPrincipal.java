/*

 AEDS3 - Trabalho Prático 1 - 06 / 06 / 2023
 Authors: Matricula 
 -Lucas Morato de Oliveira Xavier : 541509
 -Vinícius Augusto Alves Santos Mello : 767998 
*/
import java.io.*;
import java.util.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AppPrincipal extends CRUD{
    public static void main (String[]args) throws IOException, ParseException {

	String binFile = "bsa.bin"; // Atribuir o nome do arquivo "bsa.bin" à String binFile
	//acessar arquivo de dados .bin
	List<String[]> dataCsv = ImportCSV.readCsv("best_selling_artists.csv");
	int lastId = ImportCSV.lastId("best_selling_artists.csv");

    RandomAccessFile raf = new RandomAccessFile(binFile, "rw"); 
	if (raf.length() == 0) {
		ImportCSV.toBin(raf, dataCsv, lastId); 
	} 
	
	//Classe para receber uma entrada do teclado
	Scanner sc = new Scanner(System.in);

    int menu  = 0;  // Variavel para escolha de parada
    int opcao = 0;  // Opcao escolhida no menu
    Artista art = new Artista();  // Nova conta

	raf.seek(0); // Volta para o inicio do arquivo
    ultimoId = raf.readInt(); // Le o ultimo id utilizado
	//System.out.println("ultimoid = "+ultimoId);
	
	// Mostrar o menu do sistema
	do {
		System.out.println("\n---------------MENU---------------");
		System.out.println("|1| - Ler o arquivo todo com os melhor artistas de todos os tempos");
		System.out.println("|2| - Ler um registro pesquisado pelo ID");
		System.out.println("|3| - Atualizar um campo");
		System.out.println("|4| - Deletar resgistro do artista");
		System.out.println("|5| - Ordernar o registro");
		System.out.println("|6| - Ainda nada");
		System.out.println("|7| - Ainda nada");
		System.out.println("|8| - Ainda nada");
		System.out.println("|9| - Ainda nada");
		System.out.println("-----------------------------------\n");

		do {
			// Entrada do usuario
			System.out.print("Digite a opcao: ");
			opcao = sc.nextInt();

			// Mensagem de erro 
			if (opcao < 1 || opcao > 9) {
				System.out.print("Opcao invalida!\n");
			}
		  // Repetir enquanto opcao for valida  
		} while (opcao < 1 || opcao > 9);

		// Realizar operacao desejada 
		switch (opcao) { 

			//--------------- 1 ------------------ Ler arquivo inteiro -------------------------
			case 1:
			System.out.println("\n-------------LER ARQUIVO INTEIRO-------------");
			raf.seek(0); // Volta para o inicio do arquivo
    		ultimoId = raf.readInt(); // Le o ultimo id utilizado
			for(int i = 1; i <= ultimoId; i++){
				art = readAll(raf, i);

				if(art == null){
				System.out.println("\n----------------------------"+ i +"------------------------------");				
				System.out.println(" Artista nao encontrado ou deletado!");
				//raf.seek(4);
				}
				else{
				System.out.println("\n----------------------------"+ art.getId()+"------------------------------");
                System.out.println("Artista: " + art.getArtist());
                System.out.println("País de onde veio: " + art.getCountry());
                System.out.println("Periodo de atividade: " + art.getPeriodActive());
				System.out.println("Começo da carreira: " + art.getYear());
                System.out.println("Generos: ");
                for(int j = 0; j < art.getNmbrGenre(); j++) {
                    System.out.println("-" + art.getGenre()[j]);
                }
                System.out.println("Total de discos certificados: $" + (int)art.getTcu()+"000000.00");
                System.out.println("Total de vendas: $" + (int)art.getSales()+"000000.00");
				System.out.println("\n-----------------------------------------------------------");
				//raf.seek(4);
				//int tmp = raf.readInt();
				}
			}
			break;

			//-------------- 2 ---------------------- Pesquisar ID -----------------------------
			case 2:

			System.out.println("\n-------------PESQUISAR ID-------------");
			System.out.print("Digite o ID a ser pesquisado: ");
			int search = sc.nextInt();

			// Criar uma conta com os dados do registro lido
			art = readById(raf, search);

			if(art == null){
				System.out.println(" ID do artista nao encontrado ou deletado!");
				raf.seek(4);
			} 
			else{
				System.out.println("\nID encontrado, dados do artista:");
                System.out.println("ID: " + art.getId());
                System.out.println("Artista: " + art.getArtist());
                System.out.println("Country: " + art.getCountry());
                System.out.println("Periodo de atividade: " + art.getPeriodActive());
				System.out.println("Começo da carreira: " + art.getYear());
                System.out.println("Generos: ");
                for(int i = 0; i < art.getNmbrGenre(); i++) {
                    System.out.println("-" + art.getGenre()[i]);
                }
                System.out.println("Total de discos certificados: $" + (int)art.getTcu()+"000000.00");
                System.out.println("Total de vendas: $" + (int)art.getSales()+"000000.00");
				raf.seek(4);
				//int tmp = raf.readInt();
			}

			break;

			//-------------- 3 ---------------------- Atualizar Registro -----------------------------
			case 3: 
				System.out.println("\n-------------ATUALIZAR DADOS DE UM ID-------------");
				System.out.print("Digite o ID a ser atualizado: ");
				int update = sc.nextInt();

				art = readById(raf, update); // Criar um artista com os dados do registro lido
				if(art == null) System.out.println("Artista nao encontrado!");
                else {
                // Imprime os dados da conta
                System.out.println("\nArtista encontrado!");
                System.out.println("ID: " + art.getId());
                System.out.println("Artista: " + art.getArtist());
				System.out.println("Country: " + art.getCountry());
                System.out.println("Periodo de atividade: " + art.getPeriodActive());
				System.out.println("Começo da carreira: " + art.getYear());
                System.out.println("Generos: ");
                for(int i = 0; i < art.getNmbrGenre(); i++) {
                    System.out.println("-" + art.getGenre()[i]);
                }
                System.out.println("Total de discos certificados: $" + (int)art.getTcu()+"000000.00");
                System.out.println("Total de vendas: $" + (int)art.getSales()+"000000.00");
                System.out.println("-------------------------------------------\n");

                // Escolha o que atualizar
                System.out.println("-------------ATUALIZAR REGISTRO-------------");
                System.out.println("Qual campo deseja atualizar?");
                System.out.println("1 | Artista");
                System.out.println("2 | País de onde veio");
                System.out.println("3 | Periodo de atividade");
                System.out.println("4 | Comeco de carreira");
                System.out.println("5 | Genero musical");
                System.out.println("6 | Total de certificados");
                System.out.println("7 | Total de vendas");
                System.out.print("Digite a opcao: ");

                do {
                    try {
                        opcao = sc.nextInt();
                        if(opcao < 1 || opcao > 7) System.out.println("!Opção inválida!");
                        
						} catch (Exception e) {
                            System.out.println("!Digite um número!");
                            sc.next();
                            break;
                        }
                        } while (opcao < 1 || opcao > 8); // Enquanto a opção for inválida continua no loop

                        switch (opcao) { // Atualiza o campo escolhido
                            case 1: // Artista
                            	System.out.print("Novo nome do artista: ");
                                art.setArtist(sc.next());
                                break;
                            case 2: // Country
                                System.out.print("Novo país: ");
                                art.setCountry(sc.next());
                                break;
                            case 3: // Periodo de atividade
                                System.out.print("Periodo de atividade: ");
                                art.setPeriodActive(sc.next());
                                break;
							case 4: // Começo da carreira
                                System.out.print("Ano de começo: ");
								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								Date data = formatter.parse(sc.next());
                                art.setYear(data);
                                break;	
                            case 5: // Genero musical
                                System.out.print("Nova Quantidade de generos: ");
                                art.setNmbrGenre(sc.nextInt());
                                String[] genre2 = new String[art.getNmbrGenre()];
                                for(int i = 0; i < art.getNmbrGenre(); i++) {
                                    System.out.print("Novo Genero " + (i + 1) + ": ");
                                    genre2[i] = sc.next();
                                }
                                art.setGenre(genre2);
                                break;
                            case 6: // Total de discos certificados
                                System.out.print("Novo numero de certificados: ");
                                art.setTcu(sc.nextFloat());
                                break;
							case 7: // total de vendas
                                System.out.print("Novo total de vendas: ");
                                art.setSales(sc.nextFloat());
                                break;
                            case 8: // Cancelar
                                System.out.println("Cancelado!\n");
                                break;
                        }

                        if(opcao != 8) { // Se a opção for diferente de 8, atualiza o registro
                            if(update(raf, art)) System.out.println("Atualizado com sucesso!");
                            else System.out.println("!Erro ao atualizar!");
							raf.seek(4);
                        }
                    }

				break;

			//-------------- 4 ---------------------- Deletar Registro -----------------------------
			case 4: 
				System.out.println("\n-------------DELETAR REGISTRO---------------");
				System.out.print("Digite o ID do registro a ser deletado: ");
                int delete = sc.nextInt();

				art = readById(raf, delete);// Criar uma conta com os dados do registro lido

				if(art == null) System.out.println("Conta nao encontrada!");
                else { // Se a conta for encontrada, imprime os dados
                System.out.println("\nArtista encontrado!");
                System.out.println("ID: " + art.getId());
                System.out.println("Artista: " + art.getArtist());
				System.out.println("Country: " + art.getCountry());
                System.out.println("Periodo de atividade: " + art.getPeriodActive());
				System.out.println("Começo da carreira: " + art.getYear());
                System.out.println("Generos: ");
                for(int i = 0; i < art.getNmbrGenre(); i++) {
                    System.out.println("-" + art.getGenre()[i]);
                }
                System.out.println("Total de discos certificados: $" + (int)art.getTcu()+"000000.00");
                System.out.println("Total de vendas: $" + (int)art.getSales()+"000000.00");
                System.out.println("-------------------------------------------\n");
                // Confirmação de exclusão
                System.out.println("Deseja deletar essa conta?");
                System.out.println("1 | Sim");
                System.out.println("2 | Nao");
                System.out.print("Digite a opcao: ");

                do {
                	try {
                        opcao = sc.nextInt();
                        if(opcao < 1 || opcao > 2) System.out.println("Opção inválida!");
                        } catch (Exception e) {
                        System.out.println("Digite um número!");
                        sc.next();
                        break;
                        }
                        } while (opcao < 1 || opcao > 2); // Enquanto a opção for inválida continua no loop

                        if(opcao == 1) { // Se a opção for 1, deleta o registro
                        if(delete(raf, art)) System.out.println("Deletado com sucesso!");
                        else System.out.println("!Erro ao deletar!");
						raf.seek(4);
                        }
                        else System.out.println("!Cancelado!\n");
                }

				break;

				//-------------- 5 -------------------- Ordenar Registro -----------------------------
				case 5:
				System.out.println("\n-------------ORDENAR REGISTRO---------------");
				System.out.println("Qual tipo de ordenacao deseja efetuar?");
                System.out.println("1 | Intercalacao balanceada comum");
                System.out.println("2 | Intercalacao balanceada com blocos de tamanho variavel");
                System.out.println("3 | Intercalacao balanceada com selecao por substituicao");
                System.out.print("Digite a opcao: ");

				do {
                    try {
                        opcao = sc.nextInt();
                        if(opcao < 1 || opcao > 3) System.out.println("!Opcao invalida!");
                        
						} catch (Exception e) {
                            System.out.println("!Digite um numero!");
                            sc.nextInt();
                            break;
                        }
                } while (opcao < 1 || opcao > 3); // Enquanto a opção for inválida continua no loop

                switch (opcao) { // escolhe o tipo de ordenacao
                case 1: // Intercalação balanceada comum
					if(Intercalar.comunSort(raf)) System.out.println("\nIntercalado realizada com sucesso!");
					else System.out.println("\nErro ao intercalar!");
                    break;
                case 2: //  Intercalação balanceada com blocos de tamanho variável
                    if(Intercalar.variableSizeSort(raf)) System.out.println("\nIntercalacao com blocos de tamanho variável realizada com sucesso!");
                    else System.out.println("\nErro ao intercalar!");
                    break;
                case 3: // Intercalação balanceada com seleção por substituição
                    if(Intercalar.substitutionSort(dataCsv, "arqSelSub.bin")) System.out.println("\nIntercalacao com selecao por substituicao realizada com sucesso!");
                    else System.out.println("\nErro ao intercalar!");
                	break;
                }
				break;

				case 6: // Nada ainda
				System.out.println("\n-------------Nada ainda---------------------");
				
				break;
			case 7: // Nada ainda
				System.out.println("Nada ainda:");
				break;
			case 8: // Nada ainda
				System.out.println("Nada ainda:");
				break;
			case 9: // Nada ainda
                System.out.println("Nada ainda:");
				break;
		}
		// Perguntar se o usuario deseja continuar 
		do 
		{
			System.out.print("\n\nDeseja continuar usando o sistema? \n(1 - sim / 0 - nao): ");
			menu = sc.nextInt();

			// Mensagem de erro
			if (menu != 1 && menu != 0) {
				System.out.print("Opçao invalida!");
			}

			// Mensagem de encerramento
			if (menu == 0) {
				System.out.println("\n\nO Sistema foi encerrado com sucesso!");
				break;
			}
		} while (menu != 1 && menu != 0);

	}while (menu == 1);
	
	sc.close();
	raf.close();

    }
    
    
}
