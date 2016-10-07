package net.friwi.agdsn.desktop.ui;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.friwi.agdsn.desktop.Main;
import net.friwi.agdsn.desktop.icons.Icons;

public class AboutMenuItem extends MenuItem{

	private static final long serialVersionUID = 1L;

	public AboutMenuItem(final String text){
		super(text);
		this.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, Main.langDef.getTranslation("agdsn.about", Main.version), text, JOptionPane.OK_OPTION, new ImageIcon(Icons.getLogo(3)));
			}
			
		});
	}
}
