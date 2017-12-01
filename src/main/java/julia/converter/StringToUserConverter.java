package julia.converter;

import julia.entity.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

public class StringToUserConverter implements Converter<String, User> {

    private static final Logger LOG = LoggerFactory.getLogger(StringToUserConverter.class);

    @Override
    public User convert(final String data) {
        try {
            return new ObjectMapper().readValue(data, User.class);
        } catch (final Exception e) {
            LOG.error(e.getMessage());
        }
        return new User();
    }

}
