package fuzzy;

public class Variavel {
	public String nome;
	public Conjunto[] conjuntos;
	
	public double[] pertinencias(double entrada) {
		double[] resultado = new double[conjuntos.length];
		for (int i = 0; i < conjuntos.length; i++) {
			resultado[i] = conjuntos[i].pertinencia(entrada);
		}
		return resultado;
	}
}
