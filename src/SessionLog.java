import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
public class SessionLog {
    private static final LinkedHashMap<String, List<Map.Entry<String, Map.Entry<String, String>>>> logMap = new LinkedHashMap<>();
    public static void addToLog(String expression, String result){
        LocalDateTime currentTime = LocalDateTime.now();

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = currentTime.format(formatterTime);

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = currentTime.format(formatterDate);

        //Pairs expression with result
        Map.Entry<String, String> expressionResultPair = Map.entry(expression, result);

        // Pair the previous pair with time
        Map.Entry<String, Map.Entry<String, String>> timeExpressionPair = Map.entry(time, expressionResultPair);

        // If date doesn't exist in list, then it adds the date
        List<Map.Entry<String, Map.Entry<String, String>>> entriesForDate = logMap.computeIfAbsent(date, k -> new ArrayList<>());

        // Adds the Time & Expression pair to the list
        entriesForDate.add(timeExpressionPair);

    }
    public static String logGui(){
        StringBuilder returnString = new StringBuilder();

        for (Map.Entry<String, List<Map.Entry<String, Map.Entry<String, String>>>> entry : logMap.entrySet()) {
            String date = entry.getKey();
            List<Map.Entry<String, Map.Entry<String, String>>> entriesForDate = entry.getValue();

            returnString.append("Date: ").append(date).append("\n");

            for (Map.Entry<String, Map.Entry<String, String>> timeExpressionPair : entriesForDate) {
                String time = timeExpressionPair.getKey();
                Map.Entry<String, String> expressionResultPair = timeExpressionPair.getValue();

                String expression = expressionResultPair.getKey();
                String result = expressionResultPair.getValue();

                returnString.append("Time: ").append(time)
                        .append(": ").append(expression)
                        .append(" = ").append(result).append("\n");
            }
            returnString.append("\n");
        }
        return returnString.toString().trim();
    }
    public static boolean exportLog(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fileDate = currentTime.format(formatterDate);

        String userHome = System.getProperty("user.home");
        String fileName = "Log_" + fileDate + ".txt";
        String filePath = userHome + "/" + fileName;


        StringBuilder fileContent = new StringBuilder();
        fileContent.append("Calculator Session Log:\n\n");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, List<Map.Entry<String, Map.Entry<String, String>>>> entry : logMap.entrySet()) {
                String date = entry.getKey();
                List<Map.Entry<String, Map.Entry<String, String>>> entriesForDate = entry.getValue();

                fileContent.append("Date: ").append(date).append("\n");

                for (Map.Entry<String, Map.Entry<String, String>> timeExpressionPair : entriesForDate) {
                    String time = timeExpressionPair.getKey();
                    Map.Entry<String, String> expressionResultPair = timeExpressionPair.getValue();

                    String expression = expressionResultPair.getKey();
                    String result = expressionResultPair.getValue();

                    fileContent.append("Time: ").append(time)
                            .append(": ").append(expression)
                            .append(" = ").append(result).append("\n");
                }
                fileContent.append("\n");
            }

            // Write content to the file
            writer.write(fileContent.toString());
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }
}
