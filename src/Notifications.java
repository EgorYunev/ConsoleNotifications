import java.time.LocalDateTime;

public class Notifications {
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
