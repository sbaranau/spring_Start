package julia.dto.settings;

import julia.enums.SettingsEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DTO общие настройки
 *
 * @author Pudul Yuriy
 */
public class CommonSettingsDTO {

    /**Ключ параметра настройки*/
    private String key;
    /**Наименование параметра настройки*/
    private String name;
    /**Значение параметра настройки*/
    private String value;


    /**
     * Получить объект ответа по списку настроек
     * @param settingsValues мапа настроек
     * @return список настроек с именами
     */
    public static List<CommonSettingsDTO> getCommonSettingsDTO(final Map<String, String> settingsValues) {
        final List<CommonSettingsDTO> result = new ArrayList<>();

        for (Map.Entry<String, String> setting : settingsValues.entrySet()) {
            final CommonSettingsDTO response = new CommonSettingsDTO();
            response.setKey(setting.getKey());
            response.setName(SettingsEnum.valueOfKey(setting.getKey()).getName());
            response.setValue(setting.getValue());
            result.add(response);
        }

        return result;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
