package lab2;

public class um extends Operator {

    @Override
    public double solve() {
        return a.solve() * b.solve();
    }

    public um(Expression a, Expression b) {
        super(a, b);
    }
}