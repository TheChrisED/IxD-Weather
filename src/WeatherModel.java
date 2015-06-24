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

    /**
     * This Method should be called after changes were made to the model and its observers should be notified
     */
    public void setUpdated() {
        setChanged();
        notifyObservers();
    }
}
