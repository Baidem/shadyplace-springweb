package com.shadyplace.springweb.models.bookingResa;

import java.util.ArrayList;
import java.util.List;

public class Parasol {
    private Long bookingId = 0l;
    private int lineNumber = 0;
    private int rankNumber = 0;
    private List<Integer> lineScope = new ArrayList<>();
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getRankNumber() {
        return rankNumber;
    }

    public void setRankNumber(int rankNumber) {
        this.rankNumber = rankNumber;
    }

    public List<Integer> getLineScope() {
        return lineScope;
    }

    public void setLineScope(List<Integer> lineScope) {
        this.lineScope = lineScope;
    }

    @Override
    public String toString() {
        return "L" + lineNumber +
                "R" + rankNumber;
    }
}
