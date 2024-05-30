package com.telegram.bot;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
  public static void main(String[] args) {
    String botToken = "6914222572:AAFU9hseS6tCllA7LccU_klJ4bx02O6XfBA";
   //Na linha 7, é criado um objeto String chamado botToken que recebe o token do bot criado no Telegram.
    try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
        // Na linha 9, é criado um objeto TelegramBotsLongPollingApplication chamado botsApplication que é responsável por iniciar a aplicação do bot.
    botsApplication.registerBot(botToken, new EchoBot(botToken));
    //Na linha 11, é chamado o método registerBot do objeto botsApplication que registra o bot no Telegram.
      System.out.println("Bot do Telegram iniciado com sucesso!");
      //Na linha 13, é impresso no console a mensagem "Bot do Telegram iniciado com sucesso!".
      Thread.currentThread().join();
      //Na linha 15, é chamado o método join da classe Thread para garantir que o processo do bot fique em execução.
    } catch (Exception error) {
      //Na linha 16, é criado um bloco catch que captura qualquer exceção que ocorra e imprime o erro no console.
      error.printStackTrace();
    } //na linha 19, é criado printStackTrace para indentifocar e resolver problemas na execução da aplicação. 
  }
}
