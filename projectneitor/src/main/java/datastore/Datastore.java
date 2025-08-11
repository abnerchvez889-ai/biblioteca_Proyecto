package datastore;

import Model.Alumno;
import Model.Libro;
import Model.Prestamo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class Datastore {
    private static Datastore instance;
    private final DBManager db = DBManager.getInstance();

    private Datastore() {
    }

    public static Datastore getInstance() {
        if (instance == null) {
            instance = new Datastore();
        }
        return instance;
    }

    public ObservableList<Alumno> obtenerAlumnos() {
        ObservableList<Alumno> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM alumnos";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Alumno a = new Alumno(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("matricula")
                );
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void agregarAlumno(String nombre, String matricula) {
        String sql = "INSERT INTO alumnos(nombre, matricula) VALUES (?, ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, matricula);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editarAlumno(int id, String nombre, String matricula) {
        String sql = "UPDATE alumnos SET nombre = ?, matricula = ? WHERE id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, matricula);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarAlumno(Alumno alumno) {
        String sql = "DELETE FROM alumnos WHERE id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, alumno.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeAlumnoPorMatricula(String matricula) {
        String sql = "SELECT COUNT(*) FROM alumnos WHERE matricula = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ObservableList<Libro> obtenerLibros() {
        ObservableList<Libro> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM libros";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Libro l = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("disponible") == 1
                );
                lista.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ObservableList<Libro> obtenerLibrosDisponibles() {
        ObservableList<Libro> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM libros WHERE disponible = 1";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Libro l = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        true
                );
                lista.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void agregarLibro(String titulo, String autor) {
        String sql = "INSERT INTO libros(titulo, autor, disponible) VALUES (?, ?, 1)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setString(2, autor);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editarLibro(int id, String titulo, String autor) {
        String sql = "UPDATE libros SET titulo = ?, autor = ? WHERE id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setString(2, autor);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarLibro(Libro libro) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, libro.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeLibroPorTitulo(String titulo) {
        String sql = "SELECT COUNT(*) FROM libros WHERE titulo = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, titulo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<Prestamo> obtenerPrestamos() {
        ObservableList<Prestamo> lista = FXCollections.observableArrayList();
        String sql = """
            SELECT p.id, p.fecha_prestamo, a.id AS alumno_id, a.nombre, a.matricula,
                   l.id AS libro_id, l.titulo, l.autor, l.disponible
            FROM prestamos p
            JOIN alumnos a ON p.alumno_id = a.id
            JOIN libros l ON p.libro_id = l.id
            """;
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Alumno a = new Alumno(rs.getInt("alumno_id"), rs.getString("nombre"), rs.getString("matricula"));
                Libro l = new Libro(rs.getInt("libro_id"), rs.getString("titulo"), rs.getString("autor"), rs.getInt("disponible") == 1);
                Prestamo p = new Prestamo(
                        rs.getInt("id"),
                        a,
                        l,
                        LocalDate.parse(rs.getString("fecha_prestamo"))
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public void agregarPrestamo(Alumno alumno, Libro libro) {
        String sqlInsertPrestamo = "INSERT INTO prestamos(alumno_id, libro_id, fecha_prestamo) VALUES (?, ?, ?)";
        String sqlUpdateLibro = "UPDATE libros SET disponible = 0 WHERE id = ?";
        try {
            db.getConnection().setAutoCommit(false);
            try (PreparedStatement psPrestamo = db.getConnection().prepareStatement(sqlInsertPrestamo);
                 PreparedStatement psLibro = db.getConnection().prepareStatement(sqlUpdateLibro)) {
                psPrestamo.setInt(1, alumno.getId());
                psPrestamo.setInt(2, libro.getId());
                psPrestamo.setString(3, LocalDate.now().toString());
                psPrestamo.executeUpdate();

                psLibro.setInt(1, libro.getId());
                psLibro.executeUpdate();

                db.getConnection().commit();
            } catch (SQLException e) {
                db.getConnection().rollback();
                throw e;
            } finally {
                db.getConnection().setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarPrestamo(Prestamo prestamo, Alumno alumno, Libro libro) {
        String sqlUpdatePrestamo = "UPDATE prestamos SET alumno_id = ?, libro_id = ? WHERE id = ?";
        String sqlUpdateLibroDisponibleOld = "UPDATE libros SET disponible = 1 WHERE id = ?";
        String sqlUpdateLibroDisponibleNew = "UPDATE libros SET disponible = 0 WHERE id = ?";

        try {
            db.getConnection().setAutoCommit(false);
            try (PreparedStatement psUpdatePrestamo = db.getConnection().prepareStatement(sqlUpdatePrestamo);
                 PreparedStatement psLibroOld = db.getConnection().prepareStatement(sqlUpdateLibroDisponibleOld);
                 PreparedStatement psLibroNew = db.getConnection().prepareStatement(sqlUpdateLibroDisponibleNew)) {

                psLibroOld.setInt(1, prestamo.getLibro().getId());
                psLibroOld.executeUpdate();

                psUpdatePrestamo.setInt(1, alumno.getId());
                psUpdatePrestamo.setInt(2, libro.getId());
                psUpdatePrestamo.setInt(3, prestamo.getId());
                psUpdatePrestamo.executeUpdate();

                psLibroNew.setInt(1, libro.getId());
                psLibroNew.executeUpdate();

                db.getConnection().commit();
            } catch (SQLException e) {
                db.getConnection().rollback();
                throw e;
            } finally {
                db.getConnection().setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPrestamo(Prestamo prestamo) {
        String sqlDeletePrestamo = "DELETE FROM prestamos WHERE id = ?";
        String sqlUpdateLibro = "UPDATE libros SET disponible = 1 WHERE id = ?";

        try {
            db.getConnection().setAutoCommit(false);
            try (PreparedStatement psDelete = db.getConnection().prepareStatement(sqlDeletePrestamo);
                 PreparedStatement psLibro = db.getConnection().prepareStatement(sqlUpdateLibro)) {
                psDelete.setInt(1, prestamo.getId());
                psDelete.executeUpdate();

                psLibro.setInt(1, prestamo.getLibro().getId());
                psLibro.executeUpdate();

                db.getConnection().commit();
            } catch (SQLException e) {
                db.getConnection().rollback();
                throw e;
            } finally {
                db.getConnection().setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
