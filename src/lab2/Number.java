package lab2;

public class Number extends Expression {

    private double value;

    @Override
    public double solve() {
        return value;
    }

    public Number(double value) {
        this.value = value;
    }
}
