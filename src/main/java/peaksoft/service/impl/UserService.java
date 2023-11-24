package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.model.Role;
import peaksoft.model.User;
import peaksoft.service.ModelService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserService implements ModelService<User> {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        if(findAll().isEmpty()){
            Role adminRole = roleService.findByName("ADMIN");
            adminRole.setUserList(Collections.singletonList(user));
            user.setRoles(Collections.singletonList(adminRole));
        }else {
            Role useRole = roleService.findByName("USER");
            useRole.setUserList(Collections.singletonList(user));
            user.setRoles(Collections.singletonList(useRole));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }
    public User findByGmail(String gmail){
        return entityManager.createQuery("select u from User u where u.gmail=:gmail", User.class)
                .setParameter("gmail",gmail).getSingleResult();

    }
    @Override
    public List<User>findAll(){
        return entityManager.createQuery("from User ",User.class).getResultList();
    }
    @Override
    public User findById(Long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }


    @Override
    public void update(Long id, User user) {
        User user1 = findById(id);
        user1.setName(user.getName());
        user1.setAge(user.getAge());
        user1.setGmail(user.getGmail());
        user1.setPassword(user.getPassword());
        user1.setLocalDate(user.getLocalDate());
        user1.setSubscriptionStatus(user.getSubscriptionStatus());
        entityManager.persist(user1);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));

    }

    public User getUserByEmailAndPassword(String gmail, String password) {
        return (User) entityManager.createQuery("select u from  User u where u.gmail =:gmail and u.password=:pas")
                .setParameter("gmail", gmail)
                .setParameter("pas", password).getSingleResult();

    }
}
