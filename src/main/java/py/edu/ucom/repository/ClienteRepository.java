package py.edu.ucom.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import py.edu.ucom.entities.apiresponse.Cliente;

import java.util.List;

@ApplicationScoped
public class ClienteRepository implements PanacheRepositoryBase<Cliente, Integer> {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Cliente> buscarClientes(String nombres, String apellidos) {
        StringBuilder queryBuilder = new StringBuilder("SELECT c FROM Cliente c WHERE 1=1");

        if (nombres != null && !nombres.isEmpty()) {
            queryBuilder.append(" AND LOWER(c.nombres) LIKE LOWER(:nombres)");
        }
        if (apellidos != null && !apellidos.isEmpty()) {
            queryBuilder.append(" AND LOWER(c.apellidos) LIKE LOWER(:apellidos)");
        }

        TypedQuery<Cliente> query = entityManager.createQuery(queryBuilder.toString(), Cliente.class);

        if (nombres != null && !nombres.isEmpty()) {
            query.setParameter("nombres", "%" + nombres + "%");
        }
        if (apellidos != null && !apellidos.isEmpty()) {
            query.setParameter("apellidos", "%" + apellidos + "%");
        }

        return query.getResultList();
    }
}