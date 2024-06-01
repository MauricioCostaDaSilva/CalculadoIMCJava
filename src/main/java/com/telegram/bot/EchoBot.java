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
            .text(" Durma Bem: Garanta de 7 a 9 horas de sono por noite. A falta de sono pode afetar negativamente o metabolismo e aumentar o apetite.\nReduza o Estresse: Pratique técnicas de relaxamento como meditação, ioga ou hobbies que goste. O estresse pode levar à alimentação emocional.\n\nNutricionista: Um nutricionista pode ajudar a criar um plano alimentar personalizado e equilibrado. Médico: Consulte um médico para avaliar sua saúde geral e descartar quaisquer condições médicas que possam estar afetando seu peso.\n\n Mudanças de Estilo de Vida: Metas Realistas: Estabeleça metas de perda de peso realistas e sustentáveis. Perder 0,5 a 1 kg por semana é considerado saudável.Registro de Progresso: Mantenha um diário alimentar e de atividades para monitorar seu progresso e fazer ajustes conforme necessário.\n\nEducação e Suporte: Aprenda sobre Nutrição e Fitness: Eduque-se sobre hábitos saudáveis e busque suporte, seja de grupos de apoio, amigos ou família.Fazer mudanças graduais e sustentáveis é a chave para alcançar e manter um peso saudável. Lembre-se de que a jornada é pessoal e única, então encontre o que funciona melhor para você e seu estilo de vida.")
            .build();
      } else if (update.getMessage().getText().contains("/calcular")) {
        String[] parte = update.getMessage().getText().split(" ");
        double peso = Double.parseDouble(parte[1]);
        double altura = Double.parseDouble(parte[2]);
        double imc = Math.round(peso / (altura * altura) * 100.0) / 100.0;
        double qAgua = peso * 35;

        String classificacao;

        if (imc < 18.5) {
          classificacao = "Abaixo do peso.\n→ Aumente a Ingestão Calórica:\n" +"Consuma mais calorias do que você queima. Isso significa aumentar o consumo de alimentos ricos em calorias, como gorduras saudáveis, carboidratos complexos e proteínas magras.\n→ Exercite-se com Foco em Ganho de Massa:\n"+ "Combine uma alimentação calórica com exercícios de resistência para ajudar a aumentar a massa muscular.\n"+"→ Para melhores avaliações e orientação personalizada, consulte um profissional de saúde.";
        } else if (imc < 24.9)  {
          classificacao = "Peso normal.\n"+"→ Alimentação Balanceada: Priorize alimentos ricos em nutrientes, como frutas, legumes, grãos integrais, proteínas magras e gorduras saudáveis.\n"+"→ Exercício Regular: O exercício ajuda a manter a massa muscular e a regular o metabolismo.\n"+"→ Para melhores avaliações e orientação personalizada, consulte um profissional de saúde.";
        } else if (imc < 29.9) {
          classificacao = "Sobrepeso.\n"+"→ Controle de Porções: Fique atento ao tamanho das porções e evite comer em excesso. Use pratos menores, preste atenção às suas sensações de fome e saciedade e pare de comer quando estiver satisfeito, não quando estiver completamente cheio.\n"+ "→ Atividade Física Regular: Faça exercícios regularmente, incluindo atividades aeróbicas (como caminhada, corrida, natação, ciclismo) e exercícios de fortalecimento muscular.\n"+"→ Para melhores avaliações e orientação personalizada, consulte um profissional de saúde.";
        } else if (imc < 34.9) {
          classificacao = "Obesidade grau 1.\n"+"→ Controle de porções: A quantidade de comida que você consome é tão importante quanto a qualidade. Tente comer porções menores e preste atenção às suas sensações de fome e saciedade.\n"+"→ Reduza o Sedentarismo: Além de atividade Física Regular, procure maneiras de ser mais ativo no dia a dia, como subir escadas em vez de usar o elevador, estacionar mais longe do destino para caminhar mais, e fazer pausas para se movimentar durante o trabalho.\n"+"→ Para melhores avaliações e orientação personalizada, consulte um profissional de saúde.";
        } else if (imc < 39.9) {
          classificacao = "Obesidade grau 2.\n"+"→ Acompanhe sua alimentação: Mantenha um diário alimentar para acompanhar o que você está comendo e bebendo. Fique atento ao tamanho das porções e evite comer em excesso.\n"+"→ Exercício regular e variado: Além de exercícios cardiovasculares, como caminhada, corrida ou natação, incorpore também exercícios de resistência, como musculação, para ajudar a aumentar sua massa muscular e queimar mais calorias.\n"+"→ Para melhores avaliações e orientação personalizada, consulte um profissional de saúde.";
        } else {
          classificacao = "Obesidade grau 3.\n"+"→ Mudanças na Alimentação: Adote uma alimentação equilibrada e nutritiva, rica em frutas, vegetais, proteínas magras e grãos integrais.\n"+"→ Exercício Regular: Faça atividade física regularmente, de preferência com a orientação de um profissional.\n"+"→ Para melhores avaliações e orientação personalizada, consulte um profissional de saúde.";
        }
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text(
              "→ Seu IMC É: " + imc + "\n" +
              "→ Classificação: " + classificacao + "\n" +
              "→ Segundo seu peso, essa é a quantidade ideal de água para se manter saudável: " + qAgua + " ml." 
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
        telegramClient.execute(message);  //Enviando nosso objeto de mensagem ao usuário
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }
}
