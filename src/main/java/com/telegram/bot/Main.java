package com.telegram.bot;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
  public static void main(String[] args) {
    String botToken = "6914222572:AAFU9hseS6tCllA7LccU_klJ4bx02O6XfBA";
    // Using try-with-resources to allow auto close to run upon finishing
    try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
      botsApplication.registerBot(botToken, new EchoBot(botToken));
      System.out.println("Bot do Telegram iniciado com sucesso!");
      // Ensure this prcess wait forever
      Thread.currentThread().join();
    } catch (Exception error) {
      error.printStackTrace();
    }
  }
}
