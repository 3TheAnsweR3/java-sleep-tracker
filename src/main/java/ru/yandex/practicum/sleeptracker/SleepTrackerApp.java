package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {
    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>>
            ANALYSES = List.of(
                    new TotalSessionsAnalysis(),
                    new MinimumDurationAnalysis(),
                    new MaximumDurationAnalysis(),
                    new AverageDurationAnalysis(),
                    new BadQualitySessionsAnalysis(),
                    new SleeplessNightsAnalysis(),
                    new ChronotypeAnalysis()
    );

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу с логом сна.");
            return;
        }
        String filePath = args[0];

        try {
            List<SleepingSession> sessions = SleepLogReader.readSessions(filePath);
            ANALYSES.stream()
                    .map(analysis -> analysis.apply(sessions))
                    .forEach(result -> System.out.println(result.getDescription() + ": " + result.getValue()));
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл с логом сна.");
        }

    }
}