import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class App {
    private static Scanner scan = new Scanner(System.in);
    private static boolean appIsWork = true;
    private static ArrayList<String> filesDates = new ArrayList<>();
    private static final File file = new File("resourses/notifications.txt");
    public static void start() {
        System.out.println("======== WELCOME TO YUNYA NOTIFICATIONS!========\n\nAll commands: \nhelp      add     see     close     cancel");
        try (FileReader f = new FileReader(file)) {
            StringBuffer sb = new StringBuffer();
            while (f.ready()) {
                char c = (char) f.read();
                if (c == '\n') {
                    filesDates.add(sb.toString());
                    sb = new StringBuffer();
                } else {
                    sb.append(c);
                }
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
        for (int i = 0; i < filesDates.size(); i++) {
            String[] nameAndDate = filesDates.get(i).split("#");
            Alarm.getNotificationsArray().add(new Notifications(nameAndDate[1], LocalDateTime.parse(nameAndDate[2], Alarm.formatter), Integer.valueOf(nameAndDate[0])));
        }
    }

    public static File getFile() {
        return file;
    }

    public static boolean getAppIsWork() {
        return appIsWork;
    }

    public static void checkAnswer() {
        while (appIsWork) {
            String answer = scan.nextLine();
            switch (answer) {
                case "add":
                    add();
                    break;
                case "see":
                    see();
                    break;
                case "help":
                    help();
                    break;
                case "close":
                    close();
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
    }
    private static void add() {
        System.out.println("Enter the name of notification: ");
        String name = scan.nextLine();
        System.out.println("Enter the date of new notification (Format: yyyy-MM-dd HH:mm): ");
        String strDate = scan.nextLine();
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime date = LocalDateTime.parse(strDate, Alarm.formatter);
        if (date.isBefore(current)) {
            System.out.println("Incorrect date");
            return;
        }
        Alarm.getNotificationsArray().add(new Notifications(name, date, Notifications.getCount()));
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(Notifications.getCount() + "#" + name + "#" + strDate + "\n");
        } catch (IOException e) {
            System.out.println("File isn't saved!");
        }
        System.out.println("New notification created!");
    }
    private static void see() {
        Alarm.seeAllNotifications();
    }
    private static void help() {
        System.out.println("All commands: \nadd - Add a new reminder");
        System.out.println("see - Show all created reminders");
        System.out.println("close - Close the application");
        System.out.println("cancel - Сancels the action");
    }
    private static void close() {
        System.out.println("GoodBye!");
        appIsWork = false;
    }
}