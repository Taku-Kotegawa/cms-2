package jp.co.stnet.cms.common.datatables;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

/**
 * 列名クリックでソート機能を実現するためのユーティリティ
 */
@RequiredArgsConstructor
public class SortHelper {

    private final Sort sort;

    /**
     * 指定されたカラムのソート設定を探し、昇順／降順を逆転させる
     *
     * @param column カラム
     * @return ソート設定
     */
    public String id(String column) {
        for (var order : sort.toList()) {
            if (column.equals(order.getProperty())) {
                if (order.getDirection().isAscending()) {
                    return column + ",desc";
                }
            }
        }
        return column;
    }

    /**
     * 昇順・降順の矢印文字を返す
     *
     * @param column カラム名
     * @return 昇順=↓, 降順=↑
     */
    public String arrow(String column) {
        for (var order : sort.toList()) {
            if (column.equals(order.getProperty())) {
                if (order.getDirection().isAscending()) {
                    return "↓";
                } else {
                    return "↑";
                }
            }
        }
        return "";
    }
}
