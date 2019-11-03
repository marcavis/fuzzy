package fuzzy;

public class Conjunto {
	public String nome;
	public double suporteMin, suporteMax;
	public double nucleoMin, nucleoMax;
	
	public Conjunto(String nome, double suporteMin, double suporteMax, double nucleoMin, double nucleoMax) {
		super();
		this.nome = nome;
		this.suporteMin = suporteMin;
		this.suporteMax = suporteMax;
		this.nucleoMin = nucleoMin;
		this.nucleoMax = nucleoMax;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getSuporteMin() {
		return suporteMin;
	}

	public void setSuporteMin(double suporteMin) {
		this.suporteMin = suporteMin;
	}

	public double getSuporteMax() {
		return suporteMax;
	}

	public void setSuporteMax(double suporteMax) {
		this.suporteMax = suporteMax;
	}

	public double getNucleoMin() {
		return nucleoMin;
	}

	public void setNucleoMin(double nucleoMin) {
		this.nucleoMin = nucleoMin;
	}

	public double getNucleoMax() {
		return nucleoMax;
	}

	public void setNucleoMax(double nucleoMax) {
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
}
