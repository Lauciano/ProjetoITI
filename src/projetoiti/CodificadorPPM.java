import java.util.ArrayList;


public class CodificadorPPM {
	public static void main(String[] args){
		System.out.println("************ Codificador PPM ***************");
		
		Leitor l = new Leitor("texto.txt");
		ArrayList<Character> ar = l.getAlfabeto();
		TabelaPPM tppm = new TabelaPPM(ar);
		ArrayList<Intervalo> saida = tppm.geraIntervalos("texto.txt");

		System.out.println("********** Tabela **********");
		System.out.println(tppm.toString());
		
		System.out.println("********** Intervalos **********");
		for(int i = 0; i < saida.size(); i++){
			System.out.println(i + " " + saida.get(i).toString());
		}
		
		/*for(int i = 0; i < ar.size(); i++){
			System.out.println("Alfab [" + i + "] = " + ar.get(i));
		}
		
		char c;
		
		while((c = l.getNextCharacter()) != '¬'){
			System.out.println("lido = " + c + " and "  + (int)c);
		}*/
		
		/*
		Contexto abc = new Contexto("S");
		abc.addOcorrencia("S");
		abc.addOcorrencia("S");
		abc.addOcorrencia("B");
		abc.addOcorrencia("A");
		
		for(int i = 0; i < abc.v.size(); i++){
			System.out.println(abc.v.get(i).getSymbol() + " " + abc.v.get(i).getFrequencia() + " " + abc.v.get(i).getProbabilidade() + " " + abc.v.get(i).getIntervalo().toString());
		}
		*/
		/*Instancia a = abc.getInstancia("A");
		System.out.println("Freq = " + a.getFrequencia() + " Prob = " + a.getProbabilidade());
		Instancia s = abc.getInstancia("S");
		System.out.println("Freq = " + s.getFrequencia() + " Prob = " + s.getProbabilidade());
		Instancia esc = abc.getInstancia("S");
		System.out.println("Freq = " + esc.getFrequencia() + " Prob = " + esc.getProbabilidade());
		
		System.out.println("Total = " + abc.getTotal());*/
		
	}
}
