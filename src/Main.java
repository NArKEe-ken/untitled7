import lab2.*;
import lab2.Number;

import javax.swing.text.html.parser.Parser;

public class Main {
    public static void main(String[] args) {
        Rational rational = new Rational();

        Rational b = new Rational(1, 2);
        DROB outDrob = new DROB();

        Rational a = outDrob.A2();
        System.out.println("lab 1");
        System.out.println(a.toString() + " + " + b.toString() + " = " + Rational.add(a, b));
        System.out.println(a.toString() + " - " + b.toString() + " = " + Rational.sub(a, b));
        System.out.println(b.toString() + " - " + a.toString() + " = " + Rational.sub(b, a));
        System.out.println(a.toString() + " * " + b.toString() + " = " + Rational.mul(a, b));
        System.out.println(a.toString() + " % " + b.toString() + " = " + Rational.div(a, b));
        System.out.println("lab2");
        System.out.println((new Sum(new Number(5), new Number(6))).solve());
        System.out.println((new div(new Number(5), new Number(6))).solve());
        System.out.println((new um(new Number(5), new Number(6))).solve());
        System.out.println((new del(new Number(5), new Number(6))).solve());
        // Parser outparser = new Parser();

     //    System.out.println(outparser.Main(main()));

    }
}
//компановщик
