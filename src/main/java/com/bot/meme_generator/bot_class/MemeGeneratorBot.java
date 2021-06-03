package com.bot.meme_generator.bot_class;

import com.bot.meme_generator.model.Picture;
import com.bot.meme_generator.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.net.URISyntaxException;

@Component
public class MemeGeneratorBot extends TelegramLongPollingBot {

    private final PictureService pictureService;

    @Value("${bot.username}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

    @Autowired
    public MemeGeneratorBot(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && message.getText().equals("/meme")) {
            sendPic(message);
        }
    }

//    private void sendMsg(Message message, String text) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
//        sendMessage.setText(text);
//
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

    private void sendPic(Message message) {
        try {
//            URL resource = getClass().getClassLoader().getResource("static/memes/Мастер Йода.png");
            Picture pictureFromDb = pictureService.getRandomPic();
            File pic = new File(getClass()
                    .getClassLoader()
                    .getResource("static/memes/" + pictureFromDb.getName())
                    .toURI());

            InputFile photo = new InputFile(pic);

            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(message.getChatId().toString());
            sendPhoto.setPhoto(photo);

            execute(sendPhoto);
        } catch (TelegramApiException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
