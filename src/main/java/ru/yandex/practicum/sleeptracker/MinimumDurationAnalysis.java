package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MinimumDurationAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long minimumDuration = sessions.stream()
                .mapToLong(session -> session.getDurationInMinutes())
                .min()
                .orElse(0);
        return new SleepAnalysisResult("Минимальная продолжительность сессии сна в минутах", minimumDuration);
    }


}
