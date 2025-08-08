package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import Model.Alumno;
import Model.Libro;
import Model.Prestamo;

import java.time.LocalDate;

public class Vpcontroller {

    @FXML
    public void verLibro() {
        Libro libro = new Libro(1, "El principito", "Antoine de Saint-Exupéry", true);
        mostrarMensaje("Libro", "Título: " + libro.getTitulo() + "\nAutor: " + libro.getAutor());
    }

    @FXML
    public void verAlumno() {
        Alumno alumno = new Alumno(1, "María González", "A12345678");
        mostrarMensaje("Alumno", "Nombre: " + alumno.getNombre() + "\nMatrícula: " + alumno.getMatricula());
    }

    @FXML
    public void verPrestamo() {
        Prestamo prestamo = new Prestamo(1, 1, 1, LocalDate.now(), LocalDate.now().plusDays(7), false);
        mostrarMensaje("Préstamo", "Fecha préstamo: " + prestamo.getFechaPrestamo() + "\nDevolución: " + prestamo.getFechaDevolucion());
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
