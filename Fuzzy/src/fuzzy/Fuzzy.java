package fuzzy;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.swt.widgets.Menu;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class Fuzzy {

	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
	private Text text_9;
	private Text text_10;
	private Text text_11;
	private Text text_nomeVarDestino;

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
		
		criarAba(tabFolder);
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setBounds(485, 28, 475, 514);
		
		Group group_1 = new Group(composite_2, SWT.NONE);
		group_1.setBounds(10, 46, 443, 177);
		
		Label label = new Label(group_1, SWT.BORDER);
		label.setText("Sup");
		label.setBounds(10, 42, 40, 20);
		
		Label label_1 = new Label(group_1, SWT.BORDER);
		label_1.setText("Nuc");
		label_1.setBounds(10, 84, 40, 20);
		
		text_6 = new Text(group_1, SWT.BORDER);
		text_6.setBounds(56, 42, 78, 26);
		
		text_7 = new Text(group_1, SWT.BORDER);
		text_7.setBounds(56, 82, 78, 26);
		
		Label label_2 = new Label(group_1, SWT.BORDER);
		label_2.setText("Pouco");
		label_2.setBounds(67, 16, 57, 20);
		
		Label label_3 = new Label(group_1, SWT.BORDER);
		label_3.setText("M\u00E9dio");
		label_3.setBounds(174, 16, 54, 20);
		
		text_8 = new Text(group_1, SWT.BORDER);
		text_8.setBounds(161, 42, 78, 26);
		
		text_9 = new Text(group_1, SWT.BORDER);
		text_9.setBounds(161, 78, 78, 26);
		
		Label label_4 = new Label(group_1, SWT.BORDER);
		label_4.setText("Muito");
		label_4.setBounds(279, 16, 54, 20);
		
		text_10 = new Text(group_1, SWT.BORDER);
		text_10.setBounds(263, 42, 78, 26);
		
		text_11 = new Text(group_1, SWT.BORDER);
		text_11.setBounds(263, 78, 78, 26);
		
		Label lblNewLabel_1 = new Label(composite_2, SWT.BORDER);
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setBounds(10, 18, 180, 22);
		lblNewLabel_1.setText("Vari\u00E1vel de Destino:");
		
		text_nomeVarDestino = new Text(composite_2, SWT.BORDER);
		text_nomeVarDestino.setBounds(214, 18, 220, 26);
		text_nomeVarDestino.setText("VarDestino");
		
		Label label_5 = new Label(composite_2, SWT.BORDER);
		label_5.setBounds(10, 247, 443, 261);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setBounds(633, 548, 161, 47);
		btnNewButton.setText("Executar");

	}
	
	public void criarAba(CTabFolder tabFolder) {

		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("Var1");
		
		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(composite_3);
		
		Group group = new Group(composite_3, SWT.NONE);
		group.setBounds(10, 10, 443, 177);
		
		Label lblSup = new Label(group, SWT.BORDER);
		lblSup.setBounds(10, 42, 40, 20);
		lblSup.setText("Sup");
		
		Label lblNuc = new Label(group, SWT.BORDER);
		lblNuc.setText("Nuc");
		lblNuc.setBounds(10, 84, 40, 20);
		
		text = new Text(group, SWT.BORDER);
		text.setBounds(56, 42, 78, 26);
		
		text_1 = new Text(group, SWT.BORDER);
		text_1.setBounds(56, 82, 78, 26);
		
		Label lblPouco = new Label(group, SWT.BORDER);
		lblPouco.setText("Pouco");
		lblPouco.setBounds(67, 16, 57, 20);
		
		Label lblMdio = new Label(group, SWT.BORDER);
		lblMdio.setText("M\u00E9dio");
		lblMdio.setBounds(174, 16, 54, 20);
		
		text_2 = new Text(group, SWT.BORDER);
		text_2.setBounds(161, 42, 78, 26);
		
		text_3 = new Text(group, SWT.BORDER);
		text_3.setBounds(161, 78, 78, 26);
		
		Label lblMuito = new Label(group, SWT.BORDER);
		lblMuito.setText("Muito");
		lblMuito.setBounds(279, 16, 54, 20);
		
		text_4 = new Text(group, SWT.BORDER);
		text_4.setBounds(263, 42, 78, 26);
		
		text_5 = new Text(group, SWT.BORDER);
		text_5.setBounds(263, 78, 78, 26);
		
		Label lblNewLabel = new Label(composite_3, SWT.BORDER);
		lblNewLabel.setBounds(10, 209, 443, 261);
	}
}