package datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    private static final String URL = "jdbc:sqlite:biblioteca.db";
    private static DBManager instance;
    private Connection connection;

    private DBManager() {
        try {
            connection = DriverManager.getConnection(URL);
            crearTablasSiNoExisten();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al conectar con la base de datos SQLite");
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void crearTablasSiNoExisten() throws SQLException {
        String sqlAlumno = """
            CREATE TABLE IF NOT EXISTS alumnos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                matricula TEXT NOT NULL
            );
            """;

        String sqlLibro = """
            CREATE TABLE IF NOT EXISTS libros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                autor TEXT NOT NULL,
                disponible INTEGER NOT NULL
            );
            """;

        String sqlPrestamo = """
            CREATE TABLE IF NOT EXISTS prestamos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                alumno_id INTEGER NOT NULL,
                libro_id INTEGER NOT NULL,
                fecha_prestamo TEXT NOT NULL,
                FOREIGN KEY(alumno_id) REFERENCES alumnos(id),
                FOREIGN KEY(libro_id) REFERENCES libros(id)
            );
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlAlumno);
            stmt.execute(sqlLibro);
            stmt.execute(sqlPrestamo);
        }
    }
}
