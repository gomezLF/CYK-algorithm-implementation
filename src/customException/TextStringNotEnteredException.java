package customException;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class TextStringNotEnteredException extends Exception {
	
	public TextStringNotEnteredException() {
        super("Para ejecutar el algoritmo CYK, debe ingresar una cadena");
    }

    public void message(){
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
        alert.setHeaderText(super.getMessage());
        alert.show();
    }
}
