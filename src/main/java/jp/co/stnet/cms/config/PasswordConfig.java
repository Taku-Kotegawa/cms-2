package jp.co.stnet.cms.config;

import jp.co.stnet.cms.common.validation.rule.EncodedPasswordHistoryRule;
import org.passay.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static org.passay.EnglishCharacterData.*;

@Configuration
public class PasswordConfig {

    private static final int UPPERCASE_MIN_LENGTH = 1;
    private static final int LOWERCASE_MIN_LENGTH = 1;
    private static final int DIGIT_MIN_LENGTH = 1;
    private static final int SPECIAL_MIN_LENGTH = 1;

    @Value("${security.passwordMinimumLength}")
    private Integer minimumLength;

    /**
     * パスワードのハッシュアルゴリズム設定
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DelegatingPasswordEncoder(
                "pbkdf2",
                new HashMap<String, PasswordEncoder>() {{
                    put("pbkdf2", new Pbkdf2PasswordEncoder("", 16, 310000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));
                }}
        );
    }

    // --- Passy Settings ---

    @Bean
    public LengthRule lengthRule() {
        LengthRule rule = new LengthRule();
        rule.setMinimumLength(minimumLength);
        return rule;
    }

    @Bean
    public List<CharacterRule> passwordGenerationRules() {
        List<CharacterRule> list = new ArrayList<>();
        list.add(new CharacterRule(UpperCase, UPPERCASE_MIN_LENGTH));
        list.add(new CharacterRule(LowerCase, LOWERCASE_MIN_LENGTH));
        list.add(new CharacterRule(Digit, DIGIT_MIN_LENGTH));
        return list;
    }

    @Bean
    public CharacterCharacteristicsRule characterCharacteristicsRule() {
        CharacterCharacteristicsRule rule = new CharacterCharacteristicsRule();
        rule.setRules(
                new CharacterRule(UpperCase, UPPERCASE_MIN_LENGTH),
                new CharacterRule(LowerCase, LOWERCASE_MIN_LENGTH),
                new CharacterRule(Digit, DIGIT_MIN_LENGTH),
                new CharacterRule(Special, SPECIAL_MIN_LENGTH)
        );
        rule.setNumberOfCharacteristics(3);
        return rule;
    }

    @Bean
    public UsernameRule usernameRule() {
        return new UsernameRule();
    }

    @Bean
    public EncodedPasswordHistoryRule encodedPasswordHistoryRule() {
        return new EncodedPasswordHistoryRule(passwordEncoder());
    }

    @Bean
    public PasswordValidator characteristicPasswordValidator() throws IOException {

        URL resource = this.getClass().getClassLoader().getResource("messages.properties");
        Properties props = new Properties();
        props.load(new InputStreamReader(new FileInputStream(resource.getPath()), StandardCharsets.UTF_8));

        MessageResolver resolver = new PropertiesMessageResolver(props);

        return new PasswordValidator(
                resolver,
                lengthRule(),
                characterCharacteristicsRule(),
                usernameRule()
        );
    }

    @Bean
    public PasswordValidator encodedPasswordHistoryValidator() {
        return new PasswordValidator(
                encodedPasswordHistoryRule()
        );
    }

    @Bean
    public PasswordGenerator passwordGenerator() {
        return new PasswordGenerator();
    }

}
