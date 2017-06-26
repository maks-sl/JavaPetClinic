package ru.lesson.store;

import ru.lesson.lessons.Pet;
import ru.lesson.lessons.PetGenerator;
import ru.lesson.lessons.PetType;
import ru.lesson.service.Settings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Класс хранения животных в БД
 * Назначение @Override методов описано в интерфейсе PetStorage
 */
public class JdbcPetStorage implements PetStorage {

    /** Соединение с БД */
    private final Connection connection;

    /**
     * Конструктор соединения
     */
    public JdbcPetStorage() {
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
     * Принимает результаты запроса к БД в переменной rs,
     * после чего перебирает строки и на основании их добавляет животных в коллекцию toAdd
     * @param rs результат выборки из БД
     * @param toAdd массив для дополнения животными и возврата его в качестве результата
     * @return коллекция toAdd, дополненная животными из выборки rs
     * @throws SQLException ошибка при доступе к полям результата запроса
     */
    private Collection<Pet> petsFromResult(final ResultSet rs, Collection<Pet> toAdd) throws SQLException {
        while (rs.next()){
            int id = rs.getInt("id");
            String name =  rs.getString("name");
            int clientId = rs.getInt("client_id");
            int typeId = rs.getInt("type_id");
            toAdd.add(
                    PetGenerator.createPet(id, clientId, name, PetType.getTypeById(typeId))
            );


        }
        return toAdd;
    }

    @Override
    public Collection<Pet> values() {
        final List<Pet> pets = new ArrayList<>();
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM pet")) {
                this.petsFromResult(rs, pets);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }

    @Override
    public Collection<Pet> getByClientId(int clientId) {
        final List<Pet> pets = new ArrayList<>();
        try (final PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM pet WHERE client_id = ?")) {
            statement.setInt(1, clientId);
            final ResultSet rs = statement.executeQuery();
            this.petsFromResult(rs, pets);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }

    @Override
    public int add(int clientId, String name, PetType type) {

        try (final PreparedStatement statement = this.connection.prepareStatement("INSERT INTO pet(client_id, name, type_id) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, clientId);
            statement.setString(2, name);
            statement.setInt(3, PetType.getIdByPetType(type));
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
    public void edit(Pet pet) {
        try (final PreparedStatement statement = this.connection.prepareStatement("UPDATE pet SET name=?, type_id=? WHERE id=?")){
            statement.setString(1, pet.getName());
            statement.setInt(2, pet.getTypeId());
            statement.setInt(3, pet.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        try (final PreparedStatement statement = this.connection.prepareStatement("DELETE FROM pet WHERE id=?")){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pet get(int id) {
        try (final PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM pet WHERE id=(?)")){
            statement.setInt(1, id);
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()){
                    int pid = rs.getInt("id");
                    int cid =  rs.getInt("client_id");
                    String name =  rs.getString("name");
                    int tid = rs.getInt("type_id");

                    return PetGenerator.createPet(pid, cid, name, PetType.getTypeById(tid));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Pet not exist");
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
