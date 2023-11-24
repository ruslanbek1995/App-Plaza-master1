package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.model.Role;

import java.util.List;

@Service
@Transactional

public class RoleService {
    @PersistenceContext
    private EntityManager entityManager;
    public Role findByName(String roleName){
       return entityManager.createQuery("select r from  Role r where  r.name =:rolename", Role.class)
                .setParameter("rolename", roleName).getSingleResult();
    }
    public List<Role> findByAll(){
        return  entityManager.createQuery("from  Role ", Role.class).getResultList();
    }
}
