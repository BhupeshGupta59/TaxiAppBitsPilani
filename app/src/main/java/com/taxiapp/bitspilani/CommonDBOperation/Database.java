package com.taxiapp.bitspilani.CommonDBOperation;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.taxiapp.bitspilani.pojo.Booking;
import com.taxiapp.bitspilani.pojo.Driver;
import com.taxiapp.bitspilani.pojo.Owner;
import com.taxiapp.bitspilani.pojo.ReportBooking;
import com.taxiapp.bitspilani.pojo.Station;
import com.taxiapp.bitspilani.pojo.User;
import com.taxiapp.bitspilani.pojo.Vehicle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Database {
    //private DatabaseReference databaseReference;
    //private FirebaseDatabase databaseInstance;
    private List<Owner> ownerList = new ArrayList<>();
    private FirebaseFirestore firestoreInstance;
    private DatabaseReference mDatabase;

    public void setOwnerList() {

        firestoreInstance = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firestoreInstance.collection("owners")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    // List<Vehicle> vehicleList = new ArrayList<>();

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Owner owner = document.toObject(Owner.class);
                                ownerList.add(owner);
                                // Log.i("abcdef",ownerList.get(0).getCity());


                            }


                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }


                });


    }

    public List<Owner> getOwnerList() {
        return ownerList;
    }

    public List<Vehicle> getVehicleList(Owner owner) {
        return null;
    }

    public List<Station> getStationList() {
        return null;
    }

    public List<User> getUserList() {
        return null;
    }

    public List<Booking> getBookingList() {
        return null;
    }

    public FirebaseFirestore getFirestoreInstance() {
        firestoreInstance = FirebaseFirestore.getInstance();
        return firestoreInstance;
    }

    public DatabaseReference getDatabaseRef() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return mDatabase;
    }

    public void addOwner(Owner owner) {

    }

    public List<Booking> bookingTestCases(Context context) {

        List<Booking> bookingList = new ArrayList<>();
        Random rand = new Random();

        // Generate random integers in range 0 to 999
        String[] stringParameters = {"Delhi", "Jaipur", "Noida", "Ghaziabad", "Sikar", "Gurugram"};
        String[] carType = {"micro", "sedan", "suv"};
        int count =0;


                        while(count<=5) {
                            count++;
                            int p = rand.nextInt(25);
                            int k = rand.nextInt(30) + 1;
                            int j = rand.nextInt(3);
                            int x = rand.nextInt(6);
                            int m = rand.nextInt(6) + 5;
                            Date d = new Date(2019 - 1900, m, k, p, 00);
                            Timestamp t = new Timestamp(d);
                            Booking b = new Booking();
                            b.setId(getFirestoreInstance().collection("bookings").document().getId());
                            b.setSource("Pilani");
                            b.setStatus("pending");
                            b.setDestination(stringParameters[x]);
                            b.setCarType(carType[j]);
                            b.setTimestamp(t);
                            bookingList.add(b);
                            //FirebaseFirestore.getInstance().collection("bookings").document(b.getId()).set(b);
                        }


                while(count<=10) {

                        count++;
                        int p = rand.nextInt(25);
                        int k = rand.nextInt(30) + 1;
                        int j = rand.nextInt(3);
                        int x = rand.nextInt(6);
                        int m = rand.nextInt(6) + 5;
                        Date d = new Date(2019 - 1900, m, k, p, 00);
                        Timestamp t = new Timestamp(d);
                        Booking b = new Booking();
                        b.setId(getFirestoreInstance().collection("bookings").document().getId());
                        b.setStatus("pending");
                        b.setDestination("Pilani");
                        b.setSource(stringParameters[x]);
                        b.setCarType(carType[j]);
                        b.setTimestamp(t);
                        bookingList.add(b);
                       // FirebaseFirestore.getInstance().collection("bookings").document(b.getId()).set(b);
                    }

        File csvFile = null;

        FileOutputStream fileout= null;
        Uri fileUri = null;
        try {
            Timestamp reportTime = new Timestamp(new Date());
            Date reportDate = reportTime.toDate();
            SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.YYYY_hh:mm_a");
            String fileName = "booking_test_cases.csv";
            csvFile = new File(context.getCacheDir(),fileName);
            fileout = new FileOutputStream(csvFile);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

           outputWriter.write("BookingId,Source,Destination,CarType,BookingStatus,BookingTime");

           // List<ReportBooking> aList = a.getPrintList();
            for(int i=0;i<bookingList.size();i++) {
                outputWriter.write("\n");
                outputWriter.write(bookingList.get(i).getId()+","+bookingList.get(i).getSource()+","+bookingList.get(i).getDestination()+","+bookingList.get(i).getCarType()+","+bookingList.get(i).getStatus()+","+bookingList.get(i).getTimestamp().toDate().toString());
            }
            outputWriter.close();
            fileUri = Uri.fromFile(csvFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StorageReference reportRef = FirebaseStorage.getInstance().getReference().child("booking/"+fileUri.getLastPathSegment());
        UploadTask uploadTask = reportRef.putFile(fileUri);




        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });


        return bookingList;
    }
    public List<Station> stationTestCase(Context context)
    {
        List<Station> sList = new ArrayList<>();
        sList.add(new Station(context,"Pilani"));
        sList.add(new Station(context,"Delhi"));
        sList.add(new Station(context,"Jaipur"));
        for (int i=0;i<sList.size();i++)
        {
            FirebaseFirestore.getInstance().collection("stations").document(sList.get(i).getId()).set(sList.get(i));
        }
        return sList;
    }

    public List<Object> otherTestCases(Context context) {
        List<Object> obj = new ArrayList<>();
        List<Owner> oList = new ArrayList<>();
        List<Driver> dList = new ArrayList<>();
        List<Vehicle> vList = new ArrayList<>();
        String city[] = {"Delhi", "Pilani", "Jaipur", "Noida", "Ghaziabad", "Sikar", "Chandigarh"};
        String[] location = {"28.644800,77.216721", "28.380200,75.609200", "26.912400,75.787300", "28.535500,77.391000", "28.669200,77.453800", "27.609400,75.139900", "30.733300,76.779400"};
        String vehicleCode[] = {"DL05", "RJ18", "RJ14", "UP16", "UP14", "RJ23", "CH01"};

        int ownerNameCount = 1;
        int ownerPhoneCount = 1;

        int vehicleNumCount = 1;

        int driverCount = 1;
        int phoneNumCount = 1;
        int licenseCount = 1;
        int p = 1;

        String[] testCities = {"Delhi", "Jaipur", "Pilani"};
        int locCount = 0;
        int cityCount=0;
        List<Owner> ownerTestCases = new ArrayList<>();
        List<Driver> driverTestCases = new ArrayList<>();
        List<Vehicle> vehicleTestCases = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 1; j++) {
                Owner owner = new Owner();
                owner.setName("owner" + Integer.toString(ownerNameCount++));
                //Log.i("abc",city[i]);

                if (ownerPhoneCount < 10) {
                    owner.setPhoneNo("999995000" + Integer.toString(ownerPhoneCount));
                } else {
                    owner.setPhoneNo("99999500" + Integer.toString(ownerPhoneCount));
                }

                owner.setEmailId(owner.getName() + "@gmail.com");
                if(cityCount==3) cityCount=0;
                owner.setCity(testCities[cityCount++]);

                //Vehicle test cases, 5 vehicles per owner

                //name,cartype,numofseats
                String carCategory1[] = {"Hyundai Eon", "micro", "4"};
                String carCategory2[] = {"Swift DZire", "sedan", "4"};
                String carCategory3[] = {"Toyota Innova", "suv", "7"};

                //list for 5 vehicles of an owner
                List<Vehicle> veh = new ArrayList<>();

                for (int k = 1; k <= 2; k++) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setName(carCategory1[0]);
                    vehicle.setCarType(carCategory1[1]);
                    vehicle.setId(getFirestoreInstance().collection("vehicles").document().getId());
                    vehicle.setStatus("idle");
                    if (vehicleNumCount < 10) {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB000" + Integer.toString(vehicleNumCount++));
                    } else {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB00" + Integer.toString(vehicleNumCount++));
                    }

                    if(locCount == 3)
                        locCount = 0;

                    String longLat[] = location[locCount++].split(",");

                    /*
                    //random vehicle current location
                    Random generator = new Random();
                    int randomIndex = generator.nextInt(location.length);
                    String longLat[] = location[randomIndex].split(",");
                    */

                    GeoPoint loc = new GeoPoint(Double.parseDouble(longLat[0]), Double.parseDouble(longLat[1]));
                    vehicle.setLocation(loc);

                    vehicle.setOwnerCity(city[i]);
                    vehicle.setLastLocation(location[i]);
                    vehicle.setNoOfSeats(Integer.parseInt(carCategory1[2]));
                    Log.i("vehicle",vehicle.getName()+ " " + vehicle.getOwnerCity() + " " + vehicle.getLastLocationName() + " " + vehicle.getVehicleNo() + " " + vehicle.getCarType() );

                    //adding to owner list
                    veh.add(vehicle);
                    vList.add(vehicle);
                    vehicleTestCases.add(vehicle);
                   // FirebaseFirestore.getInstance().collection("vehicles").document(vehicle.getId()).set(vehicle);
                    //String writeToCSV = vehicle.getName() + "," + vehicle.getCarType() + "," + vehicle.getVehicleNo() + "," + vehicle.getLocation() +  "," + vehicle.getOwnerCity() + "," + vehicle.getLastLocationName() + "," + vehicle.getNoOfSeats();
                    //System.out.println(writeToCSV);

                }

                for (int k = 3; k <= 4; k++) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setName(carCategory2[0]);
                    vehicle.setCarType(carCategory2[1]);
                    vehicle.setId(getFirestoreInstance().collection("vehicles").document().getId());
                    vehicle.setStatus("idle");

                    if (vehicleNumCount < 10) {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB000" + Integer.toString(vehicleNumCount++));
                    } else {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB00" + Integer.toString(vehicleNumCount++));
                    }

                    Random generator = new Random();
                    int randomIndex = generator.nextInt(location.length);
                    String longLat[] = location[randomIndex].split(",");
                    GeoPoint loc = new GeoPoint(Double.parseDouble(longLat[0]), Double.parseDouble(longLat[1]));
                    vehicle.setLocation(loc);

                    vehicle.setOwnerCity(city[i]);
                    vehicle.setLastLocation(location[i]);
                    vehicle.setNoOfSeats(Integer.parseInt(carCategory2[2]));
                    Log.i("vehicle",vehicle.getName()+ " " + vehicle.getOwnerCity() + " " + vehicle.getLastLocationName() + " " + vehicle.getVehicleNo() + " " + vehicle.getCarType() );

                    veh.add(vehicle);
                    vList.add(vehicle);
                    vehicleTestCases.add(vehicle);

                    // FirebaseFirestore.getInstance().collection("vehicles").document(vehicle.getId()).set(vehicle);


                }

                for (int k = 5; k <= 5; k++) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(getFirestoreInstance().collection("vehicles").document().getId());
                    vehicle.setName(carCategory3[0]);
                    vehicle.setCarType(carCategory3[1]);
                    vehicle.setStatus("idle");

                    if (vehicleNumCount < 10) {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB000" + Integer.toString(vehicleNumCount++));
                    } else {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB00" + Integer.toString(vehicleNumCount++));
                    }

                    Random generator = new Random();
                    int randomIndex = generator.nextInt(location.length);
                    String longLat[] = location[randomIndex].split(",");
                    GeoPoint loc = new GeoPoint(Double.parseDouble(longLat[0]), Double.parseDouble(longLat[1]));
                    vehicle.setLocation(loc);

                    vehicle.setOwnerCity(city[i]);
                    vehicle.setLastLocation(location[i]);
                    vehicle.setNoOfSeats(Integer.parseInt(carCategory3[2]));
                    Log.i("vehicle",vehicle.getName()+ " " + vehicle.getOwnerCity() + " " + vehicle.getLastLocationName() + " " + vehicle.getVehicleNo() + " " + vehicle.getCarType() );

                    veh.add(vehicle);
                    vList.add(vehicle);
                    vehicleTestCases.add(vehicle);

                    // FirebaseFirestore.getInstance().collection("vehicles").document(vehicle.getId()).set(vehicle);


                }

                owner.setListOfVehicle(veh);

                //creating 5 drivers for each owner
                List<Driver> dri = new ArrayList<>();

                for (int k = 1; k <= 5; k++) {
                    Driver driver = new Driver();
                    driver.setId(getFirestoreInstance().collection("drivers").document().getId());
                    driver.setName("driver" + Integer.toString(driverCount++));

                    if (phoneNumCount < 10) {
                        driver.setPhoneNo("900005000" + Integer.toString(phoneNumCount++));
                    } else if (phoneNumCount > 9 && phoneNumCount < 100) {
                        driver.setPhoneNo("90000500" + Integer.toString(phoneNumCount++));
                    } else {
                        driver.setPhoneNo("9000050" + Integer.toString(phoneNumCount++));
                    }

                    driver.setEmailId(driver.getName() + "@gmail.com");
                    driver.setCity(city[i]);

                    if (licenseCount < 10) {
                        driver.setLicenseNo(vehicleCode[i] + "2014000000" + Integer.toString(licenseCount++));
                    } else if (licenseCount > 9 && licenseCount < 100) {
                        driver.setLicenseNo(vehicleCode[i] + "201400000" + Integer.toString(licenseCount++));
                    } else {
                        driver.setLicenseNo(vehicleCode[i] + "20140000" + Integer.toString(licenseCount++));
                    }

                    String longLat[] = location[i].split(",");
                    GeoPoint loc = new GeoPoint(Double.parseDouble(longLat[0]), Double.parseDouble(longLat[1]));
                    driver.setLocation(loc);
                    Log.i("driver",driver.getName()+" "+driver.getCity());

                    dri.add(driver);
                    dList.add(driver);
                    driverTestCases.add(driver);
                    //FirebaseFirestore.getInstance().collection("drivers").document(driver.getId()).set(driver);

                }

                owner.setListOfDriver(dri);
                // Log.i("abc","Count "+ "Integer.toString("+ p++ +") " +owner.toString());
                oList.add(owner);
                owner.setId(getFirestoreInstance().collection("owners").document().getId());
                Log.i("ownertestcase",owner.getName()+" "+owner.getCity());
                /*for(int l=0;l<vList.size();l++)
                {
                    Log.i("ownertestcase",vList.get(i).getName()+" "+vList.get(l).toCity(context)+" "+vList.get(l).getStatus());
                }
                */
                // Log.i("abc",owner.getId());
               // FirebaseFirestore.getInstance().collection("owners").document(owner.getId()).set(owner);

                ownerTestCases.add(owner);
            }
        }
        obj.add(dList);
        obj.add(oList);
        obj.add(vList);

        File csvFile = null;
        FileOutputStream fileout= null;
        Uri fileUri = null;
        try {
            Timestamp reportTime = new Timestamp(new Date());
            Date reportDate = reportTime.toDate();
            SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.YYYY_hh:mm_a");
            String fileName = "owner_test_cases.csv";
            csvFile = new File(context.getCacheDir(),fileName);
            fileout = new FileOutputStream(csvFile);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

            // outputWriter.write("BookingId,Source,Destination,CarType,BookingStatus,OwnerName,OwnerCity,VehicleName,VehicleCity,BookingTime");
            outputWriter.write("OwnerName,PhoneNo,Email,City");
            // List<ReportBooking> aList = a.getPrintList();
            for(int i=0;i<ownerTestCases.size();i++) {
                outputWriter.write("\n");
                outputWriter.write(ownerTestCases.get(i).getName()+","+ownerTestCases.get(i).getPhoneNo()+","+ownerTestCases.get(i).getEmailId()+","+ownerTestCases.get(i).getCity());
            }
            outputWriter.close();
            fileUri = Uri.fromFile(csvFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StorageReference reportRef = FirebaseStorage.getInstance().getReference().child("booking/"+fileUri.getLastPathSegment());
        UploadTask uploadTask = reportRef.putFile(fileUri);




        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

        try {
            Timestamp reportTime = new Timestamp(new Date());
            Date reportDate = reportTime.toDate();
            SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.YYYY_hh:mm_a");
            String fileName = "driver_test_cases.csv";
            csvFile = new File(context.getCacheDir(),fileName);
            fileout = new FileOutputStream(csvFile);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

            outputWriter.write("DriverName,PhoneNo,Email,City,LicenseNo");

            // List<ReportBooking> aList = a.getPrintList();
            for(int i=0;i<driverTestCases.size();i++) {
                outputWriter.write("\n");
                outputWriter.write(driverTestCases.get(i).getName()+","+driverTestCases.get(i).getPhoneNo()+","+driverTestCases.get(i).getEmailId()+","+driverTestCases.get(i).getCity()+","+driverTestCases.get(i).getLicenseNo());
            }
            outputWriter.close();
            fileUri = Uri.fromFile(csvFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         reportRef = FirebaseStorage.getInstance().getReference().child("booking/"+fileUri.getLastPathSegment());
         uploadTask = reportRef.putFile(fileUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

        try {
            Timestamp reportTime = new Timestamp(new Date());
            Date reportDate = reportTime.toDate();
            SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.YYYY_hh:mm_a");
            String fileName = "vehicle_test_cases.csv";
            csvFile = new File(context.getCacheDir(),fileName);
            fileout = new FileOutputStream(csvFile);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

            outputWriter.write("VehicleName,type,number,ownerCity,status");

            // List<ReportBooking> aList = a.getPrintList();
            for(int i=0;i<vehicleTestCases.size();i++) {
                outputWriter.write("\n");
                outputWriter.write(vehicleTestCases.get(i).getName()+","+vehicleTestCases.get(i).getCarType()+","+vehicleTestCases.get(i).getVehicleNo()+","+vehicleTestCases.get(i).getOwnerCity()+","+vehicleTestCases.get(i).getStatus());
            }
            outputWriter.close();
            fileUri = Uri.fromFile(csvFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reportRef = FirebaseStorage.getInstance().getReference().child("booking/"+fileUri.getLastPathSegment());
        uploadTask = reportRef.putFile(fileUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

        return obj;

    }
}
