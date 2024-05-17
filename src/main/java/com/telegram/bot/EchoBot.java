package com.telegram.bot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class EchoBot implements LongPollingSingleThreadUpdateConsumer {
  private final TelegramClient telegramClient;

  public EchoBot(String botToken) {
    telegramClient = new OkHttpTelegramClient(botToken);
  }

  @Override
  public void consume(Update update) {
    // We check if the update has a message and the message has text
    if (update.hasMessage() && update.getMessage().hasText()) {
      // Set variables
      String messageText = update.getMessage().getText();
      String messageToSent = "Você disse: " + messageText;
      long chatId = update.getMessage().getChatId();

      if (messageText.contains("ajuda")) {
        messageToSent = "Durma Bem: Garanta de 7 a 9 horas de sono por noite. A falta de sono pode afetar negativamente o metabolismo e aumentar o apetite.\n"
            +
            "Gerenciamento do Estresse:\n" +
            "\n" +
            "Reduza o Estresse: Pratique técnicas de relaxamento como meditação, ioga ou hobbies que goste. O estresse pode levar à alimentação emocional.\n"
            +
            "Acompanhamento Profissional:\n" +
            "\n" +
            "Nutricionista: Um nutricionista pode ajudar a criar um plano alimentar personalizado e equilibrado.\n" +
            "Médico: Consulte um médico para avaliar sua saúde geral e descartar quaisquer condições médicas que possam estar afetando seu peso.\n"
            +
            "Mudanças de Estilo de Vida:\n" +
            "\n" +
            "Metas Realistas: Estabeleça metas de perda de peso realistas e sustentáveis. Perder 0,5 a 1 kg por semana é considerado saudável.\n"
            +
            "Registro de Progresso: Mantenha um diário alimentar e de atividades para monitorar seu progresso e fazer ajustes conforme necessário.\n"
            +
            "Educação e Suporte:\n" +
            "\n" +
            "Aprenda sobre Nutrição e Fitness: Eduque-se sobre hábitos saudáveis e busque suporte, seja de grupos de apoio, amigos ou família.\n"
            +
            "Fazer mudanças graduais e sustentáveis é a chave para alcançar e manter um peso saudável. Lembre-se de que a jornada é pessoal e única, então encontre o que funciona melhor para você e seu estilo de vida.";
      }

      SendMessage message = SendMessage // Create a message object
          .builder()
          .chatId(chatId)
          .text(messageToSent)
          .build();

      try {
        telegramClient.execute(message); // Sending our message object to user
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }
}
