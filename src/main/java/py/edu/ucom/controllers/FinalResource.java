package py.edu.ucom.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import py.edu.ucom.entities.apiresponse.Cliente;
import py.edu.ucom.entities.apiresponse.PresupuestoMensual;
import py.edu.ucom.repository.PresupuestoMensualRepository;
import py.edu.ucom.repository.ClienteRepository;

import java.util.List;

@Path("/final")
public class FinalResource {

    @Inject
    PresupuestoMensualRepository presupuestoMensualRepository;

    @Inject
    ClienteRepository clienteRepository;

    @GET
    @Path("/count")
    public Response contarPresupuestos() {
        long total = presupuestoMensualRepository.contar();
        return Response.ok(total).build();
    }

    @GET
    @Path("/presupuestos-mensual/{rangoInicial}/{rangoFinal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrarPresupuestosPorRango(@PathParam("rangoInicial") int rangoInicial,
                                                @PathParam("rangoFinal") int rangoFinal) {
        List<PresupuestoMensual> presupuestos = presupuestoMensualRepository.filtrarPorRangoMontos(rangoInicial, rangoFinal);

        if (presupuestos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensaje\": \"No se encontraron presupuestos en el rango especificado\"}")
                    .build();
        }
        return Response.ok(presupuestos).build();
    }

    @GET
    @Path("/presupuestos-mensual/mayor-presupuesto")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMayorPresupuesto() {
        List<PresupuestoMensual> presupuestos = presupuestoMensualRepository.obtenerPresupuestosConMayorMonto();

        if (presupuestos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensaje\": \"No se encontraron presupuestos.\"}")
                    .build();
        }
        return Response.ok(presupuestos).build();
    }

    @GET
    @Path("/buscar")
    public Response buscarClientes(@QueryParam("nombres") String nombres,
                                   @QueryParam("apellidos") String apellidos) {
        List<Cliente> clientes = clienteRepository.buscarClientes(nombres, apellidos);

        if (clientes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No se encontraron clientes.")
                    .build();
        }
        return Response.ok(clientes).build();
    }
}