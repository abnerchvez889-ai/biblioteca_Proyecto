package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Vpcontroller {

    @FXML
    public void abrirVistaAlumno() throws Exception {
        Stage st = new Stage();
        st.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/example/application/VistaAlumno.fxml"))));
        st.setTitle("Gestion de Alumnos");
        st.setResizable(false);
        st.show();
    }

    @FXML
    public void abrirVistaLibro() throws Exception {
        Stage st = new Stage();
        st.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/example/application/VistaLibro.fxml"))));
        st.setTitle("Gestion de Libros");
        st.setResizable(false);
        st.show();
    }

    @FXML
    public void abrirVistaPrestamo() throws Exception {
        Stage st = new Stage();
        st.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/example/application/VistaPrestamo.fxml"))));
        st.setTitle("Gestion de Prestamos");
        st.setResizable(false);
        st.show();
    }
}
