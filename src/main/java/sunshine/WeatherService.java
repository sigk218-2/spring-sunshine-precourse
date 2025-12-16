package sunshine;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherService {

    private final RestClient restClient;

    public WeatherService(RestClient restClient) {
        this.restClient = restClient;
    }

    public WeatherResponse getWeatherApiDirectCall(String cityName) {
        City city = City.from(cityName);

        OpenMeteoResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.open-meteo.com")
                        .path("/v1/forecast")
                        .queryParam("latitude", city.getLatitude())
                        .queryParam("longitude", city.getLongitude())
                        .queryParam("current",
                                "temperature_2m,apparent_temperature,weather_code,relative_humidity_2m,wind_speed_10m")
                        .build())
                .retrieve()
                .body(OpenMeteoResponse.class);

        OpenMeteoResponse.Current current = response.getCurrent();

        String weatherDesc = WeatherCodeMapper.toKorean(current.getWeather_code());

        String summary = String.format(
                "현재 %s의 기온은 %.1f°C이며, 체감 온도는 %.1f°C입니다. 날씨는 %s입니다.",
                city.getName(),
                current.getTemperature_2m(),
                current.getApparent_temperature(),
                weatherDesc
        );

        return new WeatherResponse(
                city.getName(),
                current.getTemperature_2m(),
                current.getApparent_temperature(),
                current.getRelative_humidity_2m(),
                weatherDesc,
                summary
        );
    }
}