package net.friwi.agdsn.desktop.ui;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.friwi.agdsn.desktop.Main;

public class ExitMenuItem extends MenuItem{

	private static final long serialVersionUID = 1L;

	public ExitMenuItem(final String text){
		super(text);
		this.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.exit();
			}
			
		});
	}
}
