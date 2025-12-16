package sunshine;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeatherResponse {

    private String city;
    private double temperature;
    private double apparentTemperature;
    private int humidity;
    private String weather;
    private String summary;
}

