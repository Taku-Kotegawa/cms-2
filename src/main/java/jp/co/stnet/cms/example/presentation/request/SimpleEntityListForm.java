package jp.co.stnet.cms.example.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleEntityListForm implements Serializable {

    private String q;

    private String sort;

    public String getParameter() {

        StringBuilder sb = new StringBuilder();


        if (q != null) {
            sb.append(",q='").append(q).append("'");
        }

        if (sort != null) {
            sb.append(",sort='").append(sort).append("'");
        }

        // null or Empty を返すとThymeleafがエラーになるので、なんでも良いので値を返す
        if (sb.toString().isEmpty()) {
            return ",sort=";
        }

        return sb.toString();
    }


}
