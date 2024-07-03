package it.polito.tdp.exam;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.exam.model.Model;
import it.polito.tdp.exam.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Model model;

	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnDettagli;

    @FXML
    private Button btnPercorso;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<Team> cmbSquadra;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtSquadre;

    @FXML
    void handleAnno(ActionEvent event) {
    	
    	if (cmbAnno.getValue()!= null) {
    		int anno = cmbAnno.getValue();
    		cmbSquadra.getItems().addAll(model.getTeams(anno));
    		for (Team t: model.getTeams(anno)) {
    			txtSquadre.appendText(t+"\n");
    		}
    	}
    }

    @FXML
    void handleCreaGrafo(ActionEvent event) {
    	if (cmbAnno.getValue()!= null) {
    		int anno = cmbAnno.getValue();
    		model.creaGrafo(anno);
    		txtResult.appendText("Vertici: "+ model.getV()+"\nArchi: "+ model.getA());
    		
    	}
    }

    @FXML
    void handleDettagli(ActionEvent event) {
    	if (cmbSquadra.getValue()!= null) {
    		Team t = cmbSquadra.getValue();
    		Map<String,Integer> m = model.getAdiacenti(t);
    		txtResult.appendText("Gli adiacenti di "+ t.getTeamCode()+ " sono: \n");
    		for (String s : m.keySet()) {
    			txtResult.appendText(s+" ("+ m.get(s)+") \n");
    		}
    	}
    }

    @FXML
    void handlePercorso(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDettagli != null : "fx:id=\"btnDettagli\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSquadre != null : "fx:id=\"txtSquadre\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model= model;
		cmbAnno.getItems().addAll(model.getAnni());
	}

}
