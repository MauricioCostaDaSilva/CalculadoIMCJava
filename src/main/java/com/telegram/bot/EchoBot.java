package com.telegram.bot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class EchoBot implements LongPollingSingleThreadUpdateConsumer {
  private final TelegramClient telegramClient;

  public EchoBot (String botToken) {
    telegramClient = new OkHttpTelegramClient(botToken);
  }

  @Override
  public void consume(Update update) {
    // We check if the update has a message and the message has text
    if (update.hasMessage() && update.getMessage().hasText()) {
      SendMessage message = null;
      long chatId = update.getMessage().getChatId();
      String firstName = update.getMessage().getChat().getFirstName();
      String lastName = update.getMessage().getChat().getLastName();
      String username = update.getMessage().getChat().getUserName();

      if (update.getMessage().getText().equals("/dicas")) {
        message = SendMessage // Create a message object
            .builder()
            .chatId(chatId)
            .text(
                "Durma Bem: Garanta de 7 a 9 horas de sono por noite. A falta de sono pode afetar negativamente o metabolismo e aumentar o apetite.\n"
                    +
                    "Gerenciamento do Estresse:\n" +
                    "\n" +
                    "Reduza o Estresse: Pratique técnicas de relaxamento como meditação, ioga ou hobbies que goste. O estresse pode levar à alimentação emocional.\n"
                    +
                    "Acompanhamento Profissional:\n" +
                    "\n" +
                    "Nutricionista: Um nutricionista pode ajudar a criar um plano alimentar personalizado e equilibrado.\n"
                    +
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
                    "Fazer mudanças graduais e sustentáveis é a chave para alcançar e manter um peso saudável. Lembre-se de que a jornada é pessoal e única, então encontre o que funciona melhor para você e seu estilo de vida.")
            .build();
      } else if (update.getMessage().getText().contains("/calcular")) {
        String[] parts = update.getMessage().getText().split(" ");
        double weight = Double.parseDouble(parts[1]);
        double height = Double.parseDouble(parts[2]);
        double imc = Math.round(weight / (height * height) * 100.0) / 100.0;

        String classificacao;

        if (imc < 18.5) {
          classificacao = "Abaixo do peso";
        } else if (imc < 24.9) {
          classificacao = "Peso normal";
        } else if (imc < 29.9) {
          classificacao = "Sobrepeso";
        } else if (imc < 34.9) {
          classificacao = "Obesidade grau 1";
        } else if (imc < 39.9) {
          classificacao = "Obesidade grau 2";
        } else {
          classificacao = "Obesidade grau 3";
        }

        message = SendMessage
            .builder()
            .chatId(chatId)
            .text(
              "Seu IMC é: " + imc + "\n" +
              "Classificação: " + classificacao
            )
            .build();
      } else if (message == null) {
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text(
              "Olá " + firstName + " " + lastName + " (" + username + "), como posso te ajudar?\n" +
              "- Digite /dicas para receber dicas de saúde e bem-estar.\n" +
              "- Digite /calcular [peso] [altura] para calcular seu IMC. ex: /calcular 70 1.75"
            )
            .build();
      }

      try {
        telegramClient.execute(message); // Sending our message object to user
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }
}
