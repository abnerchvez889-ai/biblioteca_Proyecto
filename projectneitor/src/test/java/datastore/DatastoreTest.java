package datastore;

import Model.Libro;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatastoreTest {

    @Test
    public void testObtenerLibros() {

        Datastore datastore = Datastore.getInstance();

        ObservableList<Libro> libros = datastore.obtenerLibros();

        assertNotNull(libros, "La lista de libros no debe ser null");

        if (!libros.isEmpty()) {
            Libro libro = libros.get(0);
            assertNotNull(libro.getTitulo(), "El titulo no debe ser null");
            assertNotNull(libro.getAutor(), "El autor no debe ser null");
        }
    }
}
