package lean.http.waf;

public enum WafRequestType {

    ADDSITE("site_policy", "/api.php/v1/comm/add"),
    SHOWSITE("site_policy", "/api.php/v1/comm/show"),

    //add by lq
    SHOWANTIESCAPE("anti_escape", "/api.php/v1/comm/show2"),
    SEARCHANTIESCAPE("anti_escape", "/api.php/v1/comm/modify"),
    SHOWANTISCAN("anti_scan", "/api.php/v1/comm/show2"),
    SEARCHANTISCAN("anti_scan", "/api.php/v1/comm/modify"),
    SHOWANTIE_XML_INJECTION("anti_xml_injection", "/api.php/v1/comm/show2"),
    SEARCHANTI_XML_INJECTION("anti_xml_injection", "/api.php/v1/comm/modify"),

    SHOWKEYWORD("keyword_filter", "/api.php/v1/comm/show2"),
    UPDATEKEYWORD("keyword_filter", "/api.php/v1/comm/modify"),
    SHOWGROUP("ip_groups", "/api.php/v1/comm/show"),
    ADDGROUP("ip_groups", "/api.php/v1/comm/add"),
    UPDATEGROUP("ip_groups", "/api.php/v1/comm/modify"),
    DELETEGROUP("ip_groups", "/api.php/v1/comm/delete");

    private String module;

    private String path;

    WafRequestType(String module, String path) {
        this.module = module;
        this.path = path;
    }

    public String getModule() {
        return module;
    }

    public String getPath() {
        return path;
    }
}
