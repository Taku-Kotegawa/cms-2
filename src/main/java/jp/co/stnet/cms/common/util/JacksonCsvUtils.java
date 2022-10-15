package jp.co.stnet.cms.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class JacksonCsvUtils {

    /**
     * BOMコード
     */
    public static final String BOM_CHAR = "\uFEFF";

    /**
     * CsvMapperの取得(デフォルト設定)
     *
     * @return デフォルト設定されたCsvMapper
     */
    public static CsvMapper getDefaultMapper() {

        var mapper = CsvMapper.builder().build();
        mapper.findAndRegisterModules();

        return mapper;
    }

    /**
     * CsvSchemaの取得(デフォルト設定)
     *
     * @return デフォルト設定されたCsvSchema
     */
    public static CsvSchema getDefaultCsvSchema(Class clazz) {

        var schema = getDefaultMapper()
                .schemaFor(clazz)
                .withQuoteChar('"')
                .withEscapeChar('\\')
                .withArrayElementSeparator("|")
                .withHeader();

        return schema;
    }

    public static String withBom(String source) {
        return BOM_CHAR + source;
    }


    /**
     * 指定したオブジェクト(DTO)の形式で、CSV文字列を作成する
     *
     * @param clazz
     * @param value
     * @return
     * @throws JsonProcessingException
     */
    public static String write(Class clazz, Object value) throws JsonProcessingException {
        return getDefaultMapper()
                .writer(getDefaultCsvSchema(clazz))
                .writeValueAsString(value);
    }

    /**
     * BOMなしCSVのInputStreamを取得する
     *
     * @param clazz
     * @param value
     * @return
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public static InputStream writeStream(Class clazz, Object value) throws JsonProcessingException, UnsupportedEncodingException {
        return new ByteArrayInputStream(write(clazz, value).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * BOM付きCSVのInputStreamを取得する
     *
     * @param clazz
     * @param value
     * @return
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public static InputStream writeBomStream(Class clazz, Object value) throws JsonProcessingException, UnsupportedEncodingException {
        return new ByteArrayInputStream(withBom(write(clazz, value)).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * TSV(文字列)を取得する
     *
     * @param clazz
     * @param value
     * @return
     * @throws JsonProcessingException
     */
    public static String writeTsv(Class clazz, Object value) throws JsonProcessingException {
        return getDefaultMapper()
                .writer(getDefaultCsvSchema(clazz)
                        .withoutQuoteChar()
                        .withColumnSeparator('\t'))
                .writeValueAsString(value);
    }

    /**
     * BOMなしTSVのInputStreamを取得する
     *
     * @param clazz
     * @param value
     * @return
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public static InputStream writeTsvStream(Class clazz, Object value) throws JsonProcessingException, UnsupportedEncodingException {
        return new ByteArrayInputStream(writeTsv(clazz, value).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * BOM付きTSVのInputStreamを取得する
     *
     * @param clazz
     * @param value
     * @return
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public static InputStream writeBomTsvStream(Class clazz, Object value) throws JsonProcessingException, UnsupportedEncodingException {
        return new ByteArrayInputStream(withBom(writeTsv(clazz, value)).getBytes(StandardCharsets.UTF_8));
    }


}
