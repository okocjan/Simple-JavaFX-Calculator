package com.example.demo;

public class Model {
    public static float memory = 0;

    public float calculateBin(float number1, float number2, String operator) {
        switch (operator) {
            case "+":
                return number1 + number2;

            case "-":
                return number1 - number2;

            case "*":
                return number1 * number2;

            case "/":
                if (number2 == 0) {
                    return 0;
                } else {
                    return number1 / number2;
                }

            case "**":
                return (float) Math.pow(number1, number2);

            case "%":
                return number1 % number2;

            default:
                return 0;
        }
    }

    public float calculateUn(float number1, String operator) {
        switch (operator) {
            case "√":
                return (float) Math.sqrt(number1);
            case "n!":
                int f = 1;
                for (int i = 1; i <= number1; i++) {
                    f *= i;
                }
                return f;

            case "x^2":
                return (float) Math.pow(number1, 2);

            default:
                return 0;
        }
    }

    public float calculateMem(float number1, String operator) {
        switch (operator) {
            case "M+":
                memory += number1;
                return memory;

            case "M-":
                memory -= number1;
                return memory;

            case "MR":
                return memory;

            default:
                return 0;
        }
    }
}
