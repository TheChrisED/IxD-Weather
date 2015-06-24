import java.util.Observable;

/**
 * Created by Chris on 23.06.15.
 */
public class WeatherModel extends Observable {

    private double degCelsius;
    private double degFahrenheit;


    public WeatherModel(double degCelsius, double degFahrenheit) {
        this.degCelsius = degCelsius;
        this.degFahrenheit = degFahrenheit;
    }

    public double getDegFahrenheit() {
        return degFahrenheit;
    }

    public void setDegFahrenheit(double degFahrenheit) {
        this.degFahrenheit = degFahrenheit;
    }

    public double getDegCelsius() {
        return degCelsius;
    }

    public void setDegCelsius(double degCelsius) {
        this.degCelsius = degCelsius;
    }

    /**
     * This Method should be called after changes were made to the model and its observers should be notified
     */
    public void setUpdated() {
        setChanged();
        notifyObservers();
    }
}
