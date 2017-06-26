//package ru.lesson.lessons;
//
//import java.util.*;
//
//public class ClinicRunner{
//
//    /** КОНСТАНТНЫЙ набор комманд, допустимых для ввода пользователю */
//    private static final Set<String> OPERATORS = new HashSet<String>(Arrays.asList("list", "add", "del", "rename", "exit", "search", "findPet", "addPet", "delPet", "renamePet"));
//
//    /** сканер ввода пользователя */
//    private Scanner reader = new Scanner(System.in);
//
//    /** флаг выхода */
//    private boolean toExit = false;
//
//    /** экземпляр клиники */
//    private Clinic clinic;
//
//    /** в конструкторе принимает ArrayList с клиентами
//     * на его основе создаем клинику */
//    public ClinicRunner(Set<Client> clients) {
//        this.clinic = new Clinic(clients);
//    }
//
//    public void go(){
//
//        try {
//            /**
//             * продолжаем пока не получим команду на выход
//             * */
//            while(!this.toExit){
//
//                /**
//                 * получаем оператор от пользователя и проверяем ее
//                 * на соответствие управляющей команде
//                 * в случае если управляющая, начинаем новый ход цикла
//                 */
//                String operator = this.askOperator();
//                if(this.checkControlCommand(operator)){continue;}
//
//                /**
//                 * если до сих пор не вылетели в предыдущей проверке,
//                 * значит нам пришел оператор для выполнения к клинике
//                 */
//
//                System.out.println(this.executeCommand(operator));
//            }
//
//        } finally {
//            this.reader.close();
//        }
//    }
//
//    /**
//     * запрашивает у пользователя оператор
//     * пока он не введет подходящий из заданного набора
//     * @return оператор
//     */
//    private String askOperator(){
//        String operator = new String();
//        while(!ClinicRunner.OPERATORS.contains(operator)){
//            System.out.println("Enter command list, add, exit, search, findPet, addPet, delPet, renamePet");
//            operator = this.reader.next();
//        }
//        return operator;
//    }
//
//    /**
//     * проверяет оператор на соответствие управляющей команде
//     * в случае если команда то реализует ее выполнение
//     * @param operator оператор
//     * @return true если оператор является командой, false если нет
//     */
//    private boolean checkControlCommand(String operator){
//        boolean isControlCommand = false;
//        if(operator.equals("exit")){
//            this.toExit = true;
//            System.out.println("Good Bye!");
//            isControlCommand = true;
//        }
//        return isControlCommand;
//    }
//
//    /**
//     * Запускает цикл запроса имени
//     * и продолжает его пока не найдет экземпляр Client
//     * @return экземпляр клиента
//     */
//    private Client waitForClient(){
//        Client client = this.clinic.getEmptyClient();
//        do {
//            System.out.println("Enter Client name: ");
//            client = this.clinic.findClientByName(this.reader.next());
//        }
//        while (client.equals(this.clinic.getEmptyClient()));
//        return client;
//    }
//
//    /**
//     * проверяет действие которое получено и запускает нужный метод
//     * @param operator действие из командной строки который ввел пользователь
//     * @return информация полученная при выполнении операции
//     */
//    private String executeCommand(String operator){
//        String toReturn = "";
//        try {
//            if (operator.equals("list")) toReturn = this.listOfClients();
//            if (operator.equals("add")) toReturn = this.addClient();
//            if (operator.equals("del")) toReturn = this.delClient();
//            if (operator.equals("rename")) toReturn = this.renameClient();
//            if (operator.equals("search")) toReturn = this.searchClient();
//            if (operator.equals("findPet")) toReturn = this.searchClientByPetName();
//            if (operator.equals("addPet")) toReturn = this.addPet();
//            if (operator.equals("delPet")) toReturn = this.delPet();
////            if (operator.equals("renamePet")) toReturn = this.renamePet();
//        }
//        catch (Exception e){
//            toReturn = e.getMessage();
//        }
//        return toReturn;
//    }
//
//    /**
//     * ищет все клиентов клиники
//     * @return список пользователей
//     */
//    private String listOfClients(){
//        String listOfClients = "";
//        listOfClients += "CLIENTS:" + "\r\n";
//        for (Client c: this.clinic.getClients()){
//            listOfClients += c.toString() + "\r\n";
//        }
//        return listOfClients;
//    }
//
//    /**
//     * запускает диалог добавления клиента
//     * и пробует добавить экземпляр
//     * в случае успешного добавления спрашивает хотим ли мы добавить питомца для созданного клиента
//     * и пробует добавить его, сообщая об ошибке в случае неудачи
//     * @return информация о добавленном клиенте
//     * @throws Exception
//     */
//    private String addClient() throws Exception {
//        String toReturn = "";
//        System.out.println("ADD CLIENT:...");
//        System.out.println("Enter Client name:");
//        String name = this.reader.next();
//
//        Client addClient = new Client(name, new HashSet<Pet>());
//        this.clinic.addClient(addClient);
//        toReturn = "Client " +  addClient.getId() + " has been added";
//        System.out.println("If you want add a pet to client? (y/n)");
//        if (this.reader.next().equals("y")) {
//            try {
//                toReturn += ". " + this.addPet(addClient);
//            }
//            catch (Exception e){toReturn += ". " + e.getMessage();}
//        }
//        return toReturn;
//    }
//
//    /**
//     * удаляет клиента
//     * @return результат удаления
//     */
//    private String delClient(){
//        System.out.println("DEL CLIENT:... ");
//        Client delClient = this.waitForClient();
//        this.clinic.delClient(delClient);
//        return "Client has been deleted";
//    }
//
//    /**
//     * изменение имени клиента
//     * @return результат изменения
//     */
//    private String renameClient(){
//        System.out.println("RENAME CLIENT:...");
//        Client renameClient = this.waitForClient();
//        System.out.println("Enter New Name:");
//        renameClient.setId(this.reader.next());
//        return "Client has been renamed";
//    }
//
//    /**
//     * поиск клиента по имени
//     * @return информация о пользователе с таким именем
//     * @throws Exception даст исключение если пользователя с введенным именем не нашлось
//     */
//    private String searchClient() throws Exception{
//        System.out.println("SEARCH CLIENT:...");
//        System.out.println("Enter name for search:");
//        String clientName = this.reader.next();
//        Client searchResult = this.clinic.findClientByName(clientName);
//        if(searchResult.equals(this.clinic.getEmptyClient()))
//        {
//            throw new Exception("Client with name " + clientName + " not found");
//        }
//        return searchResult.toString();
//    }
//
//    /**
//     * ищет клиентов у которых есть животное с заданным именем
//     * @return список найденных клиентов
//     * @throws Exception бросит исключение если животных с такой кличкой у клиентов нет
//     */
//    private String searchClientByPetName() throws Exception{
//        String toReturn = null;
//        System.out.println("SEARCH CLIENT BY PET NAME:...");
//        System.out.println("Enter pet name for search:");
//        String petName = this.reader.next();
//        for(Client c: this.clinic.findClientsByPetName(petName)){
//            toReturn += c.toString();
//            toReturn += "\r\n";
//        }
//        if(toReturn == null) throw new Exception("Clients with pet " + petName + " not found");
//        return toReturn;
//    }
//
//    /**
//     * запускает диалог добавления животного
//     * @return результат добавления животного
//     * @throws Exception перебрасывает исключение которое может кинуть addPet
//     */
//    private String addPet() throws Exception {
//        System.out.println("ADD PET TO CLIENT:...");
//        Client addPetClient = this.waitForClient();
//        System.out.println("Enter pet type (cat/dog):");
//        String petType = this.reader.next();
//        System.out.println("Enter pet name:");
//        String petName = this.reader.next();
//        addPetClient.addPet(petType, petName);
//        return "Pet has been added";
//    }
//
//    /**
//     * запускает диалог добавления животного для конкретного пользователя
//     * @param addPetClient клиент для которого добавляется животное
//     * @return
//     * @throws Exception перебрасывает исключение которое может кинуть addPet
//     */
//    private String addPet(Client addPetClient) throws Exception {
//        System.out.println("ADD PET TO CLIENT:...");
//        System.out.println("Enter pet type (cat/dog):");
//        String petType = this.reader.next();
//        System.out.println("Enter pet name:");
//        String petName = this.reader.next();
//        addPetClient.addPet(petType, petName);
//        return "Pet has been added";
//    }
//
//    /**
//     * запускает диалог удаления животного
//     * @return результат удаления животного
//     * @throws Exception перебрасывает исключение которое может кинуть delPet
//     */
//    private String delPet() throws Exception{
//        System.out.println("DEL PET OF CLIENT:...");
//        Client delPetClient = this.waitForClient();
//        System.out.println("Enter pet name for delete:");
//        String petName = this.reader.next();
//        delPetClient.delPet(petName);
//        return "Pet has been deleted";
//    }
//
//    /**
//     * запускает диалог изменения клички животного
//     * @return результат изменения клички животного
//     * @throws Exception перебрасывает исключение которое может кинуть renamePet
//     */
////    private String renamePet() throws Exception{
////        System.out.println("RENAME PET OF CLIENT:...");
////        Client renamePetClient = this.waitForClient();
////        System.out.println("Enter pet name for rename:");
////        String petName = this.reader.next();
////        System.out.println("Enter pet new name:");
////        String petNewName = this.reader.next();
////        renamePetClient.renamePet(petName, petNewName);
////        return "Pet has been renamed";
////    }
//
//}