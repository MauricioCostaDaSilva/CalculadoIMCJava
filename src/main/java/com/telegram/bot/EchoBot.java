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
    if (update.hasMessage() && update.getMessage().hasText()) {
      SendMessage message = null;
      long chatId = update.getMessage().getChatId();
      String primeiroNome = update.getMessage().getChat().getFirstName();
      String sobreNome = update.getMessage().getChat().getLastName();
      String nomeDeUsuario = update.getMessage().getChat().getUserName();

      if (update.getMessage().getText().equals("/dicas")) {
        message = SendMessage
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
        String[] parte = update.getMessage().getText().split(" ");
        double peso = Double.parseDouble(parte[1]);
        double altura = Double.parseDouble(parte[2]);
        double imc = Math.round(peso / (altura * altura) * 100.0) / 100.0;
        double qAgua = peso * 35;

        String classificacao;

        if (imc < 18.5) {
          classificacao = "Abaixo do peso /n Aumente a Ingestão Calórica: Consuma mais calorias do que você queima.\r\n" + //
                        "Isso significa aumentar o consumo de alimentos ricos em calorias, como\r\n" + //
                        "gorduras saudáveis, carboidratos complexos e proteínas magras.\r\n" + //
                        "\r\n" + //
                        "Faça Refeições Mais Frequentes: Em vez de apenas três grandes refeições por\r\n" + //
                        "dia, faça várias refeições menores ao longo do dia para aumentar a ingestão de\r\n" + //
                        "calorias. Isso também pode ajudar a evitar a sensação de estufamento e facilitar\r\n" + //
                        "a ingestão de mais alimentos. \r\n" + //
                        "Exercite-se com Foco em Ganho de Massa: Combine uma alimentação calórica com exercícios de resistência para ajudar a aumentar a massa muscular. Isso inclui levantamento de peso, exercícios de musculação e treinamento de força.\r\n" + //
                        "\r\n" + //
                        "Evite Beber Água Antes das Refeições: Beber água antes das refeições pode reduzir o apetite. Se possível, tente beber líquidos após as refeições para evitar que a água encha o estômago.\r\n" + //
                        "\r\n" + //
                        "";
        } else if (imc < 24.9)  {
          classificacao = "Peso normal \n Manter peso\r\n" + //
                        "Alimentação Balanceada: Priorize alimentos ricos em nutrientes, como frutas,\r\n" + //
                        "legumes, grãos integrais, proteínas magras e gorduras saudáveis. Evite\r\n" + //
                        "alimentos processados, ricos em açúcares e gorduras saturadas.\r\n" + //
                        "\r\n" + //
                        "Controle das Porções: Aprenda a reconhecer as porções adequadas para cada alimento.\r\n" + //
                        "\r\n" + //
                        "Exercício Regular: O exercício ajuda a manter a massa muscular e a regular o metabolismo.\r\n" + //
                        "Monitore-se Regularmente: Pese-se regularmente e fique atento a sinais de ganho de peso. Isso pode ajudá-lo a identificar problemas precocemente e fazer ajustes na sua rotina, se necessário.\r\n" + //
                        "";
        } else if (imc < 29.9) {
          classificacao = "Sobrepeso /n Controle de Porções: Fique atento ao tamanho das porções e evite comer em excesso. Use pratos menores, preste atenção às suas sensações de fome e saciedade e pare de comer quando estiver satisfeito, não quando estiver completamente cheio.\r\n" + //
                        "Alimentação Equilibrada: Priorize uma alimentação balanceada, rica em frutas, vegetais, proteínas magras e grãos integrais.\r\n" + //
                        "Atividade Física Regular: Faça exercícios regularmente, incluindo atividades aeróbicas (como caminhada, corrida, natação, ciclismo) e exercícios de fortalecimento muscular.\r\n" + //
                        "Reduza o Sedentarismo: Além do exercício planejado, procure maneiras de ser mais ativo no dia a dia, como subir escadas em vez de usar o elevador, estacionar mais longe do destino para caminhar mais, e fazer pausas para se movimentar durante o trabalho.\r\n" + //
                        "";
        } else if (imc < 34.9) {
          classificacao = "Obesidade grau 1 /n Alimentação saudável: Concentre-se em uma dieta rica em vegetais, frutas, grãos \r\n" + //
                        "integrais e proteínas magras.\r\n" + //
                        "Controle de porções: A quantidade de comida que você consome é tão importante \r\n" + //
                        "quanto a qualidade. Tente comer porções menores e preste atenção às suas sensações \r\n" + //
                        "de fome e saciedade\r\n" + //
                        "Exercício regular: Faça atividades físicas regularmente. Comece com algo que goste e \r\n" + //
                        "aumente gradualmente a intensidade e a duração à medida que se sentir mais \r\n" + //
                        "confortável\r\n" + //
                        "Mantenha-se hidratado: Beba bastante água ao longo do dia. Às vezes, nosso corpo \r\n" + //
                        "confunde a sede com fome, então beber água pode ajudar a reduzir o consumo \r\n" + //
                        "excessivo de alimentos.\r\n" + //
                        "Durma o suficiente: A falta de sono pode afetar os hormônios relacionados à fome e \r\n" + //
                        "ao apetite, levando a desejos por alimentos não saudáveis e excesso de ingestão.\r\n" + //
                        "Gerencie o estresse: O estresse crônico pode levar a hábitos alimentares pouco \r\n" + //
                        "saudáveis, como comer emocionalmente\r\n" + //
                        "Procure apoio: Ter apoio de amigos, familiares ou grupos de apoio pode ser \r\n" + //
                        "fundamental para o sucesso a longo prazo na perda de peso e na manutenção de\r\n" + //
                        "hábitos saudáveis";
        } else if (imc < 39.9) {
          classificacao = "Obesidade grau 2 /n Consulte um profissional de saúde: Antes de iniciar qualquer programa de perda de \r\n" + //
                        "peso, é importante consultar um médico ou um nutricionista\r\n" + //
                        "Estabeleça metas realistas: Defina metas de perda de peso alcançáveis e mensuráveis. \r\n" + //
                        "Ao definir metas realistas, você será mais propenso a manter-se motivado e \r\n" + //
                        "comprometido ao longo do tempo.\r\n" + //
                        "Acompanhe sua alimentação: Mantenha um diário alimentar para acompanhar o que \r\n" + //
                        "você está comendo e beber.\r\n" + //
                        "Exercício regular e variado: Além de exercícios cardiovasculares, como caminhada, \r\n" + //
                        "corrida ou natação, incorpore também exercícios de resistência, como musculação, para \r\n" + //
                        "ajudar a aumentar sua massa muscular e queimar mais calorias\r\n" + //
                        "Revise suas escolhas alimentares: Faça escolhas alimentares mais conscientes, \r\n" + //
                        "optando por alimentos nutritivos e de baixa caloria\r\n" + //
                        "Busque apoio social: Encontre o apoio de amigos, familiares ou grupos de apoio que \r\n" + //
                        "possam incentivá-lo e motivá-lo ao longo do processo de perda de peso\r\n" + //
                        "Cuide da sua saúde mental: A obesidade de grau 2 pode estar associada a problemas \r\n" + //
                        "de autoestima e saúde mental\r\n" + //
                        "Seja consistente e paciente: Seja paciente consigo mesmo e esteja preparado para \r\n" + //
                        "enfrentar desafios ao longo do caminho.";
        } else {
          classificacao = "Obesidade grau 3 /n Consulte um Profissional de Saúde: O primeiro passo é consultar um médico ou um \r\n" + //
                        "nutricionista para avaliar sua situação de saúde e criar um plano personalizado para \r\n" + //
                        "perda de peso.\r\n" + //
                        "Mudanças na Alimentação: Adote uma alimentação equilibrada e nutritiva, rica em \r\n" + //
                        "frutas, vegetais, proteínas magras e grãos integrais\r\n" + //
                        "Controle de Porções: Aprenda a controlar o tamanho das porções e evite comer em\r\n" + //
                        "Exercício Regular: Faça atividade física regularmente, de preferência com a orientação \r\n" + //
                        "de um profissional.\r\n" + //
                        "Estabeleça Metas Realistas: Defina metas de perda de peso realistas e alcançáveis a \r\n" + //
                        "curto e longo prazo.\r\n" + //
                        "Mantenha-se Hidratado: Beba bastante água ao longo do dia, pois a \r\n" + //
                        "desidratação pode ser confundida com fome.\r\n" + //
                        "Durma Suficientemente: Priorize um sono de qualidade, pois a falta de sono \r\n" + //
                        "pode afetar os hormônios que regulam o apetite e o metabolismo.\r\n" + //
                        "Busque Apoio: Procure o apoio de amigos, familiares ou grupos de apoio para \r\n" + //
                        "ajudá-lo durante sua jornada de perda de peso.\r\n" + //
                        "Evite Dietas Extremas: Evite dietas da moda ou extremamente restritivas, pois \r\n" + //
                        "elas geralmente não são sustentáveis a longo prazo e podem causar danos à \r\n" + //
                        "saúde.\r\n" + //
                        "Seja Paciente e Persistente: A perda de peso pode ser um processo desafiador \r\n" + //
                        "e levar tempo. Mantenha-se focado em seus objetivos, seja paciente consigo \r\n" + //
                        "mesmo e celebre cada conquista, por menor que seja.";
        }

        message = SendMessage
            .builder()
            .chatId(chatId)
            .text(
              "Seu IMC é: " + imc + "\n" +
              "Classificação: " + classificacao +
              "Consuma: " + qAgua + "Litros"
            )
            .build();
      } else if (message == null) {
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text(
              "Olá " + primeiroNome + " " + sobreNome + " (" + nomeDeUsuario + "), como posso te ajudar?\n" +
              "- Digite /dicas para receber dicas de saúde e bem-estar.\n" +
              "- Digite /calcular [peso] [altura] para calcular seu IMC. ex: /calcular 70 1.75"
            )
            .build();
      }

      try {
        telegramClient.execute(message); // Enviando nosso objeto de mensagem ao usuário
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }
}
