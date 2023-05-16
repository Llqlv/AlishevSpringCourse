package com.llqlv.springcourse.dao;

import com.llqlv.springcourse.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
public class UserDaoHibernateImpl implements UserDao {

    //ПОЛНАЯ ЗАМЕНА Hibernate методов и классов на спецификацию JPA
    // https://www.baeldung.com/jpa-hibernate-persistence-context
    // https://www.baeldung.com/hibernate-entitymanager

//    private final SessionFactory sessionFactory;
    @PersistenceContext
    private EntityManager entityManager;



    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return entityManager.createQuery("SELECT U FROM User U", User.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getPersonById(int id) {

        return Optional.ofNullable(entityManager.find(User.class, id));

    }

    // Было так
    /*@Transactional
    @Override
    public void save(User user) {
        var session = sessionFactory.getCurrentSession();

        session.save(user);
    }*/
    // Стало так
    @Transactional
    @Override
    public void save(User user) {
        entityManager.merge(user);
    }

    // Было так
    /*@Transactional
    @Override
    public void update(int id, User updatedUser) {
        var session = sessionFactory.getCurrentSession();

        var userToBeUpdated = session.get(User.class, id);

        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setAge(updatedUser.getAge());
        userToBeUpdated.setEmail(updatedUser.getEmail());
    }*/
    //Стало так
    @Transactional
    @Override
    public void update(int id, User updatedUser) {
        var userToBeUpdated = entityManager.find(User.class, id);

        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setAge(updatedUser.getAge());
        userToBeUpdated.setEmail(updatedUser.getEmail());

        entityManager.detach(userToBeUpdated);
    }

    @Override
    @Transactional
    public void delete(int id) {
//        var session = sessionFactory.getCurrentSession();

//        session.remove(session.get(User.class, id));
        entityManager.remove(entityManager.find(User.class, id));
    }


    @Override
    @Transactional
    public Optional<User> getPersonByEmail(String email) {

        var query = entityManager.createQuery("SELECT user FROM User user WHERE user.email =:email");
        query.setParameter("email", email);
        Optional<User> user = query.getResultList().stream().findFirst();

        return user;
    }
}
