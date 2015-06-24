import java.util.Observable;
import java.util.Observer;

/**
 * Created by Chris on 23.06.15.
 */
public class WeatherView implements Observer{

    private WeatherModel model;

    public WeatherView(WeatherModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {

        } else {
            throw new AssertionError("Unbekanntes Objekt ruft update auf");
        }
    }
}
