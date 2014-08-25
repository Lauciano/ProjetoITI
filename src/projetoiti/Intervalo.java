package projetoiti;


public class Intervalo {
	private String instancia;
	private double inicio;
	private double fim;
	
	// Contrutor

	public Intervalo(String instancia, double inicio, double fim){
		this.instancia = instancia;
		this.inicio = inicio;
		this.fim = fim;
	}
	
	public Intervalo(double inicio, double fim){
		this.inicio = inicio;
		this.fim = fim;
	}
	
	// Getters e Setters
	
	public double getInicio() {
		return inicio;
	}
	public void setInicio(double inicio) {
		this.inicio = inicio;
	}
	public double getFim() {
		return fim;
	}
	public void setFim(double fim) {
		this.fim = fim;
	}
	
	public String getInstancia(){
		return instancia;
	}
	
	public void setInstancia(String instancia){
		this.instancia = instancia;
	}
	
	// Copy
	public static Intervalo copy(Intervalo i){
		return new Intervalo(i.instancia, i.inicio, i.fim);
	}
	
	// To String
	public String toString(){
		return instancia + " - [" + inicio + ", " + fim + ")";
	}
}
