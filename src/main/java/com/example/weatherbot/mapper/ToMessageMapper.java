package com.example.weatherbot.mapper;

import com.example.weatherbot.enums.Emoji;
import com.example.weatherbot.service.weatherservice.ForecastInfo;
import com.example.weatherbot.service.weatherservice.WeatherInfo;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Преттифаер для WeatherInfo и ForecastInfo.
 * Представляет данные классы в виде строки, удобночитаемой для пользователя.
 */
public class ToMessageMapper {
    private ToMessageMapper(){};
    /**
     * Маппит ForecastInfo в красивое сообщение для пользователя.
     * @param forecastInfo Прогноз, который нужно преобразовать в строку.
     *                     Каждый weatherInfo в ForecastInfo должен описывать один день.
     * @return Красивое представление ForecastInfo в виде строки.
     */
    public static String forecastInfoToMessage(ForecastInfo forecastInfo){
        StringBuilder sb = new StringBuilder();

        forecastInfo.getForecast().forEach(weatherInfo -> {
            sb.append(String.format("%s %s %s",
                    Emoji.CALENDAR.getText(),
                    unixTimeToDate("yyyy/MM/dd", weatherInfo.getDate(), weatherInfo.getTimezone()),
                    "\n"
            ));
            sb.append(String.format("%s %s %s",
                    getWeatherConditionEmoji(weatherInfo.getConditionId()), weatherInfo.getConditionDescription(),
                    "\n"
            ));
            sb.append(String.format("%s минимум %.1f°C, максимум %.1f°C %s",
                    Emoji.TEMPERATURE.getText(), weatherInfo.getMinTemperature(), weatherInfo.getMaxTemperature(),
                    "\n"
            ));
            sb.append(String.format("%s ощущается как %.1f°C %s",
                    Emoji.TONGUE.getText(), weatherInfo.getFeltTemperature(),
                    "\n"
            ));
            sb.append("\n");
        });

        return sb.toString();
    }

    /**
     *
     * @param weatherInfo
     * @return
     */
    public static String weatherInfoToMessage(WeatherInfo weatherInfo){
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s %s: %s",
                Emoji.CALENDAR.getText(),
                unixTimeToDate("yyyy/MM/dd", weatherInfo.getDate(), weatherInfo.getTimezone()),
                "\n"
        ));
        sb.append(String.format("%s %s %s",
                getWeatherConditionEmoji(weatherInfo.getConditionId()), weatherInfo.getConditionDescription(),
                "\n"
        ));
        sb.append(String.format("%s температура %.1f°C, ощущается как %.1f°C %s",
                Emoji.TEMPERATURE.getText(), weatherInfo.getTemperature(), weatherInfo.getFeltTemperature(),
                "\n"
        ));
        sb.append(String.format("%s минимум %.1f°C, максимум %.1f°C %s",
                Emoji.GRAPH.getText(), weatherInfo.getMinTemperature(), weatherInfo.getMaxTemperature(),
                "\n"
        ));
        sb.append(String.format("%s влажность %s%% %s",
                Emoji.WATER.getText(), weatherInfo.getHumidity(),
                "\n"
        ));
        sb.append(String.format("%s облачность %s%% %s",
                Emoji.CLOUDS.getText(), weatherInfo.getCloudiness(),
                "\n"
        ));
        sb.append(String.format("%s скорость ветра %sм/с %s",
                Emoji.WIND.getText(), weatherInfo.getWindSpeed(),
                "\n"
        ));
        sb.append(String.format("%s восход %s %s",
                Emoji.SUNSET.getText(),
                unixTimeToDate("HH:mm:ss", weatherInfo.getSunriseTimestamp(), weatherInfo.getTimezone()),
                "\n"
        ));
        sb.append(String.format("%s закат %s %s",
                Emoji.SUNRISE.getText(),
                unixTimeToDate("HH:mm:ss", weatherInfo.getSunsetTimestamp(), weatherInfo.getTimezone()),
                "\n"
        ));

        return sb.toString();
    }

    /**
     * Преобразует код погоды в Emoji.
     * Например, для значений 200-299 будет возвращен Emoji.THUNDERSTORM
     * @param code Код погоды, полученный от openweatherapi
     * @return Emoji, соответствующий погоде
     */
    private static String getWeatherConditionEmoji(Integer code){
        int prefix = code/100;

        switch (prefix){
            case 2 -> {
                return Emoji.THUNDERSTORM.getText();
            }
            case 3 -> {
                return Emoji.DRIZZLE.getText();
            }
            case 5-> {
                return Emoji.RAIN.getText();
            }
            case 6 -> {
                return Emoji.SNOW.getText();
            }
            case 7 -> {
                return Emoji.ATMOSPHERE.getText();
            }
            case 8 -> {
                if (code == 800){
                    return Emoji.CLEAR.getText();
                } else {
                    return Emoji.CLOUDS.getText();
                }
            }
            default -> {
                return Emoji.KAZAKHSTAN_FLAG.getText();
            }
        }
    }

    private static String unixTimeToDate(String pattern, Long unixTime, Integer timezoneOffset){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        OffsetDateTime offsetDateTime = Instant.ofEpochSecond(unixTime)
                .atOffset(ZoneOffset.ofTotalSeconds(timezoneOffset));
        LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
        return localDateTime.format(formatter);
    }
}
