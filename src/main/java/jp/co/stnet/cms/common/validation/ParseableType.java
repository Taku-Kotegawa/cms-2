package jp.co.stnet.cms.common.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ParseableType {
    TO_SHORT("Short"),
    TO_INT("Integer"),
    TO_LONG("Long"),
    TO_DOUBLE("Double"),
    TO_FLOAT("Float");
    @Getter
    private final String friendlyName;
}