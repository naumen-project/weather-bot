package com.example.weatherbot.mapper;

import com.example.weatherbot.service.weatherservice.ForecastInfo;
import com.example.weatherbot.service.weatherservice.WeatherInfo;

import java.util.Comparator;
import java.util.List;

/**
 * Маппит ForecastInfo в WeatherInfo.
 * Преобразует много запросов на день с промежутком в n часов в один цельный прогноз на целый день.
 */
public class ForecastMapper {
    private ForecastMapper(){}
    /**
     * Парсит ForecastInfo в WeatherInfo.
     * Находит среднюю, минимальную и макисмальную температуру по всем WeatherInfo, находящимся в ForecastInfo.
     * Присваеет WeatherInfo время заката, рассвета, таймзону и город от ForecastInfo.
     * @param forecastInfo Прогноз, по которому будет составляться WeatherInfo на один день.
     *                     Парсит все значения и находит минимальгную температуру, максимальную и среднюю.
     */
    public static WeatherInfo parseForecastToOneDayWeatherInfo(ForecastInfo forecastInfo){
        WeatherInfo result;
        result = calculateMinMaxAvgTemperature(forecastInfo.getForecast());

        result.setSunriseTimestamp(forecastInfo.getSunrise());
        result.setSunsetTimestamp(forecastInfo.getSunset());
        result.setTimezone(forecastInfo.getTimezone());
        result.setCity(forecastInfo.getCity());

        return result;
    }

    /**
     * Считает минимальное, максимальное и среднее значение температуры по списку WeatherInfo
     * @param weatherInfos Список WeatherInfo
     * @return WeatherInfo с посчитанными минимальной, максимальной и средней температурой
     */
    public static WeatherInfo calculateMinMaxAvgTemperature(List<WeatherInfo> weatherInfos){
        WeatherInfo result = weatherInfos.get(0);

        double minTemp = weatherInfos.stream()
                .min(Comparator.comparingDouble(WeatherInfo::getMinTemperature))
                .get()
                .getMinTemperature();
        double maxTemp = weatherInfos.stream()
                .max(Comparator.comparingDouble(WeatherInfo::getMaxTemperature))
                .get()
                .getMaxTemperature();
        double avgTemp = weatherInfos.stream()
                .mapToDouble(WeatherInfo::getTemperature)
                .average()
                .getAsDouble();
        double avgFeltTemp = weatherInfos.stream()
                .mapToDouble(WeatherInfo::getFeltTemperature)
                .average()
                .getAsDouble();

        result.setMinTemperature(minTemp);
        result.setMaxTemperature(maxTemp);
        result.setTemperature(avgTemp);
        result.setFeltTemperature(avgFeltTemp);
        return result;
    }
}
