package projetoiti;

import java.util.ArrayList;

public class TabelaPPM {
	private Contexto km1;
	private Contexto k0;
	private ArrayList<Contexto> k1;
	private ArrayList<Contexto> k2;
	private ArrayList<Character> alfabeto;
	
	// Construtores
	
	/* Construtor n�o vazio
	 * Passar a lista com os caracteres do alfabeto na forma de string */
	public TabelaPPM(ArrayList<Character> alfabeto){
		this.alfabeto = alfabeto;
		km1 = new Contexto("");
		k0 = new Contexto("");
		k1 = new ArrayList<Contexto>();
		k2 = new ArrayList<Contexto>();
		for(Character i : alfabeto){
			km1.addOcorrencia("" + i);
			km1.removeInstancia("ESC");
		}
	}
	
	/* Construtor vazio */
	public TabelaPPM(){
		km1 = new Contexto("");
		k0 = new Contexto("");
		k1 = new ArrayList<Contexto>();
		k2 = new ArrayList<Contexto>();
	}
	
	// Busca de Contexto
	public Contexto getContextoEmK1(String contexto){
		for(Contexto c : k1){
			if(c.getInstancia().equals(contexto)){
				return c;
			}
		}
		return null;
	}
	
	public Contexto getContextoEmK2(String contexto){
		for(Contexto c : k2){
			if(c.getInstancia().equals(contexto)){
				return c;
			}
		}
		return null;
	}
	
	// Gerando a Tabela
	public ArrayList<Intervalo> geraIntervalos(String arquivo){
		ArrayList<Intervalo> codigo = new ArrayList<Intervalo>();		
		Leitor leitor = new Leitor(arquivo);
		String lido, cpre = "", cprepre = "";
		Contexto caux;
		boolean temRemocaoEmK1 = false, temRemocaoEmK0 = false;
		boolean inserido = false;
		
		// La�o de Leitura do arquivo
		while(!(lido = "" + leitor.getNextCharacter()).equals("�")){
			caux = getContextoEmK2(cprepre + cpre);
			if(caux != null){
				//H� contexto K2 cprepre cpre
				if(caux.temIndice(lido)){
					codigo.add(Intervalo.copy(caux.getInstancia(lido).getIntervalo()));
					inserido = true;
					caux.addOcorrencia(lido);
				} else {
					if(caux.temIndice("ESC")){
						codigo.add(Intervalo.copy(caux.getInstancia("ESC").getIntervalo()));
					}
					//Fazer a remo��o tempor�ria do povim
					Contexto caux2 = getContextoEmK1(cpre);
					Contexto copia = Contexto.copy(caux2);
					for(Instancia i : caux2.v){
						if(!i.getSymbol().equals("ESC")) copia.removeInstancia(i.getSymbol());
					}
					temRemocaoEmK1 = true;
					caux.addOcorrencia(lido);
					//Remove ESC caso o alfabeto esteja completo
					if(caux2.v.size() > alfabeto.size()){
						caux2.removeInstancia("ESC");
					}
					caux = copia;
				}
			} else if(!cprepre.equals("")){
				//N�o h� contexto K2, mas h� cprepre
				//Portanto deve ser adicionado
				Contexto caux2 = new Contexto(cprepre + cpre);
				k2.add(caux2);
				caux2.addOcorrencia(lido);
				//Remove ESC caso o alfabeto esteja completo
				if(caux2.v.size() > alfabeto.size()){
					caux2.removeInstancia("ESC");
				}
			}
			
			if(!temRemocaoEmK1) caux = getContextoEmK1(cpre); //Esta linha ser� modificada com a remo��o
			if(caux != null){
				//H� contexto K1 cpre
				if(caux.temIndice(lido)){
					if(!inserido){
						codigo.add(Intervalo.copy(caux.getInstancia(lido).getIntervalo()));
						inserido = true;
					}
					caux.addOcorrencia(lido);
					//Remove ESC caso o alfabeto esteja completo
					if(caux.v.size() > alfabeto.size()){
						caux.removeInstancia("ESC");
					}
				} else {
					if(caux.temIndice("ESC")){
						if(!inserido) codigo.add(Intervalo.copy(caux.getInstancia("ESC").getIntervalo()));
					}
					//Fazer a remo��o tempor�ria do povim
					Contexto copia = Contexto.copy(k0);
					for(Instancia i : caux.v){
						if(!i.getSymbol().equals("ESC")) copia.removeInstancia(i.getSymbol());
					}
					temRemocaoEmK0 = true;
					caux.addOcorrencia(lido);
					//Remove ESC caso o alfabeto esteja completo
					if(caux.v.size() > alfabeto.size()){
						caux.removeInstancia("ESC");
					}
					caux = copia;
				}
			} else if(!cpre.equals("")){
				//N�o h� contexto K1, mas h� cpre
				//Portanto deve ser adicionado
				Contexto caux2 = new Contexto(cpre);
				k1.add(caux2);
				caux2.addOcorrencia(lido);
				//Remove ESC caso o alfabeto esteja completo
				if(caux2.v.size() > alfabeto.size()){
					caux2.removeInstancia("ESC");
				}
			}
						
			//O k0 ser� modificado com a remo��o
			if(!temRemocaoEmK0) caux = k0;
			if(caux.temIndice(lido)){				
				if(!inserido) codigo.add(Intervalo.copy(caux.getInstancia(lido).getIntervalo()));
			} else {
				if(caux.temIndice("ESC")){
					if(!inserido) codigo.add(Intervalo.copy(caux.getInstancia("ESC").getIntervalo()));
				}
				//Apenas pega o c�digo se o K = 0 original n�o tem
				if(!k0.temIndice(lido)){
					if(!inserido) codigo.add(Intervalo.copy(km1.getInstancia(lido).getIntervalo()));
					km1.removeInstancia(lido);
				}
			}
			k0.addOcorrencia(lido);
			//Remo��o de ESC caso j� tenha todo o alfabeto
			if(k0.v.size() > alfabeto.size()){
				k0.removeInstancia("ESC");
			}
			
			//Atualiza��o de vari�veis
			temRemocaoEmK1 = false;
			temRemocaoEmK0 = false;
			inserido = false;
			cprepre = cpre;
			cpre = lido;
		}
		
		return codigo;
	}
	
