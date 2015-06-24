import javax.swing.*;
import java.awt.event.*;
import java.text.ParseException;

/**
 * Created by Chris on 23.06.15.
 */
public class WeatherController {

    public static final int FAHRENHEIT_SLIDER_MIN = 0;
    public static final int FAHRENHEIT_SLIDER_MAX = 120;

    private WeatherView view;
    private WeatherModel model;

    public static final double INITIAL_DEG_CELSIUS = 0;

    public WeatherController() {
        double initialDegFahrenheit = convert2Fahrenheit(INITIAL_DEG_CELSIUS);
        model = new WeatherModel(INITIAL_DEG_CELSIUS, initialDegFahrenheit);
        view = new WeatherView(model, FAHRENHEIT_SLIDER_MAX, FAHRENHEIT_SLIDER_MIN,
                (int) convert2Celsius(FAHRENHEIT_SLIDER_MAX), (int) convert2Celsius(FAHRENHEIT_SLIDER_MIN));

        setupListeners();
    }

    private void setupListeners() {
        ActionListener convert2CelsiusListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double fahrenheit = Double.parseDouble(view.getFahrenheitField().getText());
                updateFahrenheit(fahrenheit);
            }
        };
        ActionListener convert2FahrenheitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double celsius = Double.parseDouble(view.getCelsiusField().getText());
                updateCelsius(celsius);
            }
        };


        // ------------ BUTTONS -------------
        JButton convert2Celsius = view.getConvert2CelsiusButton();
        convert2Celsius.addActionListener(convert2CelsiusListener);

        JButton convert2Fahrenheit = view.getConvert2FahrenheitButton();
        convert2Fahrenheit.addActionListener(convert2FahrenheitListener);

        // ------------ TEXT FIELDS -------------

        JTextField celsiusField = view.getCelsiusField();
        celsiusField.addActionListener(convert2FahrenheitListener);
        celsiusField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                correctCharInField(e);
            }
        });

        JTextField fahrenheitField = view.getFahrenheitField();
        fahrenheitField.addActionListener(convert2CelsiusListener);
        fahrenheitField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                correctCharInField(e);
            }
        });
    }

    /**
     * This method takes care of making sure a specified KeyEvent is a number or can be part of a number (. -)
     * If the KeyEvent does not match the criteria, it gets consumed, meaning it won't reach other objects like JTextFields
     * @param key the KeyEvent to check
     */
    private void correctCharInField(KeyEvent key) {
        char keyChar = key.getKeyChar();
        if (!       (Character.isDigit(keyChar) || keyChar == '.' || keyChar == '-'
                ||  (keyChar == KeyEvent.VK_BACK_SPACE) || (keyChar == KeyEvent.VK_DELETE)
                ||  (keyChar == KeyEvent.VK_ENTER)      || (keyChar == KeyEvent.VK_TAB))) {
            key.consume();
        }
    }

    /**
     * Checks wether textField's text is a valid number and if not, deletes the last character
     * @param textField the JTextField to be corrected
     * @return Returns the parsed number if the contained text is a number. Otherwise returns null
     */
    private Double correctNonNumber(JTextField textField) {
        String text = textField.getText();
        try {
            double d = Double.parseDouble(text);
            return d;
        }
        catch(NumberFormatException nfe) {
            String correctedText = text.substring(0, text.length() - 2);
            if (correctedText.length() == 0) {
                correctedText = "0";
            }
            textField.setText(correctedText);
            return null;
        }
    }

    private double convert2Fahrenheit(double celsius) {
        return celsius * 1.8 + 32;
    }

    private double convert2Celsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    private void updateCelsius(double celsius) {
        updateModel(celsius, convert2Fahrenheit(celsius));
    }

    private void updateFahrenheit(double fahrenheit) {
        updateModel(convert2Celsius(fahrenheit), fahrenheit);
    }

    private void updateModel(double celsius, double fahrenheit) {
        model.setDegCelsius(celsius);
        model.setDegFahrenheit(fahrenheit);
        model.setUpdated();
    }
}
