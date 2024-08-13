package py.edu.ucom;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/recursos-basicos")
public class RecursosBasicos {

    @Path("suma")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Integer recursosBasicosSuma() {
        return 5+5;
    }

    @Path("resta")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Integer recursosBasicosResta() {
        return 100-5;
    }

}
