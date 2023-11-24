package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.model.Application;
import peaksoft.model.Genre;
import peaksoft.model.enums.AppStatus;
import peaksoft.service.ModelService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ApplicationService implements ModelService<Application> {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void save(Application application) {
        Genre genre = entityManager.find(Genre.class, application.getGenreId());
        application.setGenre(genre);
        application.setGenreName(genre.getName());
        application.setLocalDate(LocalDate.now());
        entityManager.persist(application);
    }

    @Override
    public Application findById(Long id) {
        return entityManager.find(Application.class, id);
    }

    @Override
    public List<Application> findAll() {
        List<Application>applications= entityManager.createQuery("from Application ").getResultList();
        return applications;
    }
    @Override
    public void update(Long id, Application application) {
        Application application1 = findById(id);
        application1.setName(application.getName());
        application1.setDescription(application.getDescription());
//        if (application1.getGenre()!=null){
//            application1.setGenre(null);
//        }
        if (application1.getGenre()!=null){
            application1.setGenre(application.getGenre());
            application1.setLocalDate(application.getLocalDate());
        }
        entityManager.persist(application1);

    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));

    }
}
