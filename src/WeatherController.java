import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Chris on 23.06.15.
 */
public class WeatherController {

    private WeatherView view;
    private WeatherModel model;

    public static final double INITIAL_DEG_CELSIUS = 0;

    public WeatherController() {
        double initialDegFahrenheit = convert2Fahrenheit(INITIAL_DEG_CELSIUS);
        model = new WeatherModel(INITIAL_DEG_CELSIUS, initialDegFahrenheit);
        view = new WeatherView(model);

        setupListeners();
    }

    private void setupListeners() {
        JButton convert2Celsius = view.getConvert2CelsiusButton();
        convert2Celsius.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double fahrenheit = Double.parseDouble(view.getFahrenheitField().getText());
            }
        });

        JButton convert2Fahrenheit = view.getConvert2FahrenheitButton();
        convert2Fahrenheit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    /**
     * Checks wether textField's text is a valid number and if not, deletes the last character
     * @param textField the JTextField to be corrected
     */
    private void correctNonNumbers(JTextField textField) {
        String text = textField.getText();
        try {
            double d = Double.parseDouble(text);
        }
        catch(NumberFormatException nfe) {
            String correctedText = text.substring(0, text.length() - 2);
            textField.setText(correctedText);
        }
    }

    private double convert2Fahrenheit(double celsius) {
        return celsius * 1.8 + 32;
    }

    private double convert2Celsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    /**
     * This method should be called after changes were made to the model
     */
    private void update() {
        model.setUpdated();
    }
}
