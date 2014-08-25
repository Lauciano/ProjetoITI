import java.util.ArrayList;
import java.util.Collections;

public class Contexto {

	public ArrayList<Instancia> v;
	private String instancia;
	
	public Contexto(String inst){
		instancia = inst;
		v = new ArrayList<Instancia>();
	}
	
	public String getInstancia(){
		return instancia;
	}
	
	public int getTotal(){
		int total = 0;
		for(int i = 0; i < v.size(); i++){
			total += v.get(i).getFrequencia();
		}
		return total;
	}
	
	private void atualizaProbs(){
		double total = getTotal();
		
		for(int i = 0; i < v.size(); i++){
			v.get(i).setProbabilidade(((double)v.get(i).getFrequencia())/total);
		}
	}
	
	private void atualizaIntervalos(){
		double aux = 0;
		for(int i = 0; i < v.size(); i++){
			v.get(i).setIntervalo(aux, v.get(i).getProbabilidade() + aux);
			aux += v.get(i).getProbabilidade();
		}
	}
	
	public void addOcorrencia(String s){
		
		if(!temIndice(s)){
			Instancia newInst1 = new Instancia(s, 1);
			if(!s.equals("ESC")) addOcorrencia("ESC");
			v.add(newInst1);
		}else{
			for(int i = 0; i < v.size(); i++){
				if(v.get(i).getSymbol().equals(s)){
					v.get(i).setFrequencia(v.get(i).getFrequencia() + 1);
					break;
				}
			}
		}
		
		Collections.sort(v, Instancia.InstanciaComparator);
		atualizaProbs();
		atualizaIntervalos();
	}
	
	public boolean temIndice(String s){
		for(int i = 0; i < v.size(); i++){
			if(v.get(i).getSymbol().equals(s)){
				return true;
			}
		}
		return false;
	}
	
	public Instancia getInstancia(String s){
		for(int i = 0; i < v.size(); i++){
			if(v.get(i).getSymbol().equals(s)){
				return v.get(i);
			}
		}
		return null;
	}
	
	public void removeInstancia(String s){
		for(int i = 0; i < v.size(); i++){
			if(v.get(i).getSymbol().equals(s)){
				v.remove(i);
				break;
			}
		}
		Collections.sort(v, Instancia.InstanciaComparator);
		atualizaProbs();
		atualizaIntervalos();
	}
	
	public static Contexto copy(Contexto c){
		Contexto copia = new Contexto(c.instancia);
		for(Instancia i : c.v){
			copia.v.add(Instancia.copy(i));
		}
		return copia;
	}
}
