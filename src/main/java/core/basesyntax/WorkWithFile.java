package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class WorkWithFile {
    public void getStatistic(String fromFileName, String toFileName) {
        File sourceFile = new File(fromFileName);
        File destinationFile = new File(toFileName);
        List<String> statisticDataStrings;

        try{
            destinationFile.createNewFile();
        } catch (IOException e){
            throw new RuntimeException("Can't create a file", e);
        }

        try {
            statisticDataStrings = Files.readAllLines(sourceFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }

        for (String string : statisticDataStrings) {

            String[] dataString = string.split(",");
            if (dataString[0].equals("supply")) {
                StatisticData.SUPPLY.setData(StatisticData.SUPPLY.getData() + Integer.valueOf(dataString[1]));
            }
            else StatisticData.BUY.setData(StatisticData.BUY.getData() + Integer.valueOf(dataString[1]));
        }

        StatisticData.RESULT.setData(StatisticData.SUPPLY.getData()-StatisticData.BUY.getData());
        try{
            if (Files.exists(destinationFile.toPath())) {
                Files.write(destinationFile.toPath(), "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            }

            for(StatisticData data : StatisticData.values()){

                String dataToWrite = data.toString().toLowerCase() + "," + data.getData() + "\n";
                Files.write(destinationFile.toPath(), dataToWrite.getBytes(), StandardOpenOption.APPEND);

                data.setData(0);
            }
        }catch (IOException e){
                throw new RuntimeException("Can't write data(string) to file", e);
        }
    }
}
