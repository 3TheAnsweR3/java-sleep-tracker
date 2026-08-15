package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaximumDurationAnalysisTest {
    @Test
    @DisplayName("Должен возвращать ноль для пустого списка сессий")
    void shouldReturnZeroForEmptySessionsList() {
        MaximumDurationAnalysis maximumDurationAnalysis = new MaximumDurationAnalysis();
        List<SleepingSession> list = new ArrayList<>();
        SleepAnalysisResult result = maximumDurationAnalysis.apply(list);

        assertEquals(0L, result.getValue());
    }

    @Test
    @DisplayName("Должен возвращать максимальную продолжительность сессии")
    void shouldReturnMaximumSessionDuration() {
        MaximumDurationAnalysis maximumDurationAnalysis = new MaximumDurationAnalysis();

        SleepingSession firstSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 0),
                LocalDateTime.of(2025, 10, 2, 6, 0),
                SleepQuality.GOOD
        );
        SleepingSession secondSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 3, 23, 0),
                LocalDateTime.of(2025, 10, 4, 5, 30),
                SleepQuality.GOOD
        );
        SleepingSession thirdSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 5, 19, 30),
                LocalDateTime.of(2025, 10, 6, 4, 0),
                SleepQuality.GOOD
        );
        List<SleepingSession> list = List.of(firstSession, secondSession, thirdSession);
        SleepAnalysisResult result = maximumDurationAnalysis.apply(list);

        assertEquals(510L, result.getValue());
    }
}
