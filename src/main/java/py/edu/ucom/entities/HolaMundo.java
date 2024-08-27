package py.edu.ucom.entities;

import java.util.Date;

public class HolaMundo {
    public String nombre;
    public Integer edad;
    public Date fechaNacimiento;

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Integer getEdad(){
        return edad;
    }

    public void setEdad(Integer edad){
        this.edad = edad;
    }

    public Date getFechaNacimiento(){
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "HolaMundo [nombre=" + nombre + ", edad=" + edad + ", fechaNacimiento=" + fechaNacimiento + "]";
    }

}
