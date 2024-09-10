package py.edu.ucom.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import py.edu.ucom.entities.apiresponse.Cliente;

@ApplicationScoped
public class ClienteRepository implements PanacheRepositoryBase<Cliente, Integer> {

}
