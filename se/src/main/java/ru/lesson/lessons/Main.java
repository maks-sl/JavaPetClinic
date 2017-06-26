//package ru.lesson.lessons;
//
//import java.util.*;
//
//public class Main{
//
//    public static void main(String[] arg){
//
//        Set<Client> clients = new HashSet<>();
//
//        clients.add(new Client(
//                "Vasiliy",
//                new HashSet<Pet>(Arrays.asList(
//                    new Pet[]
//                    {
//                        new Dog("Shurik"),
//                        new Cat("Grom")
//                    }
//        ))));
//
//        clients.add(new Client(
//                "Denis",
//                new HashSet<Pet>(Arrays.asList(
//                        new Pet[]
//                                {
//                                        new Cat("Vas'ka"),
//                                        new Cat("Bor'ka")
//                                }
//                ))));
//
//        clients.add(new Client(
//                "Petya",
//                new HashSet<Pet>()));
//
//        clients.add(new Client(
//                "Petya",
//                new HashSet<Pet>()));
//
//
//        ClinicRunner cr = new ClinicRunner(clients);
//        cr.go();
//    }
//}
