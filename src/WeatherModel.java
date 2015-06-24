/**
 * Created by Chris on 23.06.15.
 */
public class WeatherModel {

    private double degCelsius;
    private double degFahrenheit;


    public WeatherModel(double degCelsius, double degFahrenheit) {
        this.degCelsius = degCelsius;
        this.degFahrenheit = degFahrenheit;
    }

    public int getDegFahrenheit() {
        return degFahrenheit;
    }

    public void setDegFahrenheit(int degFahrenheit) {
        this.degFahrenheit = degFahrenheit;
    }

    public int getDegCelsius() {
        return degCelsius;
    }

    public void setDegCelsius(int degCelsius) {
        this.degCelsius = degCelsius;
    }
}
