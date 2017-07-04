package ru.lesson.store;

import ru.lesson.lessons.Pet;
import ru.lesson.models.Client;
import ru.lesson.service.Settings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Класс хранения клиентов в БД
 * Назначение @Override методов описано в интерфейсе ClientStorage
 */
public class JdbcClientStorage implements ClientStorage {

    /** Соединение с БД */
    private final Connection connection;

    /**
     * Конструктор соединения
     */
    JdbcClientStorage() {
        final Settings settings = Settings.getInstance();
        try {
            Class.forName(settings.value("jdbc.driver_class"));  // Не удалось настроить без этого
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.connection = DriverManager.getConnection(settings.value("jdbc.url"), settings.value("jdbc.username"), settings.value("jdbc.password"));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     *
     * Принимает результаты запроса к БД в переменной rs,
     * после чего перебирает строки и на основании их добавляет клиентов в коллекцию toAdd
     * @param rs результат выборки из БД
     * @param toAdd массив для дополнения клиентами и возврата его в качестве результата
     * @return коллекция toAdd, дополненная клиентами из выборки rs
     * @throws SQLException ошибка при доступе к полям запроса
     */
    private Collection<Client> clientsFromResult(final ResultSet rs, Collection<Client> toAdd) throws SQLException {
        while (rs.next()){
            int id = rs.getInt("id");
            String name =  rs.getString("name");
            String surname =  rs.getString("surname");
            String email =  rs.getString("email");
            int gender = rs.getInt("gender");
            toAdd.add(
                    new Client(id, name, surname, email, gender)
            );
        }
        return toAdd;
    }

    @Override
    public Collection<Client> values() {
        final List<Client> clients = new ArrayList<>();
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM client")) {
            this.clientsFromResult(rs, clients);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public int add(String name, String surname, String email, int gender) {

        try (final PreparedStatement statement = this.connection.prepareStatement("INSERT INTO client(name, surname, email, gender) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, email);
            statement.setInt(4, gender);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()){ return generatedKeys.getInt(1);}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Not created");

    }

    @Override
    public void edit(Client client) {
        try (final PreparedStatement statement = this.connection.prepareStatement("UPDATE client SET name=?, surname=?, email=?, gender=? WHERE id=?")){
            statement.setString(1, client.getName());
            statement.setString(2, client.getSurname());
            statement.setString(3, client.getEmail());
            statement.setInt(4, client.getGender());
            statement.setInt(5, client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        try (final PreparedStatement statement = this.connection.prepareStatement("DELETE FROM client WHERE id=?")){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client get(int id) {
        try (final PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM client WHERE id=(?)")){
            statement.setInt(1, id);
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()){
                    int cid = rs.getInt("id");
                    String name =  rs.getString("name");
                    String surname =  rs.getString("surname");
                    String email =  rs.getString("email");
                    int gender = rs.getInt("gender");
                    return new Client(cid, name, surname, email, gender);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Client not exist");
    }

    @Override
    public Collection<Client> searchOr(String clientName, String petName) {
        Collection<Client> toReturn = new HashSet<>();
        toReturn.addAll(searchByName(clientName));
        toReturn.addAll(searchByPetName(petName));
        return toReturn;
    }

    @Override
    public Collection<Client> searchAnd(String clientName, String petName) {
        Collection<Client> toReturn = new ArrayList<>();
        Collection<Client> cPetName = searchByPetName(petName);
        for (Client c :searchByName(clientName)){
            if(cPetName.contains(c)) toReturn.add(c);
        }
        return toReturn;
    }

    @Override
    public Collection<Client> searchByName(String clientName) {
        final Collection<Client> toReturn = new ArrayList<>();
        try (final PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM client WHERE lower(name) || ' ' || lower(surname) like (?)")){
            statement.setString(1, '%'+clientName.toLowerCase()+'%');
            try (final ResultSet rs = statement.executeQuery()) {
                this.clientsFromResult(rs, toReturn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public Collection<Client> searchByPetName(String petName) {

        final Collection<Client> toReturn = new ArrayList<>();
        /*try (final PreparedStatement statement = this.connection.prepareStatement("SELECT c.* FROM client c join pet p ON c.id = p.client_id WHERE lower(p.name) like (?)")){
            statement.setString(1, '%'+petName.toLowerCase()+'%');
            try (final ResultSet rs = statement.executeQuery()) {
                this.clientsFromResult(rs, toReturn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        for (Client client: this.values()){
            if(!"".equals(petName)){
                for (Pet pet: client.getPetsOfClient()){
                    if(pet.getName().toLowerCase().contains(petName.toLowerCase())) toReturn.add(client);
                }
            }
        }
        return toReturn;
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
