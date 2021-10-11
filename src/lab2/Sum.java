package lab2;

public class Sum extends Operator {

    @Override
    public double solve() {
        return a.solve() + b.solve();
    }

    public Sum(Expression a, Expression b) {
        super(a, b);
    }
}
