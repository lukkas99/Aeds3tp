/*

 AEDS3 - Trabalho Prático 1 - 06 / 06 / 2023
 Author: Lucas Morato de Oliveira Xavier
 Matricula: 541509

*/
import java.io.*;
import java.util.*;
import java.io.BufferedReader;


import java.io.FileReader;

public class AppPrincipal {
    public static void main (String[]args) throws IOException {
       //RandomAccessFile file = new RandomAccessFile("best_selling_artists_Date.csv", "rw");   
        List<Artista> Artistas = readCsv("best_selling_artists_Date.csv");
    }
    
    public static List<Artista> readCsv(String filename) {
		List<Artista> artistas = new ArrayList<>();
		int id = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			br.readLine(); // Ignora a primeira linha que contém cabeçalhos de coluna
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return artistas;
	}
}
