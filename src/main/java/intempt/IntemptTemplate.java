package intempt;

import com.fasterxml.jackson.databind.ObjectMapper;
import liqp.RenderSettings;
import liqp.Template;

import java.util.HashMap;
import java.util.Map;

public class IntemptTemplate {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String parseLiquid(String liquid, Map<String, Object> unTransformed) {
        HashMap<String, Object> transformed = new HashMap<>();

        return Template.parse(transformString(liquid, unTransformed, transformed))
                .withRenderSettings(withCustomSettings())
                .render(transformed);

    }

    /**
     * Can  Throw Exception when attribute is not found
     * Exception : VariableNotExistException.class
     * method :  withStrictVariables(true)
     */

    private static RenderSettings withCustomSettings() {
        return new RenderSettings.Builder()
                .withEvaluateMode(RenderSettings.EvaluateMode.EAGER)
                .withStrictVariables(true)
                .build();
    }


    private static String transformString(String text, Map<String, Object> map,
                                          Map<String, Object> resultMap) {

        for (String s : map.keySet()) {
            Object value = map.get(s);
            String newName = s.replaceAll("[.]+", "_");
            resultMap.put(newName, value);
            text = text.replace(s, newName);
        }

        return text;
    }

}
