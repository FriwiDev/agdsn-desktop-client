package net.friwi.agdsn.desktop.ui;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.friwi.agdsn.desktop.Main;

public class LanguageMenuItem extends MenuItem{

	private static final long serialVersionUID = 1L;

	public LanguageMenuItem(final String code, String text){
		super(text);
		this.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.updateLanguage(code);
				Notificator.sendNotification(Main.langDef.getTranslation("agdsn.lang_updated_title"), Main.langDef.getTranslation("agdsn.lang_updated_msg"));
			}
			
		});
		this.setEnabled(!Main.langDef.getLanguageCode().equals(code));
	}
}
