
public class Rational {
    private int n, d;

    public Rational() {
    }

    private static int gcd(int n, int d) {//наибольший общий делитель
        return (d == 0 ? n : gcd(d, n % d));
    }

    public Rational(int n, int d) throws ArithmeticException {
        int divisor = gcd(n, d);
        this.n = n / divisor;
        this.d = d / divisor;  //к общему знаменателю
    }


    public static Rational add(Rational x, Rational y) {
        return new Rational(x.n * y.d + y.n * x.d, x.d * y.d);
    }

    public static Rational sub(Rational x, Rational y) {
        return new Rational(x.n * y.d - y.n * x.d, x.d * y.d);
    }

    public static Rational mul(Rational x, Rational y) {
        return new Rational(x.n * y.n, x.d * y.d);
    }

    public static Rational div(Rational x, Rational y) {
        return new Rational(x.n * y.d, x.d * y.n);
    }

    public String toString() {
        return n + "/" + d;
    }
}


