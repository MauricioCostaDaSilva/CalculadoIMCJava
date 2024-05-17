package br.com.SimplIMC.bot.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EchoBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return DadosBot.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return DadosBot.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText("Durma Bem: Garanta de 7 a 9 horas de sono por noite. A falta de sono pode afetar negativamente o metabolismo e aumentar o apetite.\n" +
                            "Gerenciamento do Estresse:\n" +
                            "\n" +
                            "Reduza o Estresse: Pratique técnicas de relaxamento como meditação, ioga ou hobbies que goste. O estresse pode levar à alimentação emocional.\n" +
                            "Acompanhamento Profissional:\n" +
                            "\n" +
                            "Nutricionista: Um nutricionista pode ajudar a criar um plano alimentar personalizado e equilibrado.\n" +
                            "Médico: Consulte um médico para avaliar sua saúde geral e descartar quaisquer condições médicas que possam estar afetando seu peso.\n" +
                            "Mudanças de Estilo de Vida:\n" +
                            "\n" +
                            "Metas Realistas: Estabeleça metas de perda de peso realistas e sustentáveis. Perder 0,5 a 1 kg por semana é considerado saudável.\n" +
                            "Registro de Progresso: Mantenha um diário alimentar e de atividades para monitorar seu progresso e fazer ajustes conforme necessário.\n" +
                            "Educação e Suporte:\n" +
                            "\n" +
                            "Aprenda sobre Nutrição e Fitness: Eduque-se sobre hábitos saudáveis e busque suporte, seja de grupos de apoio, amigos ou família.\n" +
                            "Fazer mudanças graduais e sustentáveis é a chave para alcançar e manter um peso saudável. Lembre-se de que a jornada é pessoal e única, então encontre o que funciona melhor para você e seu estilo de vida.");

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
