package sunshine;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum City {

    SEOUL("Seoul", 37.5665, 126.9780),
    TOKYO("Tokyo", 35.6895, 139.6917),
    NEW_YORK("NewYork", 40.7128, -74.0060),
    PARIS("Paris", 48.8566, 2.3522),
    LONDON("London", 51.5074, -0.1278);

    private final String name;
    private final double latitude;
    private final double longitude;

    public static City from(String cityName) {
        return Arrays.stream(values())
                .filter(city -> city.name.equalsIgnoreCase(cityName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 도시입니다."));
    }
}