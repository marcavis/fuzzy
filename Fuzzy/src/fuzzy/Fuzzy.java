package fuzzy;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class Fuzzy {

	protected Display display;
	protected Shell shell;
	
	private ArrayList<Variavel> variaveis = new ArrayList<Variavel>();

	private CTabFolder tabFolder;

	private Combo varDestino;
	private Combo[] regras;
	private int qtRegras = 12;
	private Label labelValor;
	private int destinoMin, destinoMax;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Fuzzy window = new Fuzzy();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		//shell.setSize(978, 677);
		shell.setText("Sistema Fuzzy");
		GridLayout mainGrid = new GridLayout();
		mainGrid.numColumns = 3;
		shell.setLayout(mainGrid);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmarquivo = new MenuItem(menu, SWT.CASCADE);
		mntmarquivo.setText("&Arquivo");
		
		Menu menu_1 = new Menu(mntmarquivo);
		mntmarquivo.setMenu(menu_1);
		
		MenuItem mntmAbrir = new MenuItem(menu_1, SWT.NONE);
		mntmAbrir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setFilterNames(new String[] { "Sistemas difusos (*.fzy)", "Todos os arquivos (*.*)" });
				dialog.setFilterExtensions(new String[] { "*.fzy", "*.*" }); 
				String caminhoAbrir = dialog.open();
				if(caminhoAbrir != null) {
					carregarEstado(dialog.open());
				}
			}
		});
		mntmAbrir.setText("&Abrir");
		
		MenuItem mntmSalvar = new MenuItem(menu_1, SWT.NONE);
		mntmSalvar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterNames(new String[] { "Sistemas difusos (*.fzy)", "Todos os arquivos (*.*)" });
				dialog.setFilterExtensions(new String[] { "*.fzy", "*.*" }); 
			    dialog.setFileName("unknown.fzy");
			    String caminhoSalvar = dialog.open();
			    if(caminhoSalvar != null) {
			    	salvarEstado(caminhoSalvar);
			    }
			}
		});
		mntmSalvar.setText("&Salvar");
		
		MenuItem mntmFechar = new MenuItem(menu_1, SWT.NONE);
		mntmFechar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
				dialog.setText("Confirmação");
				dialog.setMessage("Deseja realmente sair da aplicação?");
				boolean ehPraSair = dialog.open() == 32;
				if(ehPraSair) {
					shell.close();
				}
			}
		});
		mntmFechar.setText("&Fechar");
		
		Button btnNovaVar = new Button(shell, SWT.NONE);
		btnNovaVar.setText("Nova Variável");
		
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		tabFolder = new CTabFolder(shell, SWT.BORDER);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		tabFolder.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			public void close(CTabFolderEvent event) {
				MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
				dialog.setText("Confirmação");
				System.out.println(event.item);
				CTabItem aba = (CTabItem) event.item;
				dialog.setMessage("Deseja realmente excluir a variável " + aba.getText() + "?");
				int retorno = dialog.open();
				event.doit = retorno == 32; //confirmou exclusão
				if(event.doit) {
					removerVariavel(aba);
				}
			}
		});
		
		btnNovaVar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(tabFolder.getItemCount() < 5) {
					criarAba(tabFolder, variaveis);
					atualizarComboVarDestino();
				}
			}
		});
		
		criarAba(tabFolder, variaveis);
		
		Composite compo = new Composite(shell, SWT.NONE);
		GridLayout layoutCompo2 = new GridLayout();
	    layoutCompo2.numColumns = 2;
		compo.setLayout(layoutCompo2);
	    
		Label temp = new Label(compo, SWT.NONE);
		temp.setText("Variável Destino");
		
		varDestino = new Combo(compo, SWT.READ_ONLY);
		atualizarComboVarDestino();
		varDestino.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				atualizarCombosRegras();
			}
		});
		
		GridData layoutBtnDup = new GridData();
		layoutBtnDup.horizontalAlignment = GridData.FILL;
		layoutBtnDup.horizontalSpan = 2;
		
		GridLayout layoutGrupo = new GridLayout();
	    layoutGrupo.numColumns = 4;
		compo.setLayout(layoutCompo2);
		
		Group grpRegras = new Group(compo, SWT.BORDER);
		grpRegras.setText("Regras");
		grpRegras.setLayoutData(layoutBtnDup);
		grpRegras.setLayout(layoutGrupo);
		
		for (Variavel variavel : variaveis) {
			variavel.atualizar();
		}
		
		Label[] l = new Label[4];
		l[0]= new Label(grpRegras, SWT.NONE);
		l[0].setText("Variável 1");
		l[1] = new Label(grpRegras, SWT.NONE);
		l[1].setText("Operador");
		l[2] = new Label(grpRegras, SWT.NONE);
		l[2].setText("Variável 2");
		l[3] = new Label(grpRegras, SWT.NONE);
		l[3].setText("Destino");
		int qtRegras = 12;
		regras = new Combo[qtRegras * 4];
		for(int i = 0; i < qtRegras * 4; i++) {
			regras[i] = new Combo(grpRegras, SWT.READ_ONLY);
			
			regras[i].pack();
		}
		
		atualizarCombosRegras();
		
		Button btnExecutar = new Button(compo, SWT.NONE);
		//btnExecutar.setBounds(633, 548, 161, 47);
		btnExecutar.setText("Executar");
		btnExecutar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(varDestino.getSelectionIndex() < 0) {
					MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
					dialog.setText("Erro");
					dialog.setMessage("Não há variável de destino selecionada.");
					dialog.open();
				} else {
					executarSistema();
				}
			}
		});

	}
	
	public void executarSistema() {
		Shell janExecutar = new Shell(shell);
		janExecutar.setText("Executar");
		GridLayout novaJanGrid = new GridLayout();
		novaJanGrid.numColumns = 3;
		janExecutar.setLayout(novaJanGrid);
		
		GridLayout grpVarsGrid = new GridLayout();
		grpVarsGrid.numColumns = 2;
		Group grpVars = new Group(janExecutar, SWT.BORDER);
		grpVars.setText("Variáveis");
		grpVars.setLayout(grpVarsGrid);
		Spinner[] entradas = new Spinner[variaveis.size()];
		labelValor = null;
		destinoMin = 0;
		destinoMax = 0;
		for(int i = 0; i < variaveis.size(); i++) {
			if(i == varDestino.getSelectionIndex()) {
				Label labelDestino = new Label(janExecutar, SWT.NONE);
				labelDestino.setText(variaveis.get(i).getNome().getText());
				labelValor = new Label(janExecutar, SWT.NONE);
				labelValor.setText("?");
				destinoMin = variaveis.get(i).getUnivMinimo().getSelection();
				destinoMax = variaveis.get(i).getUnivMaximo().getSelection();
			} else {
				Label l = new Label(grpVars, SWT.NONE);
				l.setText(variaveis.get(i).getNome().getText());
				entradas[i] = new Spinner(grpVars, SWT.NONE);
				configuraSpinner(entradas[i]);
				entradas[i].setMinimum(variaveis.get(i).getUnivMinimo().getSelection());
				entradas[i].setMaximum(variaveis.get(i).getUnivMaximo().getSelection());
			}
		}
		
		Button recalcular = new Button(grpVars, SWT.PUSH);
		recalcular.setText("Recalcular");
		recalcular.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println(recalcularResultado(entradas, destinoMin, destinoMax));
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				labelValor.setText(""+df.format(recalcularResultado(entradas, destinoMin, destinoMax)/100));
				labelValor.setSize(300, labelValor.getSize().y);
			}
		});
		janExecutar.open();
		
	}
	
	private double recalcularResultado(Spinner[] entradas, int destinoMin, int destinoMax) {
		double[] pertinencias = new double[regras[0].getItemCount()];
		int p = 0;
		Variavel vD = null;
		for (int i = 0; i < variaveis.size(); i++) {
			if(i == varDestino.getSelectionIndex()) {
				vD = variaveis.get(i);
			} else {
				System.out.println(entradas[i].getSelection());
				int j = 0;
				for (Conjunto c : variaveis.get(i).getConjuntos()) {
					System.out.print(variaveis.get(i).afirmacoes()[j] + ":");
					j++;
					p++;
					pertinencias[p] = c.pertinencia(entradas[i].getSelection());
					System.out.println(pertinencias[p]);
				}
			}
		}
		
		double[] implicacoes = new double[3];
		
		for(int i = 0; i < regras.length; i += 4) {
			int indR1 = regras[i].getSelectionIndex(); //Nada = 0, conjuntos da primeira variável = 1 a 3...
			int indOp = regras[i+1].getSelectionIndex(); //E = 0, Ou = 1
			int indR2 = regras[i+2].getSelectionIndex();
			int indDest = regras[i+3].getSelectionIndex();
			if(indR1 == 0 || indDest == 0) {
				break;
			}
			System.out.println("\nImplicação");
			double novaImplicacao = executarRegra(indR1, indOp, indR2, indDest, pertinencias);
			if(novaImplicacao > implicacoes[indDest - 1]) {
				implicacoes[indDest - 1] = novaImplicacao;
			}
			for (double d : implicacoes) {
				System.out.println(d);
			}
		}
		//double[] pertinenciasFinais = new double[(destinoMax - destinoMin)/100 + 1];
		double somaNumerador = 0.0;
		double somaDenominador = 0.0;
		
		for(int i = destinoMin; i <= destinoMax; i += 100) {
			double estaPertinencia = maiorPertinencia(i, vD, implicacoes);
			somaNumerador += estaPertinencia * i;
			somaDenominador += estaPertinencia;
		}
		if(somaDenominador == 0.0) {
			return 0.0;
		} else {
			return somaNumerador / somaDenominador;
		}
	}

	private double executarRegra(int indR1, int indOp, int indR2, int indDest, double[] pertinencias) {
		if(indR2 == 0) {
			//apenas foi preenchida o primeiro conjunto
			return pertinencias[indR1];
		}
		if(indOp == 0) {
			//E
			return Math.min(pertinencias[indR1], pertinencias[indR2]);
		}
		//OU
		return Math.min(pertinencias[indR1], pertinencias[indR2]);
	}
	
	private double maiorPertinencia(int entrada, Variavel vd, double[] limites) {
		double y1 = Math.min(limites[0], vd.getConjuntos()[0].pertinencia(entrada));
		double y2 = Math.min(limites[1], vd.getConjuntos()[1].pertinencia(entrada));
		double y3 = Math.min(limites[2], vd.getConjuntos()[2].pertinencia(entrada));
		return Math.max(y1, Math.max(y2, y3));
	}
	
	private void atualizarComboVarDestino() {
		String[] nomesVars = new String[tabFolder.getItemCount()];
		for (int i = 0; i < tabFolder.getItemCount(); i++) {
			nomesVars[i] = tabFolder.getItem(i).getText();
		}
		varDestino.setItems(nomesVars);
	}

	public void criarAba(CTabFolder tabFolder, ArrayList<Variavel> variaveis) {
		Spinner[] estaVariavel = new Spinner[12];

		GridLayout layout = new GridLayout();
	    layout.numColumns = 7;
		
		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.CLOSE);
		tbtmNewItem.setText("Var" + (tabFolder.getItemCount()));
		
		Composite thisComposite = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(thisComposite);
		thisComposite.setLayout(layout);
		thisComposite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		
		GridData layoutBtnDup = new GridData();
		layoutBtnDup.horizontalAlignment = GridData.FILL;
		layoutBtnDup.horizontalSpan = 2;
		
		Label lblNomeVar = new Label(thisComposite, SWT.NONE);
		lblNomeVar.setText("Nome da Variável");
		lblNomeVar.setLayoutData(layoutBtnDup);
		
		Text textNomeVar = new Text(thisComposite, SWT.BORDER);
		textNomeVar.setText(tbtmNewItem.getText());
		textNomeVar.setLayoutData(layoutBtnDup);
		textNomeVar.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				Text p = (Text) arg0.getSource();
				if(p.getText().length() > 0) {
					tbtmNewItem.setText(p.getText());
				} else {
					tbtmNewItem.setText("Vazio");
				}
				atualizarComboVarDestino();
			}
		});
		Label nada = new Label(thisComposite, SWT.NONE);
		new Label(thisComposite, SWT.NONE);
		new Label(thisComposite, SWT.NONE);
		
		Label lblUniverso = new Label(thisComposite, SWT.NONE);
		lblUniverso.setText("Valores Possíveis");
		lblUniverso.setLayoutData(layoutBtnDup);
		
		Spinner univMin = new Spinner(thisComposite, SWT.NONE);
		
		univMin.setLayoutData(layoutBtnDup);
		
		Spinner univMax = new Spinner(thisComposite, SWT.NONE);
		univMax.setLayoutData(layoutBtnDup);
		configuraSpinner(univMin);
		configuraSpinner(univMax);
		univMax.setSelection(10000);
		
		
		
		new Label(thisComposite, SWT.NONE);
		new Label(thisComposite, SWT.NONE);
		
		Text lblPouco = new Text(thisComposite, SWT.BORDER);
		lblPouco.setText("Pouco");
		lblPouco.setLayoutData(layoutBtnDup);
		
		Text lblMdio = new Text(thisComposite, SWT.BORDER);
		lblMdio.setText("M\u00E9dio");
		lblMdio.setLayoutData(layoutBtnDup);
		
		Text lblMuito = new Text(thisComposite, SWT.BORDER);
		lblMuito.setText("Muito");
		lblMuito.setLayoutData(layoutBtnDup);
		
		for (Text t : new Text[] {lblPouco, lblMdio, lblMuito}) {
			t.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent arg0) {
					atualizarCombosRegras();
				}
				@Override
				public void focusGained(FocusEvent arg0) {
					
				}
			});
		}
		
		for (Spinner spinner : new Spinner[] {univMin, univMax}) {
			spinner.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent arg0) {
					variavelPai(tbtmNewItem).atualizar();
				}
				@Override
				public void focusGained(FocusEvent arg0) {
					
				}
			});
		}

		new Label(thisComposite, SWT.NONE);
		nada = new Label(thisComposite, SWT.NONE);
		nada.setText("Mínimo");
		nada = new Label(thisComposite, SWT.NONE);
		nada.setText("Máximo");
		nada = new Label(thisComposite, SWT.NONE);
		nada.setText("Mínimo");
		nada = new Label(thisComposite, SWT.NONE);
		nada.setText("Máximo");
		nada = new Label(thisComposite, SWT.NONE);
		nada.setText("Mínimo");
		nada = new Label(thisComposite, SWT.NONE);
		nada.setText("Máximo");
		
		Label lblSup = new Label(thisComposite, SWT.NONE);
		lblSup.setText("Suporte");
		lblSup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		
		for (int i = 0; i < estaVariavel.length; i++) {
			if(i == 6) {
				Label lblNuc = new Label(thisComposite, SWT.NONE);
				lblNuc.setText("Núcleo");
				lblNuc.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
			}
			estaVariavel[i] = new Spinner(thisComposite, SWT.NONE);
			estaVariavel[i].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					Spinner s = (Spinner) e.getSource();
					configuraMaximosSpinner(s, univMin.getSelection(), univMax.getSelection());
				}
			});
			
			configuraSpinner(estaVariavel[i]);
			
		}		
		CLabel lblGrafico = new CLabel(thisComposite, SWT.BORDER);
		GridData imagemLayout = new GridData(); 
		imagemLayout.widthHint = 400;
		imagemLayout.heightHint = 320;
		imagemLayout.horizontalSpan = 7;
		imagemLayout.horizontalAlignment = SWT.CENTER;
		lblGrafico.setLayoutData(imagemLayout);
		
		for (int i = 0; i < estaVariavel.length; i++) {
			estaVariavel[i].addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent arg0) {
					try {
						variavelPai(tbtmNewItem).atualizar();
						BufferedImage imgGrafVarDestino = geraGrafico(estaVariavel,
								new String[] {lblPouco.getText(), lblMdio.getText(), lblMuito.getText()});
						ImageIO.write(imgGrafVarDestino, "bmp", new File("temp/_graficovd.bmp"));
						Image graficoVarDestino = new Image(null, "temp/_graficovd.bmp");
						carregaImagem(lblGrafico, graficoVarDestino);
						
					} catch (Exception f) {
						f.printStackTrace();
					}
				}
				@Override
				public void focusGained(FocusEvent arg0) {
					
				}
			});
		}
		Text[] nomesConj = new Text[] {lblPouco, lblMdio, lblMuito};
		variaveis.add(new Variavel(tbtmNewItem, textNomeVar, estaVariavel, nomesConj, univMin, univMax));
		
		if(regras != null) {
			atualizarCombosRegras();
		}
	}
	
	private Variavel variavelPai(CTabItem item) {
		int indice = -1;
		for(int i = 0; i < variaveis.size(); i++) {
			if(variaveis.get(i).getAba() == item) {
				indice = i;
			}
		}
		if (indice > -1) {
			return variaveis.get(indice);
		} else {
			return null;
		}
	}
	
	private void removerVariavel(CTabItem item) {
		int indice = -1;
		for(int i = 0; i < variaveis.size(); i++) {
			if(variaveis.get(i).getAba() == item) {
				indice = i;
			}
		}
		if (indice > -1) {
			variaveis.remove(indice);
			atualizarComboVarDestino();
		}
	}
	
	private void configuraSpinner(Spinner s) {
		s.setSelection(0);
		s.setDigits(2);
		s.setMinimum(-100000);
		s.setMaximum(100000);
		s.setIncrement(100);
		s.setPageIncrement(500);
	}
	
	private void configuraMaximosSpinner(Spinner s, int minimo, int maximo) {
		s.setMinimum(minimo);
		s.setMaximum(maximo);
	}

	private String[] popularComboVarNormal() {
		//afirmações como "Var 1 é pouco"
		ArrayList<String> afirmacoes = new ArrayList<String>();
		afirmacoes.add("Nenhum Conjunto");
		for (Variavel v : variaveis) {
			if (v.getAba().getText() != varDestino.getText()) {
				afirmacoes.addAll(new ArrayList<String>(Arrays.asList(v.afirmacoes())));
			}
		}
		String[] result = new String[afirmacoes.size()];
		for (int i = 0; i < afirmacoes.size(); i++) {
			result[i] = afirmacoes.get(i);
		}
		return result;
	}
	
	private String[] popularComboVarDestino() {
		//afirmações como "Var 1 é pouco"
		ArrayList<String> afirmacoes = new ArrayList<String>();
		afirmacoes.add("Nenhum Conjunto");
		for (Variavel v : variaveis) {
			if (v.getAba().getText() == varDestino.getText()) {
				afirmacoes.addAll(new ArrayList<String>(Arrays.asList(v.afirmacoes())));
			}
		}
		String[] result = new String[afirmacoes.size()];
		for (int i = 0; i < afirmacoes.size(); i++) {
			result[i] = afirmacoes.get(i);
		}
		return result;
	}
	
	private void atualizarCombosRegras() {
		for(int i = 0; i < qtRegras * 4; i++) {
			if (i % 2 == 0) {
				//variáveis de entrada
				//regras[i].setItems(new String[] {"Nada"});
				regras[i].setItems(popularComboVarNormal());
				regras[i].select(0);
			} else if (i % 4 == 1) {
				regras[i].setItems(new String[] {"E", "Ou"});
				regras[i].select(0);
			} else {
				//variável destino
				//regras[i].setItems(new String[] {"Nada"});
				regras[i].setItems(popularComboVarDestino());
				regras[i].select(0);
			}
			regras[i].pack();
		}
	}
	
	private BufferedImage geraGrafico(Spinner[] dados, String[] nomesConj) {
		BufferedImage res = new BufferedImage(420, 320, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = res.getRaster();
		
		try {
			res.setData(raster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private void carregaImagem(CLabel label, Image img) {
		label.setBackground(img);
		label.setBounds(
				label.getBounds().x,
				label.getBounds().y,
				img.getImageData().width,
				img.getImageData().height
				);
	}
	
	private boolean salvarEstado(String caminho) {
		//é preciso garantir que haverá uma variável no arquivo salvo,
		//pois a rotina de carregar arquivos não pode deletar todas as abas
		if (variaveis.size() == 0) {
			MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			dialog.setText("Erro");
			dialog.setMessage("Erro ao salvar - não há variáveis para serem salvas!");
			dialog.open();
			return false;
		}
		String saida = "";
		for (Variavel v : variaveis) {
			saida += "nomeVar:" + v.getNome().getText() + "\n";
			saida += "univ:" + v.getUnivMinimo().getSelection() + ":" + v.getUnivMaximo().getSelection() + "\n";
			for (Text t : v.getNomesConjuntos()) {
				saida += "grupo:" + t.getText() + "\n";
			}
			saida += "suporte1:" + v.getValores()[0].getSelection() + ":" + v.getValores()[1].getSelection() + "\n";
			saida += "suporte2:" + v.getValores()[2].getSelection() + ":" + v.getValores()[3].getSelection() + "\n";
			saida += "suporte3:" + v.getValores()[4].getSelection() + ":" + v.getValores()[5].getSelection() + "\n";
			saida += "nucleo1:" + v.getValores()[6].getSelection() + ":" + v.getValores()[7].getSelection() + "\n";
			saida += "nucleo2:" + v.getValores()[8].getSelection() + ":" + v.getValores()[9].getSelection() + "\n";
			saida += "nucleo3:" + v.getValores()[10].getSelection() + ":" + v.getValores()[11].getSelection() + "\n";
			saida += "fimvar\n";
		}
		saida += "fimvars\n";
		saida += "destino:" + varDestino.getSelectionIndex() + "\n";
		for (int i = 0; i < regras.length; i += 4) {
			saida += "regra:" + regras[i].getSelectionIndex() + ":" + regras[i+1].getSelectionIndex() + ":";
			saida += regras[i+2].getSelectionIndex() + ":" + regras[i+3].getSelectionIndex() + "\n";
		}
		
		try (PrintWriter out = new PrintWriter(caminho)) {
		    out.println(saida);
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao salvar " + caminho);
			return false;
		}
		return true;
	}
	
	private boolean carregarEstado(String caminho) {
		System.out.println(tabFolder.getItems());
		while (tabFolder.getItemCount() > 1) {
			removerVariavel(tabFolder.getItem(0));
			tabFolder.getItem(0).dispose();
		}
		List<String> lines = new ArrayList<String>();
		try {
			//URI uri = this.getClass().getResource(fileName).toURI();
			lines = Files.readAllLines(Paths.get(caminho));
		} catch (Exception e) {
	    	e.printStackTrace();
		}
		
		int cursor = 0;
		int qtVars = 0;
		//primeira passagem para verificar quantar variaveis criar
		while(!lines.get(cursor).equals("fimvars")) {
			if(lines.get(cursor).equals("fimvar")) {
				qtVars++;
				if(qtVars > 1) {
					criarAba(tabFolder, variaveis);
				}
			}
			cursor++;
		}
		
		cursor = 0;
		int indiceVar = 0;
		while(!lines.get(cursor).equals("fimvars")) {
			Variavel v = variaveis.get(indiceVar);
			v.getNome().setText(lines.get(cursor).split(":")[1]);
			cursor++;
			v.getUnivMinimo().setSelection(Integer.parseInt(lines.get(cursor).split(":")[1]));
			v.getUnivMaximo().setSelection(Integer.parseInt(lines.get(cursor).split(":")[2]));
			cursor++;
			v.getNomesConjuntos()[0].setText(lines.get(cursor).split(":")[1]);
			v.getNomesConjuntos()[1].setText(lines.get(cursor + 1).split(":")[1]);
			v.getNomesConjuntos()[2].setText(lines.get(cursor + 2).split(":")[1]);
			cursor += 3;
			for(int j = 0; j < v.getValores().length; j += 2) {
				v.getValores()[j].setSelection(Integer.parseInt(lines.get(cursor).split(":")[1]));
				v.getValores()[j+1].setSelection(Integer.parseInt(lines.get(cursor).split(":")[2]));
				cursor++;
			}
			//ignorar o fimvar
			cursor++;
			indiceVar++;
		}
		cursor++;
		int indiceVarDestino = Integer.parseInt(lines.get(cursor).split(":")[1]);
		if (indiceVarDestino >= 0) {
			varDestino.select(indiceVarDestino);
		}
		cursor++;
		for (int i = 0; i < regras.length; i += 4) {
			regras[i].select(Integer.parseInt(lines.get(cursor).split(":")[1]));
			regras[i+1].select(Integer.parseInt(lines.get(cursor).split(":")[2]));
			regras[i+2].select(Integer.parseInt(lines.get(cursor).split(":")[3]));
			regras[i+3].select(Integer.parseInt(lines.get(cursor).split(":")[4]));
			cursor++;
		}
		return true;
	}
}