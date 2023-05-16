package com.llqlv.springcourse.dao;

import com.llqlv.springcourse.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        var session = sessionFactory.getCurrentSession();
        // Дальнейшая работа с Hibernate

        var userU = session.createQuery("SELECT U FROM User U", User.class).getResultList();

        return userU;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getPersonById(int id) {
        var session = sessionFactory.getCurrentSession();

        return Optional.ofNullable(session.get(User.class, id));
    }

    @Transactional
    @Override
    public void save(User user) {
        var session = sessionFactory.getCurrentSession();

        session.save(user);
    }

    @Transactional
    @Override
    public void update(int id, User updatedUser) {
        var session = sessionFactory.getCurrentSession();

        var userToBeUpdated = session.get(User.class, id);

        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setAge(updatedUser.getAge());
        userToBeUpdated.setEmail(updatedUser.getEmail());
    }

    @Override
    @Transactional
    public void delete(int id) {
        var session = sessionFactory.getCurrentSession();

        session.remove(session.get(User.class, id));
    }

    @Override
    @Transactional
    public Optional<User> getPersonByEmail(String email) {
        var session = sessionFactory.getCurrentSession();

        Query<User> query = session.createQuery("from User u where u.email=:email", User.class);
        query.setParameter("email", email);
        User user = query.uniqueResult();

        return Optional.ofNullable(user);
    }
}
