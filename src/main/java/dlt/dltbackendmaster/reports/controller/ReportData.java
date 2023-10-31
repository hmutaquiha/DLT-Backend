package dlt.dltbackendmaster.reports.controller;

public class ReportData {
    private Integer id;
    private String name;

    public ReportData(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
