package com.example.demo;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class mainController {

    @FXML
    private Label history;
    @FXML
    private Label result;

    private float number1 = 0;
    private String operator = "";
    private boolean start = true;
    private final String path = getPath();
    private final Model model = new Model();

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private String getPath() {

        String system = System.getProperty("os.name").toLowerCase();
        if (system.contains("windows")) {
            return System.getProperty("user.dir") + "\\logs.log";
        } else if (system.contains("mac")) {
            return System.getProperty("user.dir") + "/logs.log";
        } else {
            return "ERROR";
        }
    }

    @FXML
    public void processNumbers(ActionEvent event) {

        if (start) {
            history.setText("");
            result.setText("0");
            start = false;
        }
        String id = ((Button) event.getSource()).getId();
        String value = ((Button) event.getSource()).getText();
        if (result.getText().startsWith("0")) {
            result.setText("");
        }

        try {
            if (result.getText().contains(".") && id.equals("dot")) {
                value = "";
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        result.setText(result.getText() + value);
    }

    @FXML
    public void processBinOperators(ActionEvent event) {

        String value = ((Button) event.getSource()).getText();
        if (!value.equals("=")) {
            if (!operator.isEmpty()) {
                return;
            }
            operator = value;
            number1 = Float.parseFloat(result.getText());
            result.setText("");

            logs("First number inserted: " + number1);
            logs("Selected operator: " + operator);
            history.setText(number1 + " " + operator);
        } else {
            if (operator.isEmpty()) {
                return;
            }
            float number2 = Float.parseFloat(result.getText());
            logs("Second number inserted: " + number2);
            history.setText(history.getText() + " " + number2);
            float output = model.calculateBin(number1, number2, operator);

            logs("Equation: " + number1 + " " + operator + " " +
                    number2 + " = " + output);

            result.setText(String.valueOf(output));
            operator = "";
            start = true;
        }

    }

    @FXML
    public void processUnOperators(ActionEvent event) {

        String value = ((Button) event.getSource()).getText();
        if (!operator.isEmpty()) {
            return;
        }

        operator = value;
        number1 = Float.parseFloat(result.getText());
        result.setText("");
        switch (operator) {
            case "√" -> history.setText("√(" + number1 + ")");
            case "x^2" -> history.setText("(" + number1 + ")^2");
            case "n!" -> history.setText("(" + number1 + ")!");
        }
        float output = model.calculateUn(number1, operator);
        logs("Equation: " + operator + " on " + number1 + " = " + output);
        result.setText(String.valueOf(output));
        operator = "";
        start = true;
    }

    @FXML
    public void ClearFunction() {

        operator = "";
        history.setText("");
        Model.setMemory(0);
        start = true;
        result.setText("0");
        logs("Calculator data cleared\n");
    }

    @FXML
    public void processMemory(ActionEvent event) {

        String value = ((Button) event.getSource()).getText();
        if (!operator.isEmpty()) {
            return;
        }
        operator = value;
        number1 = Float.parseFloat(result.getText());
        result.setText("0");
        double mem = Model.getMemory();
        double output = model.calculateMem(number1, operator);

        if (operator.equals("MR")) {
            logs("In memory: " + Model.getMemory());
        } else {
            logs("Memory operation, In memory " + mem + ", operation " +
                    operator + ", number: " + number1 + ", equals: " + output);
        }

        result.setText(String.valueOf(output));
        operator = "";
        start = true;
    }

    public void logs(String toLog) {

        try {
            FileWriter writer = new FileWriter(path, true);
            Date date = new Date();
            writer.write("\n\n[" + formatter.format(date) + "] " + toLog);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}