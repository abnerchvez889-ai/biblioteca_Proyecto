package Model;

public class Alumno {
    private int id;
    private String nombre;
    private String matricula;

    public Alumno(int id, String nombre, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.matricula = matricula;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getMatricula() { return matricula; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}
