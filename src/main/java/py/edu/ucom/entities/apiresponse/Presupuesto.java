package py.edu.ucom.entities.apiresponse;

import java.util.Date;
import java.util.List;

public class Presupuesto {

    private int id;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer montoPresupuestado;
    private List<Gastos> gastos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getMontoPresupuestado() {
        return montoPresupuestado;
    }

    public void setMontoPresupuestado(Integer montoPresupuestado) {
        this.montoPresupuestado = montoPresupuestado;
    }

    public List<Gastos> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gastos> gastos) {
        this.gastos = gastos;
    }
}
