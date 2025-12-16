package sunshine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class WeatherServiceTest {

    private WeatherService weatherService;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();

        mockServer = MockRestServiceServer.bindTo(builder).build();

        RestClient restClient = builder.build();
        weatherService = new WeatherService(restClient);
    }

    @Test
    @DisplayName("도시 이름으로 날씨 정보를 조회하고 요약 문장을 생성한다")
    void getWeather_success() {
        // given
        String mockResponse = """
            {
              "current": {
                "temperature_2m": 3.4,
                "apparent_temperature": 0.8,
                "weather_code": 3,
                "relative_humidity_2m": 65,
                "wind_speed_10m": 5.7
              }
            }
            """;

        mockServer.expect(requestTo(org.hamcrest.Matchers.containsString("api.open-meteo.com")))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));

        // when
        WeatherResponse result = weatherService.getWeatherApiDirectCall("Seoul");

        // then
        assertThat(result.getCity()).isEqualTo("Seoul");
        assertThat(result.getTemperature()).isEqualTo(3.4);
        assertThat(result.getApparentTemperature()).isEqualTo(0.8);
        assertThat(result.getHumidity()).isEqualTo(65);
        assertThat(result.getWeather()).isEqualTo("흐림");
        assertThat(result.getSummary())
                .contains("현재 Seoul의 기온은 3.4°C")
                .contains("날씨는 흐림");
    }
}
