package sunshine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WeatherGenaiService {
    private final ChatClient client;

    public WeatherGenaiService(ChatClient.Builder builder) {
        this.client = builder.build();
    }


    public String getWeatherSummary(City city, OpenMeteoResponse.Current current, String weatherDesc) {
        String summary = String.format(
                "현재 %s의 기온은 %.1f°C이며, 체감 온도는 %.1f°C입니다. 날씨는 %s입니다.",
                city.getName(),
                current.getTemperature_2m(),
                current.getApparent_temperature(),
                weatherDesc);

        var user = new UserMessage("""
                Tell me about three famous pirates from the Golden Age of Piracy and what they did.
                Write at least one sentence for each pirate.
                """);
        Prompt prompt = new Prompt(user);
        summary = client.prompt(prompt).call().chatResponse();
        return summary;
    }
}
