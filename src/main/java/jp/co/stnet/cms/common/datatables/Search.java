package jp.co.stnet.cms.common.datatables;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * DataTables(Server-Side)からのリクエストを格納するクラス(グローバルフィルタ)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Search {

    /**
     * Global search value. To be applied to all columns which have searchable as true.
     */
    @NotNull
    private String value;

    /**
     * true if the global filter should be treated as a regular expression for advanced searching,
     * false otherwise. Note that normally server-side processing scripts will not perform regular
     * expression searching for performance reasons on large data sets, but it is technically possible
     * and at the discretion of your script.
     */
    @NotNull
    private Boolean regex;

}
