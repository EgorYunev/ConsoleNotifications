import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("======== WELCOME TO YUNYA NOTIFICATIONS!========\n\nAll commands: \nhelp      add     see     close");
        new Thread(Alarm::alarmNotification).start();
        Scanner scan = new Scanner(System.in);
        close:
        while (true) {
            String answer = scan.nextLine();
            switch (answer) {
                case "add":
                    System.out.println("Enter the name of notification: ");
                    String name = scan.nextLine();
                    System.out.println("Enter the date of new notification (Format: yyyy-MM-dd HH:mm): ");
                    String strDate = scan.nextLine();
                    LocalDateTime date = LocalDateTime.parse(strDate, Alarm.formatter);
                    Alarm.getNotificationsArray().add(new Notifications(name, date));
                    break;
                case "see":
                    Alarm.seeAllNotifications();
                    break;
                case "help":
                    System.out.println("All commands: \nadd - Add a new reminder");
                    System.out.println("see - Show all created reminders");
                    System.out.println("close - Close the application");
                    break;
                case "close" :
                    System.out.println("GoodBye!");
                    break close;
            }
        }
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
        while (true) {
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