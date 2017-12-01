package julia.service.settings;

import julia.dto.settings.CommonSettingsDTO;
import julia.enums.SettingsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;

/**
 * Класс для использования Spring транзакций
 * 
 * @author Aleksandr Golovnyov
 */
@Service
public class SystemSettingsServiceImpl implements SystemSettingsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemSettingsServiceImpl.class);

    @Resource
    @Qualifier("settings")
    private Map<String, String> settings;

    @Override
    public Map<String, String> getValues() {

        Map<String, String> map = new LinkedHashMap<>();

        Set<Entry<String, String>> entrySet = settings.entrySet();
        for (Entry<String, String> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }

    @Override
    public Map<String, String> getValues(List<SettingsEnum> settingsKeys) {

        Map<String, String> map = new LinkedHashMap<>();

        for (SettingsEnum settingKey : settingsKeys) {
            map.put(settingKey.getKey(), settings.get(settingKey));
        }

        return map;
    }

    @Override
    public void updateValues(Map<String, String> values) {
        Set<Entry<String, String>> entrySet = values.entrySet();

        for (Entry<String, String> entry : entrySet) {

            if (entry.getValue() != null && !entry.getValue().trim().isEmpty()) {

                SettingsEnum enumValue = SettingsEnum.valueOfKey(entry.getKey());

                if (enumValue != null) {
                    String pattern = enumValue.getPattern();

                    if (pattern == null) {

                        settings.put(entry.getKey(), entry.getValue());
                    }

                    if (pattern != null && entry.getValue().matches(pattern)) {

                        settings.put(entry.getKey(), entry.getValue());
                    }
                } else {
                    LOGGER.error("Parameter '" + entry.getKey() + "' don't exist in enumeration");
                }
            }
        }
    }

    @Override
    public void updateValues(final List<CommonSettingsDTO> settingList) {
        final Map<String, String> values = new HashMap<>(settingList.size());
        for (final CommonSettingsDTO setting : settingList) {
            values.put(setting.getKey(), setting.getValue());
        }
        updateValues(values);
    }
}
