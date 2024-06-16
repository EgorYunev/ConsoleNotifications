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