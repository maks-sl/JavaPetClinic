package ru.lesson.lessons;

import java.util.*;

public class Clinic{

    /** клиенты клиники */
    private Set<Client> clients;
    /** пустой клиент */
    private final Client emptyClient = new Client("", new HashSet<Pet>());

    public Clinic(Set<Client> clients){
        this.clients = clients;
    }

    public Client getEmptyClient() {
        return emptyClient;
    }

    public Set<Client> getClients() {
        return clients;
    }

    /**
     * Добавляет клиента в список clients
     * @param client
     * @return true в случае удачи и false в случае неудачи
     * @throws Exception в случае если клиент с таким именем уже есть
     */
    public boolean addClient(Client client) throws Exception {
        if(this.clients.contains(client)) {throw new Exception("User with this name is already exist");}
//        for(Client c: this.clients){
//            if(c.getId().equals(client.getId())) {
//                throw new Exception("User with this name is already exist");
//            }
//        }
        return clients.add(client);
    }

    /**
     * удалить клиента из клиники
     * @param client экземпляр клиента
     * @return флаг успешности удаления
     */
    public boolean delClient(Client client){
        return clients.remove(client);
    }


    /**
     * поиск клиента по имени
     * @param name имя для поиска
     * @return экземпляр найденного клиента или если ничего не нашли то пустой клиент
     */
    public Client findClientByName(String name){
        Client toReturn = this.emptyClient;
        for (Client client: this.clients
             ) {
            if(client.getId().equals(name)){toReturn = client;}
        }
        return toReturn;
    }

    /**
     * найти список клиентов у которых есть животное с заданной кличкой
     * @param petName кличка животного
     * @return список найденных клиентов или если ничего не нашли то пустой список
     */
    public List<Client> findClientsByPetName(String petName) {

        List<Client> toReturn = new ArrayList<Client>();
        for (Client client: this.clients) {
            for (Pet pet: client.getPets()){
                if(pet.getName().equals(petName)){
                    toReturn.add(client);}
            }
        }
        return toReturn;
    }

}