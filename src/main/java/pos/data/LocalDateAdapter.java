package pos.data;


import java.time.LocalDate;

public class LocalDateAdapter   {

    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }

}
