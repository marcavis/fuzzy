package fuzzy;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;

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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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
//	private Text text_1;
//	private Text text_2;
//	private Text text_3;
//	private Text text_4;
//	private Text text_5;
	
	private ArrayList<Text[]> variaveis;

	private CTabFolder tabFolder;

	private Combo varDestino;

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
		
		MenuItem mntmFechar = new MenuItem(menu_1, SWT.NONE);
		mntmFechar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
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
				System.out.println("want to close");
				MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
				dialog.setText("Confirmação");
				System.out.println(event.item);
				CTabItem text = (CTabItem) event.item;
				dialog.setMessage("Deseja realmente excluir a variável " + text.getText() + "?");
				int retorno = dialog.open();
				event.doit = retorno == 32; //confirmou exclusão
				if(event.doit) {
					atualizarComboVarDestino();
				}
			}
		});
		
		btnNovaVar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(tabFolder.getItemCount() < 8) {
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
		
		Button btnExecutar = new Button(shell, SWT.NONE);
		//btnExecutar.setBounds(633, 548, 161, 47);
		btnExecutar.setText("Executar");

	}
	
	private void atualizarComboVarDestino() {
		String[] nomesVars = new String[tabFolder.getItemCount()];
		for (int i = 0; i < tabFolder.getItemCount(); i++) {
			nomesVars[i] = tabFolder.getItem(i).getText();
		}
		varDestino.setItems(nomesVars);
	}

	public void criarAba(CTabFolder tabFolder, ArrayList<Text[]> variaveis) {
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
		
		GridData layout5Esp = new GridData();
		layout5Esp.horizontalAlignment = GridData.FILL;
		layout5Esp.horizontalSpan = 5;
		nada = new Label(thisComposite, SWT.NONE);
		nada.setLayoutData(layout5Esp);
		
		Button btnHabilitar = new Button(thisComposite, SWT.CHECK);
		btnHabilitar.setLayoutData(layoutBtnDup);
		btnHabilitar.setSelection(true);
		btnHabilitar.setText("Habilitar");
		btnHabilitar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println();
				int[] conjunto3 = new int[] {4, 5, 10, 11};
				for (int i : conjunto3) {
					estaVariavel[i].setVisible(btnHabilitar.getSelection());
				}
			}
		});
		
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
		imagemLayout.heightHint = 300;
		imagemLayout.horizontalSpan = 7;
		imagemLayout.horizontalAlignment = SWT.CENTER;
		lblGrafico.setLayoutData(imagemLayout);
		
		for (int i = 0; i < estaVariavel.length; i++) {
			estaVariavel[i].addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent arg0) {
					try {
						BufferedImage imgGrafVarDestino = geraGrafico(estaVariavel, 
								btnHabilitar.getSelection() ? 3 : 2,
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

	private BufferedImage geraGrafico(Spinner[] dados, int qtConjuntos, String[] nomesConj) {
		BufferedImage res = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
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
		Conjunto[] conjs = new Conjunto[qtConjuntos];
		for(int i = 0; i < qtConjuntos; i++) {
			conjs[i] = new Conjunto(nomesConj[i], 	dados[0 + (i*2)].getSelection(),
													dados[1 + (i*2)].getSelection(),
													dados[6 + (i*2)].getSelection(),
													dados[7 + (i*2)].getSelection());
			System.out.println(conjs[i]);
		}
		
//		for(int i = 0; i < qtConjuntos; i++) {
//			System.out.println("Pertinência para " + (i) + ": " + c.pertinencia(i));
//		}
		
		for(int i = 0; i < 200; i++) {
			for(int j = 0; j < 200; j++) {
				raster.setPixel(10 + i, j, new int[] {i, j, 1});
			}
		}
		
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
}