package com.shadyplace.springweb.forms;

public class SearchCommandForm {
    private String searchContentBar;

    private String filterStatus;

    public SearchCommandForm(String searchContentBar, String filterStatus) {
        this.searchContentBar = searchContentBar;
        this.filterStatus = filterStatus;
    }

    public String getSearchContentBar() {
        return searchContentBar;
    }

    public void setSearchContentBar(String searchContentBar) {
        this.searchContentBar = searchContentBar;
    }

    public String getFilterStatus() {
        return filterStatus;
    }

    public void setFilterStatus(String filterStatus) {
        this.filterStatus = filterStatus;
    }
}
