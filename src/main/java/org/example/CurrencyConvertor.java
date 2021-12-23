package org.example;

import com.google.gson.Gson;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class CurrencyConvertor extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (text.equals("/start")) {
                // User send /start
                SendMessage message = new SendMessage()
                        .setChatId(chatId)
                        .setText("Welcome to my_currency_convertor_bot \nWrite /markup to choose a currency and /hide to hide");

                try {
                    // Sending our message object to user
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (text.equals("/markup")) {
                // Create a message object
                SendMessage message = new SendMessage()
                        .setChatId(chatId)
                        .setText("Here is your keyboard");

                // Create ReplyKeyboardMarkup object
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                // Create the keyboard (list of keyboard rows)
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                // Create a keyboard row
                KeyboardRow keyboardRow = new KeyboardRow();
                // Set each button, you can also use KeyboardButton objects if you need something else than text
                keyboardRow.add("USD");
                keyboardRow.add("RUB");
                keyboardRow.add("EUR");
                // Add the first row to the keyboard
                keyboardRows.add(keyboardRow);
                // Create another keyboard row
                keyboardRow = new KeyboardRow();
                // Set each button for the second line
                keyboardRow.add("JPY");
                keyboardRow.add("CHF");
                keyboardRow.add("CNY");
                // Add the second row to the keyboard
                keyboardRows.add(keyboardRow);
                // Set the keyboard to the markup
                keyboardMarkup.setKeyboard(keyboardRows);
                // Add it to the message
                message.setReplyMarkup(keyboardMarkup);

                try {
                    // Sending our message object to user
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (text.equals("/hide")) {
                SendMessage message = new SendMessage()
                        .setChatId(chatId)
                        .setText("Keyboard hidden");
                ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
                message.setReplyMarkup(keyboardMarkup);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                Currency currency = connectToCBU(text);
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                if (currency != null) {
                    message.setText(currency.getNominal() + " " + currency.getCcy() + " = " + currency.getRate() + " UZS\nDate: " + currency.getDate());
                } else {
                    message.setText("Unknown command");
                }

                try {
                    // Sending our message object to user
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // Get currency rate from Central Bank of Uzbekistan
    public static Currency connectToCBU(String currency) {
        Gson gson = new Gson();
        try {
            URL url = new URL("https://cbu.uz/oz/arkhiv-kursov-valyut/json/" + currency + "/");
            URLConnection urlConnection = url.openConnection();
            Reader reader = new InputStreamReader(urlConnection.getInputStream());
            Currency[] currencyArray = gson.fromJson(reader, Currency[].class);
            return currencyArray[0];
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String getBotUsername() {
        return "my_currency_convertor_bot";
    }

    @Override
    public String getBotToken() {
        return "5043934378:AAEiQ_hYm-woZGsObWxT0vIGwNR_JNizr8o";
    }
}
