package com.telegram.bot;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
  public static void main(String[] args) {
    String botToken = System.getenv("TELEGRAM_BOT_TOKEN");
    // Using try-with-resources to allow auto close to run upon finishing
    try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
      botsApplication.registerBot(botToken, new EchoBot(botToken));
      System.out.println("MyAmazingBot successfully started!");
      // Ensure this prcess wait forever
      Thread.currentThread().join();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
