package org.example;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {
    public static void main(String[] args) {
        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        // Register our bot
        try {
            telegramBotsApi.registerBot(new CurrencyConvertor());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
