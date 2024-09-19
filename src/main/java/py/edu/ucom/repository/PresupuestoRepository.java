package py.edu.ucom.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.edu.ucom.entities.apiresponse.Gastos;
import py.edu.ucom.entities.apiresponse.Presupuesto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@ApplicationScoped
public class PresupuestoRepository {

    private static final String FILE_PATH = "src/main/resources/presupuesto.json";
    private List<Presupuesto> presupuestoList;

    @Inject
    ObjectMapper objectMapper;

    public PresupuestoRepository() {
        presupuestoList = cargarDatos();
    }

    // Cargar la lista de presupuestos desde un archivo JSON
    private List<Presupuesto> cargarDatos() {
        try {
            System.out.println("CARGA DE DATOS DESDE: " + FILE_PATH);
            File file = new File(FILE_PATH);
            if (file.exists()) { // Corrección: Cargar si el archivo existe
                return objectMapper.readValue(file, new TypeReference<List<Presupuesto>>() {});
            } else {
                System.out.println("Archivo no encontrado, creando una nueva lista.");
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Guardar la lista de presupuestos en un archivo JSON
    public void guardarDatos() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), presupuestoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Obtener un presupuesto específico por su ID
    public Presupuesto obtenerById(Integer id) {
        return presupuestoList.stream()
                .filter(presupuesto -> presupuesto.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Listar todos los presupuestos
    public List<Presupuesto> listar() {
        return new ArrayList<>(presupuestoList);
    }

    // Agregar un nuevo presupuesto
    public Presupuesto agregar(Presupuesto param) {
        Integer newId = presupuestoList.isEmpty() ? 1 : presupuestoList.stream().mapToInt(Presupuesto::getId).max().getAsInt() + 1;
        param.setId(newId);
        presupuestoList.add(param);
        guardarDatos();
        return param;
    }

    // Modificar un presupuesto existente
    public Presupuesto modificar(Presupuesto param) {
        Optional<Presupuesto> existingPresupuesto = presupuestoList.stream()
                .filter(presupuesto -> presupuesto.getId().equals(param.getId()))
                .findFirst();
        if (existingPresupuesto.isPresent()) {
            presupuestoList = presupuestoList.stream()
                    .map(presupuesto -> presupuesto.getId().equals(param.getId()) ? param : presupuesto)
                    .collect(Collectors.toList());
            guardarDatos();
            return param;
        } else {
            return null;
        }
    }

    // Eliminar un presupuesto por ID
    public void eliminar(Integer id) {
        presupuestoList = presupuestoList.stream()
                .filter(presupuesto -> !presupuesto.getId().equals(id))
                .collect(Collectors.toList());
        guardarDatos();
    }

    // Agregar un nuevo gasto a un presupuesto específico
    public void agregarGasto(Integer presupuestoId, Gastos gasto) {
        Presupuesto presupuesto = obtenerById(presupuestoId);
        if (presupuesto != null) {
            presupuesto.getGastos().add(gasto);
            guardarDatos();
        } else {
            System.out.println("Presupuesto no encontrado con ID: " + presupuestoId);
        }
    }

    public long contarTodos() {
        return presupuestoList.size();
    }

    public List<Presupuesto> buscarPorMontoEntre(double montoInicial, double montoFinal) {
        return presupuestoList.stream()
                .filter(p -> p.getMontoPresupuestado() >= montoInicial && p.getMontoPresupuestado() <= montoFinal)
                .collect(Collectors.toList());
    }

    public List<Presupuesto> buscarMayorPresupuesto() {
        Optional<Integer> maxMonto = presupuestoList.stream()
                .map(Presupuesto::getMontoPresupuestado)
                .max(Integer::compareTo);

        return maxMonto.map(monto -> presupuestoList.stream()
                        .filter(p -> p.getMontoPresupuestado().equals(monto))
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }
}
