package py.edu.ucom.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import py.edu.ucom.entities.apiresponse.PresupuestoMensual;
import py.edu.ucom.repository.PresupuestoMensualRepository;

import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@Path("/presupuestos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PresupuestoResource {

    @Inject
    PresupuestoMensualRepository presupuestoMensualRepository;

    @POST
    public Response crearPresupuesto(PresupuestoMensual presupuestoMensual) {
        PresupuestoMensual nuevoPresupuesto = presupuestoMensualRepository.crearPresupuesto(presupuestoMensual);
        return Response.status(Response.Status.CREATED).entity(nuevoPresupuesto).build();
    }

    @GET
    public List<PresupuestoMensual> listarPresupuestos() {
        return presupuestoMensualRepository.listar();
    }

    @GET
    @Path("/{presupuestoId}")
    public Response obtenerPresupuesto(@PathParam("presupuestoId") Integer presupuestoId) {
        Optional<PresupuestoMensual> presupuesto = presupuestoMensualRepository.obtenerById(presupuestoId);
        if (presupuesto.isPresent()) {
            return Response.ok(presupuesto.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Presupuesto no encontrado").build();
        }
    }

    @DELETE
    @Path("/{presupuestoId}")
    public Response eliminarPresupuesto(@PathParam("presupuestoId") Integer presupuestoId) {
        Optional<PresupuestoMensual> presupuestoOpt = presupuestoMensualRepository.obtenerById(presupuestoId);
        if (presupuestoOpt.isPresent()) {
            presupuestoMensualRepository.eliminar(presupuestoOpt.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Presupuesto no encontrado").build();
        }
    }
}