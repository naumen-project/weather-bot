package com.example.weatherbot.botapi.handlers.callbackquery;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryHandler {
    SendMessage handle(CallbackQuery callbackQuery);
    String getHandlerName();
}
