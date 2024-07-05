import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.*;

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
                    try {
                        Files.createFile(Path.of("resourses/temp.txt"));
                        File temp = new File("resourses/temp.txt");

                        try (FileReader reader = new FileReader(App.getFile()); FileWriter writer = new FileWriter(temp)) {
                            Scanner scan = new Scanner(reader);
                            while (scan.hasNextLine()) {
                                String line = scan.nextLine();
                                String[] buffer = line.split("#");
                                if (Integer.valueOf(buffer[0]) != Integer.valueOf(notificationsArray.get(i).getId())) {
                                    writer.write(line + "\n");
                                }
                            }

                        } catch (Exception e) {
                            System.out.println("Error. File didn't deleted");
                        }
                        App.getFile().delete();
                        temp.renameTo(App.getFile());
                        notificationsArray.remove(i);
                    } catch (IOException e) {
                        System.out.println("Temp File didn't save");
                    }

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

class SoundPlayer {
    static void playSound(String soundFile) throws Exception {
        File f = new File(soundFile);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }

    public static void main(String[] args) throws Exception {
        playSound("resourses/message-sound.wav");
    }
}
