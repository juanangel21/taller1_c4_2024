package py.edu.ucom;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/resources")
public class GreetingResource {

    @Path("hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @Path("recursos-baicos")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String recursosBaicos() {
        return "recursos-baicos";
    }

    @Path("recursos-baicos/suma")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Integer recursosBaicosSuma() {
        return 5+5;
    }

    @Path("recursos-baicos/resta")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Integer recursosBaicosResta() {
        return 100-5;
    }

}
