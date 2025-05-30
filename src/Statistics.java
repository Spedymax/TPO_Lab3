import java.util.*;

class Statistics {
    private double mean;
    private double variance;
    private double stdDev;
    private int min;
    private int max;
    private int mode;
    private double median;

    public Statistics(List<Integer> lengths) {
        calculateStatistics(lengths);
    }

    private void calculateStatistics(List<Integer> lengths) {
        if (lengths.isEmpty()) {
            return;
        }

        Collections.sort(lengths);

        min = lengths.get(0);
        max = lengths.get(lengths.size() - 1);

        long sum = 0;
        for (int length : lengths) {
            sum += length;
        }
        mean = (double) sum / lengths.size();

        double sumSquaredDiff = 0;
        for (int length : lengths) {
            sumSquaredDiff += Math.pow(length - mean, 2);
        }
        variance = sumSquaredDiff / lengths.size();
        stdDev = Math.sqrt(variance);

        int n = lengths.size();
        if (n % 2 == 0) {
            median = (lengths.get(n/2 - 1) + lengths.get(n/2)) / 2.0;
        } else {
            median = lengths.get(n/2);
        }

        Map<Integer, Integer> frequency = new HashMap<>();
        for (int length : lengths) {
            frequency.put(length, frequency.getOrDefault(length, 0) + 1);
        }

        int maxFreq = 0;
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                mode = entry.getKey();
            }
        }
    }

    public double getMean() { return mean; }
    public double getVariance() { return variance; }
    public double getStdDev() { return stdDev; }
    public int getMin() { return min; }
    public int getMax() { return max; }
    public int getMode() { return mode; }
    public double getMedian() { return median; }

    @Override
    public String toString() {
        return String.format(
                "Статистичні характеристики довжини слів:\n" +
                        "Середнє значення: %.2f\n" +
                        "Медіана: %.2f\n" +
                        "Мода: %d\n" +
                        "Мінімум: %d\n" +
                        "Максимум: %d\n" +
                        "Дисперсія: %.2f\n" +
                        "Стандартне відхилення: %.2f",
                mean, median, mode, min, max, variance, stdDev
        );
    }
}