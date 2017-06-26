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
 * Created by User on 22.06.2017.
 */
public class JdbcClientStorage implements ClientStorage {

    private final Connection connection;

    public JdbcClientStorage() {
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
        final List<Client> clients = new ArrayList<Client>();
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
            try (final ResultSet rs = statement.executeQuery();) {
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
        throw new IllegalStateException("User not exist");
    }

    @Override
    public Collection<Client> searchOr(String clientName, String petName) {
        Collection<Client> toReturn = new ArrayList<>();
        toReturn.addAll(searchByName(clientName));
        toReturn.removeAll(searchByPetName(petName));
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
        final List<Client> toReturn = new ArrayList<Client>();
        try (final PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM client WHERE lower(name) || ' ' || lower(surname) like (?)")){
            statement.setString(1, '%'+clientName.toLowerCase()+'%');
            try (final ResultSet rs = statement.executeQuery();) {
                this.clientsFromResult(rs, toReturn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public Collection<Client> searchByPetName(String petName) {

        Collection<Client> toReturn = new ArrayList<>();
        for (Client client: this.values()){
            if(petName != ""){
                for (Pet pet: client.getPets()){
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
