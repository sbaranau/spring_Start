package julia.service.settings;

import julia.dto.settings.CommonSettingsDTO;
import julia.enums.SettingsEnum;

import java.util.List;
import java.util.Map;

/**
 * Класс для использования Spring транзакций
 * @author Aleksandr Golovnyov
 */
public interface SystemSettingsService {

    /**
     * Функция для чтения всех значений из одной транзакции
     * @return карта полученных значений
     */
    Map<String, String> getValues();

    /**
     * Функция для чтения из одной транзакции
     * @param settingsKeys список ключей настроек
     * @return карта полученных значений
     */
    Map<String, String> getValues(List<SettingsEnum> settingsKeys);

    /**
     * Функция для обновления в одной транзакции
     * @param settings карта обновляемых значений
     */
    void updateValues(Map<String, String> settings);

    /**
     * Обновляет параметры настроек
     * @param settingList список настроек
     */
    void updateValues(final List<CommonSettingsDTO> settingList);
}
