import java.time.LocalDateTime;

public class Notifications {
    private LocalDateTime notificationTime;
    private String name;
    private int id;
    private static int count = 0;

    public Notifications(String name, LocalDateTime time, int id) {
        notificationTime = time;
        this.name = name;
        this.id = id;
        count++;

    }
    public static int getCount() {
        return count;
    }
    public int getId() {
        return id;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public String getName() {
        return name;
    }
}
