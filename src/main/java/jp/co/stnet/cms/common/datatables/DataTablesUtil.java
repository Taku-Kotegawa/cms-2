package jp.co.stnet.cms.common.datatables;

import jp.co.stnet.cms.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataTablesUtil {

    // ---------------------------------------------------------------------------------------------------------

    /**
     * Where句を作成する。
     *
     * @param input DataTablesInput
     * @return Where句文字列
     */
    public static String getWhereClause(DataTablesInput input, Map<String, String> fieldMap) {
        StringBuilder sql = new StringBuilder();
        // Where句
        if (hasFieldFilter(input)) {
            // フィールドフィルタ
            sql.append(" WHERE 1 = 1 ");

            int i = 0;
            for (Column column : input.getColumns()) {
                sql.append(getFieldFilterWhereClause(column, fieldMap, i));
                i++;
            }

        } else {
            // グローバルフィルタ
            if (!StringUtils.isEmpty(input.getSearch().getValue())) {
                sql.append(" WHERE 1 = 2 ");
                for (Column column : input.getColumns()) {
                    sql.append(getGlobalFilterWhereClause(column, fieldMap));
                }
                input.getSearch().setValue("%" + input.getSearch().getValue() + "%");
            }
        }

        return blankToNull(sql.toString());
    }

    private static String blankToNull(String source) {
        if (source.isBlank()) {
            return null;
        }
        return source;
    }

    /**
     * グローバルフィルターのためのフィールド毎の等号文字列を作成する。
     *
     * @param column Column
     * @return 等号文字列
     */
    protected static StringBuilder getGlobalFilterWhereClause(Column column, Map<String, String> fieldMap) {
        StringBuilder sql = new StringBuilder();
        if (column.getSearchable()) {
            sql.append(" OR ");
            String originalColumnName = column.getData();
            String convertedColumnName = StringUtils.toLowerSnakeCase(convertColumnName(originalColumnName));
            if (isLocalDate(originalColumnName, fieldMap)) {
                sql.append("to_char(");
                sql.append(convertedColumnName);
                sql.append(", 'YYYY/MM/DD') ILIKE #{dataTablesInput.search.value}");
            } else if (isLocalDateTime(originalColumnName, fieldMap)) {
                sql.append("to_char(");
                sql.append(convertedColumnName);
                sql.append(", 'YYYY/MM/DD HH24:MI:SS') ILIKE #{dataTablesInput.search.value}");
            } else if (isNumber(originalColumnName, fieldMap)) {
                sql.append("to_char(");
                sql.append(convertedColumnName);
                sql.append(", 'FM999999999') ILIKE #{dataTablesInput.search.value}");
            } else if (isCollection(originalColumnName, fieldMap)) {
                sql.append(convertedColumnName);
                sql.append(" ILIKE #{dataTablesInput.search.value}");
            } else if (isBoolean(originalColumnName, fieldMap)) {
                sql.append("to_char(cast(");
                sql.append(convertedColumnName);
                sql.append(" as integer), 'FM9') ILIKE #{dataTablesInput.search.value}");
            } else {
                sql.append(convertedColumnName);
                sql.append(" ILIKE #{dataTablesInput.search.value}");
            }
        }
        return sql;
    }

    /**
     * フィールドフィルターのためのフィールド毎の等号文字列を作成する。
     *
     * @param column Column
     * @return 等号文字列
     */
    protected static StringBuilder getFieldFilterWhereClause(Column column, Map<String, String> fieldMap, int i) {
        StringBuilder sql = new StringBuilder();

        if (!StringUtils.isEmpty(column.getSearch().getValue())) {

            String originalColumnName = column.getData();
            String convertedColumnName = StringUtils.toLowerSnakeCase(convertColumnName(originalColumnName));
            String replacedColumnName = replacedColumnName(convertedColumnName);

            sql.append(" AND ");
            if (isFilterINClause(originalColumnName, fieldMap)) {
                sql.append(convertedColumnName);
                sql.append(" IN (#{dataTablesInput.columns[" + i + "].search.value})");
            } else if (isLocalDate(originalColumnName, fieldMap)) {
                sql.append("to_char(");
                sql.append(convertedColumnName);
                sql.append(", 'YYYY/MM/DD') ILIKE #{dataTablesInput.columns[" + i + "].search.value}");
                setWildcard(column);
            } else if (isLocalDateTime(originalColumnName, fieldMap)) {
                sql.append("to_char(");
                sql.append(convertedColumnName);
                sql.append(", 'YYYY/MM/DD HH24:MI:SS') ILIKE #{dataTablesInput.columns[" + i + "].search.value}");
                setWildcard(column);
            } else if (isNumber(originalColumnName, fieldMap)) {
                sql.append("to_char(");
                sql.append(convertedColumnName);
                sql.append(", 'FM999999999') ILIKE #{dataTablesInput.columns[" + i + "].search.value}");
                setWildcard(column);
            } else if (isBoolean(originalColumnName, fieldMap)) {
                sql.append("to_char(cast(");
                sql.append(convertedColumnName);
                sql.append(" as integer), 'FM9') = #{dataTablesInput.columns[" + i + "].search.value}");
            } else {
                sql.append(convertedColumnName);
                sql.append(" ILIKE #{dataTablesInput.columns[" + i + "].search.value}");
                setWildcard(column);
            }
        }
        return sql;
    }

    private static void setWildcard(Column column) {
        var originalValue = column.getSearch().getValue();
        column.getSearch().setValue("%" + originalValue + "%");
    }


    /**
     * フィールドフィルターの設定されている
     *
     * @param input DataTablesInput
     * @return true:フィールドフィルタを設定あり, false:なし
     */
    protected static boolean hasFieldFilter(DataTablesInput input) {
        for (Column column : input.getColumns()) {
            if (column.getSearchable() && !StringUtils.isEmpty(column.getSearch().getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定されたフィールド名がLocalDate型かどうか
     *
     * @param fieldName フィールド名
     * @return true:LocalDate型である, false:ない
     */
    protected static boolean isLocalDate(String fieldName, Map<String, String> fieldMap) {
        return "java.time.LocalDate".equals(fieldMap.get(fieldName));
    }

    /**
     * 指定されたフィールド名がLocalDateTIme型かどうか
     *
     * @param fieldName フィールド名
     * @return true:LocalDateTIme型である, false:ない
     */
    protected static boolean isLocalDateTime(String fieldName, Map<String, String> fieldMap) {
        return "java.time.LocalDateTime".equals(fieldMap.get(fieldName));
    }

    /**
     * 指定されたフィールド名がNumber型かどうか
     *
     * @param fieldName フィールド名
     * @return true:Number型である, false:ない
     */
    protected static boolean isNumber(String fieldName, Map<String, String> fieldMap) {
        return "java.lang.Integer".equals(fieldMap.get(fieldName))
                || "java.lang.Long".equals(fieldMap.get(fieldName))
                || "java.lang.BigDecimal".equals(fieldMap.get(fieldName))
                || "java.lang.Float".equals(fieldMap.get(fieldName))
                || "java.lang.Double".equals(fieldMap.get(fieldName))
                || "java.lang.Short".equals(fieldMap.get(fieldName))
                || "java.lang.Byte".equals(fieldMap.get(fieldName))
                || "java.math.BigInteger".equals(fieldMap.get(fieldName));
    }

    /**
     * 指定されたフィールド名がCollection型かどうか
     *
     * @param fieldName フィールド名
     * @return true:Collection型である, false:ない
     */
    protected static boolean isCollection(String fieldName, Map<String, String> fieldMap) {
        return "java.util.Collection".equals(fieldMap.get(fieldName))
                || "java.util.Set".equals(fieldMap.get(fieldName))
                || "java.util.List".equals(fieldMap.get(fieldName));
    }

    /**
     * 指定されたフィールド名がBooleanかどうか
     *
     * @param fieldName フィールド名
     * @return true:Booleanである, false:ない
     */
    protected static boolean isBoolean(String fieldName, Map<String, String> fieldMap) {
        return "java.lang.Boolean".equals(fieldMap.get(fieldName));
    }

    /**
     * 指定されたフィールド名はEnumかどうか
     *
     * @param fieldName フィールド名
     * @return true:Enumである, false:ない
     */
    protected static boolean isEnum(String fieldName, Map<String, String> fieldMap) {
        try {
            Class<?> c = Class.forName(fieldMap.get(fieldName));
            return c.isEnum();
        } catch (Exception e) {
            return false;
        }
    }

//    /**
//     * 指定されたフィールド名に@IDが設定されている。
//     *
//     * @param fieldName フィールド名
//     * @return true:IDである, false:ない
//     */
//    protected boolean isId(String fieldName) {
//        Map<String, Annotation> map = BeanUtils.getFieldByAnnotation(clazz, null, Id.class);
//        return map.containsKey(fieldName);
//    }

//    /**
//     * 指定されたフィールド名に@IDが設定され、Integer型である。
//     *
//     * @param fieldName フィールド名
//     * @return true:Integer型のIDである, false:ない
//     */
//    protected boolean isIdInteger(String fieldName) {
//        return isId(fieldName) && "java.lang.Integer".equals(fieldMap.get(fieldName));
//    }

//    /**
//     * 指定されたフィールド名に@IDが設定され、Long型である。
//     *
//     * @param fieldName フィールド名
//     * @return true:Long型のIDである, false:ない
//     */
//    protected boolean isIdLong(String fieldName) {
//        return isId(fieldName) && "java.lang.Long".equals(fieldMap.get(fieldName));
//    }

//    /**
//     * 指定されたフィールド名に@IDが設定され、String型である。
//     *
//     * @param fieldName フィールド名
//     * @return true:String型のIDである, false:ない
//     */
//    protected boolean isIdString(String fieldName) {
//        return isId(fieldName) && "java.lang.String".equals(fieldMap.get(fieldName));
//    }

//    /**
//     * 指定されたフィールド名がCollectionElementかどうか
//     *
//     * @param fieldName フィールド名
//     * @return true:CollectionElementである, false:ない
//     */
//    protected boolean isCollectionElement(String fieldName) {
//        return elementCollectionFieldsMap.containsKey(fieldName);
//    }

//    /**
//     * 指定したフィールドがリレーションかどうか
//     *
//     * @param fieldName フィールド名
//     * @return true:リレーションである, false:ない
//     */
//    protected boolean isRelation(String fieldName) {
//        return getRelationEntity(fieldName) != null;
//    }

    /**
     * LIKEでなくINで検索するフィールドを設定する。(オーバーライトする前提)
     *
     * @param fieldName フォールド名
     * @return true:INで検索する, false:しない
     */
    protected static boolean isFilterINClause(String fieldName, Map<String, String> fieldMap) {
        return false;
    }

    /**
     * 検索文字列のリストをEnumのリストに変換する(サブクラスでオーバーライトして利用する)
     *
     * @param fieldName フィールド名
     * @param values    検索文字列のリスト
     * @return Enumのリスト
     */
    protected List<Object> getEnumListByName(String fieldName, List<String> values) {
        // オーバーライトしないと何も検索しない。
        return new ArrayList<>();
    }

    /**
     * DataTablesのフィールド名とエンティティのフィールド名の変換
     *
     * @param fieldName 変換前のフィールド名
     * @return 変換後のフォールド名
     */
    public static String convertColumnName(String fieldName) {
        if (StringUtils.endsWith(fieldName, "Label")) {
            return StringUtils.left(fieldName, fieldName.length() - 5);
        } else {
            return fieldName;
        }
    }

    /**
     * フィールド名の変換(プレースフォルダ用にフィールド名に . が使えないことへの対応)
     *
     * @param fieldName . を含むフィールド名
     * @return . を _ に置換したフィールド名
     */
    protected static String replacedColumnName(String fieldName) {
        return fieldName.replace('.', '_');
    }

//    /**
//     * 指定したフィールドが外部結合の場合、結合するエンティテイ名を取得する。
//     *
//     * @param fieldName フィールド名
//     * @return 結合するエンティティ名, 存在しない場合はnull.
//     */
//    protected String getRelationEntity(String fieldName) {
//
//        String entityName = null;
//        if (fieldName != null && fieldName.contains(".")) {
//            entityName = fieldName.substring(0, fieldName.indexOf('.'));
//        }
//
//        if (relationFieldsMap.containsKey(entityName)) {
//            return entityName;
//        } else {
//            return null;
//        }
//    }

}
