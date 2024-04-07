// import java.sql.Date;
// import java.util.ArrayList;

// public class Cars extends Model {
//     ArrayList<Object> cars;

//     public Cars() {
//         cars = new ArrayList<>();
//     }

//     public ArrayList<Object> getData() throws DLExeption {
//         this.cars = new ArrayList<>();
//         ArrayList<String> params = new ArrayList<>();
//         params.add("available");
//         String statement = "Select * from Car where status = ?";
//         ArrayList<ArrayList<String>> data = db.executeQuery(statement, params);

//         for (int i = 1; i < data.size(); i++) {
//             Car car = new Car(Integer.parseInt(data.get(i).get(0)), data.get(i).get(1), data.get(i).get(2),
//                     Integer.parseInt(data.get(i).get(3)), Date.valueOf(data.get(i).get(4)),
//                     Integer.parseInt(data.get(i).get(5)), Boolean.valueOf(data.get(i).get(5)));
//             this.cars.add(car);
//         }
//         return this.cars;
//     }
// }
