import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Alarm {
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
