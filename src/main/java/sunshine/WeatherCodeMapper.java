package sunshine;

public class WeatherCodeMapper {

    public static String toKorean(int code) {
        return switch (code) {
            case 0 -> "맑음";
            case 1, 2 -> "대체로 맑음";
            case 3 -> "흐림";
            case 45, 48 -> "안개";
            case 51, 53, 55 -> "이슬비";
            case 61, 63, 65 -> "비";
            case 71, 73, 75 -> "눈";
            default -> "알 수 없음";
        };
    }
}