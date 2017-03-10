package mobi.mateam.alarma.weather.model.params;

/**
 * Created by Des63rus on 3/10/2017.
 *
 * Rain types enum
 */

public enum SnowType {
    NO_SNOW("No Snow", 0), LIGHT_SNOW("Light Snow", 1), MODERATE_SNOW("Moderate Snow", 2), HEAVY_SNOW("Heavy Snow", 3), VIOLENT_SNOW("Violent Snow", 4);

    private String text;
    /**
     * rank for comparison mechanizm to work
     */
    private int rank;

    SnowType(String text, int rank){
        this.text = text;
    }

    public int getRank() {
        return rank;
    }

    public static SnowType getSnowTypeByMM(Integer rainMM){
        if (rainMM == null || rainMM == 0){
            return NO_SNOW;
        } else if(rainMM < 7){
            return LIGHT_SNOW;
        } else if (rainMM < 30){
            return MODERATE_SNOW;
        } else if (rainMM < 150){
            return HEAVY_SNOW;
        } else {
            return VIOLENT_SNOW;
        }
    }

}
