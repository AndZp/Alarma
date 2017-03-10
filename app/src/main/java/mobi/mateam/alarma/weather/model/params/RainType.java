package mobi.mateam.alarma.weather.model.params;

/**
 * Created by Des63rus on 3/10/2017.
 *
 * Rain types enum
 */

public enum RainType{
    NO_RAIN("No rain", 0), LIGHT_RAIN("Light Rain", 1), MODERATE_RAIN("Moderate Rain", 2), HEAVY_RAIN("Heavy Rain", 3), VIOLENT_RAIN("Violent Rain", 4);

    private String text;
    /**
     * rank for comparison mechanizm to work
     */
    private int rank;

    RainType(String text, int rank){
        this.text = text;
    }

    public int getRank() {
        return rank;
    }

    public static RainType getRainTypeByMM(Integer rainMM){
        if (rainMM == null || rainMM == 0){
            return NO_RAIN;
        } else if(rainMM < 7){
            return LIGHT_RAIN;
        } else if (rainMM < 30){
            return MODERATE_RAIN;
        } else if (rainMM < 150){
            return HEAVY_RAIN;
        } else {
            return VIOLENT_RAIN;
        }
    }

}
