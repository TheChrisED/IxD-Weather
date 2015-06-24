import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Chris on 23.06.15.
 */
public class WeatherView implements Observer {

    private WeatherModel model;

    private JFrame weatherFrame;

    private JTextField celsiusField;
    private JTextField fahrenheitField;
    private JSlider celsiusSlider;
    private JSlider fahrenheitSlider;

    private JButton convert2CelsiusButton;
    private JButton convert2FahrenheitButton;

    public WeatherView(WeatherModel model) {
        this.model = model;
        model.addObserver(this);
        setupUIComponents();
        updateUI();
        weatherFrame.setVisible(true);
    }

    private void setupUIComponents() {
        weatherFrame = new JFrame("Temperatur Konverter");

        // ------------ FAHRENHEIT -------------
        JPanel fahrenheitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel fahrenheitLabel = new JLabel("Fahrenheit");
        NumberFormat fahrenheitFormat = NumberFormat.getNumberInstance();
        fahrenheitField = new JTextField();
        fahrenheitField.setColumns(10);
        convert2CelsiusButton = new JButton("Konvertiere nach Celsius");

        fahrenheitPanel.add(fahrenheitLabel);
        fahrenheitPanel.add(fahrenheitField);
        fahrenheitPanel.add(convert2CelsiusButton);

        // ------------ CELSIUS -------------
        JPanel celsiusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel celsiusLabel = new JLabel("Celsius");
        celsiusField = new JTextField();
        celsiusField.setColumns(10);
        convert2FahrenheitButton = new JButton("Konvertiere nach Fahrenheit");

        celsiusPanel.add(celsiusLabel);
        celsiusPanel.add(celsiusField);
        celsiusPanel.add(convert2FahrenheitButton);


        weatherFrame.add(celsiusPanel, BorderLayout.NORTH);
        weatherFrame.add(fahrenheitPanel, BorderLayout.SOUTH);
        weatherFrame.pack();

    }

    public JButton getConvert2CelsiusButton() {
        return convert2CelsiusButton;
    }

    public JButton getConvert2FahrenheitButton() {
        return convert2FahrenheitButton;
    }

    public JTextField getCelsiusField() {
        return celsiusField;
    }

    public JTextField getFahrenheitField() {
        return fahrenheitField;
    }

    public JSlider getCelsiusSlider() {
        return celsiusSlider;
    }

    public JSlider getFahrenheitSlider() {
        return fahrenheitSlider;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            updateUI();
        } else {
            throw new AssertionError("Unbekanntes Objekt ruft update auf");
        }
    }


    private String formatTempForDisplay(double temp) {
        int decimalPlaces = 2;
        BigDecimal bd = new BigDecimal(temp);

        bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        temp = bd.doubleValue();
        String tempText = String.valueOf(temp);
        if (temp % 1 == 0) {
            tempText = tempText.substring(0, tempText.lastIndexOf('.'));
        }
        return tempText;
    }

    private void updateUI() {

        double celsius = model.getDegCelsius();
        updateCelsius(formatTempForDisplay(celsius));
        updateFahrenheit(formatTempForDisplay(model.getDegFahrenheit()));
    }

    private void updateCelsius(String celsius) {
        celsiusField.setText(celsius);
    }

    private void updateFahrenheit(String fahrenheit) {
        fahrenheitField.setText(fahrenheit);
    }
}
