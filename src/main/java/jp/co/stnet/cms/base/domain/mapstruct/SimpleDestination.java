package jp.co.stnet.cms.base.domain.mapstruct;

import lombok.Data;

import java.util.List;

@Data
public class SimpleDestination {
    private String name;
    private String description;
    private List<SimpleSource> list;
}
