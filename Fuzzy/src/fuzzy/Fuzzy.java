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

public class Fuzzy {

	protected Shell shell;

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
		shell.setSize(800, 600);
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

	}
}
