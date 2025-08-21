package com.sport.service.bot.commands.admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StartAdminCommand implements IBotCommand {

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Launch bot and save user's data to database";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        User user = message.getFrom();
        log.info("Call command start by user: {}", user.getUserName());
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        answer.setText("""
               👋\s
               Ты здесь один из администраторов нашего бота.
               Ты можешь создать/удалить спортивные места и события.
               Также ты можешь пользоваться ботом как обычный юзер.
               Доступные команды:
               /get_place - выбрать место (по дефолту ты можешь найти сейчас одно место загруженное в БД: центральный-футбольное поле-помещение)
               /get_upcoming_events - недоступно
               /get_notifications - недоступно (подписаться на получение уведомлений о грядущих спортивных событиях)
               /stop_notifications - недоступно (отписаться от получения уведомлений о грядущих спортивных событиях)
               /create_place - создать место
               /delete_place - удалить место
               /create_event - недоступно
               /delete_event - недоступно
               \s""");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("/get_place");
        row1.add("/get_upcoming_events");

        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add("/get_notifications");
        row2.add("/stop_notifications");
        keyboard.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add("/create_place");
        row3.add("/delete_place");
        keyboard.add(row3);

        KeyboardRow row4 = new KeyboardRow();
        row4.add("/create_event");
        row4.add("/delete_event");
        keyboard.add(row4);

        keyboardMarkup.setKeyboard(keyboard);
        answer.setReplyMarkup(keyboardMarkup);

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /start command", e);
        }
    }
}