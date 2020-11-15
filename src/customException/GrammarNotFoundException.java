package customException;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class GrammarNotFoundException extends Exception{
	
	
	public GrammarNotFoundException() {
        super("Para ejecutar el algoritmo CYK, debe ingresar una GIC en FNC y no dejar campos sin llenar");
    }

    public void message(){
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
        alert.setHeaderText(super.getMessage());
        alert.show();
    }

}
