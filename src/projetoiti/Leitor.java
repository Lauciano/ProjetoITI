package projetoiti;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Leitor {

	private BufferedReader br;
	private String linhaAtual;
	private int index;
	private String nomeArq;
	
	public Leitor(String nomeArq){
		
		linhaAtual = "";
		index = 0;
		this.nomeArq = nomeArq;
		
		try{
			br = new BufferedReader(new FileReader(nomeArq));
			linhaAtual = br.readLine();
			System.out.println("Linha lida = " + linhaAtual);
		}catch(FileNotFoundException e){
			System.err.println("Arquivo n�o encontrado!");
		} catch (IOException e) {
			System.err.println("Erro na leitura do arquivo!");
		}
	}
	
	public ArrayList<Character> getAlfabeto(){
		String linha;
		ArrayList<Character> alfabeto = new ArrayList<Character>();
		
		try {
			
			BufferedReader br2 = new BufferedReader(new FileReader(nomeArq));
			for(int i = 0; (linha = br2.readLine()) != null; i++){
				for(int j = 0; j < linha.length(); j++){
					if(!alfabeto.contains(linha.charAt(j))){
						alfabeto.add(linha.charAt(j));
					}
				}
			}
			
			
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo n�o encontrado!");
		} catch (IOException e){
			System.err.println("Erro na leitura do arquivo!");
		}
		
		return alfabeto;
 		
	}
	
	public char getNextCharacter(){
		
		try {
			br.ready();
		} catch (IOException e1) {
			System.err.println("Arquivo ja foi fechado!");
			return '�';
		}
		
		if(index == linhaAtual.length()){
			index = 0;
			try {
				linhaAtual = br.readLine();
				if(linhaAtual == null){
					br.close();
					return '�';
				}
			} catch (IOException e) {
				System.out.println("Erro na leitura do arquivo!");
			}

		}
		
		return linhaAtual.charAt(index++);
		
	}
	
}
