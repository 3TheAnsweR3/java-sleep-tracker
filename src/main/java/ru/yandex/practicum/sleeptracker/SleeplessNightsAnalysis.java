package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightsAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }
        LocalDateTime firstSessionStart = sessions.get(0).getStartTime();
        LocalDate firstNightDate = firstSessionStart.toLocalDate();

        if (firstSessionStart.toLocalTime().isAfter(LocalTime.of(12, 0))) {
            firstNightDate = firstNightDate.plusDays(1);
        }

        LocalDateTime lastSessionEnd = sessions.get(sessions.size() - 1).getEndTime();
        LocalDate lastSessionEndDate = lastSessionEnd.toLocalDate();

        long totalNights = ChronoUnit.DAYS.between(firstNightDate, lastSessionEndDate.plusDays(1));
        long nightsWithSleep = sessions.stream()
                .filter(session -> {
                    boolean crossesMidnight =
                            !session.getStartTime().toLocalDate().equals(session.getEndTime().toLocalDate());
                    boolean startsBeforeSix =
                            session.getStartTime().toLocalTime().isBefore(LocalTime.of(6, 0));

                    return crossesMidnight || startsBeforeSix;
                })
                .count();
        long sleeplessNights = totalNights - nightsWithSleep;

        return new SleepAnalysisResult("Количество бессонных ночей", sleeplessNights);
    }

}
