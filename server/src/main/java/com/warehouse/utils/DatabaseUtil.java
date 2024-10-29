package com.warehouse.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.warehouse.models.Car;
import com.warehouse.models.Color;
import com.warehouse.models.Make;
import com.warehouse.models.Model;
import com.warehouse.models.Owner;
import com.warehouse.repositories.CarRepository;
import com.warehouse.repositories.ColorRepository;
import com.warehouse.repositories.MakeRepository;
import com.warehouse.repositories.ModelRepository;
import com.warehouse.repositories.OwnerRepository;

@Component
public class DatabaseUtil {

    final private MakeRepository makeRepository;
    final private CarRepository carRepository;
    final private ModelRepository modelRepository;
    final private OwnerRepository ownerRepository;
    final private ColorRepository colorRepository;

    public DatabaseUtil(MakeRepository makeRepository, CarRepository carRepository, ModelRepository modelRepository, OwnerRepository ownerRepository, ColorRepository colorRepository) {
        this.makeRepository = makeRepository;
        this.carRepository = carRepository;
        this.modelRepository = modelRepository;
        this.ownerRepository = ownerRepository;
        this.colorRepository = colorRepository;
    }
  
    public void clearDatabase() {
        makeRepository.deleteAll();
        modelRepository.deleteAll();
        carRepository.deleteAll();
        ownerRepository.deleteAll();
        colorRepository.deleteAll();
        carRepository.resetIdSequence();
    }

