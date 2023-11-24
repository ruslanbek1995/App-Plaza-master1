package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import peaksoft.model.MailSender;
import peaksoft.model.User;
import peaksoft.service.ModelService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MailSenderService implements ModelService<MailSender> {
    @PersistenceContext
    private EntityManager entityManager;

    private final UserService userService;
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailSenderService(UserService userService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    public void save(MailSender mailSender) {
        List<User> userList = userService.findAll();
        for (User user : userList) {
            if (user.isSubscribeToTheNewsLetter()) {
                sendMessage(mailSender, user.getGmail());
            }
        }
        mailSender.setCreateDate(LocalDate.now());
        entityManager.persist(mailSender);
    }

    private void sendMessage(MailSender mailSender, String gmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(gmail);
        message.setFrom("AppPlaza");
        message.setSubject(mailSender.getSender());
        message.setText(mailSender.getText());
        javaMailSender.send(message);
    }

    public List<MailSender> findAll() {
        return entityManager.createQuery("from MailSender ", MailSender.class).getResultList();
    }

//    @Override
//    public void save(MailSender mailSender) {
//        mailSender.setCreateDate(LocalDate.now());
//        entityManager.persist(mailSender);
//    }

    @Override
    public MailSender findById(Long id) {
        return entityManager.find(MailSender.class, id);
    }

    @Override
    public void update(Long id, MailSender mailSender) {
        MailSender mailSender1 = findById(id);
        mailSender1.setSender(mailSender.getSender());
        mailSender1.setText(mailSender.getText());
        mailSender1.setCreateDate(mailSender.getCreateDate());
        entityManager.persist(mailSender1);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }
}
