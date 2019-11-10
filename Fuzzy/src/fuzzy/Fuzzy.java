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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Fuzzy {

	protected Shell shell;
//	private Text text_1;
//	private Text text_2;
//	private Text text_3;
//	private Text text_4;
//	private Text text_5;
	
	private Text text_nomeVarDestino;
	private ArrayList<Text[]> variaveis;
	private Text[] varDestino;

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
		shell.setSize(978, 677);
		shell.setText("SWT Application");
		
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
		
		MenuItem mntmFechar= new MenuItem(menu_1, SWT.NONE);
		mntmFechar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		mntmFechar.setText("&Fechar");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 960, 605);
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setBounds(0, 28, 479, 577);
		
		CTabFolder tabFolder = new CTabFolder(composite_1, SWT.BORDER);
		tabFolder.setBounds(10, 10, 469, 567);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
		      public void close(CTabFolderEvent event) {
		        System.out.println("want to close");
		        MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
		        dialog.setText("Confirmação");
		        System.out.println(event.item);
		        CTabItem text = (CTabItem) event.item;
		        dialog.setMessage("Deseja realmente excluir a variável " + text.getText() + "?");
		        int retorno = dialog.open();
		        event.doit = retorno == 32;
//		        if (event.item.equals(specialItem)) {
//		          event.doit = false;
//		        }
		      }
		    });
		
		criarAba(tabFolder, variaveis);
		
		varDestino = new Text[12];
		
//		Composite composite_2 = new Composite(composite, SWT.NONE);
//		composite_2.setBounds(485, 28, 475, 514);
//		
//		Group group_1 = new Group(composite_2, SWT.NONE);
//		group_1.setBounds(10, 46, 443, 177);
//		
//		Label label = new Label(group_1, SWT.BORDER);
//		label.setText("Suporte");
//		label.setBounds(10, 42, 80, 20);
//		
//		Label label_1 = new Label(group_1, SWT.BORDER);
//		label_1.setText("Núcleo");
//		label_1.setBounds(10, 84, 80, 20);
//		
//		varDestino[0] = new Text(group_1, SWT.BORDER);
//		varDestino[0].setBounds(96, 42, 42, 26);
//		
//		varDestino[1] = new Text(group_1, SWT.BORDER);
//		varDestino[1].setBounds(146, 42, 42, 26);
//		
//		varDestino[2] = new Text(group_1, SWT.BORDER);
//		varDestino[2].setBounds(96, 82, 42, 26);
//		
//		varDestino[3] = new Text(group_1, SWT.BORDER);
//		varDestino[3].setBounds(146, 82, 42, 26);
//		
//		Label label_2 = new Label(group_1, SWT.BORDER);
//		label_2.setText("Pouco");
//		label_2.setBounds(120, 16, 50, 20);
//		
//		Label label_3 = new Label(group_1, SWT.BORDER);
//		label_3.setText("M\u00E9dio");
//		label_3.setBounds(240, 16, 50, 20);
//		
//		varDestino[4] = new Text(group_1, SWT.BORDER);
//		varDestino[4].setBounds(216, 42, 42, 26);
//		
//		varDestino[5] = new Text(group_1, SWT.BORDER);
//		varDestino[5].setBounds(266, 42, 42, 26);
//		
//		varDestino[6] = new Text(group_1, SWT.BORDER);
//		varDestino[6].setBounds(216, 82, 42, 26);
//		
//		varDestino[7] = new Text(group_1, SWT.BORDER);
//		varDestino[7].setBounds(266, 82, 42, 26);
//		
//		Label label_4 = new Label(group_1, SWT.BORDER);
//		label_4.setText("Muito");
//		label_4.setBounds(360, 16, 50, 20);
//		
//		varDestino[8] = new Text(group_1, SWT.BORDER);
//		varDestino[8].setBounds(336, 42, 42, 26);
//		
//		varDestino[9] = new Text(group_1, SWT.BORDER);
//		varDestino[9].setBounds(386, 42, 42, 26);
//		
//		varDestino[10] = new Text(group_1, SWT.BORDER);
//		varDestino[10].setBounds(336, 82, 42, 26);
//		
//		varDestino[11] = new Text(group_1, SWT.BORDER);
//		varDestino[11].setBounds(386, 82, 42, 26);
//		
//		Label lblNewLabel_1 = new Label(composite_2, SWT.BORDER);
//		lblNewLabel_1.setAlignment(SWT.CENTER);
//		lblNewLabel_1.setBounds(10, 18, 180, 22);
//		lblNewLabel_1.setText("Vari\u00E1vel de Destino:");
//		
//		text_nomeVarDestino = new Text(composite_2, SWT.BORDER);
//		text_nomeVarDestino.setBounds(214, 18, 220, 26);
//		text_nomeVarDestino.setText("VarDestino");
//		
//		CLabel label_5 = new CLabel(composite_2, SWT.BORDER);
//		label_5.setBounds(10, 240, 400, 240);
		
