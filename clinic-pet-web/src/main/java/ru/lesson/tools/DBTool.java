package ru.lesson.tools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.lesson.models.Client;
import ru.lesson.models.Pet;
import ru.lesson.store.Storages;

import java.util.*;

/**
 * Класс для управления приложением из консоли
 */
public class DBTool {

    /** КОНСТАНТНЫЙ набор комманд, допустимых для ввода пользователю */
    private static final Set<String> OPERATORS = new HashSet<String>(Arrays.asList("list", "add", "exit", "search", "addPet"));

    /** сканер ввода пользователя */
    private Scanner reader = new Scanner(System.in);

    /** флаг выхода */
    private boolean toExit = false;

    ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    Storages storages = context.getBean(Storages.class);

    public static void main(String[] arg){
        DBTool dbTool = new DBTool();
        dbTool.go();
    }

    /**
     * Запускает цикл обработки комманд
     */
    public void go(){

        try {
            /**
             * продолжаем пока не получим команду на выход
             * */
            while(!this.toExit){

                /**
                 * получаем оператор от пользователя и проверяем ее
                 * на соответствие команде 'выход'
                 * в случае если управляющая, начинаем новый ход цикла
                 */
                String operator = this.askOperator();
                if(this.checkExitCommand(operator)){continue;}

                /**
                 * если до сих пор не вылетели в предыдущей проверке,
                 * значит нам пришел оператор для выполнения к клинике
                 */
                this.executeCommand(operator);
            }

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            this.reader.close();
        }
    }

    /**
     * запрашивает у пользователя оператор
     * пока он не введет подходящий из заданного набора
     * @return оператор
     */
    private String askOperator(){
        String operator = new String();
        while(!this.OPERATORS.contains(operator)){
            System.out.println("Enter command from list below:");
            for (String o: this.OPERATORS){
                System.out.println("\t- "+o);
            }
            operator = this.reader.next();
        }
        return operator;
    }

    /**
     * проверяет оператор на соответствие команде "выход"
     * в случае если команда на выход то реализует ее выполнение
     * @param operator оператор
     * @return true если оператор является командой, false если нет
     */
    private boolean checkExitCommand(String operator){
        boolean isControlCommand = false;
        if(operator.equals("exit")){
            this.toExit = true;
            System.out.println("Good Bye!");
            isControlCommand = true;
        }
        return isControlCommand;
    }

    /**
     * проверяет действие которое получено и запускает нужный метод
     * @param operator действие из командной строки который ввел пользователь
     * @return информация полученная при выполнении операции
     */
    private void executeCommand(String operator) throws Exception {
        try {
            if (operator.equals("list")) this.listOfClients();
            if (operator.equals("add")) this.addClient();
//            if (operator.equals("del")) toReturn = this.delClient();
            if (operator.equals("search")) this.searchClients();
//            if (operator.equals("findPet")) toReturn = this.searchClientByPetName();
            if (operator.equals("addPet")) this.addPet();
//            if (operator.equals("delPet")) toReturn = this.delPet();
        }
        catch (Exception e){
            throw new Exception("Exception while execute command '"+operator+"'");
        }
    }

    /**
     * ищет все клиентов клиники
     * @return список пользователей
     */
    private void listOfClients(){
        System.out.println(this.storages.clientStorage.values());
    }

    private void addClient() throws Exception {
        System.out.println("ADD NEW CLIENT:...");
        System.out.println("Enter name:");
        String name = this.reader.next();
        System.out.println("Enter surname:");
        String surname = this.reader.next();
        System.out.println("Enter email:");
        String email = this.reader.next();
        System.out.println("Enter gender:");
        int gender = Integer.valueOf(this.reader.next());

        int cid = this.storages.clientStorage.add(name,surname,email,gender);

        System.out.println("Client with id:" +  cid + " has been added");
        System.out.println("If you want add a pet to client? (y/n)");
        if (this.reader.next().equals("y")) {
            try {
                //add Pet Dialog
                this.addPet(cid);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * запускает диалог добавления животного
     * @return результат добавления животного
     * @throws Exception перебрасывает исключение которое может кинуть addPet
     */
    private void addPet() throws Exception {
        System.out.println("ADD PET TO CLIENT:...");
        int cid = this.waitForClientId();
        this.addPet(cid);
    }

    private int waitForClientId(){
        int cid = 0;
        do {
            System.out.println("Enter the Client id:");
            try {
                int toCid = Integer.valueOf(this.reader.next());
                cid = this.storages.clientStorage.get(toCid).getId();
            }
            catch (Exception e){
                System.out.println("Sorry, client not found");
                continue;
            }
        }
        while (cid == 0);
        return cid;
    }

    /**
     * запускает диалог добавления животного для конкретного пользователя
     * @param cid id клиента для которого добавляется животное
     * @return
     * @throws Exception перебрасывает исключение которое может кинуть addPet
     */
    private void addPet(int cid) throws Exception {
        System.out.println("ADD PET TO CLIENT:...");
        System.out.println("Enter pet type (1-cat/2-dog/3-some pet):");
        String petType = this.reader.next();
        System.out.println("Enter pet name:");
        String petName = this.reader.next();

        Pet pet = new Pet();
        pet.setName(petName);
        pet.setOwner(this.storages.clientStorage.get(cid));
        pet.setPetType(storages.petTypeStorage.get(Integer.valueOf(petType)));
        int pid = this.storages.petStorage.add(pet);
        if(pid != 0)System.out.println("Pet has been added");
            else {System.out.println("Pet not added");}
    }

    /**
     * поиск клиента по имени
     * @return информация о пользователе с таким именем
     * @throws Exception даст исключение если пользователя с введенным именем не нашлось
     */
    private void searchClients() throws Exception{
        boolean found = false;
        System.out.println("SEARCH CLIENT:...");
        do {
            System.out.println("Enter name for search:");
            String clientName = this.reader.next();
            Collection<Client> foundedClients = this.storages.clientStorage.searchByName(clientName);
            System.out.println(foundedClients);
            if (foundedClients.size() == 0) {
                System.out.println("Sorry, nothing to show");
                continue;
            }
            System.out.println("If we found your client? (y/n)");
            String foundClient = this.reader.next();
            if(foundClient.equals("y")){
                found = true;
            }
        }while (!found);
    }



}
