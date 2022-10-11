package liqp.filters.intempt;


import liqp.TemplateContext;
import liqp.filters.Filter;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64_Encode extends Filter {

    @Override
    public Object apply(Object value, TemplateContext context, Object... params) {
        try {
            byte[] word = super.asString(value, context).getBytes("UTF-8");
            return Base64.getEncoder().encodeToString(word);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
