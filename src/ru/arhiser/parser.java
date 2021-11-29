package ru.arhiser;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.HashMap;

    class Main {
        /*------------------------------------------------------------------
         * PARSER RULES
         *------------------------------------------------------------------*/

//    expr : plusminus* EOF ;
//
//    plusminus: multdiv ( ( '+' | '-' ) multdiv )* ;
//
//    multdiv : factor ( ( '*' | '/' ) factor )* ;
//
//    factor : func | unary | NUMBER | '(' expr ')' ;
//
//    unary : '-' factor
//
//    func : NAME '(' (expr (',' expr)+)? ')'
        // лексема это единица, выше тправила. 3) номер или выражение в скобках 2)  умножение деление несколько факторов и скобки 1) сложение деление вычитание
        // рекурентные правила каждый член последовательности. через. предыдущих
        // используется на подобии синтактического анализатора
// мы тут используем несоклько анализаторов каждый из которы отвечает за свои значения и ликсемы


        public static HashMap<String, Function> functionMap;
        public static void main(String[] args) {
            functionMap = getFunctionMap();
            Scanner scan = new Scanner(System.in);
            String expressionText = scan.next();
            //String expressionText ="10 - 34 - 3* (55 + 5* (3 - 2)) * 2";
            List<Lexeme> lexemes = lexAnalyze(expressionText); //лексический анализатор
            LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
            System.out.println(expr(lexemeBuffer));
        }
// создаем для типой лексем; операторы скобки умножение деления и так далее
        public enum LexemeType {
            LEFT_BRACKET, RIGHT_BRACKET,
            OP_PLUS, OP_MINUS, OP_MUL, OP_DIV,
            NUMBER,NAME, COMMA,
            EOF;// конец строки
        }

        public interface Function {
            int apply(List<Integer> args);
        }

        public static HashMap<String, Function> getFunctionMap() {
            HashMap<String, Function> functionTable = new HashMap<>();
            functionTable.put("min", args -> {

                if (args.isEmpty()) {
            throw new RuntimeException("No arguments for function min");
        }
        int min = args.get(0);
            for (Integer val: args) {
            if (val < min) {
                min = val;
            }
        }
            return min;
    });
        functionTable.put("pow", args -> {
        if (args.size() != 2) {
        throw new RuntimeException("Wrong argument count for function pow: " + args.size());
        }
        return (int) Math.pow(args.get(0), args.get(1));
        });
        functionTable.put("rand", args -> {
        if (!args.isEmpty()) {
        throw new RuntimeException("Wrong argument count for function rand");
        }
        return (int)(Math.random() * 256f);
        });
        functionTable.put("avg", args -> {
        int sum = 0;
        for (int i = 0; i < args.size(); i++) {
        sum += args.get(i);
        }
        return sum / args.size();
        });
        return functionTable;
        }
// вспомагательный класс для представления лексем
        public static class Lexeme {
            LexemeType type;
            String value;
// конструктор лексем
            public Lexeme(LexemeType type, String value) {
                this.type = type;
                this.value = value;
            }

            public Lexeme(LexemeType type, Character value) {
                this.type = type;
                this.value = value.toString();
            }

            @Override
            public String toString() {
                return "Lexeme{" +
                        "type=" + type +
                        ", value='" + value + '\'' +
                        '}';
            }
        }
// используем для того чтобы запоминать позицию и всю информацию. принцип ООП
        // вспомогательный класс
        public static class LexemeBuffer {
            private int pos;

            public List<Lexeme> lexemes; //конструктор

            public LexemeBuffer(List<Lexeme> lexemes) {
                this.lexemes = lexemes;
            }

            public Lexeme next() {
                return lexemes.get(pos++);
            } //метод получения ликсемы передвигаем вправо на 1

            public void back() {
                pos--;
            }//если приедтся вернутся назад и даьшше узнать позицию

            public int getPos() {
                return pos;
            }
        }
// функция лексического анализа
        //функция принимает строку выражения просто идет по строке и сканирует и вписывает лексемы пока не дойдет до конца с символом
        public static List<Lexeme> lexAnalyze(String expText) {
            ArrayList<Lexeme> lexemes = new ArrayList<>();
            int pos = 0;
            while (pos< expText.length()) { // берем символ из текса
                char c = expText.charAt(pos);
                switch (c) {
                    case '(':
                        lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));// добавляем ликсему
                        pos++; // к следующей лексеме
                        continue;
                    case ')':
                        lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                        pos++;
                        continue;
                    case '+':
                        lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                        pos++;
                        continue;
                    case '-':
                        lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                        pos++;
                        continue;
                    case '*':
                        lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                        pos++;
                        continue;
                    case '/':
                        lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
                        pos++;
                        continue;
                    case ',':
                        lexemes.add(new Lexeme(LexemeType.COMMA, c));
                        pos++;
                        continue;

                    default: // если это символ то делаем условие точнее цифра если она цифра мы считываем число
                        if (c <= '9' && c >= '0') {
                            StringBuilder sb = new StringBuilder();// сюда добавляем цифры
                            do { //цикл из за того что мы знаем что жто цифра увеличивая позицию переходим к след цифре
                                sb.append(c);
                                pos++;
                                if (pos >= expText.length()) {
                                    break; //если в коцне то все
                                }
                                c = expText.charAt(pos);// следующую цифру из строки смысл в том что пока мы не найдем следующую знак то мы смотрим только цифры
                                // ну и склеиваем его
                            } while (c <= '9' && c >= '0'); //пробел необхзодимо прервать анализ пишем исклбчение
                            lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                        } else {
                            if (c != ' ') {
                                if (c >= 'a' && c <= 'z'
                                        || c >= 'A' && c <= 'Z') {
                                    StringBuilder sb = new StringBuilder();
                                    do {
                                        sb.append(c);
                                        pos++;
                                        if (pos >= expText.length()) {
                                            break;
                                        }
                                        c = expText.charAt(pos);
                                    } while (c >= 'a' && c <= 'z'
                                            || c >= 'A' && c <= 'Z');

                                    if (functionMap.containsKey(sb.toString())) {
                                        lexemes.add(new Lexeme(LexemeType.NAME, sb.toString()));
                                    } else {
                                        throw new RuntimeException("Unexpected character: " + c);
                                    }
                                }
                            } else {
                                pos++;
                            }
                        }
                }
            }
            lexemes.add(new Lexeme(LexemeType.EOF, "")); //конец строки
            return lexemes; //возвращаем массив лексем
        }


            // можно не писать это правило но все же оставлю
        public static int expr(LexemeBuffer lexemes) {
            Lexeme lexeme = lexemes.next();
            if (lexeme.type == LexemeType.EOF) { //проверка на пустое выражение
                return 0;
            } else {
                lexemes.back();
                return plusminus(lexemes);// возращемся назад к плюс минус
            }
        }

        public static int plusminus(LexemeBuffer lexemes) {
            int value = multdiv(lexemes);
            while (true) {
                Lexeme lexeme = lexemes.next();
                switch (lexeme.type) {
                    case OP_PLUS:
                        value += multdiv(lexemes);
                        break;
                    case OP_MINUS:
                        value -= multdiv(lexemes);
                        break;
                    case EOF:
                    case RIGHT_BRACKET:
                    case COMMA:
                        lexemes.back();
                        return value;
                    default:
                        throw new RuntimeException("Unexpected token: " + lexeme.value
                                + " at position: " + lexemes.getPos());
                }
            }
        }

        public static int multdiv(LexemeBuffer lexemes) {
            int value = factor(lexemes);
            while (true) {// цикл
                Lexeme lexeme = lexemes.next();//достаем следующую лексему
                switch (lexeme.type) { // если там что то другое
                    case OP_MUL://умножение
                        value *= factor(lexemes);
                        break;
                    case OP_DIV://деление
                        value /= factor(lexemes);
                        break;
                    case EOF:
                    case RIGHT_BRACKET:
                    case OP_PLUS:
                    case COMMA:
                    case OP_MINUS:
                        lexemes.back();
                        return value;
                    default:
                        throw new RuntimeException("Unexpected token: " + lexeme.value
                                + " at position: " + lexemes.getPos());
                }
            }
        }
