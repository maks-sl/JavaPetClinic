package ru.lesson.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lesson.models.Appuser;

import java.util.Collection;

/**
 * Класс хранения типов животных в БД использующий Hibernate
 * Назначение @Override методов описано в интерфейсе IHTUserStorage
 */
@Repository
@Transactional
public class HTUserStorage implements IHTUserStorage{

    private final HibernateTemplate ht;

    /**
     * В конструкторе инициализируем HibernateTemplate
     */
    @Autowired
    public HTUserStorage(HibernateTemplate ht) {
        this.ht = ht;
    }

    @Override
    public Appuser findOnAuth(String login, String password) {
        Appuser toReturn = null;
        String[] paramsName = {"login", "password"};
        Object[] paramsVal = {login, password};
        this.ht.setFetchSize(1);
        Collection<Appuser> found =  (Collection<Appuser>) this.ht.findByNamedParam(
                "from Appuser as u join fetch u.role as ur where u.login = :login and u.password = :password",
                paramsName,
                paramsVal);
        if (!found.isEmpty()){
            toReturn = found.iterator().next();
        }
        return toReturn;
    }

    @Override
    public Collection<Appuser> values() {
        return (Collection<Appuser>) this.ht.find("from Appuser ");
    }

    @Override
    public int add(Appuser user) {
        return (int) this.ht.save(user);
    }

    @Override
    public void edit(Appuser user) {
        this.ht.update(user);
    }

    @Override
    public void delete(int id) {
        this.ht.delete(this.ht.get(Appuser.class, id));
    }

    @Override
    public Appuser get(int id) {
        return this.ht.get(Appuser.class, id);
    }

    @Override
    public void close() {}
}
