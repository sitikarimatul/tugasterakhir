package model;

public class MatakuliahModel {
  String _id, kodeMK, namaMK, jam, hari, ruangan, nidn, dosen;

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getKodeMK() {
    return kodeMK;
  }

  public void setKodeMK(String kodeMK) {
    this.kodeMK = kodeMK;
  }

  public String getNamaMK() {
    return namaMK;
  }

  public void setNamaMK(String namaMK) {
    this.namaMK = namaMK;
  }

  public String getJam() {
    return jam;
  }

  public void setJam(String jam) {
    this.jam = jam;
  }

  public String getHari() {
    return hari;
  }

  public void setHari(String hari) {
    this.hari = hari;
  }

  public String getRuangan() {
    return ruangan;
  }

  public void setRuangan(String ruangan) {
    this.ruangan = ruangan;
  }

  public String getNidn() {
    return nidn;
  }

  public void setNidn(String nidn) {
    this.nidn = nidn;
  }

  public String getDosen() {
    return dosen;
  }

  public void setDosen(String dosen) {
    this.dosen = dosen;
  }
}
