package mobi.mateam.alarma.weather.model.params;

/**
 * Created by Des63rus on 3/10/2017.
 *
 * Rain types enum
 */

public enum RainType{
    NO_RAIN("No rain"), LIGHT_RAIN("Light Rain"), MODERATE_RAIN("Moderate Rain"), HEAVY_RAIN("Heavy Rain"), VIOLENT_RAIN("Violent Rain");

    private String text;

    RainType(String text){
        this.text = text;
    }

    public RainType getRainTypeByMM(Integer rainMM){
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
