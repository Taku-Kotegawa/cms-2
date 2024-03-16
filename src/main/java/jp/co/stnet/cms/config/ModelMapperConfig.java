package jp.co.stnet.cms.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.modelmapper.module.jsr310.Jsr310ModuleConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Configuration
public class ModelMapperConfig {

    private static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy/MM/dd";

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Jsr310ModuleConfig config = Jsr310ModuleConfig.builder()
                .dateTimePattern(DATE_TIME_FORMAT) // default is yyyy-MM-dd HH:mm:ss
                .datePattern(DATE_FORMAT) // default is yyyy-MM-dd
                // .zoneId(ZoneOffset.) // default is ZoneId.systemDefault()
                .build();

        // 厳密なマッチングに設定 https://tech.excite.co.jp/entry/2021/06/12/120000, http://modelmapper.org/user-manual/configuration/
        modelMapper
                .registerModule(new Jsr310Module(config))
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFullTypeMatchingRequired(true)
                .setCollectionsMergeEnabled(false)
                ;
//                .setSkipNullEnabled(true);

        // カスタムコンバーターの追加
        modelMapper.addConverter(setToString);
        modelMapper.addConverter(stringToSet);
        modelMapper.addConverter(listToString);
        modelMapper.addConverter(stringToList);

        return modelMapper;
    }


    /**
     * Set<String> -> String Set -> カンマ区切り
     */
    Converter<Set<String>, String> setToString = new AbstractConverter<>() {
        @Override
        protected String convert(Set<String> source) {
            return String.join(",", source);
        }
    };

    /**
     * String -> Set<String> カンマ区切り -> Set
     */
    Converter<String, Set<String>> stringToSet = new AbstractConverter<>() {
        @Override
        protected Set<String> convert(String source) {
            return Set.copyOf(Arrays.asList(source.split(",", 0)));
        }
    };

    /**
     * List<String> -> String リスト -> カンマ区切り
     */
    Converter<List<String>, String> listToString = new AbstractConverter<>() {
        @Override
        protected String convert(List<String> source) {
            return String.join(",", source);
        }
    };

    /**
     * String -> List<String> カンマ区切り -> リスト
     */
    Converter<String, List<String>> stringToList = new AbstractConverter<>() {
        @Override
        protected List<String> convert(String source) {
            return Arrays.asList(source.split(",", 0));
        }
    };

    /**
     * 大文字に変換(String -> String)
     */
    Converter<String, String> toUpperCase = new AbstractConverter<String, String>() {
        protected String convert(String source) {
            return source == null ? null : source.toUpperCase();
        }
    };


}
