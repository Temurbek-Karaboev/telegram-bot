package uz.bots.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.bots.model.User;

import java.util.ArrayList;

/**
 * Author: Muhammad
 * Date: 02.11.2021
 * Time: 20:14
 */
public class Keyboard {
    public static void userKeyboard(User user, SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);

        ArrayList<KeyboardRow> rows = new ArrayList<KeyboardRow>();
        KeyboardRow row1, row2, row3;
        KeyboardButton button1, button2, button3, button4, button5;

        switch (user.getStep()) {
            case "getPhoneNumber":
                row1 = new KeyboardRow();
                button1 = new KeyboardButton("Raqam bilan ulashish");
                button1.setRequestContact(true);
                row1.add(button1);
                rows.add(row1);
                break;
            case "getName":
                row1 = new KeyboardRow();
                button1 = new KeyboardButton("Ortga qaytish");
                row1.add(button1);
                rows.add(row1);
                break;
            case "mainMenu":
                row1 = new KeyboardRow();
                button1 = new KeyboardButton("Million");
                button2 = new KeyboardButton("Dizayn");
                row1.add(button1);
                row1.add(button2);
                rows.add(row1);

                row2 = new KeyboardRow();
                button3 = new KeyboardButton("Bravo");
                button4 = new KeyboardButton("Shoxruxon");
                row2.add(button3);
                row2.add(button4);
                rows.add(row2);

                row3 = new KeyboardRow();
                button5 = new KeyboardButton("Ortga qaytish");
                row3.add(button5);
                rows.add(row3);
                break;
            case "getPartTime":
                row1 = new KeyboardRow();
                button1 = new KeyboardButton("09.12.2021");
                button2 = new KeyboardButton("15.12.2021");
                row1.add(button1);
                row1.add(button2);
                rows.add(row1);

                row2 = new KeyboardRow();
                button3 = new KeyboardButton("21.12.2021");
                button4 = new KeyboardButton("31.12.2021");
                row2.add(button3);
                row2.add(button4);
                rows.add(row2);

                row3 = new KeyboardRow();
                button5 = new KeyboardButton("Ortga qaytish");
                row3.add(button5);
                rows.add(row3);
                break;
            case "payMenu":
                row1 = new KeyboardRow();
                button1 = new KeyboardButton("Oldi qator 300 000 so'm");
                button2 = new KeyboardButton("O'rta qator 200 000 so'm");
                row1.add(button1);
                row1.add(button2);
                rows.add(row1);

                row2 = new KeyboardRow();
                button3 = new KeyboardButton("Orqa qator 100 000 so'm");
                button4 = new KeyboardButton("Balkon 50 000 so'm");
                row2.add(button3);
                row2.add(button4);
                rows.add(row2);

                row3 = new KeyboardRow();
                button5 = new KeyboardButton("Ortga qaytish");
                row3.add(button5);
                rows.add(row3);
                break;
        }

        keyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }
}
