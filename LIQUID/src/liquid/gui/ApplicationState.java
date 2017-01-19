package liquid.gui;

import java.util.LinkedList;

import liquid.core.LiquidApplication;

/**
 * ApplicationState class detects when there are unsaved changes, determined by the <code>LinkedList Array</code> of parameters which
 * is incremented with each change. From there, the <code>undo()</code> and <code>redo()</code> methods can be executed accordingly.
 */
public class ApplicationState {
	// declares LinkedLists to store unsaved changes
	LinkedList<String[]> savedStates = new LinkedList<String[]>(),
						 undoStates  = new LinkedList<String[]>();
	
	/**
	 * Method checks if there are unsaved parameter changes to the current simulation. If
	 * so, the file name will include an '*' to imitate standard software functionalities.
	 */
	public void saveState() {
		// adds another set of parameters to store history of changes & allow undo capabilities
		savedStates.push(LiquidApplication.getGUI().getFileVariables().writeArray());
		undoStates.clear();
		// printStateList(savedStates,"Saved States");
		
		// '*' gets added to title with unsaved parameter changes present
		if (savedStates.size() > 1)
			LiquidApplication.getGUI().getFrame().setTitle("*"+LiquidApplication.getGUI().getFileVariables().getFilenameOnlyAndTitle(true));
		else
			LiquidApplication.getGUI().getFrame().setTitle(LiquidApplication.getGUI().getFileVariables().getFilenameOnlyAndTitle(true));
	}
	
	/**
	 * Method undoes the latest change made by the user. The current set of parameters is first removed from the
	 * <code>savedStates LinkedList</code>, and the next set of parameters are set to the current simulation.
	 */
	public void undo() {
		// old sets of parameters are saved to allow redo functionalities
		if (savedStates.size() > 1) {
			undoStates.push(savedStates.pop());
			LiquidApplication.getGUI().setApplication();}
		
		// eliminates the '*' from the title when no unsaved changes exist
		if (savedStates.size() == 1)
			LiquidApplication.getGUI().getFrame().setTitle(LiquidApplication.getGUI().getFileVariables().getFilenameOnlyAndTitle(true));
	} 
	
	/**
	 * Method redos the latest undo change made by the user. The current set of parameters is
	 * obtained from the <code>undoStates LinkedList</code> and set to the current simulation.
	 */
	public void redo() {
		// when applicable, undid sets of parameters get returned to the simulation
		if (!undoStates.isEmpty()) {
			savedStates.push(undoStates.pop());
			LiquidApplication.getGUI().setApplication();
			LiquidApplication.getGUI().getFrame().setTitle("*"+LiquidApplication.getGUI().getFileVariables().getFilenameOnlyAndTitle(true));}
	}
	
	/**
	 * Method is designed to print a <code>String Array LinkedList</code>, primarily for testing purposes.
	 * @param statesList - states list to print
	 */
	public void printStateList(LinkedList<String[]> statesList, String statesListName) {
		for (int i = 0; i < statesList.size(); i++) {
			System.out.print("\n"+statesListName+" "+i+" - ");
			for (int j = 0; j < statesList.get(i).length; j++)
				System.out.print(statesList.get(i)[j]+" ");}
		System.out.println();
	}
	
	/**
	 * Method resets the properties of the ApplicationState class.
	 */
	public void reset() {
		savedStates.clear();
		saveState();
	}
	
	/**
	 * Getter method returns the saved parameter state(s).
	 * @return - <code>String Array</code> of saved parameter state(s)
	 */
	public LinkedList<String[]> getSavedStates() {return savedStates;}
	
	/**
	 * Getter method returns the undid parameter state(s).
	 * @return - <code>String Array</code> of undid parameter state(s)
	 */
	public LinkedList<String[]> getUndoStates() {return undoStates;}
}