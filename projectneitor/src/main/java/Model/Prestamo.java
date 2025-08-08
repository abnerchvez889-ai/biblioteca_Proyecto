package Model;

import java.time.LocalDate;

public class Prestamo {
    private int id;
    private int idLibro;
    private int idAlumno;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean devuelto;

    public Prestamo(int id, int idLibro, int idAlumno, LocalDate fechaPrestamo, LocalDate fechaDevolucion, boolean devuelto) {
        this.id = id;
        this.idLibro = idLibro;
        this.idAlumno = idAlumno;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    public int getId() { return id; }
    public int getIdLibro() { return idLibro; }
    public int getIdAlumno() { return idAlumno; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public boolean isDevuelto() { return devuelto; }

    public void setDevuelto(boolean devuelto) { this.devuelto = devuelto; }
}