	// Getters e Setters
	
	public Contexto getkm1() {
		return km1;
	}

	public void setkm1(Contexto km1) {
		this.km1 = km1;
	}

	public Contexto getK0() {
		return k0;
	}

	public void setK0(Contexto k0) {
		this.k0 = k0;
	}

	public ArrayList<Contexto> getK1() {
		return k1;
	}

	public void setK1(ArrayList<Contexto> k1) {
		this.k1 = k1;
	}

	public ArrayList<Contexto> getK2() {
		return k2;
	}

	public void setK2(ArrayList<Contexto> k2) {
		this.k2 = k2;
	}
	
	// To String
	public String toString(){
		String tabela = "********** k = -1 **********\n";
		for(Instancia i : this.getkm1().v){
			tabela = tabela + i.getSymbol() + " " + i.getFrequencia() + " " + i.getProbabilidade() + "\n";
		}
		tabela = tabela + "********** k = 0 **********\n";
		for(Instancia i : this.getK0().v){
			tabela = tabela + i.getSymbol() + " " + i.getFrequencia() + " " + i.getProbabilidade() + "\n";
		}
		tabela = tabela + "********** k = 1 **********\n";
		for(Contexto c : this.getK1()){
			tabela = tabela + "********** " + c.getInstancia() + " **********\n";
			for(Instancia i : c.v){
				tabela = tabela + i.getSymbol() + " " + i.getFrequencia() + " " + i.getProbabilidade() + "\n";
			}
		}
		tabela = tabela + "********** k = 2 **********\n";
		for(Contexto c : this.getK2()){
			tabela = tabela + "********** " + c.getInstancia() + " **********\n";
			for(Instancia i : c.v){
				tabela = tabela + i.getSymbol() + " " + i.getFrequencia() + " " + i.getProbabilidade() + '\n';
			}
		}
		return tabela;
	}
	
}
