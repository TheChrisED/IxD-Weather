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
