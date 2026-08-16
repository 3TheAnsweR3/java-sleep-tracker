package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleeplessNightsAnalysisTest {
    @Test
    @DisplayName("Должен возвращать ноль для пустого списка сессий")
    void shouldReturnZeroForEmptySessionsList() {
        SleeplessNightsAnalysis sleeplessNightsAnalysis = new SleeplessNightsAnalysis();
        List<SleepingSession> list = new ArrayList<>();
        SleepAnalysisResult result = sleeplessNightsAnalysis.apply(list);

        assertEquals(0L, result.getValue());
    }

    @Test
    @DisplayName("Должен возвращать ноль, если пользователь спал ночью")
    void shouldReturnZeroWhenSessionCrossesMidnight() {
        SleeplessNightsAnalysis sleeplessNightsAnalysis = new SleeplessNightsAnalysis();

        SleepingSession firstSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 0),
                LocalDateTime.of(2025, 10, 2, 7, 0),
                SleepQuality.GOOD
        );

        List<SleepingSession> list = List.of(firstSession);
        SleepAnalysisResult result = sleeplessNightsAnalysis.apply(list);

        assertEquals(0L, result.getValue());
    }

    @Test
    @DisplayName("Должен учитывать сон, начавшийся после полуночи")
    void shouldReturnZeroWhenSessionStartsBeforeSix() {
        SleeplessNightsAnalysis sleeplessNightsAnalysis = new SleeplessNightsAnalysis();

        SleepingSession firstSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 2, 0),
                LocalDateTime.of(2025, 10, 2, 7, 0),
                SleepQuality.GOOD
        );

        List<SleepingSession> list = List.of(firstSession);
        SleepAnalysisResult result = sleeplessNightsAnalysis.apply(list);

        assertEquals(0L, result.getValue());
    }

    @Test
    @DisplayName("Должен считать ночь бессонной при наличии только дневной сессии")
    void shouldReturnOneWhenSessionDoesNotOverlapNight() {
        SleeplessNightsAnalysis sleeplessNightsAnalysis = new SleeplessNightsAnalysis();

        SleepingSession firstSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 7, 0),
                LocalDateTime.of(2025, 10, 2, 11, 0),
                SleepQuality.GOOD
        );

        List<SleepingSession> list = List.of(firstSession);
        SleepAnalysisResult result = sleeplessNightsAnalysis.apply(list);

        assertEquals(1L, result.getValue());
    }

    @Test
    @DisplayName("Должен считать бессонные ночи при переходе между месяцами")
    void shouldCountSleeplessNightAcrossMonthBoundary() {
        SleeplessNightsAnalysis sleeplessNightsAnalysis = new SleeplessNightsAnalysis();

        SleepingSession firstSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 30, 23, 0),
                LocalDateTime.of(2025, 10, 31, 7, 0),
                SleepQuality.GOOD
        );
        SleepingSession secondSession = new SleepingSession(
                LocalDateTime.of(2025, 11, 1, 23, 0),
                LocalDateTime.of(2025, 11, 2, 7, 0),
                SleepQuality.GOOD
        );

        List<SleepingSession> list = List.of(firstSession, secondSession);
        SleepAnalysisResult result = sleeplessNightsAnalysis.apply(list);

        assertEquals(1L, result.getValue());
    }

    @Test
    @DisplayName("Должен считать бессонные ночи за период больше месяца")
    void shouldCountSleeplessNightsForPeriodLongerThanMonth() {
        SleeplessNightsAnalysis sleeplessNightsAnalysis = new SleeplessNightsAnalysis();

        SleepingSession firstSession = new SleepingSession(
                LocalDateTime.of(2025, 1, 1, 23, 0),
                LocalDateTime.of(2025, 1, 2, 7, 0),
                SleepQuality.GOOD
        );
        SleepingSession secondSession = new SleepingSession(
                LocalDateTime.of(2025, 2, 1, 23, 0),
                LocalDateTime.of(2025, 2, 2, 7, 0),
                SleepQuality.GOOD
        );

        List<SleepingSession> list = List.of(firstSession, secondSession);
        SleepAnalysisResult result = sleeplessNightsAnalysis.apply(list);

        assertEquals(30L, result.getValue());
    }
}
