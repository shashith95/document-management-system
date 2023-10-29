package com.api.documentmanagementservice.commons.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventTypes {
    CREATE(1),
    UPDATE(2),
    GET(3),
    DELETE(4),
    RESTORE(5),
    VERIFICATION(6);

    private final int value;
}
