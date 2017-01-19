package liquid.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import liquid.core.GlobalVariables;
import liquid.core.LiquidApplication;

/**
 * MenuBarHelp class is a branch of MenuBar. Here, the Help tab is declared/defined. Users may view Project LIQUID's
 * licensing information, particularly in regards to the Engine and from whom it was borrowed (for a school project).
 */
public class MenuBarHelp {
	JMenuItem helpItem;
	
	/**
	 * Method creates the components of the Help menu. A global <code>enum</code> provides the specific sub-tab
	 * names as well as the base for a <code>switch</code> statement to define each sub-tab's action functionality.
	 * @param menu - base menu to add sub-tabs to
	 */
	public void setUpHelpMenu(JMenu menu) {
		for (GlobalVariables.HelpMenuItems item : GlobalVariables.HelpMenuItems.values()) {
			helpItem = new JMenuItem(item.toString());
			
			// adds action functionality for each sub-tab via a switch statement
			helpItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					switch (item) {
					
					// 'About' provides license information, particularly for the Engine
					case About:
						LiquidApplication.getGUI().getVariousMessages().about();
						break;
					default:
						break;}
			}});
			menu.add(helpItem);
		}
	}
}