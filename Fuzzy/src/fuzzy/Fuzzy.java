package fuzzy;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
		shell.setText("SWT Application");
		GridLayout mainGrid = new GridLayout();
		mainGrid.numColumns = 3;
		shell.setLayout(mainGrid);
		
//		Menu menu = new Menu(shell, SWT.BAR);
//		shell.setMenuBar(menu);
//		
//		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.NONE);
//		mntmNewItem_1.setText("New Item");
//		
//		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
//		mntmNewItem.setText("New Item");
//		
//		MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
//		menuItem.setText("New SubMenu");
//		
//		Menu menu_1 = new Menu(menuItem);
//		menuItem.setMenu(menu_1);
		
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
				//shell.close();
			}
		});
		mntmAbrir.setText("&Abrir");
		
		MenuItem mntmSalvar = new MenuItem(menu_1, SWT.NONE);
		mntmSalvar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterNames(new String[] { "Sistemas difusos (*.fzy)", "Todos os arquivos (*.*)" });
				dialog.setFilterExtensions(new String[] { "*.fzy", "*.*" }); // Windows
			                                    // wild
			                                    // cards
			    //dialog.setFilterPath("c:\\"); // Windows path
				
			    dialog.setFileName("unknown.fzy");
			    String caminhoSalvar = dialog.open();
			    salvarEstado(caminhoSalvar);
			}
		});
		mntmSalvar.setText("&Salvar");
		
		MenuItem mntmFechar = new MenuItem(menu_1, SWT.NONE);
		mntmFechar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
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
					atualizarComboVarDestino();
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
		
//		Table tabela = new Table(compo, SWT.NONE);
//		tabela.setHeaderVisible(true);
//		tabela.setLinesVisible(true);
//		String[] nomesColunas = new String[] {"Variável 1", "Operador", "Variável 2", "Resposta"};
//		for (int i = 0; i < nomesColunas.length; i++) {
//			TableColumn column = new TableColumn(tabela, SWT.NULL);
//			column.setText(nomesColunas[i]);
//		}
		
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
			//System.out.println(regras[i]);
		}
		
		atualizarCombosRegras();
		
		Button btnExecutar = new Button(compo, SWT.NONE);
		//btnExecutar.setBounds(633, 548, 161, 47);
		btnExecutar.setText("Executar");
		btnExecutar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(variaveis.get(0));
				
			}
		});

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
		
		for (Spinner spinner : new Spinner[] {univMin, univMax}) {
			spinner.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent arg0) {
					variavelPai(tbtmNewItem).atualizar();
				}
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					
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
					// TODO Auto-generated method stub
					
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
		afirmacoes.add("Nada");
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
		afirmacoes.add("Nada");
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
		
//		int[] valores = new int[dados.length];
//		for (int i = 0; i < dados.length; i++) {
//			System.out.println(dados[i].getSelection());
//			String str = dados[i].getText();
//			//System.out.println(text.getText() + "?");
//			try	{
//				valores[i] = Double.parseDouble(str);
//			}
//			catch(NumberFormatException e) {
//				valores[i] = 0.0;
//			}
//		}
//		Conjunto[] conjs = new Conjunto[qtConjuntos];
//		for(int i = 0; i < qtConjuntos; i++) {
//			//o construtor de conjunto força que o núcleo esteja contido no suporte
//			conjs[i] = new Conjunto(nomesConj[i], 	dados[0 + (i*2)].getSelection(),
//													dados[1 + (i*2)].getSelection(),
//													dados[6 + (i*2)].getSelection(),
//													dados[7 + (i*2)].getSelection());
//			System.out.println(conjs[i]);
//		}
//		System.out.println();
		
//		for(int i = 0; i < 10000; i+= 100) {
//			
//			System.out.println("Pertinência para " + (double) (i)/100 + ": " + conjs[0].pertinencia(i));
//		}
		
//		for(int i = 0; i < qtConjuntos; i++) {
//			System.out.println("Pertinência para " + (i) + ": " + conjs[0].pertinencia(i));
//		}
		
//		for(int i = 0; i < 400; i++) {
//			//System.out.println(i + ", " + (int) Math.round(300*conjs[0].pertinencia(i)));
//			int altura = 310 - (int) Math.round(300*conjs[0].pertinencia(i*25));
//			raster.setPixel(10 + i, altura, new int[] {255, 255, 0});
//			raster.setPixel(10 + i, altura-1, new int[] {255, 255, 0});
//			raster.setPixel(10 + i, altura-2, new int[] {255, 255, 0});
//		}
		
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
		String saida = "";
		for (Variavel v : variaveis) {
			saida += "nomeVar:" + v.getNome() + "\n";
			saida += "univ:" + v.getUnivMinimo() + ":" + v.getUnivMaximo() + "\n";
//			saida += "temGrupo3:" + v.getAba()
		}
		return true;
	}
}