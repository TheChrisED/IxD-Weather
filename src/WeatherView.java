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

	private int fahrenheitSliderMax;
	private int fahrenheitSliderMin;
	private int celsiusSliderMax;
	private int celsiusSliderMin;

	private WeatherModel model;
	private WeatherController controller;

	private JFrame weatherFrame;

	private JTextField celsiusField;
	private JTextField fahrenheitField;
	private JSlider celsiusSlider;
	private JSlider fahrenheitSlider;

	private JButton convert2CelsiusButton;
	private JButton convert2FahrenheitButton;

	public WeatherView(WeatherModel model, WeatherController controller,
			int fahrenheitSliderMax, int fahrenheitSliderMin,
			int celsiusSliderMax, int celsiusSliderMin) {
		this.fahrenheitSliderMax = fahrenheitSliderMax;
		this.fahrenheitSliderMin = fahrenheitSliderMin;
		this.celsiusSliderMax = celsiusSliderMax;
		this.celsiusSliderMin = celsiusSliderMin;
		this.model = model;
		this.controller = controller; // Laut Aufgabe muss die View eine
										// Referenz auf den Controller
										// enthalten.

		model.addObserver(this);
		setupUIComponents();
		weatherFrame.setVisible(true);
	}

	private void setupUIComponents() {
		weatherFrame = new JFrame("Temperatur Konverter");
		weatherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		weatherFrame.setResizable(false);

		// ------------ FAHRENHEIT -------------
		JPanel fahrenheitPanel = new JPanel();
		fahrenheitPanel.setLayout(new BoxLayout(fahrenheitPanel,
				BoxLayout.Y_AXIS));

		JPanel fahrenheitUpperPanel = new JPanel(new FlowLayout(
				FlowLayout.RIGHT));

		JLabel fahrenheitLabel = new JLabel("Fahrenheit");
		NumberFormat fahrenheitFormat = NumberFormat.getNumberInstance();
		fahrenheitField = new JTextField(Integer.toString(model.getDegFahrenheit()));
		fahrenheitField.setColumns(10);
		convert2CelsiusButton = new JButton("Konvertiere nach Celsius");

		fahrenheitUpperPanel.add(fahrenheitLabel);
		fahrenheitUpperPanel.add(fahrenheitField);
		fahrenheitUpperPanel.add(convert2CelsiusButton);

		fahrenheitSlider = new JSlider(SwingConstants.HORIZONTAL,
				fahrenheitSliderMin, fahrenheitSliderMax, fahrenheitSliderMin);

		fahrenheitPanel.add(fahrenheitUpperPanel);
		fahrenheitPanel.add(fahrenheitSlider);

		// ------------ CELSIUS -------------
		JPanel celsiusPanel = new JPanel();
		celsiusPanel.setLayout(new BoxLayout(celsiusPanel, BoxLayout.Y_AXIS));

		JPanel celsiusUpperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel celsiusLabel = new JLabel("Celsius");
		celsiusField = new JTextField(Integer.toString(model.getDegCelsius()));
		celsiusField.setColumns(10);
		convert2FahrenheitButton = new JButton("Konvertiere nach Fahrenheit");

		celsiusUpperPanel.add(celsiusLabel);
		celsiusUpperPanel.add(celsiusField);
		celsiusUpperPanel.add(convert2FahrenheitButton);

		celsiusSlider = new JSlider(SwingConstants.HORIZONTAL,
				celsiusSliderMin, celsiusSliderMax, celsiusSliderMin);

		celsiusPanel.add(celsiusUpperPanel);
		celsiusPanel.add(celsiusSlider);

		weatherFrame.add(celsiusPanel, BorderLayout.NORTH);
		weatherFrame.add(fahrenheitPanel, BorderLayout.SOUTH);
		weatherFrame.pack();

	}

	public JFrame getWeatherFrame() {
		return weatherFrame;
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
		int fahrenheit = model.getDegFahrenheit();
		int celsius = model.getDegCelsius();
		
		updateFahrenheit(Integer.toString(fahrenheit));
		updateCelsius(Integer.toString(celsius));
	}

	private void updateCelsius(String celsius) {
		celsiusField.setText(celsius);
		celsiusSlider.setValue(Integer.parseInt(celsius));
	}

	private void updateFahrenheit(String fahrenheit) {
		fahrenheitField.setText(fahrenheit);
		fahrenheitSlider.setValue(Integer.parseInt(fahrenheit));
	}
}
