import java.util.Observable;

/**
 * Created by Chris on 23.06.15.
 */
public class WeatherModel extends Observable {

	private int degCelsius;
	private int degFahrenheit;

	public WeatherModel(int degCelsius, int degFahrenheit) {
		this.degCelsius = degCelsius;
		this.degFahrenheit = degFahrenheit;
	}

	public int getDegFahrenheit() {
		return degFahrenheit;
	}

	public void setDegFahrenheit(int degFahrenheit) {
		if (this.degFahrenheit != degFahrenheit) {
			this.degFahrenheit = degFahrenheit;
			setUpdated();
		}
	}

	public int getDegCelsius() {
		return degCelsius;
	}

	public void setDegCelsius(int degCelsius) {
		if (this.degCelsius != degCelsius) {
			this.degCelsius = degCelsius;
			setUpdated();
		}
	}

	/**
	 * This Method should be called after changes were made to the model and its
	 * observers should be notified
	 */
	private void setUpdated() {
		setChanged();
		notifyObservers();
	}
}
