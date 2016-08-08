package linearlayout.example.manh.mobileshop.Model;

/**
 * Created by MANH on 03-Aug-16.
 */
public class SanPham {
    private   Integer  MaSP_Id;
    private   String TENSP;
    private   Integer GIASP;
    private   Integer SOLUONGSP;
    private   byte[] HINHANHSP;
    private   String THONGTINSP;
    private Integer FKHangSanXuat_Id;
    public SanPham(){

    }

    public SanPham(Integer Id,String TENSP, Integer GIASP, Integer SOLUONGSP, byte[] HINHANHSP, String THONGTINSP, Integer FKHangSanXuat_Id) {
        this.MaSP_Id = Id;
        this.TENSP = TENSP;
        this.GIASP = GIASP;
        this.SOLUONGSP = SOLUONGSP;
        this.HINHANHSP = HINHANHSP;
        this.THONGTINSP = THONGTINSP;
        this.FKHangSanXuat_Id = FKHangSanXuat_Id;
    }

    public Integer getMaSP_Id() {
        return MaSP_Id;
    }

    public void setMaSP_Id(Integer maSP_Id) {
        MaSP_Id = maSP_Id;
    }

    public String getTENSP() {
        return TENSP;
    }

    public void setTENSP(String TENSP) {
        this.TENSP = TENSP;
    }

    public Integer getGIASP() {
        return GIASP;
    }

    public void setGIASP(Integer GIASP) {
        this.GIASP = GIASP;
    }

    public Integer getSOLUONGSP() {
        return SOLUONGSP;
    }

    public void setSOLUONGSP(Integer SOLUONGSP) {
        this.SOLUONGSP = SOLUONGSP;
    }

    public byte[] getHINHANHSP() {
        return HINHANHSP;
    }

    public void setHINHANHSP(byte[] HINHANHSP) {
        this.HINHANHSP = HINHANHSP;
    }

    public String getTHONGTINSP() {
        return THONGTINSP;
    }

    public void setTHONGTINSP(String THONGTINSP) {
        this.THONGTINSP = THONGTINSP;
    }

    public Integer getFKHangSanXuat_Id() {
        return FKHangSanXuat_Id;
    }

    public void setFKHangSanXuat_Id(Integer FKHangSanXuat_Id) {
        this.FKHangSanXuat_Id = FKHangSanXuat_Id;
    }
}
