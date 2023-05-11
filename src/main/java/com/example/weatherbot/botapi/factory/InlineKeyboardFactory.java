package com.example.weatherbot.botapi.factory;

import com.example.weatherbot.enums.Emoji;
import com.example.weatherbot.enums.UserState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardFactory {
    @Value("#{'${bot.most-popular-cities}'.split(',')}")
    private List<String> cities;

    /**
     * Создает и возвращает InlineKeyboardMarkup с самыми популярными городами.
     * @return Список популярных городов в виде кнопок
     */
    public InlineKeyboardMarkup getPopularCitiesInlineKeyboard(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> citiesButtons = new ArrayList<>();
        cities.forEach(x -> citiesButtons.add(getCityAsButton(x)));

        markup.setKeyboard(citiesButtons);

        return markup;
    }

    /**
     * Работает со списком самых популярных городов,
     * устанавливает чекбокс напротив указанного города.
     * @param city Название города, напротив которого нужно установить чекбокс
     * @return Список популярных городов в виде кнопок с чекбоксом напротив выбранного города
     */
    public InlineKeyboardMarkup getPopularCitiesWithChosenCity(String city){
        InlineKeyboardMarkup mockMarkup = getPopularCitiesInlineKeyboard();
        List<List<InlineKeyboardButton>> newMarkup = new ArrayList<>();

        mockMarkup.getKeyboard()
                .forEach( i -> {
                    if (i.get(0).getCallbackData().contains(city)) {
                        newMarkup.add(getCheckedCityButton(city));
                    } else {
                        newMarkup.add(i);
                    }
                }
        );

        return new InlineKeyboardMarkup(newMarkup);
    }

    /**
     * Фабричный метод. Возвращает кнопку для получения прогноза на заданное количество дней.
     * Количество дней для прогноза кладет в callbackData по схеме 'forecast=<количество дней>'
     * @param city Город, по которому будет взят прогноз
     * @return Кнопка с встроенной CallbackData
     */
    public InlineKeyboardMarkup getForecastButton(String city){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton(UserState.FORECAST_BY_BUTTON.getTitle());
        button.setCallbackData(String.format("forecast=%s", city));

        buttonRow.add(button);
        buttons.add(buttonRow);
        markup.setKeyboard(buttons);
        return markup;
    }

    /**
     * Создает и возвращает список из одной кнопки с названием города.
     * @param city Название города, которое будет использоваться в кнопке
     * @return Список из одной кнопки с названием города
     */
    private List<InlineKeyboardButton> getCityAsButton(String city){
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton(city);
        button.setCallbackData(String.format("city=%s", city));
        buttonRow.add(button);

        return buttonRow;
    }
    /**
     * Создает и возвращает список из одной кнопки с названием города и чекбоксом напротив него.
     * @param city Название города, которое будет использоваться в кнопке
     * @return Список из одной кнопки с названием города с чекбоксом
     */
    private List<InlineKeyboardButton> getCheckedCityButton(String city){
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton(city + " " + Emoji.CHECKED.getText());
        button.setCallbackData(String.format("city=%s", city));
        buttonRow.add(button);

        return buttonRow;
    }
}
