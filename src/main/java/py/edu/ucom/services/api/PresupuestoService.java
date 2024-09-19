package py.edu.ucom.services.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.edu.ucom.entities.apiresponse.Presupuesto;
import py.edu.ucom.entities.apiresponse.Gastos;
import py.edu.ucom.repository.PresupuestoRepository;

import java.util.List;

@ApplicationScoped
public class PresupuestoService {

    @Inject
    PresupuestoRepository presupuestoRepository;

    // Crear un nuevo presupuesto
    public void agregar(Presupuesto presupuesto) {
        presupuestoRepository.listar().add(presupuesto);
        presupuestoRepository.guardarDatos();
    }

    // Listar todos los presupuestos
    public List<Presupuesto> listar() {
        return presupuestoRepository.listar();
    }

    // Obtener un presupuesto específico por ID
    public Presupuesto obtenerPresupuestoPorId(Integer id) {
        return presupuestoRepository.obtenerById(id);
    }

    // Retornar la suma de los gastos de un presupuesto
    public double obtenerTotalGastos(Integer presupuestoId) {
        Presupuesto presupuesto = obtenerPresupuestoPorId(presupuestoId);
        if (presupuesto != null) {
            return presupuesto.getGastos().stream().mapToDouble(Gastos::getMonto).sum();
        }
        return 0;
    }

    // Agregar un nuevo gasto y validar que no supere el monto presupuestado
    public void agregarGasto(Integer presupuestoId, Gastos nuevoGasto) throws Exception {
        Presupuesto presupuesto = obtenerPresupuestoPorId(presupuestoId);
        if (presupuesto != null) {
            double totalGastos = obtenerTotalGastos(presupuestoId);
            if (totalGastos + nuevoGasto.getMonto() > presupuesto.getMontoPresupuestado()) {
                throw new Exception("El gasto supera el monto presupuestado.");
            }
            presupuestoRepository.agregarGasto(presupuestoId, nuevoGasto);
        } else {
            throw new Exception("Presupuesto no encontrado.");
        }
    }

    public long contarPresupuestos() {
        return presupuestoRepository.contarTodos();
    }

    public List<Presupuesto> filtrarPorMonto(double rangoInicial, double rangoFinal) {
        return presupuestoRepository.buscarPorMontoEntre(rangoInicial, rangoFinal);
    }

    public List<Presupuesto> obtenerMayorPresupuesto() {
        return presupuestoRepository.buscarMayorPresupuesto();
    }


}
