import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;

public class Bot {

    private static final String IN_PROGRESS_LABEL = "...";
    private final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));

    public void serve() {

// Register for updates
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void process(Update update) {
        Message message = update.message();
        CallbackQuery callbackQuery = update.callbackQuery();

        BaseRequest request = null;

        if (message != null && message.viaBot() != null && message.viaBot().username().equals("figaGame_bot")) {
            InlineKeyboardMarkup replyMarkup = message.replyMarkup();
            if (replyMarkup == null) return;

            InlineKeyboardButton[][] buttons = replyMarkup.inlineKeyboard();

            if (buttons == null) return;

            InlineKeyboardButton button = buttons[0][0];
            String buttonLabel = button.text();

            if (!buttonLabel.equals(IN_PROGRESS_LABEL)) return;

            //TODO

            Long chatID = message.chat().id();
            request = new SendMessage(chatID, "Hello!");
        }

        if (request != null) {
            bot.execute(request);
        }
    }
}
