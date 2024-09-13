package pos.presentation.estadisticas;

public class Rango {
    int annoDesde;
    int annoHasta;
    int mesDesde;
    int mesHasta;

    public Rango() {}

    public Rango(int anD , int anH , int mD , int mH){
        this.annoDesde = anD;
        this.annoHasta = anH;
        this.mesDesde = mD;
        this.mesHasta = mH;
    }

    public int getAnoDesde() {return annoDesde;}
    public int getAnoHasta() {return annoHasta;}
    public int getMesDesde() {return mesDesde;}
    public int getMesHasta() {return mesHasta;}
    public void setAnoDesde(int anD) {this.annoDesde = anD;}
    public void setAnoHasta(int anH) {this.annoHasta = anH;}
    public void setMesDesde(int mD) {this.mesDesde = mD;}
    public void setMesHasta(int mH) {this.mesHasta = mH;}

}
