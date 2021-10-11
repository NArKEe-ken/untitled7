import java.util.Scanner;


import static java.awt.PageAttributes.MediaType.A2;

public class DROB {
    public static Rational A2() {
        int q, w, z, v;
        System.out.print("введите число 1 или 2, 1-ведем целое число, 2-введем дробь, любое другое число дефолт");
        Scanner scan = new Scanner(System.in);
        v = scan.nextInt();
        int r;
        if (v == 1) {
            System.out.println("введите ваше число ");
            r = scan.nextInt();// целое число
            return new Rational(r, 1);
        }
        if (v == 2) {
            System.out.println("введите вашу дрообь");
            w = scan.nextInt();
            z = scan.nextInt();
            return new Rational(w, z);
        }
        System.out.println("установлен дефолт");
        return new Rational(1, 1);
    }


}