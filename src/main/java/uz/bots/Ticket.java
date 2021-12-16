package uz.bots;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.bots.service.LoopWriter;
import uz.bots.service.TelegramBot;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Ticket {
    public static final Properties PROPERTIES;
    public static Connection connection = null;
    public static Statement statement = null;

    static {
        PROPERTIES = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("config.ini")) {
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws TelegramApiException, SQLException {
                System.out.println("Bot running");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TelegramBot());
        System.out.println("Connection to bot api is success");
        connection = DriverManager.getConnection(PROPERTIES.getProperty("baseUrl"), PROPERTIES.getProperty("baseUserName"), PROPERTIES.getProperty("basePassword"));
        System.out.println("Connection to database is success");
        statement = connection.createStatement();
        try {
            statement.execute("CREATE TABLE orders(id bigserial    primary key,tg_name varchar not null,tg_id bigint,user_name varchar,telephone_number varchar,bilet_name varchar,sana bigint,date_part bigint)");
            System.out.println("Table is created");
        } catch (Exception e) {
            System.out.println("Table is bor");
        }
        new LoopWriter();
    }
}
