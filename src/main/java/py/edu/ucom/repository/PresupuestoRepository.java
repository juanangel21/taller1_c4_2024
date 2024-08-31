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
import java.util.stream.Collectors;
import java.util.Optional;

@ApplicationScoped
public class PresupuestoRepository {

    private static final String FILE_PATH = "src/main/resources/presupuesto.json";
    private List<Presupuesto> presupuestoList;

    @Inject
    private ObjectMapper objectMapper;

    public PresupuestoRepository() {
        objectMapper = new ObjectMapper();
        presupuestoList = cargarDatos();
    }

    private List<Presupuesto> cargarDatos() {
        try {
            System.out.println("CARGA DE DATOS " + FILE_PATH);
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<Presupuesto>>() {});
            } else {

                System.out.println("UPSSS NO HAY DATOS");
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void guardarDatos(){
        try{
            objectMapper.writeValue(new File(FILE_PATH), presupuestoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Presupuesto obtenerById(Integer id){
        return presupuestoList.stream().filter(presupuesto -> presupuesto.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Presupuesto> listar(){
        return new ArrayList<>(presupuestoList);
    }

    public Presupuesto agregar(Presupuesto param) {
        Integer newId = presupuestoList.isEmpty() ? 1 : presupuestoList.stream().mapToInt(Presupuesto::getId).max().getAsInt() + 1;
        param.setId(newId);
        presupuestoList.add(param);
        guardarDatos();
        return param;
    }

    public Presupuesto modificar(Presupuesto param) {
        Optional<Presupuesto> existingPresupuesto = presupuestoList.stream().filter(presupuesto -> presupuesto.getId() == param.getId()).findFirst();
        if (existingPresupuesto.isPresent()){
            presupuestoList = presupuestoList.stream().map(presupuesto -> presupuesto.getId() == param.getId() ? param : presupuesto).collect(Collectors.toList());
            guardarDatos();
            return param;
        } else {
            return null;
        }
    }

    public void eliminar(Integer id) {
        presupuestoList = presupuestoList.stream().filter(presupuesto -> presupuesto.getId() != id).collect(Collectors.toList());
        guardarDatos();
    }

    public void agregarGasto(Integer presupuestoId, Gastos gasto) {
        Optional<Presupuesto> presupuestoOpt = Optional.ofNullable(obtenerById(presupuestoId));
        if (presupuestoOpt.isPresent()) {
            Presupuesto presupuesto = presupuestoOpt.get();
            presupuesto.getGastos().add(gasto);
            guardarDatos();
        } else {
            System.out.println("Presupuesto no encontrado con ID: " + presupuestoId);
        }
    }
}
