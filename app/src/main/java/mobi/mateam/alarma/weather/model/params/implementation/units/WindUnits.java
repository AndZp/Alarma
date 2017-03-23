package mobi.mateam.alarma.weather.model.params.implementation.units;

/**
 * Created by andreik on 19/03/2017.
 *
 * Enum for wind speed units
 */

public enum WindUnits {
    METERSEC,
    MILESHOUR;

    public static Double convertToDefault(WindUnits units, Double value) {
        if(WindUnits.MILESHOUR.equals(units)){
            return value * 0.447;
        }
        return value;
    }
}
