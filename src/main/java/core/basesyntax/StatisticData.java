package core.basesyntax;

public enum StatisticData {
    SUPPLY(0),
    BUY(0),
    RESULT(0);

    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    StatisticData(int data){this.data = data;}
}
