package client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * The class LimitAreaAndTextFields responsible to check areas and fields texts
 * that have to be in chosen limits in the suitable form
 * 
 * @author gal
 *
 */
public class LimitAreaAndTextFields {
	/**
	 * This method limits the text in the field
	 * 
	 * @param txt   The text in the field
	 * @param limit The chosen limit
	 */
	public static void limitTxtField(TextField txt, int limit) {
		txt.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (txt.getText().length() >= limit) {

						// if it's 11th character then just setText to previous
						// one
						txt.setText(txt.getText().substring(0, limit));
					}
				}
			}
		});
	}

	/**
	 * This method limits the text in the area
	 * 
	 * @param txt   The text in the area
	 * @param limit The chosen limit
	 */
	public static void limitTxtArea(TextArea txt, int limit) {
		txt.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (txt.getText().length() >= limit) {

						// if it's 11th character then just setText to previous
						// one
						txt.setText(txt.getText().substring(0, limit));
					}
				}
			}
		});
	}

}