//		Button btnVis1 = new Button(group_1, SWT.NONE);
//		btnVis1.setBounds(160, 120, 161, 47);
//		btnVis1.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				try {
//					BufferedImage imgGrafVarDestino = geraGrafico(varDestino);
//					ImageIO.write(imgGrafVarDestino, "bmp", new File("temp/_graficovd.bmp"));
//					Image graficoVarDestino = new Image(null, "temp/_graficovd.bmp");
//					carregaImagem(label_5, graficoVarDestino);
//				} catch (Exception f) {
//					f.printStackTrace();
//				}
//			}
//		});
//		btnVis1.setText("Visualizar");
		
		Button btnExecutar = new Button(composite, SWT.NONE);
		btnExecutar.setBounds(633, 548, 161, 47);
		btnExecutar.setText("Executar");

	}
	
	public void criarAba(CTabFolder tabFolder, ArrayList<Text[]> variaveis) {
		Text[] estaVariavel = new Text[12];
		
		GridLayout layout = new GridLayout();
	    layout.numColumns = 3;
	    
		
		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.CLOSE);
		tbtmNewItem.setText("Var1");
		
		Composite thisComposite = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(thisComposite);
		
		Group group = new Group(thisComposite, SWT.NONE);
		group.setBounds(10, 10, 443, 177);
		
		Label lblSup = new Label(group, SWT.BORDER);
		lblSup.setBounds(10, 42, 80, 20);
		lblSup.setText("Suporte");
		
		Label lblNuc = new Label(group, SWT.BORDER);
		lblNuc.setText("Núcleo");
		lblNuc.setBounds(10, 84, 80, 20);
		
		estaVariavel[0] = new Text(group, SWT.BORDER);
		estaVariavel[0].setBounds(96, 42, 42, 26);
		
		estaVariavel[1] = new Text(group, SWT.BORDER);
		estaVariavel[1].setBounds(146, 42, 42, 26);
		
		estaVariavel[2] = new Text(group, SWT.BORDER);
		estaVariavel[2].setBounds(96, 82, 42, 26);
		
		estaVariavel[3] = new Text(group, SWT.BORDER);
		estaVariavel[3].setBounds(146, 82, 42, 26);
		
		Label lblPouco = new Label(group, SWT.BORDER);
		lblPouco.setText("Pouco");
		lblPouco.setBounds(120, 16, 50, 20);
		lblPouco.setAlignment(SWT.CENTER);
		
		Label lblMdio = new Label(group, SWT.BORDER);
		lblMdio.setText("M\u00E9dio");
		lblMdio.setBounds(240, 16, 50, 20);
		lblMdio.setAlignment(SWT.CENTER);
		
		estaVariavel[4] = new Text(group, SWT.BORDER);
		estaVariavel[4].setBounds(216, 42, 42, 26);
		
		estaVariavel[5] = new Text(group, SWT.BORDER);
		estaVariavel[5].setBounds(266, 42, 42, 26);
		
		estaVariavel[6] = new Text(group, SWT.BORDER);
		estaVariavel[6].setBounds(216, 82, 42, 26);
		
		estaVariavel[7] = new Text(group, SWT.BORDER);
		estaVariavel[7].setBounds(266, 82, 42, 26);
		
		Label lblMuito = new Label(group, SWT.BORDER);
		lblMuito.setText("Muito");
		lblMuito.setBounds(360, 16, 50, 20);
		lblMuito.setAlignment(SWT.CENTER);
		
		estaVariavel[8] = new Text(group, SWT.BORDER);
		estaVariavel[8].setBounds(336, 42, 42, 26);
		
		estaVariavel[9] = new Text(group, SWT.BORDER);
		estaVariavel[9].setBounds(386, 42, 42, 26);
		
		estaVariavel[10] = new Text(group, SWT.BORDER);
		estaVariavel[10].setBounds(336, 82, 42, 26);
		
		estaVariavel[11] = new Text(group, SWT.BORDER);
		estaVariavel[11].setBounds(386, 82, 42, 26);
		
		CLabel lblNewLabel = new CLabel(thisComposite, SWT.BORDER);
		lblNewLabel.setBounds(10, 209, 400, 240);
	}
	
	private BufferedImage geraGrafico(Text[] dados) {
		BufferedImage res = new BufferedImage(400, 240, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = res.getRaster();
		
		double[] valores = new double[dados.length];
		for (int i = 0; i < dados.length; i++) {
			String str = dados[i].getText();
			//System.out.println(text.getText() + "?");
			try	{
				valores[i] = Double.parseDouble(str);
			}
			catch(NumberFormatException e) {
				valores[i] = 0.0;
			}
		}
		
		Conjunto c = new Conjunto("Teste", valores[0], valores[1], valores[2], valores[3]);
		for(int i = 0; i < 100; i++) {
			System.out.println("Pertinência para " + (i) + ": " + c.pertinencia(i));
		}
		
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