package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MaximumDurationAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long maximumDuration = sessions.stream()
                .mapToLong(session -> session.getDurationInMinutes())
                .max()
                .orElse(0);
        return new SleepAnalysisResult("Максимальная продолжительность сессии сна в минутах", maximumDuration);
    }
}
