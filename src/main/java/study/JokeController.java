package study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@Slf4j
public class JokeController {
    private final ChatClient client;

    public JokeController(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    @GetMapping("/joke")
    public ChatResponse joke(
            @RequestParam(defaultValue = "Tell me a joke about {topic}") String message,
            @RequestParam(defaultValue = "programing") String topic
    ) {

        var template = new PromptTemplate(message);
        var prompt = template.render(Map.of("topic", topic));
        return client.prompt(prompt).call().chatResponse();
    }

    @GetMapping("/joke2")
    public ChatResponse joke2(
            @RequestParam(defaultValue = "soojin") String name,
            @RequestParam(defaultValue = "soft") String voice
    ) {
        var user = new UserMessage("""
                Tell me about three famous pirates from the Golden Age of Piracy and what they did.
                Write at least one sentence for each pirate.
                """);
        var template = new SystemPromptTemplate("""
                You are a helpful AI assistant.
                You are an AI assistant that helps people find information.
                Your name is {name}.
                You should reply to the user's request using your name and in the style of a {voice}.
                """);
        var system = template.createMessage(Map.of("name", name, "voice", voice));
        Prompt prompt = new Prompt(user, system);
        return client.prompt(prompt).call().chatResponse();
    }

    @GetMapping("/actors")
    public ActorsFilms actors(
            @RequestParam(defaultValue = "tomhanks") String actor
    ) {
        var beanOutputConverter = new BeanOutputConverter<>(ActorsFilms.class);
        var format = beanOutputConverter.getFormat();
        log.info(format);

        var userMessage = """
                Generate the filmography of 5 movies for {actor}.
                {format}
                """;
        var prompt = new PromptTemplate(userMessage).create(Map.of("actor", actor, "format", format));
        var text = client.prompt(prompt).call().content();
        return beanOutputConverter.convert(text);
    }

    @GetMapping("/addDays")
    public String addDays(
            @RequestParam(defaultValue = "0") int days
    ) {
        var template = new PromptTemplate("Tell me the date {days} days later");
        var prompt = template.render(Map.of("days", days));
        return client.prompt(prompt).tools(new Funtions()).call().content();
    }
}
