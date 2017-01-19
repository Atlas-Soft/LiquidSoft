package liquid.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import liquid.core.GlobalVariables;
import liquid.core.LiquidApplication;

/**
 * MenuBarEdit class is a branch of MenuBar. Here, the Edit tab is declared/defined. Users may undo or
 * redo previously-made changes to the simulation parameters prior to saving said settings onto a log file.
 */
public class MenuBarEdit {
	JMenuItem editItem;
	
	/**
	 * Method creates the components of the Edit menu. A global <code>enum</code> provides the specific sub-tab
	 * names as well as the base for a <code>switch</code> statement to define each sub-tab's action functionality.
	 * @param menu - base menu to add sub-tabs to
	 */
	public void setUpEditMenu(JMenu menu) {
		for (GlobalVariables.EditMenuItems item : GlobalVariables.EditMenuItems.values()) {
			editItem = new JMenuItem(item.toString());
			
			// adds action functionality for each sub-tab via a switch statement
			editItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					switch (item) {
					
					// 'Undo' undoes the user's last action
					case Undo:
						LiquidApplication.getGUI().getApplicationState().undo();
						break;
					
					// 'Redo' re-does the user's last action
					case Redo:
						LiquidApplication.getGUI().getApplicationState().redo();
						break;
					default:
						break;}
			}});
			menu.add(editItem);
		}
	}
}