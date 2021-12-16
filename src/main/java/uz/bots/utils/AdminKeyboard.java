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
public class AdminKeyboard {
    public static void userKeyboard( SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);

        ArrayList<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1, row2, row3;
        KeyboardButton button1, button2, button3, button4, button5;



        keyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }
}
