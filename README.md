# CalculadoraIMC

## Explicação do que foi feito

### Arquivo `Main.java`

````java
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
````

Na `linha 7`, é criado um objeto `String` chamado `botToken` que recebe o token do bot criado no Telegram.

Na `linha 10`, é criado um objeto `TelegramBotsLongPollingApplication` chamado `botsApplication` que é responsável por iniciar a aplicação do bot.

Na `linha 12`, é chamado o método `registerBot` do objeto `botsApplication` que registra o bot no Telegram.

Na `linha 13`, é impresso no console a mensagem "Bot do Telegram iniciado com sucesso!".

Na `linha 15`, é chamado o método `join` da classe `Thread` para garantir que o processo do bot fique em execução.

Na `linha 16`, é criado um bloco `catch` que captura qualquer exceção que ocorra e imprime o erro no console.

### Arquivo `EchoBot.java`

````java
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
  public void consume (Update update) {
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
````

Na `linha 7`, é criado um objeto `TelegramClient` chamado `telegramClient` que é responsável por enviar mensagens para o Telegram.

Na `linha 8` é criado um construtor `EchoBot` que implementa a interface LongPollingSingleThreadUpdateConsumer` que é responsável por consumir as atualizações do bot.

Na `linha 9`, é criado um construtor `EchoBot` que recebe o token do bot e inicializa o objeto `telegramClient`.

Na `linha 11`, é sobrescrito o método `consume` da interface `LongPollingSingleThreadUpdateConsumer` que é responsável por consumir as atualizações do bot.

Na `linha 13`, é verificado se a atualização possui uma mensagem e se a mensagem possui texto.

Na `linha 15`, é criado um objeto `SendMessage` chamado `message` que será responsável por enviar a mensagem para o Telegram.

Na `linha 16`, é criado um objeto `long` chamado `chatId` que recebe o id do chat da mensagem.

Na `linha 17`, é criado um objeto `String` chamado `firstName` que recebe o primeiro nome do usuário.

Na `linha 18`, é criado um objeto `String` chamado `lastName` que recebe o último nome do usuário.

Na `linha 19`, é criado um objeto `String` chamado `username` que recebe o nome de usuário do usuário.

Na `linha 21`, é verificado se a mensagem recebida é igual a "/dicas".

Na `linha 23`, é criado um objeto `SendMessage` chamado `message` que será responsável por enviar as dicas de saúde e bem-estar para o Telegram.

Na `linha 41`, é verificado se a mensagem recebida contém "/calcular".

Na `linha 43`, é criado um array de `String` chamado `parts` que recebe a mensagem recebida dividida por espaços.

Na `linha 44`, é criado um objeto `double` chamado `weight` que recebe o peso do usuário.

Na `linha 45`, é criado um objeto `double` chamado `height` que recebe a altura do usuário.

Na `linha 46`, é criado um objeto `double` chamado `imc` que recebe o cálculo do IMC do usuário.

Na `linha 48`, é criado um objeto `String` chamado `classificacao` que será responsável por armazenar a classificação do IMC do usuário.

Na `linha 50`, é verificado se o IMC do usuário é menor que 18.5.

Na `linha 52`, é verificado se o IMC do usuário é menor que 24.9.

Na `linha 54`, é verificado se o IMC do usuário é menor que 29.9.

Na `linha 56`, é verificado se o IMC do usuário é menor que 34.9.

Na `linha 58`, é verificado se o IMC do usuário é menor que 39.9.

Na `linha 60`, é atribuído a classificação "Obesidade grau 3" ao objeto `classificacao`.

Na `linha 62`, é criado um objeto `SendMessage` chamado `message` que será responsável por enviar o IMC e a classificação do IMC do usuário para o Telegram.

Na `linha 64`, é verificado se a mensagem é nula.

Na `linha 66`, é criado um objeto `SendMessage` chamado `message` que será responsável por enviar as instruções de uso do bot para o Telegram.

Na `linha 68`, é chamado o método `execute` do objeto `telegramClient` para enviar a mensagem para o Telegram.

Na `linha 70`, é criado um bloco `catch` que captura qualquer exceção que ocorra e imprime o erro no console.
