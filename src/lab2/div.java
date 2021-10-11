package lab2;

public class div extends Operator {

    @Override
    public double solve() {
        return a.solve() / b.solve();
    }

    public div(Expression a, Expression b) {
        super(a, b);
    }
}

