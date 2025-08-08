package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import Model.Libro;

import java.net.URL;
import java.util.ResourceBundle;

public class LibroController implements Initializable {
    @FXML
    private Label lblTitulo, lblAutor, lblDisponible;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Libro libro = new Libro(1, "El Principito", "Antoine de Saint-Exupéry", true);
        lblTitulo.setText("Título: " + libro.getTitulo());
        lblAutor.setText("Autor: " + libro.getAutor());
        lblDisponible.setText("¿Disponible?: " + (libro.isDisponible() ? "Sí" : "No"));
    }
}
