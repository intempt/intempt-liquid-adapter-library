package liqp.filters.intempt;

import liqp.TemplateContext;
import liqp.filters.Filter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64_Decode extends Filter {

    @Override
    public Object apply(Object value, TemplateContext context, Object... params) {
        byte[] decoded = Base64.getDecoder().decode(super.asString(value, context));

        String decodedStr = new String(decoded, StandardCharsets.UTF_8);

        return decodedStr;
    }

}
