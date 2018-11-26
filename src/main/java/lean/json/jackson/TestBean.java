package lean.json.jackson;

public class TestBean {

    private String field1;

    private String field2;

    public String field1() {
        return this.field1;
    }

    public void field1(String field1) {
        this.field1 = field1;
    }

    public TestBean(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}
