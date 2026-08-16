package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final LocalTime OWL_BEDTIME_THRESHOLD =
            LocalTime.of(23, 0);

    private static final LocalTime OWL_WAKE_UP_THRESHOLD =
            LocalTime.of(9, 0);

    private static final LocalTime LARK_BEDTIME_THRESHOLD =
            LocalTime.of(22, 0);

    private static final LocalTime LARK_WAKE_UP_THRESHOLD =
            LocalTime.of(7, 0);

    private static final LocalTime NIGHT_END_TIME =
            LocalTime.of(6, 0);

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

        boolean fallsAsleepAfterTwentyThree = sleepStartTime.isAfter(OWL_BEDTIME_THRESHOLD);
        boolean wakesUpAfterNine = wakeUpTime.isAfter(OWL_WAKE_UP_THRESHOLD);
        boolean fallsAsleepBeforeTwentyTwo = sleepStartTime.isBefore(LARK_BEDTIME_THRESHOLD);
        boolean wakesUpBeforeSeven = wakeUpTime.isBefore(LARK_WAKE_UP_THRESHOLD);

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
                session.getStartTime().toLocalTime().isBefore(NIGHT_END_TIME);

        return crossesMidnight || startsBeforeSix;
    }

}
