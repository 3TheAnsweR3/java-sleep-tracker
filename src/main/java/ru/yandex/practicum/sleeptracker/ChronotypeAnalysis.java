package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        List<Chronotype> chronotypes = sessions.stream()
                .filter(this::isNightSession)
                .map(this::classifySession)
                .collect(Collectors.toList());

        long owlCount = chronotypes.stream()
                .filter(chronotype -> chronotype == Chronotype.OWL)
                .count();
        long larkCount = chronotypes.stream()
                .filter(chronotype -> chronotype == Chronotype.LARK)
                .count();
        long pigeonCount = chronotypes.stream()
                .filter(chronotype -> chronotype == Chronotype.PIGEON)
                .count();

        Chronotype finalType = Chronotype.PIGEON;

        if (owlCount > larkCount && owlCount > pigeonCount) {
            finalType = Chronotype.OWL;
        } else if (larkCount > owlCount && larkCount > pigeonCount) {
            finalType = Chronotype.LARK;
        }

        return new SleepAnalysisResult("Хронотип пользователя", finalType);
    }

    private Chronotype classifySession(SleepingSession session) {
        LocalTime sleepStartTime = session.getStartTime().toLocalTime();
        LocalTime wakeUpTime = session.getEndTime().toLocalTime();

        boolean fallsAsleepAfterTwentyThree = sleepStartTime.isAfter(LocalTime.of(23, 0));
        boolean wakesUpAfterNine = wakeUpTime.isAfter(LocalTime.of(9, 0));
        boolean fallsAsleepBeforeTwentyTwo = sleepStartTime.isBefore(LocalTime.of(22, 0));
        boolean wakesUpBeforeSeven = wakeUpTime.isBefore(LocalTime.of(7, 0));

        if (fallsAsleepAfterTwentyThree && wakesUpAfterNine) {
            return Chronotype.OWL;
        } else if (fallsAsleepBeforeTwentyTwo && wakesUpBeforeSeven) {
            return Chronotype.LARK;
        } else {
            return Chronotype.PIGEON;
        }
    }

    private boolean isNightSession(SleepingSession session) {
        boolean crossesMidnight =
                !session.getStartTime().toLocalDate().equals(session.getEndTime().toLocalDate());
        boolean startsBeforeSix =
                session.getStartTime().toLocalTime().isBefore(LocalTime.of(6, 0));

        return crossesMidnight || startsBeforeSix;
    }

}