//номер или выражение в скобках
        public static int factor(LexemeBuffer lexemes) {
            Lexeme lexeme = lexemes.next();//читаем лексему и тип
            switch (lexeme.type) {
                case NAME:
                    lexemes.back();
                    return func(lexemes);
                case OP_MINUS:
                    int value =factor(lexemes);
                    return -value;
                case NUMBER:
                    return Integer.parseInt(lexeme.value);//если номер то оно и содержится
                case LEFT_BRACKET:// встретили скобку то
                    value = plusminus(lexemes);
                    lexeme = lexemes.next();
                    if (lexeme.type != LexemeType.RIGHT_BRACKET) {// проверка на лексему
                        throw new RuntimeException("Unexpected token: " + lexeme.value //проверка исключения, в какой позиции возникли пробемы и возращем значение
                        // если встретили что то другое то проблемы с синтакцсисом
                                + " at position: " + lexemes.getPos());
                    }
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + lexeme.value
                            + " at position: " + lexemes.getPos());
            }
        }
        public static int func(LexemeBuffer lexemeBuffer) {
            String name = lexemeBuffer.next().value;
            Lexeme lexeme = lexemeBuffer.next();

            if (lexeme.type != LexemeType.LEFT_BRACKET) {
                throw new RuntimeException("Wrong function call syntax at " + lexeme.value);
            }

            ArrayList<Integer> args = new ArrayList<>();

            lexeme = lexemeBuffer.next();
            if (lexeme.type != LexemeType.RIGHT_BRACKET) {
                lexemeBuffer.back();
                do {
                    args.add(expr(lexemeBuffer));
                    lexeme = lexemeBuffer.next();

                    if (lexeme.type != LexemeType.COMMA && lexeme.type != LexemeType.RIGHT_BRACKET) {
                        throw new RuntimeException("Wrong function call syntax at " + lexeme.value);
                    }

                } while (lexeme.type == LexemeType.COMMA);
            }
            return functionMap.get(name).apply(args);
        }
        public class tree {
            public static void main(String[] params) {
                Scanner scan = new Scanner(System.in);
                String root = scan.next();
                System.out.println(root);
            }

            static class Tree {
                int value;
                Tree left;
                Tree right;

                public Tree(int value,Tree left,Tree right) {
                    this.value = value;
                    this.left = left;
                    this.right = right;
                }

                public Tree(int value) {
                    this.value = value;
                }

            }

    }
    
