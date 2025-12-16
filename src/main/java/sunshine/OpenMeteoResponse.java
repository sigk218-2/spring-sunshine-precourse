package sunshine;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OpenMeteoResponse {

    private Current current;

    @Getter
    @NoArgsConstructor
    public static class Current {
        private double temperature_2m;
        private double apparent_temperature;
        private int weather_code;
        private int relative_humidity_2m;
        private double wind_speed_10m;
    }
}