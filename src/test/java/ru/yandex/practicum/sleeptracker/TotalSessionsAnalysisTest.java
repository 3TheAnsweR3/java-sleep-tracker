package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalSessionsAnalysisTest {
    @Test
    @DisplayName("Должен возвращать ноль для пустого списка сессий")
    void shouldReturnZeroForEmptySessionsList() {
        TotalSessionsAnalysis totalSessionsAnalysis = new TotalSessionsAnalysis();
        List<SleepingSession> list = List.of();
        SleepAnalysisResult result = totalSessionsAnalysis.apply(list);

        assertEquals(0, result.getValue());
    }

    @Test
    @DisplayName("Должен возвращать количество сессий в непустом списке")
    void shouldReturnSessionsCount() {
        TotalSessionsAnalysis totalSessionsAnalysis = new TotalSessionsAnalysis();
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 0),
                LocalDateTime.of(2025, 10, 2, 7, 0),
                SleepQuality.GOOD
        );
        List<SleepingSession> list = List.of(session, session, session);
        SleepAnalysisResult result = totalSessionsAnalysis.apply(list);

        assertEquals(3, result.getValue());
        assertEquals("Общее количество сессий сна", result.getDescription()
        );

    }
}