    public void seedTheDatabase() {
        if (makeRepository.findAll().isEmpty() && carRepository.findAll().isEmpty() && modelRepository.findAll().isEmpty() && ownerRepository.findAll().isEmpty() && colorRepository.findAll().isEmpty()) {
            Owner owner1 = new Owner(null, "Warehouse", "owner@warehouse.com", null);
            Owner owner2 = new Owner(null, "Jane Smith", "jane.smith@gmail.com", null);
            Owner owner3 = new Owner(null, "John Doe", "john.doe@gmail.com", null);
            List<Owner> owners = List.of(owner1, owner2, owner3);
            ownerRepository.saveAll(owners);
            Color red = Color.builder().name("Red").build();
            Color blue = Color.builder().name("Blue").build();
            Color black = Color.builder().name("Black").build();
            Color white = Color.builder().name("White").build();
            Color gray = Color.builder().name("Gray").build();
            List<Color> colors = List.of(blue, red, black, white, gray);
            colorRepository.saveAll(colors);
            // Define makes and models
            Make toyota = new Make(null, "Toyota", "https://res.cloudinary.com/dbpwbih9m/image/upload/v1722368644/logo1_tz77oh.jpg",  null);
            Make ford = new Make(null, "Ford", "https://res.cloudinary.com/dbpwbih9m/image/upload/v1722368644/ford-logo_rkgvtu.png", null);
            Make chevrolet = new Make(null, "Chevrolet", "https://res.cloudinary.com/dbpwbih9m/image/upload/v1722368644/chevrolet-logo_qo2vab.png", null);
            Make honda = new Make(null, "Honda", "https://res.cloudinary.com/dbpwbih9m/image/upload/v1722368644/honda-logo_jm4p7k.png", null);
            Make tesla = new Make(null, "Tesla", "https://res.cloudinary.com/dbpwbih9m/image/upload/v1722368644/tesla-logo_i6dddi.png", null);
            List<Model> toyotaModels = Arrays.asList(
                    Model.builder().name("Corolla").make(toyota).build(),
                    Model.builder().name("Camry").make(toyota).build(),
                    Model.builder().name("RAV4").make(toyota).build()
            );
            List<Model> fordModels = Arrays.asList(
                    Model.builder().name("F-150").make(ford).build(),
                    Model.builder().name("Mustang").make(ford).build(),
                    Model.builder().name("Explorer").make(ford).build()
            );
            List<Model> chevroletModels = Arrays.asList(
                    Model.builder().name("Silverado").make(chevrolet).build(),
                    Model.builder().name("Equinox").make(chevrolet).build(),
                    Model.builder().name("Malibu").make(chevrolet).build()
            );
            List<Model> hondaModels = Arrays.asList(
                    Model.builder().name("Civic").make(honda).build(),
                    Model.builder().name("Accord").make(honda).build(),
                    Model.builder().name("CR-V").make(honda).build()
            );
            List<Model> teslaModels = Arrays.asList(
                    Model.builder().name("Model S").make(tesla).build(),
                    Model.builder().name("Model 3").make(tesla).build(),
                    Model.builder().name("Model X").make(tesla).build(),
                    Model.builder().name("Model Y").make(tesla).build()
            );
            toyota.setModels(toyotaModels);
            ford.setModels(fordModels);
            chevrolet.setModels(chevroletModels);
            honda.setModels(hondaModels);
            tesla.setModels(teslaModels);
            List<Make> makes = Arrays.asList(toyota, ford, chevrolet, honda, tesla);
            makeRepository.saveAll(makes);
            modelRepository.saveAll(toyotaModels);
            modelRepository.saveAll(fordModels);
            modelRepository.saveAll(chevroletModels);
            modelRepository.saveAll(hondaModels);
            modelRepository.saveAll(teslaModels);
            // Define cars
            List<Car> cars = Arrays.asList(
                Car.builder()
                    .vin("1HGBH41JXMN109186")
                    .model(toyotaModels.get(0))
                    .make(toyota)
                    .year(2020)
                    .color(red)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(20000))
                    .mileage(15000)
                    .registrationNumber("ABC123")
                    .insurancePolicyNumber("INS123")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109187")
                    .model(fordModels.get(1))
                    .make(ford)
                    .year(2019)
                    .color(blue)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(25000))
                    .mileage(20000)
                    .registrationNumber("XYZ789")
                    .insurancePolicyNumber("INS456")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109188")
                    .model(chevroletModels.get(0))
                    .make(chevrolet)
                    .year(2021)
                    .color(black)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(30000))
                    .mileage(10000)
                    .registrationNumber("LMN456")
                    .insurancePolicyNumber("INS789")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109189")
                    .model(hondaModels.get(2))
                    .make(honda)
                    .year(2018)
                    .color(white)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(18000))
                    .mileage(25000)
                    .registrationNumber("OPQ234")
                    .insurancePolicyNumber("INS012")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109190")
                    .model(teslaModels.get(3))
                    .make(tesla)
                    .year(2022)
                    .color(gray)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(60000))
                    .mileage(5000)
                    .registrationNumber("RST678")
                    .insurancePolicyNumber("INS345")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109191")
                    .model(toyotaModels.get(1))
                    .make(toyota)
                    .year(2021)
                    .color(blue)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(22000))
                    .mileage(10000)
                    .registrationNumber("UVW234")
                    .insurancePolicyNumber("INS678")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109192")
                    .model(fordModels.get(2))
                    .make(ford)
                    .year(2017)
                    .color(red)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(15000))
                    .mileage(30000)
                    .registrationNumber("JKL567")
                    .insurancePolicyNumber("INS910")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109193")
                    .model(chevroletModels.get(1))
                    .make(chevrolet)
                    .year(2022)
                    .color(gray)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(32000))
                    .mileage(5000)
                    .registrationNumber("NOP345")
                    .insurancePolicyNumber("INS112")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109194")
                    .model(hondaModels.get(1))
                    .make(honda)
                    .year(2020)
                    .color(black)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(27000))
                    .mileage(8000)
                    .registrationNumber("QRS789")
                    .insurancePolicyNumber("INS314")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109195")
                    .model(teslaModels.get(0))
                    .make(tesla)
                    .year(2021)
                    .color(white)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(55000))
                    .mileage(12000)
                    .registrationNumber("TUV567")
                    .insurancePolicyNumber("INS516")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109196")
                    .model(toyotaModels.get(2))
                    .make(toyota)
                    .year(2019)
                    .color(white)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(21000))
                    .mileage(18000)
                    .registrationNumber("GHI789")
                    .insurancePolicyNumber("INS789")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109197")
                    .model(fordModels.get(0))
                    .make(ford)
                    .year(2021)
                    .color(black)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(35000))
                    .mileage(9000)
                    .registrationNumber("JKL012")
                    .insurancePolicyNumber("INS101")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109198")
                    .model(chevroletModels.get(2))
                    .make(chevrolet)
                    .year(2018)
                    .color(gray)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(24000))
                    .mileage(22000)
                    .registrationNumber("MNO345")
                    .insurancePolicyNumber("INS202")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109199")
                    .model(hondaModels.get(0))
                    .make(honda)
                    .year(2022)
                    .color(red)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(28000))
                    .mileage(7000)
                    .registrationNumber("PQR678")
                    .insurancePolicyNumber("INS303")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109200")
                    .model(teslaModels.get(1))
                    .make(tesla)
                    .year(2020)
                    .color(blue)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(62000))
                    .mileage(15000)
                    .registrationNumber("STU901")
                    .insurancePolicyNumber("INS404")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109201")
                    .model(toyotaModels.get(1))
                    .make(toyota)
                    .year(2021)
                    .color(blue)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(22000))
                    .mileage(10000)
                    .registrationNumber("UVW234")
                    .insurancePolicyNumber("INS678")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109202")
                    .model(fordModels.get(2))
                    .make(ford)
                    .year(2017)
                    .color(red)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(15000))
                    .mileage(30000)
                    .registrationNumber("JKL567")
                    .insurancePolicyNumber("INS910")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109203")
                    .model(chevroletModels.get(1))
                    .make(chevrolet)
                    .year(2022)
                    .color(gray)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(32000))
                    .mileage(5000)
                    .registrationNumber("NOP345")
                    .insurancePolicyNumber("INS112")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109204")
                    .model(hondaModels.get(1))
                    .make(honda)
                    .year(2020)
                    .color(black)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(27000))
                    .mileage(8000)
                    .registrationNumber("QRS789")
                    .insurancePolicyNumber("INS314")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109205")
                    .model(teslaModels.get(0))
                    .make(tesla)
                    .year(2021)
                    .color(white)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(55000))
                    .mileage(12000)
                    .registrationNumber("TUV567")
                    .insurancePolicyNumber("INS516")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109206")
                    .model(toyotaModels.get(2))
                    .make(toyota)
                    .year(2019)
                    .color(white)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(21000))
                    .mileage(18000)
                    .registrationNumber("GHI789")
                    .insurancePolicyNumber("INS789")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109207")
                    .model(fordModels.get(0))
                    .make(ford)
                    .year(2021)
                    .color(black)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(35000))
                    .mileage(9000)
                    .registrationNumber("JKL012")
                    .insurancePolicyNumber("INS101")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109208")
                    .model(chevroletModels.get(2))
                    .make(chevrolet)
                    .year(2018)
                    .color(gray)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(24000))
                    .mileage(22000)
                    .registrationNumber("MNO345")
                    .insurancePolicyNumber("INS202")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109209")
                    .model(hondaModels.get(0))
                    .make(honda)
                    .year(2022)
                    .color(red)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(28000))
                    .mileage(7000)
                    .registrationNumber("PQR678")
                    .insurancePolicyNumber("INS303")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109210")
                    .model(teslaModels.get(1))
                    .make(tesla)
                    .year(2020)
                    .color(blue)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(62000))
                    .mileage(15000)
                    .registrationNumber("STU901")
                    .insurancePolicyNumber("INS404")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109211")
                    .model(toyotaModels.get(0))
                    .make(toyota)
                    .year(2018)
                    .color(red)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(19000))
                    .mileage(25000)
                    .registrationNumber("GHI123")
                    .insurancePolicyNumber("INS789")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109212")
                    .model(fordModels.get(1))
                    .make(ford)
                    .year(2020)
                    .color(blue)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(26000))
                    .mileage(14000)
                    .registrationNumber("JKL456")
                    .insurancePolicyNumber("INS678")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109213")
                    .model(chevroletModels.get(0))
                    .make(chevrolet)
                    .year(2021)
                    .color(black)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(31000))
                    .mileage(9000)
                    .registrationNumber("MNO789")
                    .insurancePolicyNumber("INS567")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109214")
                    .model(hondaModels.get(2))
                    .make(honda)
                    .year(2019)
                    .color(white)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(20000))
                    .mileage(19000)
                    .registrationNumber("PQR012")
                    .insurancePolicyNumber("INS456")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109215")
                    .model(teslaModels.get(2))
                    .make(tesla)
                    .year(2022)
                    .color(gray)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(58000))
                    .mileage(4000)
                    .registrationNumber("STU345")
                    .insurancePolicyNumber("INS345")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109216")
                    .model(toyotaModels.get(1))
                    .make(toyota)
                    .year(2017)
                    .color(blue)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(17000))
                    .mileage(35000)
                    .registrationNumber("VWX678")
                    .insurancePolicyNumber("INS234")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109217")
                    .model(fordModels.get(2))
                    .make(ford)
                    .year(2019)
                    .color(red)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(23000))
                    .mileage(21000)
                    .registrationNumber("YZA901")
                    .insurancePolicyNumber("INS123")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109218")
                    .model(chevroletModels.get(1))
                    .make(chevrolet)
                    .year(2020)
                    .color(black)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(29000))
                    .mileage(16000)
                    .registrationNumber("BCD234")
                    .insurancePolicyNumber("INS012")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109219")
                    .model(hondaModels.get(0))
                    .make(honda)
                    .year(2018)
                    .color(white)
                    .owner(owner1)
                    .price(BigDecimal.valueOf(15000))
                    .mileage(30000)
                    .registrationNumber("EFG567")
                    .insurancePolicyNumber("INS789")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build(),
                Car.builder()
                    .vin("1HGBH41JXMN109220")
                    .model(teslaModels.get(3))
                    .make(tesla)
                    .year(2021)
                    .color(gray)
                    .owner(owner2)
                    .price(BigDecimal.valueOf(62000))
                    .mileage(5000)
                    .registrationNumber("HIJ890")
                    .insurancePolicyNumber("INS678")
                    .insuranceExpiration(new Date())
                    .registrationExpiration(new Date())
                    .lastMaintenanceDate(new Date())
                    .build()
            );
            carRepository.saveAll(cars);
        }
    }
}
