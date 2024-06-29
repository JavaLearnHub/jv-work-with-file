package core.basesyntax;

public enum ProductOperation {
    SUPPLY("supply"),
    BUY("buy"),
    RESULT("result");

    private final String value;

    ProductOperation(String value) {
        this.value = value;
    }

    static String getValue(ProductOperation op) {
        return op.value;
    }

    static ProductOperation getOperation(String value) {
        for (ProductOperation op : ProductOperation.values()) {
            if (op.value.equals(value)) {
                return op;
            }
        }
        return null;
    }
}
