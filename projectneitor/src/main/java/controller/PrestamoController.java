package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import Model.Prestamo;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PrestamoController implements Initializable {
    @FXML
    private Label lblFechaPrestamo, lblFechaDevolucion, lblDevuelto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Prestamo prestamo = new Prestamo(1, 1, 1, LocalDate.now(), LocalDate.now().plusDays(7), false);
        lblFechaPrestamo.setText("Fecha de préstamo: " + prestamo.getFechaPrestamo());
        lblFechaDevolucion.setText("Fecha de devolución: " + prestamo.getFechaDevolucion());
        lblDevuelto.setText("¿Devuelto?: " + (prestamo.isDevuelto() ? "Sí" : "No"));
    }
}
