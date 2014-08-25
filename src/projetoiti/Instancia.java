package projetoiti;

import java.util.Comparator;

public class Instancia{

	private String simb;
	private int freq;
	private double prob;
	private Intervalo intervalo;
	
	public Instancia(String s, int f){
		simb = s;
		freq = f;
		intervalo = new Intervalo(simb, 0, 1);
	}
	
	public String getSymbol(){
		return simb;
	}
	
	public void setSymbol(String s){
		simb = s;
	}
	
	public int getFrequencia(){
		return freq;
	}
	
	public void setFrequencia(int f){
		freq = f;
	}
	
	public double getProbabilidade(){
		return prob;
	}
	
	public void setProbabilidade(double p){
		prob = p;
	}
	
	public Intervalo getIntervalo(){
		return intervalo;
	}
	
	public void setIntervalo(double inicio, double fim){
		intervalo.setInicio(inicio);
		intervalo.setFim(fim);
	}
	
	public int compareTo(Instancia i){
		if(this.getSymbol() == "ESC") return 1;
		if(i.getSymbol() == "ESC") return -1;
		
		if(this.getFrequencia() == i.getFrequencia()){
			return this.getSymbol().compareTo(i.getSymbol());
		}else{
			return this.getFrequencia() - i.getFrequencia();
		}
	}
	
	public static Comparator<Instancia> InstanciaComparator = new Comparator<Instancia>(){
		public int compare(Instancia inst1, Instancia inst2){
			if(inst1.getSymbol() == "ESC") return 1;
			if(inst2.getSymbol() == "ESC") return -1;
			
			if(inst1.getFrequencia() == inst2.getFrequencia()){
				return inst1.getSymbol().compareTo(inst2.getSymbol());
			}else{
				return inst2.getFrequencia() - inst1.getFrequencia();
			}
			
		}
	};
	
	//Copia Objeto
	public static Instancia copy(Instancia i){
		Instancia copia = new Instancia(i.simb, i.freq);
		copia.setProbabilidade(i.prob);
		copia.setIntervalo(i.intervalo.getInicio(), i.intervalo.getFim());
		return copia;
	}
	
}
