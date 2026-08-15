package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleepLogReader {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static SleepingSession parseSession(String line) {
        String[] parts = line.split(";");
        LocalDateTime startTime = LocalDateTime.parse(parts[0], FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(parts[1], FORMATTER);
        SleepQuality quality = SleepQuality.valueOf(parts[2]);
        return new SleepingSession(startTime, endTime, quality);
    }

    public static List<SleepingSession> readSessions(String filePath) throws IOException {
        Path path = Path.of(filePath);

        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines
                    .map(SleepLogReader::parseSession)
                    .collect(Collectors.toList());
        }
    }
}
