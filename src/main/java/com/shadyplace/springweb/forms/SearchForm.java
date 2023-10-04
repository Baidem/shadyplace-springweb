package com.shadyplace.springweb.forms;

public class SearchForm {
    private String searchBar;

    public SearchForm(String searchBar) {
        this.searchBar = searchBar;
    }

    public String getSearchBar() {
        return searchBar;
    }

    public void setSearchBar(String searchBar) {
        this.searchBar = searchBar;
    }

}
