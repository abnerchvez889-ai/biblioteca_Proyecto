package controller;

import Model.Libro;
import datastore.Datastore;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LibroController {

    @FXML private TableView<Libro> tablaLibros;
    @FXML private TableColumn<Libro, Integer> colId;
    @FXML private TableColumn<Libro, String> colTitulo;
    @FXML private TableColumn<Libro, String> colAutor;
    @FXML private TableColumn<Libro, String> colDisponible;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;

    private final Datastore ds = Datastore.getInstance();
    private Libro seleccionado = null;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("autor"));
        colDisponible.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().isDisponible() ? "Si" : "No"));
        tablaLibros.setItems(ds.obtenerLibros());

        tablaLibros.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            seleccionado = newV;
            if (newV != null) {
                txtTitulo.setText(newV.getTitulo());
                txtAutor.setText(newV.getAutor());
            } else limpiarFormulario();
        });
    }

    @FXML
    public void agregarLibro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();

            if (titulo.isEmpty() || autor.isEmpty()) { mostrarAlerta("Rellena todos los campos"); return; }
            if (!autor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) { mostrarAlerta("El autor solo puede contener letras y espacios"); return; }
            if (ds.existeLibroPorTitulo(titulo)) { mostrarAlerta("Ya existe un libro con ese titulo"); return; }

            ds.agregarLibro(titulo, autor);
            tablaLibros.setItems(ds.obtenerLibros());
            limpiarFormulario();
        } catch (Exception e) {
            mostrarAlerta("Error al agregar libro");
            e.printStackTrace();
        }
    }

    @FXML
    public void editarLibro() {
        if (seleccionado == null) { mostrarAlerta("Selecciona un libro para editar"); return; }
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();

        if (titulo.isEmpty() || autor.isEmpty()) { mostrarAlerta("Rellena todos los campos"); return; }
        if (!autor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) { mostrarAlerta("El autor solo puede contener letras y espacios"); return; }
        if (!seleccionado.getTitulo().equalsIgnoreCase(titulo) && ds.existeLibroPorTitulo(titulo)) { mostrarAlerta("Ese titulo ya existe"); return; }

        ds.editarLibro(seleccionado.getId(), titulo, autor);
        tablaLibros.setItems(ds.obtenerLibros());
        limpiarFormulario();
    }

    @FXML
    public void eliminarLibro() {
        Libro l = tablaLibros.getSelectionModel().getSelectedItem();
        if (l == null) { mostrarAlerta("Selecciona un libro para eliminar"); return; }
        if (!l.isDisponible()) { mostrarAlerta("No puedes eliminar un libro que esta prestado"); return; }
        ds.eliminarLibro(l);
        tablaLibros.setItems(ds.obtenerLibros());
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtTitulo.clear();
        txtAutor.clear();
        tablaLibros.getSelectionModel().clearSelection();
        seleccionado = null;
    }

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Aviso");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
