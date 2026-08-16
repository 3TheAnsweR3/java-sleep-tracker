package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AverageDurationAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        double averageDuration = sessions.stream()
                .mapToLong(session -> session.getDurationInMinutes())
                .average()
                .orElse(0.0);
        return new SleepAnalysisResult("Средняя продолжительность сессии сна в минутах", averageDuration);
    }

}
