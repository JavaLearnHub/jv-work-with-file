package core.basesyntax;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static core.basesyntax.ProductOperation.*;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class WorkWithFile {
    private static final String DATA_SEPARATOR = ",";
    private static final String EMPTY_STRING = "";
    private static final int INDEX_OF_PRODUCT_OPERATION = 0;
    private static final int INDEX_OF_PRODUCT_QUANTITY = 1;


    @SneakyThrows(IOException.class)
    public void getStatistic(String fromFileName, String toFileName) {
        File sourceFile = new File(fromFileName);
        List<String> fileRows = Files.readAllLines(sourceFile.toPath());
        File destinationFile = new File(toFileName);

        destinationFile.createNewFile();

        int buyQuantity = 0;
        int supplyQuantity = 0;
        int resultQuantity;

        for (String fileRow : fileRows) {
            String[] productInfo = fileRow.split(DATA_SEPARATOR);

            ProductOperation productOperation = ProductOperation.getOperation(productInfo[INDEX_OF_PRODUCT_OPERATION]);
            int quantity = Integer.parseInt(productInfo[INDEX_OF_PRODUCT_QUANTITY]);

            switch (productOperation) {
                case BUY -> buyQuantity += quantity;
                case SUPPLY -> supplyQuantity += quantity;
                default -> throw new RuntimeException("Operation: " + productOperation + " not supported");
            }
        }

        resultQuantity = supplyQuantity - buyQuantity;

        if (Files.exists(destinationFile.toPath())) {
            Files.write(destinationFile.toPath(), EMPTY_STRING.getBytes(), TRUNCATE_EXISTING);
        }

        StringBuilder dataToWrite = new StringBuilder()
                .append(SUPPLY.getValue())
                .append(DATA_SEPARATOR)
                .append(supplyQuantity)
                .append(System.lineSeparator())
                .append(BUY.getValue())
                .append(DATA_SEPARATOR)
                .append(buyQuantity)
                .append(System.lineSeparator())
                .append(RESULT.getValue())
                .append(DATA_SEPARATOR)
                .append(resultQuantity);

        String dataToWriteResult = dataToWrite.toString();
        Files.write(destinationFile.toPath(), dataToWriteResult.getBytes(), APPEND);

    }
}
