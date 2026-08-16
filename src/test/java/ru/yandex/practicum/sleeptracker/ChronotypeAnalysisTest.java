package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChronotypeAnalysisTest {
    @Test
    @DisplayName("Должен определять хронотип совы")
    void shouldReturnOwlWhenOwlNightsPredominate() {
        ChronotypeAnalysis chronotypeAnalysis = new ChronotypeAnalysis();
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 30),
                LocalDateTime.of(2025, 10, 2, 9, 30),
                SleepQuality.GOOD
        );
        List<SleepingSession> list = List.of(session);
        SleepAnalysisResult result = chronotypeAnalysis.apply(list);

        assertEquals(Chronotype.OWL, result.getValue());

    }

    @Test
    @DisplayName("Должен определять хронотип жаворонка")
    void shouldReturnLarkWhenLarkNightsPredominate() {
        ChronotypeAnalysis chronotypeAnalysis = new ChronotypeAnalysis();
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 21, 30),
                LocalDateTime.of(2025, 10, 2, 6, 30),
                SleepQuality.GOOD
        );
        List<SleepingSession> list = List.of(session);
        SleepAnalysisResult result = chronotypeAnalysis.apply(list);

        assertEquals(Chronotype.LARK, result.getValue());

    }

    @Test
    @DisplayName("Должен определять хронотип голубя")
    void shouldReturnPigeonForIntermediateSleepSchedule() {
        ChronotypeAnalysis chronotypeAnalysis = new ChronotypeAnalysis();
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 30),
                LocalDateTime.of(2025, 10, 2, 8, 0),
                SleepQuality.GOOD
        );
        List<SleepingSession> list = List.of(session);
        SleepAnalysisResult result = chronotypeAnalysis.apply(list);

        assertEquals(Chronotype.PIGEON, result.getValue());

    }

    @Test
    @DisplayName("Должен определять голубя при равном количестве разных хронотипов")
    void shouldReturnPigeonWhenChronotypeCountsAreTied() {
        ChronotypeAnalysis chronotypeAnalysis = new ChronotypeAnalysis();
        SleepingSession firstSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 30),
                LocalDateTime.of(2025, 10, 2, 9, 30),
                SleepQuality.GOOD
        );
        SleepingSession secondSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 21, 30),
                LocalDateTime.of(2025, 10, 3, 6, 30),
                SleepQuality.GOOD
        );
        List<SleepingSession> list = List.of(firstSession, secondSession);
        SleepAnalysisResult result = chronotypeAnalysis.apply(list);

        assertEquals(Chronotype.PIGEON, result.getValue());

    }

    @Test
    @DisplayName("Должен игнорировать дневные сессии при определении хронотипа")
    void shouldIgnoreDaytimeSessionsWhenDeterminingChronotype() {
        ChronotypeAnalysis chronotypeAnalysis = new ChronotypeAnalysis();
        SleepingSession firstSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 30),
                LocalDateTime.of(2025, 10, 2, 9, 30),
                SleepQuality.GOOD
        );
        SleepingSession secondSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 13, 0),
                LocalDateTime.of(2025, 10, 2, 14, 0),
                SleepQuality.GOOD
        );
        SleepingSession thirdSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 3, 15, 0),
                LocalDateTime.of(2025, 10, 3, 16, 0),
                SleepQuality.GOOD
        );
        List<SleepingSession> list = List.of(firstSession, secondSession, thirdSession);
        SleepAnalysisResult result = chronotypeAnalysis.apply(list);

        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    @DisplayName("Должен определять голубя для пустого списка сессий")
    void shouldReturnPigeonForEmptySessionsList() {
        ChronotypeAnalysis chronotypeAnalysis = new ChronotypeAnalysis();

        List<SleepingSession> list = List.of();
        SleepAnalysisResult result = chronotypeAnalysis.apply(list);

        assertEquals(Chronotype.PIGEON, result.getValue());
    }

}
