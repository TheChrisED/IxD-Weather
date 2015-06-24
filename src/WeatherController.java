import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import java.text.ParseException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Chris on 23.06.15.
 */
public class WeatherController {

    private static final int FAHRENHEIT_SLIDER_MIN = -40;
    private static final int FAHRENHEIT_SLIDER_MAX = 200;

    private WeatherView view;
    private WeatherModel model;

    public static final int INITIAL_DEG_CELSIUS = -40;

    public WeatherController() {
        int initialDegFahrenheit = convert2Fahrenheit(INITIAL_DEG_CELSIUS);
        model = new WeatherModel(INITIAL_DEG_CELSIUS, initialDegFahrenheit);
        view = new WeatherView(model, this, FAHRENHEIT_SLIDER_MAX, FAHRENHEIT_SLIDER_MIN,
                (int) convert2Celsius(FAHRENHEIT_SLIDER_MAX), (int) convert2Celsius(FAHRENHEIT_SLIDER_MIN));

        setupListeners();
    }

    private void setupListeners() {
        ActionListener convert2CelsiusListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int fahrenheit = Integer.parseInt(view.getFahrenheitField().getText());
                    updateFahrenheit(fahrenheit);
                } catch (NumberFormatException exc) {
                    showNumberErrorDialog();
                }
            }
        };
        ActionListener convert2FahrenheitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int celsius = Integer.parseInt(view.getCelsiusField().getText());
                    updateCelsius(celsius);
                } catch (NumberFormatException exc) {
                    showNumberErrorDialog();
                }
            }
        };


        // ------------ BUTTONS -------------
        JButton convert2Celsius = view.getConvert2CelsiusButton();
        convert2Celsius.addActionListener(convert2CelsiusListener);

        JButton convert2Fahrenheit = view.getConvert2FahrenheitButton();
        convert2Fahrenheit.addActionListener(convert2FahrenheitListener);
        
        // ------------ SLIDERS -------------
        JSlider fahrenheitSlider = view.getFahrenheitSlider();
        fahrenheitSlider.addChangeListener((ChangeEvent e)->{
        	int fahrenheit = fahrenheitSlider.getValue();
        	updateFahrenheit(fahrenheit);
        });
        
        JSlider celsiusSlider = view.getCelsiusSlider();
        celsiusSlider.addChangeListener((ChangeEvent e) ->{
        	int celsius = celsiusSlider.getValue();
        	updateCelsius(celsius);
        });

        // ------------ TEXT FIELDS -------------

        JTextField celsiusField = view.getCelsiusField();
        celsiusField.addActionListener(convert2FahrenheitListener);
        
        KeyAdapter correctCharAdapter = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                correctCharInField(e);
            }
            @Override
            public void keyPressed (KeyEvent e) {
            	correctCharInField(e);
            }
        };
        celsiusField.addKeyListener(correctCharAdapter);

        JTextField fahrenheitField = view.getFahrenheitField();
        fahrenheitField.addActionListener(convert2CelsiusListener);
        fahrenheitField.addKeyListener(correctCharAdapter);
    }
    //TODO: Textfield mit Tastendrucküberprüfung durch bessere Variante ersetzen
    //(z.B. http://stackoverflow.com/questions/1313390/is-there-any-way-to-accept-only-numeric-values-in-a-jtextfield)
    /**
     * This method takes care of making sure a specified KeyEvent is a number or can be part of a number (. -)
     * If the KeyEvent does not match the criteria, it gets consumed, meaning it won't reach other objects like JTextFields
     * @param key the KeyEvent to check
     */
    private void correctCharInField(KeyEvent key) {
        char keyChar = key.getKeyChar();
        if (!       (Character.isDigit(keyChar) || keyChar == '-'
                ||  (keyChar == KeyEvent.VK_BACK_SPACE) || (keyChar == KeyEvent.VK_DELETE)
                ||  (keyChar == KeyEvent.VK_ENTER)      || (keyChar == KeyEvent.VK_TAB))
                || 	(keyChar == KeyEvent.VK_PASTE)) {
            key.consume();
        }
    }

    private void showNumberErrorDialog() {
        JOptionPane.showMessageDialog(view.getWeatherFrame(), "Bitte geben Sie eine korrekte Zahl ein!");
    }

    /**
     * Checks whether textField's text is a valid number and if not, deletes the last character
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

    private int convert2Fahrenheit(int celsius) {
        return (int) Math.round(celsius * 1.8 + 32);
    }

    private int convert2Celsius(int fahrenheit) {
        return (int) Math.round((fahrenheit - 32) * 5.0 / 9);
    }

    private void updateCelsius(int celsius) {
        updateModel(celsius, convert2Fahrenheit(celsius));
    }

    private void updateFahrenheit(int fahrenheit) {
        updateModel(convert2Celsius(fahrenheit), fahrenheit);
    }

    private void updateModel(int celsius, int fahrenheit) {
        model.setDegCelsius(celsius);
        model.setDegFahrenheit(fahrenheit);
    }
}
