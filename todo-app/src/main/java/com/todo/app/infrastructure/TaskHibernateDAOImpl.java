package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.filters.Filter;
import com.todo.app.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import com.todo.app.exceptions.PersistentException;

public class TaskHibernateDAOImpl implements TaskDAO {
    EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();

    @Override
    public void save(Task task) {
        try {
            entity.getTransaction().begin();
            entity.persist(task);
            entity.getTransaction().commit();
        } catch (PersistentException exception) {
            throw new PersistentException("Error while saving in Hibernate", exception);
        }
    }

    @Override
    public void saveList(List<Task> tasks) {

    }

    @Override
    public void update(UUID id, Task task) {

    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }

    @Override
    public boolean deleteByFilter(Filter filter) {
        return false;
    }

    @Override
    public Task read(UUID id) {
        return null;
    }

    @Override
    public List<Task> loadTasks() {
        return null;
    }

    @Override
    public List<Task> find(Filter filter) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int countByFilter(Filter filter) {
        return 0;
    }
}
