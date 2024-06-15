import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        App.start();
        new Thread(App::checkAnswer).start();
        new Thread(Alarm::alarmNotification).start();
    }
}

class App {
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
        Alarm.getNotificationsArray().add(new Notifications(name, date));
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(name + "|" + strDate + "\n");
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

class Notifications {
    private LocalDateTime notificationTime;
    private String name;

    public Notifications(String name, LocalDateTime time) {
        notificationTime = time;
        this.name = name;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public String getName() {
        return name;
    }
}

class Alarm {
    private static ArrayList<Notifications> notificationsArray = new ArrayList<>();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static ArrayList<Notifications> getNotificationsArray() {
        return notificationsArray;
    }

    public static void alarmNotification() {
        while (App.getAppIsWork()) {
            LocalDateTime current = LocalDateTime.now();
            for (int i = 0; i < notificationsArray.size(); i++) {
                if (notificationsArray.get(i).getNotificationTime().isBefore(current)) {
                    System.out.println("It's time for: " + notificationsArray.get(i).getName());
                    notificationsArray.remove(i);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void seeAllNotifications() {
        for (Notifications date : notificationsArray) {
            System.out.println(date.getName() + " " + date.getNotificationTime().format(formatter));
        }
    }
}