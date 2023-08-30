package jp.co.stnet.cms.config;

import jp.co.stnet.cms.base.domain.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.terasoluna.gfw.common.codelist.EnumCodeList;
import org.terasoluna.gfw.common.codelist.JdbcCodeList;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class CodeListConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean("EMPTY_MAP")
    public Map<String, String> emptyMap() {
        return new LinkedHashMap<>();
    }

    @Bean("CL_STATUS")
    public EnumCodeList status() {
        return new EnumCodeList(Status.class);
    }

    @Bean("CL_YESNO")
    public EnumCodeList yesNo() {
        return new EnumCodeList(YesNo.class);
    }

    @Bean("CL_FILESTATUS")
    public EnumCodeList fileStatus() {
        return new EnumCodeList(FileStatus.class);
    }

    @Bean("CL_VARIABLETYPE")
    public EnumCodeList variableType() {
        return new EnumCodeList(VariableType.class);
    }

    @Bean("CL_ROLE")
    public EnumCodeList role() {
        return new EnumCodeList(Role.class);
    }

    @Bean("CL_PERMISSION")
    public EnumCodeList permission() {
        return new EnumCodeList(Permission.class);
    }

    @Bean("CL_FILETYPE")
    public EnumCodeList fileType() {
        return new EnumCodeList(FileType.class);
    }

    private JdbcCodeList getJdbcCodeListBase() {
        JdbcCodeList jdbcCodeList = new JdbcCodeList();
        jdbcCodeList.setDataSource(dataSource);
        jdbcCodeList.setJdbcTemplate(jdbcTemplate);
        jdbcCodeList.setLazyInit(true);
        return jdbcCodeList;
    }

    @Bean("CL_SAMPLE")
    public JdbcCodeList sample() {
        JdbcCodeList jdbcCodeList = getJdbcCodeListBase();
        jdbcCodeList.setQuerySql("SELECT CODE, CONCAT(VALUE1, case when STATUS = '2' then '(無効)' else '' end) as VALUE1 FROM T_VARIABLE WHERE TYPE = 'SAMPLE_CODELIST' ORDER BY STATUS, CODE");
        jdbcCodeList.setValueColumn("CODE");
        jdbcCodeList.setLabelColumn("VALUE1");
        return jdbcCodeList;
    }

    @Bean("CL_EMPLOYEE")
    public JdbcCodeList docEmployee() {
        JdbcCodeList jdbcCodeList = getJdbcCodeListBase();
        jdbcCodeList.setQuerySql("SELECT CODE, CONCAT(VALUE1, case when STATUS = '2' then '(退職)' else '' end) as VALUE1 FROM T_VARIABLE WHERE TYPE = 'EMPLOYEE' ORDER BY STATUS, VALINT1, CODE");
        jdbcCodeList.setValueColumn("CODE");
        jdbcCodeList.setLabelColumn("VALUE1");
        return jdbcCodeList;
    }

    @Bean("CL_DEPARTMENT")
    public JdbcCodeList docDepartmetn() {
        JdbcCodeList jdbcCodeList = getJdbcCodeListBase();
        jdbcCodeList.setQuerySql("SELECT CODE, CONCAT(VALUE1, case when STATUS = '2' then '(無効)' else '' end) as VALUE1 FROM T_VARIABLE WHERE TYPE = 'DEPARTMENT' ORDER BY STATUS, VALINT1, CODE");
        jdbcCodeList.setValueColumn("CODE");
        jdbcCodeList.setLabelColumn("VALUE1");
        return jdbcCodeList;
    }

    @Bean("CL_ACCOUNT_FULLNAME")
    public JdbcCodeList accountFullName() {
        JdbcCodeList jdbcCodeList = getJdbcCodeListBase();
        jdbcCodeList.setQuerySql("SELECT USERNAME, TRIM(CONCAT(LAST_NAME, ' ', FIRST_NAME)) AS FULL_NAME FROM T_ACCOUNT ORDER BY USERNAME");
        jdbcCodeList.setValueColumn("USERNAME");
        jdbcCodeList.setLabelColumn("FULL_NAME");
        return jdbcCodeList;
    }

    @Bean("CL_EMPLOYEE")
    public JdbcCodeList employee() {
        JdbcCodeList jdbcCodeList = getJdbcCodeListBase();
        jdbcCodeList.setQuerySql("SELECT EMP_ID, EMP_NAME FROM EMPLOYEE ORDER BY EMP_ID");
        jdbcCodeList.setValueColumn("EMP_ID");
        jdbcCodeList.setLabelColumn("EMP_NAME");
        return jdbcCodeList;
    }

    @Bean("CL_ORGANIZATION")
    public JdbcCodeList organization() {
        JdbcCodeList jdbcCodeList = getJdbcCodeListBase();
        jdbcCodeList.setQuerySql("SELECT ORGANIZATION_ID, GROUP_NAME FROM ORGANIZATION ORDER BY ORGANIZATION_ID");
        jdbcCodeList.setValueColumn("ORGANIZATION_ID");
        jdbcCodeList.setLabelColumn("GROUP_NAME");
        return jdbcCodeList;
    }

    @Bean("CL_POSITION")
    public JdbcCodeList position() {
        JdbcCodeList jdbcCodeList = getJdbcCodeListBase();
        jdbcCodeList.setQuerySql("SELECT POSITION_ID, POSITION_NAME FROM POSITION ORDER BY POSITION_ID");
        jdbcCodeList.setValueColumn("POSITION_ID");
        jdbcCodeList.setLabelColumn("POSITION_NAME");
        return jdbcCodeList;
    }
}
