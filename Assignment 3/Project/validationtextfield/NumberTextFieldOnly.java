package validationtextfield;

import javafx.scene.control.TextField;

/**
 * The class NumberTextFieldOnly defined a functions which set text field object
 * to only numbers
 * 
 * @author Shoval David
 *
 */
public class NumberTextFieldOnly extends TextField {
	public NumberTextFieldOnly() {
	}

	/**
	 * Override the replace text function from TextField which set the insertion of
	 * letters inside the text field only to numbers
	 */
	@Override
	public void replaceText(int i, int il, String string) {
		if (string.matches("[0-9]") || string.isEmpty()) {
			super.replaceText(i, il, string);
		}
	}

	/**
	 * Replaces the selection with the given replacement String
	 */
	@Override
	public void replaceSelection(String string) {
		super.replaceSelection(string);
	}
}
