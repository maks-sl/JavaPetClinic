package ru.lesson.store;

import org.junit.Test;
import ru.lesson.models.Client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Тест для Hibernate
 */
public class HibernateClientStorageTest {
    @Test
    public void testCreate() throws Exception {
        final HibernateClientStorage storage = new HibernateClientStorage();
        final int id = storage.add("Valera", "Drakonov", "q@q.q", 1);
        final Client client = storage.get(id);
        assertEquals(id, client.getId());
        assertEquals(client, storage.get(id));
        storage.delete(id);
        assertNull(storage.get(id));
        storage.close();
    }
}