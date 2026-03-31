package me.fortibrine.justreports.dialog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class Dialog {
    private final UUID playerId;
    private final UUID adminId;
    private final boolean admin;
}
