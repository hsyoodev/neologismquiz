package com.hsyoodev.neologismquiz;

public enum ScoreType {
    ZERO(0),
    TWENTY(20),
    FORTY(40),
    SIXTY(60),
    EIGHTY(80),
    HUNDRED(100);

    private final int value;

    ScoreType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
