import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Contexto {
	private boolean esc;
	private byte valor;
	private int  frequencia;
	private double probabilidade;
	private Codigo intervaloInteiro;
	private Intervalo intervalo;
	private ArrayList<Contexto> filhos;
	
	//Construtores
	//N�O USAR !!! RECURS�O!!! SOCORRO!!!!
	public Contexto(Contexto c){
		esc = c.esc;
		valor = c.valor;
		frequencia = c.frequencia;
		filhos = new ArrayList<Contexto>();
		for(Contexto i : c.filhos){
			filhos.add(new Contexto(i));
		}
		probabilidade = c.probabilidade;
		intervaloInteiro = new Codigo(c.intervaloInteiro.getLow(), c.intervaloInteiro.getHigh());
		intervalo = new Intervalo(c.intervalo.getInicio(), c.intervalo.getFim());
	}
	
	public Contexto(int valor){
		byte v = Byte.parseByte("" + valor);
		this.esc = false;
		this.valor = v;
		this.frequencia = 1;
		this.filhos = new ArrayList<Contexto>();
		probabilidade = 1;
		intervaloInteiro = new Codigo(0, 1);
		intervalo = new Intervalo(0, 1);
	}
	
	public Contexto(){
		this.esc = true;
		this.valor = 0;
		this.frequencia = 1;
		this.filhos = null;
		this.probabilidade = 1;
		this.intervaloInteiro = new Codigo(0, 1);
		this.intervalo = new Intervalo(0, 1);
	}
	
	// Codifica��o
	public static ArrayList<Codigo> geraCodigoInteiro(Contexto raiz, Leitor leitor, int contextoMaximo) throws FileNotFoundException, IOException{
		ArrayList<Codigo> codigo = new ArrayList<Codigo>();
		ArrayList<Byte> ultimos = new ArrayList<Byte>();
		ArrayList<Byte> alfabeto = leitor.getAlfabeto();
		ArrayList<Byte> contextoTemp;
		Byte lido;
		boolean jaEntrou = false;
		Contexto km1 = new Contexto(0);
		for(Byte i : alfabeto){
			km1.addOcorrencia(new Contexto(i));
		}
		km1.removeEsc();
		
		while((lido = leitor.getNextByte()) != null){
			
			//System.out.println("lido = " + (char)lido.byteValue());

			//Atualiza a Tabela e Pega intervalos
			for(int i = -1, j = contextoMaximo; i <= contextoMaximo; i++, j--){
				contextoTemp = new ArrayList<Byte>();
				if(ultimos.size() < j) continue;
				
				if(j > 0){
					for(int k = 0; k < j; k++){
						contextoTemp.add(ultimos.get(ultimos.size() - 1 - k));
					}
					
					//Pega Intervalo
					Codigo intv = null;
					//////System.out.println("CTEMP = " + contextoTemp + " e lido " + lido);
 					if((jaEntrou == false) && (intv = raiz.getIntervaloInteiro(contextoTemp, lido, -1)) != null){
						jaEntrou = true;
						Codigo intervalo = new Codigo(intv);
						intervalo.setTotal(raiz.getTotalFrequencias(contextoTemp, -1));
						////System.out.println("Inserido 1: " + intervalo);
						codigo.add(intervalo);
					}else if((jaEntrou == false) && (intv = raiz.getIntervaloInteiroEsc(contextoTemp, -1)) != null){
						Codigo intervalo = new Codigo(intv);
						intervalo.setTotal(raiz.getTotalFrequencias(contextoTemp, -1));
						////System.out.println("Inserido ESC 1: " + intervalo);
						codigo.add(intervalo);						
					}
					
					Contexto c = new Contexto(lido);
					//////System.out.println("Inserindo " + c + " " + contextoTemp);
					raiz.addOcorrencia(c, contextoTemp, -1);
					if(raiz.getTotalFilhos(contextoTemp, -1) == alfabeto.size() + 1){
						raiz.removeEsc(contextoTemp, -1);
					}
				}else if(j == 0){
					
					Codigo intv = null;
					if((jaEntrou == false) && (intv = raiz.getIntervaloInteiro(lido)) != null){
						jaEntrou = true;
						Codigo intervalo = new Codigo(intv);
						intervalo.setTotal(raiz.getTotal());
						////System.out.println("Inserido 0: " + intervalo);
						codigo.add(intervalo);						
					}else if((jaEntrou == false) && (intv = raiz.getIntervaloInteiroEsc()) != null){
						Codigo intervalo = new Codigo(intv);
						intervalo.setTotal(raiz.getTotal());
						////System.out.println("Inserido ESC 0: " + intervalo);
						codigo.add(intervalo);						
					}
					
					Contexto c = new Contexto(lido);
					raiz.addOcorrencia(c);
					if(raiz.getFilhos().size() == alfabeto.size() + 1){
						raiz.removeEsc();
					}
				}else{
					
					if(jaEntrou == false){
						Codigo intervalo = new Codigo(km1.getIntervaloInteiro(lido));
						intervalo.setTotal(km1.getTotal());
						////System.out.println("Inserido -1: " + intervalo);
						codigo.add(intervalo);						
					}
					km1.removeOcorrencia(lido);
				}
				
				//////System.out.println("\n\n\n");
				
			}
			jaEntrou = false;
			ultimos.add(lido);
		}
		
		return codigo;
	}
	
	public static ArrayList<Intervalo> geraCodigo(Contexto raiz, Leitor leitor, int contextoMaximo) throws FileNotFoundException, IOException{
		ArrayList<Intervalo> codigo = new ArrayList<Intervalo>();
		ArrayList<Byte> ultimos = new ArrayList<Byte>();
		ArrayList<Byte> alfabeto = leitor.getAlfabeto();
		ArrayList<Byte> contextoTemp;
		Collections.sort(alfabeto);
		Byte lido;
		boolean jaEntrou = false;
		
		Contexto km1 = new Contexto(0);
		for(Byte i : alfabeto){
			km1.addOcorrencia(new Contexto(i));
		}
		km1.removeEsc();
		
		while((lido = leitor.getNextByte()) != null){
			
			////System.out.println("lido = " + lido);

			//Atualiza a Tabela e Pega intervalos
			for(int i = -1, j = contextoMaximo; i <= contextoMaximo; i++, j--){
				contextoTemp = new ArrayList<Byte>();
				if(ultimos.size() < j) continue;
				
				if(j > 0){
					for(int k = 0; k < j; k++){
						contextoTemp.add(ultimos.get(ultimos.size() - 1 - k));
					}
					
					//Pega Intervalo
					Intervalo intv = null;
					//////System.out.println("CTEMP = " + contextoTemp + " e lido " + lido);
 					if((jaEntrou == false) && (intv = raiz.getIntervalo(contextoTemp, lido, -1)) != null){
						jaEntrou = true;
						////System.out.println("Inserido 1: " + intv);
						codigo.add(new Intervalo(intv));
					}else if((jaEntrou == false) && (intv = raiz.getIntervaloEsc(contextoTemp, -1)) != null){
						////System.out.println("Inserido ESC 1: " + intv);
						codigo.add(new Intervalo(intv));
					}
					
					Contexto c = new Contexto(lido);
					//////System.out.println("Inserindo " + c + " " + contextoTemp);
					raiz.addOcorrencia(c, contextoTemp, -1);
					if(raiz.getTotalFilhos(contextoTemp, -1) == alfabeto.size() + 1){
						raiz.removeEsc(contextoTemp, -1);
					}
				}else if(j == 0){
					
					Intervalo intv = null;
					if((jaEntrou == false) && (intv = raiz.getIntervalo(lido)) != null){
						jaEntrou = true;
						////System.out.println("Inserido 0: " + intv);
						codigo.add(new Intervalo(intv));
					}else if((jaEntrou == false) && (intv = raiz.getIntervaloEsc()) != null){
						////System.out.println("Inserido ESC 0: " + intv);
						codigo.add(new Intervalo(intv));
					}
					
					Contexto c = new Contexto(lido);
					raiz.addOcorrencia(c);
					if(raiz.getFilhos().size() == alfabeto.size() + 1){
						raiz.removeEsc();
					}
				}else{
					
					if(jaEntrou == false){
						////System.out.println("Inserido -1: " + km1.getIntervalo(lido));
						codigo.add(new Intervalo(km1.getIntervalo(lido)));
					}
					km1.removeOcorrencia(lido);
				}
				
				//////System.out.println("\n\n\n");
				
			}
			jaEntrou = false;
			ultimos.add(lido);
		}
		
		return codigo;
	}
	
	// �rvore
	
	public int getTotalFilhos(ArrayList<Byte> contexto, int nivel){
		if(nivel > -1){
			if(this.isEsc() || (this.getValor() != contexto.get(nivel))) return -1;
		}
		
		if(nivel == contexto.size() - 1) {
			
			if(this.isEsc()) return -1;
			
			return filhos.size();
			
		}
		
		for(Contexto c : filhos){
			int res = c.getTotalFilhos(contexto, nivel+1);
			if(res != -1){
				return res;
			}
		}
		
		return -1;
	}
	
	public void removeEsc(ArrayList<Byte> contexto, int nivel){
		if(nivel > -1){
			if(this.isEsc() || this.getValor() != contexto.get(nivel)) return;
		}
		
		if(nivel == contexto.size() - 1) {
			removeEsc();
			return;
		}
		
		for(Contexto c : filhos){
			c.removeEsc(contexto, nivel+1);
		}

	}
	
	public void removeEsc(){
		for(int i = 0 ; i < filhos.size(); i++){
			if(filhos.get(i).isEsc()){
				filhos.remove(i);
				break;
			}
		}
		this.atualizaProbabilidade();
		this.atualizaIntervalo();
		this.atualizaIntervaloInteiro();
	}
	
	public void removeOcorrencia(byte b){
		for(int i = 0 ; i < filhos.size(); i++){
			if((!filhos.get(i).isEsc()) && filhos.get(i).getValor() == b){
				filhos.remove(i);
				break;
			}
		}
		this.atualizaProbabilidade();
		this.atualizaIntervalo();
		this.atualizaIntervaloInteiro();
	}
	
	public void atualizaIntervalo(){
		double aux = 0;
		for(Contexto c : filhos){
			c.getIntervalo().setInicio(aux);
			c.getIntervalo().setFim(c.getProbabilidade() + aux);
			aux += c.getProbabilidade();
		}
	}
	
	public void atualizaProbabilidade(){
		double total = getTotal();		
		for(Contexto c : filhos){
			c.setProbabilidade(((double)c.getFrequencia())/total);
		}
	}
	
	public void addOcorrencia(Contexto ocorrencia){
		if(!temOcorrencia(ocorrencia.getValor())){
			filhos.add(ocorrencia);
			Contexto esc = findEsc();
			if(esc == null){
				esc = new Contexto(); 
				filhos.add(esc);
			} else {
				esc.setFrequencia(esc.getFrequencia() + 1);
			}
		} else {
			for(Contexto c : filhos){
				if(c.valor == ocorrencia.getValor()){
					c.setFrequencia(c.getFrequencia()+1);
					break;
				}
			}
		}
		
		Collections.sort(filhos, Contexto.ContextoComparator);
		atualizaProbabilidade();
		atualizaIntervalo();
		atualizaIntervaloInteiro();
	}
	
	public void addOcorrencia(Contexto ocorrencia, ArrayList<Byte> contexto, int nivel){
		if(nivel > -1){
			if(isEsc() || this.getValor() != contexto.get(nivel)) return;
		}
		
		if(nivel == contexto.size() - 1) {
			if(!this.isEsc()){
				this.addOcorrencia(ocorrencia);
			}
			return;
		}
		
		for(Contexto c : filhos){
			c.addOcorrencia(ocorrencia, contexto, nivel + 1);
		}
	}
	
	public boolean temOcorrencia(byte v){
		if(filhos.isEmpty()) return false;
		for(Contexto c : filhos){
			if((!c.isEsc()) && c.getValor() == v){
				return true;
			}
		}
		return false;
	}
	
	// Getter e Setter
	public Contexto findEsc(){
		for(Contexto c : filhos){
			if(c.isEsc()){
				return c;
			}
		}
		return null;
	}
	
	public Contexto findContexto(byte valor){
		for(Contexto c : filhos){
			if(c.valor == valor){
				return c;
			}
		}
		return null;
	}
	
	public boolean isEsc() {
		return esc;
	}

	public void setEsc(boolean esc) {
		this.esc = esc;
	}

	public byte getValor() {
		return valor;
	}

	public void setValor(byte valor) {
		this.valor = valor;
	}

	public int getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}

	public ArrayList<Contexto> getFilhos() {
		return filhos;
	}

	public void setFilhos(ArrayList<Contexto> filhos) {
		this.filhos = filhos;
	}
	
	private Intervalo getIntervalo() {
		return intervalo;
	}
	
	public Intervalo getIntervaloEsc(){
		Contexto filho = null;
		if(this.isEsc()) return null;
		for(Contexto c : filhos){
			if(c.isEsc()){
				filho = c;
				break;
			}
		}
		if(filho == null) return null;
		return filho.getIntervalo();		
	}
	
	public Intervalo getIntervaloEsc(ArrayList<Byte> contexto, int nivel) {
		if(nivel > -1){
			if(isEsc() || (this.getValor() != contexto.get(nivel))) return null;
		}
		
		if(nivel == contexto.size() - 1) {
			////System.out.println("Contexto size " + contexto.size() + " "  + filhos.size());
			Contexto filho = null;
			for(Contexto c : filhos){
				if(c.isEsc()){
					filho = c;
					break;
				}
			}
			if(filho == null) return null;
			return filho.getIntervalo();
		}
		
		for(Contexto c : filhos){
			Intervalo res = c.getIntervaloEsc(contexto, nivel+1);
			if(res != null){
				return res;
			}
		}
		
		return null;
	}
	
	public Intervalo getIntervalo(byte valor){
		Contexto filho = null;
		if(this.isEsc()) return null;
		for(Contexto c : filhos){
			if(!c.isEsc() && c.getValor() == valor){
				filho = c;
				break;
			}
		}
		if(filho == null) return null;
		return filho.getIntervalo();		
	}
	
	public Intervalo getIntervalo(ArrayList<Byte> contexto, byte valor, int nivel) {

		if(nivel > -1){
			if(isEsc() || (this.getValor() != contexto.get(nivel))) return null;
		}
		
		if(nivel == contexto.size() - 1) {
			Contexto filho = null;
			if(this.isEsc()) return null;
			for(Contexto c : filhos){
				if(!c.isEsc() && c.getValor() == valor){
					filho = c;
					break;
				}
			}
			if(filho == null) return null;
			return filho.getIntervalo();
		}
		
		for(Contexto c : filhos){
			Intervalo res = c.getIntervalo(contexto, valor, nivel+1);
			if(res != null){
				return res;
			}
		}
		
		return null;
	}
	
	private double getProbabilidade() {
		return probabilidade;
	}
	
	public double getProbabilidade(ArrayList<Byte> contexto, int nivel){
		if(nivel > -1){
			if(this.getValor() != contexto.get(nivel)) return -1;
		}
		
		if(nivel == contexto.size() - 1) {
			Contexto filho = null;
			if(this.isEsc()) return -1;
			for(Contexto c : filhos){
				if(c.isEsc()){
					filho = c;
					break;
				}
			}
			if(filho == null) return -1;
			return filho.getProbabilidade();
		}
		
		for(Contexto c : filhos){
			double res = c.getProbabilidade(contexto, nivel+1);
			if(res != -1){
				return res;
			}
		}
		
		return -1;
	}
	
	public double getProbabilidade(ArrayList<Byte> contexto, byte valor, int nivel){
		if(nivel > -1){
			if(this.getValor() != contexto.get(nivel)) return -1;
		}
		
		if(nivel == contexto.size() - 1) {
			Contexto filho = null;
			if(this.isEsc()) return -1;
			for(Contexto c : filhos){
				if(!c.isEsc() && c.getValor() == valor){
					filho = c;
					break;
				}
			}
			if(filho == null) return -1;
			return filho.getProbabilidade();
		}
		
		for(Contexto c : filhos){
			double res = c.getProbabilidade(contexto, valor, nivel+1);
			if(res != -1){
				return res;
			}
		}
		
		return -1;
	}
	
	public void setProbabilidade(double probabilidade) {
		this.probabilidade = probabilidade;
	}

	public int getTotal(){
		int total = 0;
		if(isEsc()) return -1;
		for(Contexto c : filhos){
			total += c.getFrequencia();
		}
		return total;
	}
	
	public Codigo getIntervaloInteiro(){
		return intervaloInteiro;
	}
	
	public Codigo getIntervaloInteiroEsc(){
		Contexto filho = null;
		if(this.isEsc()) return null;
		for(Contexto c : filhos){
			if(c.isEsc()){
				filho = c;
				break;
			}
		}
		if(filho == null) return null;
		return filho.getIntervaloInteiro();		
	}
	
	public Codigo getIntervaloInteiroEsc(ArrayList<Byte> contexto, int nivel) {
		if(nivel > -1){
			if(isEsc() || (this.getValor() != contexto.get(nivel))) return null;
		}
		
		if(nivel == contexto.size() - 1) {
			////System.out.println("Contexto size " + contexto.size() + " "  + filhos.size());
			Contexto filho = null;
			for(Contexto c : filhos){
				if(c.isEsc()){
					filho = c;
					break;
				}
			}
			if(filho == null) return null;
			return filho.getIntervaloInteiro();
		}
		
		for(Contexto c : filhos){
			Codigo res = c.getIntervaloInteiroEsc(contexto, nivel+1);
			if(res != null){
				return res;
			}
		}
		
		return null;
	}
	
	public Codigo getIntervaloInteiro(byte valor){
		Contexto filho = null;
		if(this.isEsc()) return null;
		for(Contexto c : filhos){
			if(!c.isEsc() && c.getValor() == valor){
				filho = c;
				break;
			}
		}
		if(filho == null) return null;
		return filho.getIntervaloInteiro();		
	}
	
	public Codigo getIntervaloInteiro(ArrayList<Byte> contexto, byte valor, int nivel) {

		if(nivel > -1){
			if(isEsc() || (this.getValor() != contexto.get(nivel))) return null;
		}
		
		if(nivel == contexto.size() - 1) {
			Contexto filho = null;
			if(this.isEsc()) return null;
			for(Contexto c : filhos){
				if(!c.isEsc() && c.getValor() == valor){
					filho = c;
					break;
				}
			}
			if(filho == null) return null;
			return filho.getIntervaloInteiro();
		}
		
		for(Contexto c : filhos){
			Codigo res = c.getIntervaloInteiro(contexto, valor, nivel+1);
			if(res != null){
				return res;
			}
		}
		
		return null;
	}
	
	public int getTotalFrequencias(ArrayList<Byte> contexto, int nivel){
		if(nivel > -1){
			if(this.isEsc() || (this.getValor() != contexto.get(nivel))) return -1;
		}
		
		if(nivel == contexto.size() - 1) {
			
			if(this.isEsc()) return -1;
			int contador = 0;
			for(Contexto c : filhos){
				contador += c.getFrequencia();
			}
			return contador;
			
		}
		
		for(Contexto c : filhos){
			int res = c.getTotalFrequencias(contexto, nivel+1);
			if(res != -1){
				return res;
			}
		}
		
		return -1;
	}
	
	public void atualizaIntervaloInteiro(){
		int aux = 0;
		for(Contexto c : filhos){
			c.getIntervaloInteiro().setLow(aux);
			c.getIntervaloInteiro().setHigh(c.getFrequencia() + aux);
			aux += c.getFrequencia();
		}
	}
	
	// To String
	public String toString(){
		return (valor&0xFF) + " " + frequencia + " " + intervalo;
	}
	
	public static Comparator<Contexto> ContextoComparator = new Comparator<Contexto>(){
		public int compare(Contexto inst1,Contexto inst2){
			if(inst1.isEsc()) return 1;
			if(inst2.isEsc()) return -1;
			if(inst1.getFrequencia() == inst2.getFrequencia()){
				return inst1.getValor() - inst2.getValor();
			}else return inst2.getFrequencia() - inst1.getFrequencia();
			
		}
	};
}
