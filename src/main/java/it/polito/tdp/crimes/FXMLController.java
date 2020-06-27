/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Distretto;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	txtResult.setText("Crea grafo...");
    	
    	Integer anno = this.boxAnno.getValue();
    	
    	if(anno == null) {
    		txtResult.setText("Selezionare un anno.\n");
    		return;
    	}
    	
    	this.model.creaGrafo(anno);
    	
    	txtResult.appendText("\n\n#VERTICI: "+this.model.numeroVertici());
    	txtResult.appendText("\n#ARCHI: "+this.model.numeroArchi());

    	txtResult.appendText("\n\nPer ogni distretto i suoi adiacenti:\n");
    	for(Distretto d: this.model.verticiGrafo()) {
    		txtResult.appendText("\nPer il distretto "+d.getId()+":\n"+this.model.distrettiAdiacenti(d));
    	}
    	
    	//this.boxMese.getItems().addAll(this.model.prendiMesi(anno));
    	
    	//Integer mese = this.boxMese.getValue();
    	//non funziona bene
    	//this.boxGiorno.getItems().addAll(this.model.prendiGiorni(anno, mese));
    	//System.out.println(this.model.prendiGiorni(anno, mese));
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    	txtResult.clear();
    	
    	Integer anno = this.boxAnno.getValue();
    	Integer mese = this.boxMese.getValue();
    	Integer giorno = this.boxGiorno.getValue();
    	
    	this.boxMese.getItems().addAll(this.model.prendiMesi(anno));
    	
    	//non funziona bene
    	this.boxGiorno.getItems().addAll(this.model.prendiGiorni(anno, mese));
    	
    	String N = this.txtN.getText();
    	
    	if(anno == null || mese == null || giorno == null) {
    		txtResult.setText("Selezionare tutti i campi!\n");
    		return;
    	}

    	try {
    		int n = Integer.parseInt(N);
    	}catch(NumberFormatException e) {
    		txtResult.setText("Selezionare un numero intero da 1 a 10.\n");
    		return;
    	}
    	
    	//this.model.

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxAnno.getItems().addAll(this.model.elencoAnni());
    }
}
