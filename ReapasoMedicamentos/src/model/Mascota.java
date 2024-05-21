package model;

import java.time.LocalDate;
import java.time.Period;

public class Mascota {
    private int id;
    private String nombre;
    private String direccion;
    private LocalDate fechaDeNacimiento;
    private int edad;
    private String propietario;

    public Mascota(int id, String nombre, String direccion, LocalDate fechaDeNacimiento, String propietario) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.propietario = propietario;
        this.setEdad();
    }

    public Mascota(String nombre, String direccion, LocalDate fechaDeNacimiento, String propietario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.propietario = propietario;
        this.setEdad();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad() {
        this.edad = calcularEdad();
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    private int calcularEdad() {
        if (fechaDeNacimiento == null) {
            return 0; // Manejo de caso si fechaDeNacimiento es nulo
        }
        return Period.between(fechaDeNacimiento, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Mascota" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaDeNacimiento=" + fechaDeNacimiento +
                ", edad=" + edad +
                ", propietario='" + propietario + '\'';
    }
}
