package lab2;

public abstract class Operator extends Expression {

    protected Expression a, b;

    public Operator(Expression a, Expression b) {
        this.a = a;
        this.b = b;
    }
}
