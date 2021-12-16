package uz.bots.service;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.bots.Ticket;
import uz.bots.model.User;
import uz.bots.utils.AdminKeyboard;
import uz.bots.utils.Keyboard;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Muhammad
 * Date: 04.12.2021
 * Time: 20:24
 */
public class TelegramBot extends TelegramLongPollingBot {

    public static final Map<Long, User> users = new HashMap<>();
//    private static final Long[] ADMINS = {184535535L, 602701577L};
    private static final Long[] ADMINS = {184535535L};
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.HH.yyyy");

    @Override
    public String getBotUsername() {
        return Ticket.PROPERTIES.getProperty("botName");
    }

    @Override
    public String getBotToken() {
        return Ticket.PROPERTIES.getProperty("botToken");
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long userId = message.getChatId();
        boolean isAdmin = false;

        for (Long id : ADMINS) {
            if (message.getFrom().getId().equals(id)) {
                isAdmin = true;
            }
        }

        if (!isAdmin) {
            if (users.get(userId) == null) {
                User user = new User();
                user.setTgId(userId);
                user.setStep("welcome");
                user.setTgName("@" + message.getFrom().getUserName());
                users.put(userId, user);
            }
            switch (users.get(userId).getStep()) {
                case "welcome":
                    users.get(userId).setStep("getPhoneNumber");
                    sender(users.get(userId), message, "Assalomu Aleykum!\nRaqamingizni yuboring");
                    break;
                case "getPhoneNumber":
                    String text = "No to'g'ri raqam yuborildi";
                    if (message.getContact() != null) {
                        users.get(userId).setTelephoneNumber(message.getContact().getPhoneNumber());
                        text = "Ismingizni kiriting";
                        users.get(userId).setStep("getName");
                    }
                    sender(users.get(userId), message, text);
                    break;
                case "getName":
                    if (!message.getText().equals("Ortga qaytish") && message.getText().length() < 26 && message.getText().length() > 1) {
                        users.get(userId).setUserName(message.getText());
                        users.get(userId).setStep("mainMenu");
                        sender(users.get(userId), message, "Konsert tanlang");
                    } else if (message.getText().equals("Ortga qaytish")){
                        users.get(userId).setStep("getPhoneNumber");
                        sender(users.get(userId), message, "Raqamingizni yuboring");
                    }
                    break;
                case "mainMenu":
                    if (message.getText().equals("Million") ||
                            message.getText().equals("Dizayn") ||
                            message.getText().equals("Bravo") ||
                            message.getText().equals("Shoxrux")) {
                        users.get(userId).setBiletName(message.getText());
                        users.get(userId).setStep("getPartTime");
                        sender(users.get(userId), message, "Vaqtni tanlang");
                    }
                    if (message.getText().equals("Ortga qaytish")) {
                        users.get(userId).setStep("getName");
                        sender(users.get(userId), message, "Ismingizni kiriting");
                    }
                    break;
                case "getPartTime":
                    try {
                        if (!message.getText().equals("Ortga qaytish")) {
                            users.get(userId).setDatePart(simpleDateFormat.parse(message.getText()).getTime());
                            sendToAdmin(users.get(userId));
                            users.get(userId).setStep("mainMenu");
                            sender(users.get(userId), message, "Sizning murojatingiz qabul qilindi\nSiz bilan aloqaga chiqamiz :)");
                        } else {
                            users.get(userId).setStep("mainMenu");
                            sender(users.get(userId), message, "Konsert tanlang");
                        }
                    } catch (Exception e) {
                        sender(users.get(userId), message, "Qandaydur xatolik ketti");
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            new Admin();
            adminSender(message, users.);
        }
    }

    public void sendToAdmin(User user) {
//        try {
//            Ticket.statement.execute("INSERT INTO orders (tg_name,tg_id,user_name,telephone_number,bilet_name,sana,date_part) VALUES ('"+user.getTgName()+"',"+user.getTgId()+",'"+user.getUserName()+"','"+user.getTelephoneNumber()+"','"+user.getBiletName()+"',"+new Date().getTime() +","+user.getDatePart()+")");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        users.get(user.getTgId()).setCompleted(true);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Yangi murojat:\nIsmi: "
                + user.getUserName() + "\nTel: +"
                + user.getTelephoneNumber() + "\nKonsert: "
                + user.getBiletName() + "\nTg name: "
                + user.getTgName() + "\nVaqti: "
                + simpleDateFormat.format(user.getDatePart()) + "\nID: "
                + user.getTgId());

        for (Long id : ADMINS) {
            sendMessage.setChatId(String.valueOf(id));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void sender(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sender(User user, Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(text);
        Keyboard.userKeyboard(user, sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void adminSender( Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(text);
        AdminKeyboard.userKeyboard( sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
