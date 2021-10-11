package lab2;

public class del extends Operator {

    @Override
    public double solve() {
        return a.solve() - b.solve();
    }

    public del(Expression a, Expression b) {
        super(a, b);
    }
}
