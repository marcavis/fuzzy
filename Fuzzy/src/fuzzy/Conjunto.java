package fuzzy;

import java.util.Arrays;

public class Conjunto {
	public String nome;
	public int suporteMin, suporteMax;
	public int nucleoMin, nucleoMax;
	
	public Conjunto(String nome, int suporteMin, int suporteMax, int nucleoMin, int nucleoMax, int univMin, int univMax) {
		super();
		this.nome = nome;
		int _suporteMin = Math.min(Math.max(univMin, suporteMin), univMax);
		int _suporteMax = Math.min(Math.max(univMin, suporteMax), univMax);
		int _nucleoMin = Math.min(Math.max(univMin, nucleoMin), univMax);
		int _nucleoMax = Math.min(Math.max(univMin, nucleoMax), univMax);
		int[] valores = new int[] {_suporteMin, _suporteMax, _nucleoMin, _nucleoMax};
		Arrays.sort(valores);
		this.suporteMin = valores[0];
		this.nucleoMin = valores[1];
		this.nucleoMax = valores[2];
		this.suporteMax = valores[3];
		
//		this.suporteMin = Math.min(suporteMin, suporteMax);
//		this.suporteMax = Math.max(suporteMin, suporteMax);
//		if (nucleoMin < this.suporteMin) {
//			this.nucleoMin = this.suporteMin;
//		} else {
//			this.nucleoMin = nucleoMin;
//		}
//		if (nucleoMax > this.suporteMax) {
//			this.nucleoMax = this.suporteMax;
//		} else {
//			this.nucleoMax = nucleoMax;
//		}
//		
//		if(this.nucleoMin > this.nucleoMax) {
//			int aux = this.nucleoMin;
//			this.nucleoMin = this.nucleoMax;
//			this.nucleoMax = aux;
//		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getSuporteMin() {
		return suporteMin;
	}

	public void setSuporteMin(int suporteMin) {
		this.suporteMin = suporteMin;
	}

	public int getSuporteMax() {
		return suporteMax;
	}

	public void setSuporteMax(int suporteMax) {
		this.suporteMax = suporteMax;
	}

	public int getNucleoMin() {
		return nucleoMin;
	}

	public void setNucleoMin(int nucleoMin) {
		this.nucleoMin = nucleoMin;
	}

	public int getNucleoMax() {
		return nucleoMax;
	}

	public void setNucleoMax(int nucleoMax) {
		this.nucleoMax = nucleoMax;
	}

	public double pertinencia(double entrada) {
		if(entrada < suporteMin) {
			return 0.0;
		}
		if(entrada >= suporteMin && entrada < nucleoMin) {
			return (entrada - suporteMin) / (nucleoMin - suporteMin);
		}
		if(entrada >= nucleoMin && entrada <= nucleoMax) {
			return 1.0;
		}
		if(entrada > nucleoMax && entrada <= suporteMax) {
			return (suporteMax - entrada) / (suporteMax - nucleoMax);
		}
		else { //entrada > suporteMax
			return 0.0;
		}
	}

	@Override
	public String toString() {
		return "Conjunto [nome=" + nome + ", suporteMin=" + suporteMin/100.0 + ", suporteMax=" + suporteMax/100.0 + ", nucleoMin="
				+ nucleoMin/100.0 + ", nucleoMax=" + nucleoMax/100.0 + "]";
	}
	
	
}
