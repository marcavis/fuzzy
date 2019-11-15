package fuzzy;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class Variavel {
	private CTabItem aba;
	private Text nome;
	private Conjunto[] conjuntos;
	private Spinner[] valores;
	private Text[] nomesConjuntos;
	private Spinner univMinimo;
	private Spinner univMaximo;
	
	public Variavel(CTabItem aba, Text nome, Spinner[] valores, Text[] nomesConjuntos, Spinner univMinimo,
			Spinner univMaximo) {
		super();
		this.aba = aba;
		this.nome = nome;
		this.valores = valores;
		this.nomesConjuntos = nomesConjuntos;
		this.univMinimo = univMinimo;
		this.univMaximo = univMaximo;
	}

	public CTabItem getAba() {
		return aba;
	}

	public void setAba(CTabItem aba) {
		this.aba = aba;
	}

	public Text getNome() {
		return nome;
	}

	public void setNome(Text nome) {
		this.nome = nome;
	}

	public Spinner[] getValores() {
		return valores;
	}

	public void setValores(Spinner[] valores) {
		this.valores = valores;
	}

	public Text[] getNomesConjuntos() {
		return nomesConjuntos;
	}

	public void setNomesConjuntos(Text[] nomesConjuntos) {
		this.nomesConjuntos = nomesConjuntos;
	}

	public Spinner getUnivMinimo() {
		return univMinimo;
	}

	public void setUnivMinimo(Spinner univMinimo) {
		this.univMinimo = univMinimo;
	}

	public Spinner getUnivMaximo() {
		return univMaximo;
	}

	public void setUnivMaximo(Spinner univMaximo) {
		this.univMaximo = univMaximo;
	}

	@Override
	public String toString() {
		String result = "Variavel " + nome.getText();
		result += " : Universo de " + univMinimo.getSelection()/100.0 + " a " + univMaximo.getSelection()/100.0;
		result += "\n" + mostraConj(nomesConjuntos[0], valores[0], valores[1], valores[6], valores[7]);
		result += "\n" + mostraConj(nomesConjuntos[1], valores[2], valores[3], valores[8], valores[9]);
		result += "\n" + mostraConj(nomesConjuntos[2], valores[4], valores[5], valores[10], valores[11]);
		return result;
		//return "Variavel [aba=" + aba + ", nome=" + nome + ", valores=" + Arrays.toString(valores) + ", nomesConjuntos="
		//		+ Arrays.toString(nomesConjuntos) + ", univMinimo=" + univMinimo + ", univMaximo=" + univMaximo + "]";
	}

	public String mostraConj(Text nome, Spinner sup1, Spinner sup2, Spinner nuc1, Spinner nuc2) {
		String result = nome.getText() + ": Suporte " + sup1.getSelection()/100.0 + " a " + sup2.getSelection()/100.0;
		result += ", Núcleo " + nuc1.getSelection()/100.0 + " a " + nuc2.getSelection()/100.0;
		return result;
	}
	
	public void atualizar(int qtConjuntos) {
		conjuntos = new Conjunto[qtConjuntos];
		for(int i = 0; i < qtConjuntos; i++) {
			//o construtor de conjunto força que o núcleo esteja contido no suporte
			conjuntos[i] = new Conjunto(nomesConjuntos[i].getText(), 	valores[0 + (i*2)].getSelection(),
					valores[1 + (i*2)].getSelection(),
					valores[6 + (i*2)].getSelection(),
					valores[7 + (i*2)].getSelection(),
					univMinimo.getSelection(),
					univMaximo.getSelection());
			//System.out.println(conjuntos[i]);
			
			//alterar os spinners para trazer o valor que foi usado na criação do conjunto
			valores[0 + (i*2)].setSelection(conjuntos[i].getSuporteMin());
			valores[1 + (i*2)].setSelection(conjuntos[i].getSuporteMax());
			valores[6 + (i*2)].setSelection(conjuntos[i].getNucleoMin());
			valores[7 + (i*2)].setSelection(conjuntos[i].getNucleoMax());
		}
		//System.out.println();
	}
	

//	public double[] pertinencias(double entrada) {
//		double[] resultado = new double[conjuntos.length];
//		for (int i = 0; i < conjuntos.length; i++) {
//			resultado[i] = conjuntos[i].pertinencia(entrada);
//		}
//		return resultado;
//	}
}
