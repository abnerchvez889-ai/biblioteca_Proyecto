package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import Model.Alumno;

import java.net.URL;
import java.util.ResourceBundle;

public class AlumnoController implements Initializable {
    @FXML
    private Label lblNombre, lblMatricula;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Alumno alumno = new Alumno(1, "María González", "A12345678");
        lblNombre.setText("Nombre: " + alumno.getNombre());
        lblMatricula.setText("Matrícula: " + alumno.getMatricula());
    }
}
