package julia.web.convert;

import org.springframework.core.convert.converter.Converter;

public class Convert implements Converter<String, String> {

    @Override
    public String convert(String source) {
        if (source.trim().isEmpty()) {
            return null;
        }
        return source;
    }

}
