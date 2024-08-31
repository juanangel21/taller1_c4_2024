package py.edu.ucom.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import py.edu.ucom.entities.apiresponse.Gastos;
import py.edu.ucom.entities.apiresponse.Presupuesto;
import py.edu.ucom.repository.PresupuestoRepository;

import java.util.List;
import java.util.Optional;

@Path("/presupuestos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PresupuestoResource {

    @Inject
    PresupuestoRepository presupuestoRepository;

    @POST
    public Response crearPresupuesto(Presupuesto presupuesto) {
        List<Presupuesto> presupuestos = presupuestoRepository.listar();
        Integer newPresupuestoId = presupuestos.isEmpty() ? 1 : presupuestos.stream()
                .mapToInt(Presupuesto::getId)
                .max()
                .getAsInt() + 1;
        presupuesto.setId(newPresupuestoId);

        // Validamos que las fechas ingresadas para el presupuesto esten correctas
        if (presupuesto.getFechaInicio().after(presupuesto.getFechaFin())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("La fecha de inicio debe ser anterior o igual a la fecha de fin.")
                    .build();
        }

        // Validamos que la fecha del gasto este en el rango de la fecha del presupuesto
        for (Gastos gasto : presupuesto.getGastos()) {
            if (gasto.getFecha().before(presupuesto.getFechaInicio()) ||
                    gasto.getFecha().after(presupuesto.getFechaFin())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Las fechas de los gastos deben estar dentro del rango del presupuesto.")
                        .build();
            }
        }


        List<Gastos> gastosList = presupuesto.getGastos();
        if (gastosList != null) {
            int currentMaxGastoId = 0;
            for (Gastos gasto : gastosList) {
                gasto.setId(++currentMaxGastoId);  // Incrementa y asigna un ID único
            }
        }

        presupuestoRepository.agregar(presupuesto);
        return Response.status(Response.Status.CREATED).entity(presupuesto).build();
    }

    @GET
    public List<Presupuesto> listarPresupuestos() {
        return presupuestoRepository.listar();
    }

    @GET
    @Path("/{presupuestoId}")
    public Response obtenerPresupuesto(@PathParam("presupuestoId") Integer presupuestoId) {
        Optional<Presupuesto> presupuesto = Optional.ofNullable(presupuestoRepository.obtenerById(presupuestoId));
        if (presupuesto.isPresent()) {
            return Response.ok(presupuesto.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Presupuesto con ID " + presupuestoId + " no encontrado.")
                    .build();
        }
    }

    @GET
    @Path("/total-gastos/{presupuestoId}")
    public Response obtenerTotalGastos(@PathParam("presupuestoId") Integer presupuestoId) {
        Optional<Presupuesto> presupuestoOpt = Optional.ofNullable(presupuestoRepository.obtenerById(presupuestoId));
        if (presupuestoOpt.isPresent()) {
            Presupuesto presupuesto = presupuestoOpt.get();
            int totalGastos = presupuesto.getGastos().stream()
                    .mapToInt(Gastos::getMonto)
                    .sum();
            return Response.ok(totalGastos).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Presupuesto con ID " + presupuestoId + " no encontrado.")
                    .build();
        }
    }

    @POST
    @Path("/agregar-gasto/{presupuestoId}")
    public Response agregarGasto(@PathParam("presupuestoId") Integer presupuestoId, Gastos nuevoGasto) {
        Optional<Presupuesto> presupuestoOpt = Optional.ofNullable(presupuestoRepository.obtenerById(presupuestoId));
        if (presupuestoOpt.isPresent()) {
            Presupuesto presupuesto = presupuestoOpt.get();
            int totalGastos = presupuesto.getGastos().stream()
                    .mapToInt(Gastos::getMonto)
                    .sum();

            // Validar que la fecha del gasto esté dentro del rango del presupuesto
            if (nuevoGasto.getFecha().before(presupuesto.getFechaInicio()) ||
                    nuevoGasto.getFecha().after(presupuesto.getFechaFin())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("La fecha del gasto debe estar dentro del rango del presupuesto.")
                        .build();
            }

            // Validar que la suma de los gastos del presupuesto mas el gasto nuevo ingresado no supere al presupuestado
            if (totalGastos + nuevoGasto.getMonto() > presupuesto.getMontoPresupuestado()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El gasto supera el monto presupuestado. Total actual: " + totalGastos + "\nGasto adicional: " + nuevoGasto.getMonto())
                        .build();
            }

            Integer newId = presupuesto.getGastos().isEmpty() ? 1 : presupuesto.getGastos().stream()
                    .mapToInt(Gastos::getId)
                    .max()
                    .getAsInt() + 1;
            nuevoGasto.setId(newId);
            presupuesto.getGastos().add(nuevoGasto);
            presupuestoRepository.guardarDatos();

            return Response.status(Response.Status.CREATED).entity(nuevoGasto).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Presupuesto con ID " + presupuestoId + " no encontrado.")
                    .build();
        }
    }
}
