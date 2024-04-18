package Models;

public class Insurance {
    private int carID;
    private String insuranceModel;

    public Insurance(int carID, String insuranceModel) {
        this.carID = carID;
        this.insuranceModel = insuranceModel;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getInsuranceModel() {
        return insuranceModel;
    }

    public void setInsuranceModel(String insuranceModel) {
        this.insuranceModel = insuranceModel;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "carID=" + carID +
                ", insuranceModel='" + insuranceModel + '\'' +
                '}';
    }

}
