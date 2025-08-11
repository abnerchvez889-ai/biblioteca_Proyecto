package Model;

import java.time.LocalDate;

public class Prestamo {
    private final int id;
    private Alumno alumno;
    private Libro libro;
    private LocalDate fechaPrestamo;

    public Prestamo(int id, Alumno alumno, Libro libro, LocalDate fechaPrestamo) {
        this.id = id;
        this.alumno = alumno;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
    }

    public int getId() { return id; }
    public Alumno getAlumno() { return alumno; }
    public Libro getLibro() { return libro; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }

    public void setAlumno(Alumno alumno) { this.alumno = alumno; }
    public void setLibro(Libro libro) { this.libro = libro; }

    @Override
    public String toString() {
        return alumno.getNombre() + " → " + libro.getTitulo();
    }
}
