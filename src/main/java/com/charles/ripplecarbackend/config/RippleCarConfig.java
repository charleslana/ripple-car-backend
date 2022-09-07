package com.charles.ripplecarbackend.config;

import com.charles.ripplecarbackend.model.entity.Car;
import com.charles.ripplecarbackend.model.entity.User;
import com.charles.ripplecarbackend.enums.CarClassEnum;
import com.charles.ripplecarbackend.enums.RarityEnum;
import com.charles.ripplecarbackend.enums.RoleEnum;
import com.charles.ripplecarbackend.enums.StatusEnum;
import com.charles.ripplecarbackend.model.entity.UserCar;
import com.charles.ripplecarbackend.repository.CarRepository;
import com.charles.ripplecarbackend.repository.UserCarRepository;
import com.charles.ripplecarbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RippleCarConfig {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final UserCarRepository userCarRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (carRepository.count() == 0) {
                createCar();
                createUser();
            }
        };
    }

    private void createUser() {
        User user1 = new User();
        user1.setEmail("example@example.com");
        user1.setPassword(encoder.encode("123456"));
        user1.setName("Example");
        user1.setRole(RoleEnum.ADMIN);

        User user2 = new User();
        user2.setEmail("example2@example.com");
        user2.setPassword(encoder.encode("123456789"));
        user2.setName("Example2");

        User user3 = new User();
        user3.setEmail("example3@example.com");
        user3.setPassword(encoder.encode("123456"));
        user3.setName("Example3");
        user3.setStatus(StatusEnum.INACTIVE);

        List<User> users = userRepository.saveAll(List.of(user1, user2, user3));

        carRepository.findAll().forEach(item ->  {
            Car car = new Car();
            car.setId(item.getId());
            User user = new User();
            user.setId(users.get(0).getId());
            UserCar userCar = new UserCar();
            userCar.setCar(car);
            userCar.setUser(user);
            userCarRepository.save(userCar);
        });
    }

    private void createCar() {
        Car car1 = new Car();
        car1.setName("Alfa Romeo 4C Concept");
        car1.setImage("MW12_Alfa_Romeo_4C_Concept.png");
        car1.setNitro(0L);
        car1.setWeight(10L);
        car1.setPotency(5L);
        car1.setControl(1L);
        car1.setToughness(1L);
        car1.setAcceleration(1L);
        car1.setTopSpeed(250L);
        car1.setCarClass(CarClassEnum.SPORT);
        car1.setRarity(RarityEnum.COMMON);
        car1.setMaxLevel(100L);

        Car car2 = new Car();
        car2.setName("Alfa Romeo MiTo QV");
        car2.setImage("MW12_Alfa_Romeo_MiTo_QV.jpg");
        car2.setNitro(0L);
        car2.setWeight(9L);
        car2.setPotency(4L);
        car2.setControl(1L);
        car2.setToughness(1L);
        car2.setAcceleration(1L);
        car2.setTopSpeed(218L);
        car2.setCarClass(CarClassEnum.EVERYDAY);
        car2.setRarity(RarityEnum.COMMON);
        car2.setMaxLevel(100L);

        Car car3 = new Car();
        car3.setName("Ariel Atom 500 V8");
        car3.setImage("MW12_Ariel_Atom_500_V8.png");
        car3.setNitro(0L);
        car3.setWeight(5L);
        car3.setPotency(4L);
        car3.setControl(1L);
        car3.setToughness(1L);
        car3.setAcceleration(1L);
        car3.setTopSpeed(281L);
        car3.setCarClass(CarClassEnum.RACE);
        car3.setRarity(RarityEnum.RARE);
        car3.setMaxLevel(100L);

        Car car4 = new Car();
        car4.setName("Aston Martin DB5 Vantage");
        car4.setImage("MW12_Aston_Martin_DB5_Vantage.jpg");
        car4.setNitro(0L);
        car4.setWeight(7L);
        car4.setPotency(4L);
        car4.setControl(1L);
        car4.setToughness(1L);
        car4.setAcceleration(1L);
        car4.setTopSpeed(230L);
        car4.setCarClass(CarClassEnum.SPORT);
        car4.setRarity(RarityEnum.COMMON_MORE);
        car4.setMaxLevel(100L);

        Car car5 = new Car();
        car5.setName("Aston Martin DBS");
        car5.setImage("MW12_Aston_Martin_DBS.jpg");
        car5.setNitro(0L);
        car5.setWeight(7L);
        car5.setPotency(4L);
        car5.setControl(1L);
        car5.setToughness(1L);
        car5.setAcceleration(1L);
        car5.setTopSpeed(295L);
        car5.setCarClass(CarClassEnum.GRAND_TOUR);
        car5.setRarity(RarityEnum.RARE_MORE);
        car5.setMaxLevel(100L);

        Car car6 = new Car();
        car6.setName("Aston Martin V12 Vantage");
        car6.setImage("MW12_Aston_Martin_V12_Vantage.png");
        car6.setNitro(0L);
        car6.setWeight(7L);
        car6.setPotency(4L);
        car6.setControl(1L);
        car6.setToughness(1L);
        car6.setAcceleration(1L);
        car6.setTopSpeed(305L);
        car6.setCarClass(CarClassEnum.SPORT);
        car6.setRarity(RarityEnum.EPIC);
        car6.setMaxLevel(100L);

        Car car7 = new Car();
        car7.setName("Audi A1 Clubsport quattro");
        car7.setImage("MW12_Audi_A1_Clubsport_quattro.png");
        car7.setNitro(0L);
        car7.setWeight(7L);
        car7.setPotency(4L);
        car7.setControl(1L);
        car7.setToughness(1L);
        car7.setAcceleration(1L);
        car7.setTopSpeed(250L);
        car7.setCarClass(CarClassEnum.EVERYDAY);
        car7.setRarity(RarityEnum.EPIC_MORE);
        car7.setMaxLevel(100L);

        Car car8 = new Car();
        car8.setName("Audi R8 GT Spyder");
        car8.setImage("MW12_Audi_R8_GT_Spyder.png");
        car8.setNitro(1L);
        car8.setWeight(7L);
        car8.setPotency(4L);
        car8.setControl(1L);
        car8.setToughness(1L);
        car8.setAcceleration(1L);
        car8.setTopSpeed(317L);
        car8.setCarClass(CarClassEnum.SPORT);
        car8.setRarity(RarityEnum.LEGENDARY);
        car8.setMaxLevel(100L);

        Car car9 = new Car();
        car9.setName("Audi RS3 Sportback");
        car9.setImage("MW12_Audi_RS3_Sportback.jpg");
        car9.setNitro(0L);
        car9.setWeight(7L);
        car9.setPotency(4L);
        car9.setControl(1L);
        car9.setToughness(1L);
        car9.setAcceleration(1L);
        car9.setTopSpeed(318L);
        car9.setCarClass(CarClassEnum.EVERYDAY);
        car9.setRarity(RarityEnum.LEGENDARY_MORE);
        car9.setMaxLevel(100L);

        Car car10 = new Car();
        car10.setName("BAC Mono");
        car10.setImage("MW12_BAC_Mono.png");
        car10.setNitro(0L);
        car10.setWeight(7L);
        car10.setPotency(4L);
        car10.setControl(1L);
        car10.setToughness(1L);
        car10.setAcceleration(1L);
        car10.setTopSpeed(273L);
        car10.setCarClass(CarClassEnum.RACE);
        car10.setRarity(RarityEnum.MYTHICAL);
        car10.setMaxLevel(100L);

        Car car11 = new Car();
        car11.setName("Bentley Continental Supersports Convertible ISR");
        car11.setImage("MW12_Bentley_Continental_Supersports_Convertible_ISR.png");
        car11.setNitro(0L);
        car11.setWeight(7L);
        car11.setPotency(4L);
        car11.setControl(1L);
        car11.setToughness(1L);
        car11.setAcceleration(1L);
        car11.setTopSpeed(325L);
        car11.setCarClass(CarClassEnum.GRAND_TOUR);
        car11.setRarity(RarityEnum.MYTHICAL_MORE);
        car11.setMaxLevel(100L);

        Car car12 = new Car();
        car12.setName("BMW 1 Series M Coupé");
        car12.setImage("MW12_BMW_1_Series_M_Coupe.jpg");
        car12.setNitro(0L);
        car12.setWeight(7L);
        car12.setPotency(4L);
        car12.setControl(1L);
        car12.setToughness(1L);
        car12.setAcceleration(1L);
        car12.setTopSpeed(250L);
        car12.setCarClass(CarClassEnum.EVERYDAY);
        car12.setRarity(RarityEnum.EPIC);
        car12.setMaxLevel(100L);

        Car car13 = new Car();
        car13.setName("BMW M3 (E46) GTR");
        car13.setImage("MW12_BMW_M3_E46_GTR.jpg");
        car13.setNitro(1L);
        car13.setWeight(7L);
        car13.setPotency(4L);
        car13.setControl(1L);
        car13.setToughness(1L);
        car13.setAcceleration(1L);
        car13.setTopSpeed(321L);
        car13.setCarClass(CarClassEnum.RACE);
        car13.setRarity(RarityEnum.ASCENDED);
        car13.setMaxLevel(100L);

        Car car14 = new Car();
        car14.setName("BMW M3 (E92) Coupé");
        car14.setImage("MW12_BMW_M3_E92_Coupe.png");
        car14.setNitro(0L);
        car14.setWeight(7L);
        car14.setPotency(4L);
        car14.setControl(1L);
        car14.setToughness(1L);
        car14.setAcceleration(1L);
        car14.setTopSpeed(259L);
        car14.setCarClass(CarClassEnum.MUSCLE);
        car14.setRarity(RarityEnum.EPIC);
        car14.setMaxLevel(100L);

        Car car15 = new Car();
        car15.setName("Bugatti Veyron 16.4 Grand Sport Vitesse");
        car15.setImage("MW12_Bugatti_Veyron_164_Grand_Sport_Vitesse.png");
        car15.setNitro(0L);
        car15.setWeight(7L);
        car15.setPotency(4L);
        car15.setControl(1L);
        car15.setToughness(1L);
        car15.setAcceleration(1L);
        car15.setTopSpeed(410L);
        car15.setCarClass(CarClassEnum.EXOTIC);
        car15.setRarity(RarityEnum.ASCENDED);
        car15.setMaxLevel(100L);

        Car car16 = new Car();
        car16.setName("Bugatti Veyron 16.4 Super Sport");
        car16.setImage("MW12_Bugatti_Veyron_164_Super_Sport.png");
        car16.setNitro(0L);
        car16.setWeight(7L);
        car16.setPotency(4L);
        car16.setControl(1L);
        car16.setToughness(1L);
        car16.setAcceleration(1L);
        car16.setTopSpeed(431L);
        car16.setCarClass(CarClassEnum.EXOTIC);
        car16.setRarity(RarityEnum.ASCENDED_MORE);
        car16.setMaxLevel(100L);

        Car car17 = new Car();
        car17.setName("Caterham Superlight R500");
        car17.setImage("MW12_Caterham_Superlight_R500.png");
        car17.setNitro(0L);
        car17.setWeight(7L);
        car17.setPotency(4L);
        car17.setControl(1L);
        car17.setToughness(1L);
        car17.setAcceleration(1L);
        car17.setTopSpeed(238L);
        car17.setCarClass(CarClassEnum.RACE);
        car17.setRarity(RarityEnum.COMMON);
        car17.setMaxLevel(100L);

        Car car18 = new Car();
        car18.setName("Chevrolet Camaro ZL1");
        car18.setImage("MW12_Chevrolet_Camaro_ZL1.png");
        car18.setNitro(0L);
        car18.setWeight(7L);
        car18.setPotency(4L);
        car18.setControl(1L);
        car18.setToughness(1L);
        car18.setAcceleration(1L);
        car18.setTopSpeed(296L);
        car18.setCarClass(CarClassEnum.MUSCLE);
        car18.setRarity(RarityEnum.COMMON);
        car18.setMaxLevel(100L);

        Car car19 = new Car();
        car19.setName("Chevrolet Corvette ZR1");
        car19.setImage("MW12_Chevrolet_Corvette_ZR1.png");
        car19.setNitro(0L);
        car19.setWeight(7L);
        car19.setPotency(4L);
        car19.setControl(1L);
        car19.setToughness(1L);
        car19.setAcceleration(1L);
        car19.setTopSpeed(330L);
        car19.setCarClass(CarClassEnum.EXOTIC);
        car19.setRarity(RarityEnum.COMMON);
        car19.setMaxLevel(100L);

        Car car20 = new Car();
        car20.setName("Dodge Challenger SRT-8 392");
        car20.setImage("MW12_Dodge_Challenger_SRT8_392.png");
        car20.setNitro(0L);
        car20.setWeight(7L);
        car20.setPotency(4L);
        car20.setControl(1L);
        car20.setToughness(1L);
        car20.setAcceleration(1L);
        car20.setTopSpeed(293L);
        car20.setCarClass(CarClassEnum.MUSCLE);
        car20.setRarity(RarityEnum.COMMON);
        car20.setMaxLevel(100L);

        Car car21 = new Car();
        car21.setName("Dodge Charger R/T");
        car21.setImage("MW12_Dodge_Charger_RT.jpg");
        car21.setNitro(0L);
        car21.setWeight(7L);
        car21.setPotency(4L);
        car21.setControl(1L);
        car21.setToughness(1L);
        car21.setAcceleration(1L);
        car21.setTopSpeed(249L);
        car21.setCarClass(CarClassEnum.MUSCLE);
        car21.setRarity(RarityEnum.COMMON);
        car21.setMaxLevel(100L);

        Car car22 = new Car();
        car22.setName("Dodge Charger SRT-8");
        car22.setImage("MW12_Dodge_Charger_SRT8.jpg");
        car22.setNitro(0L);
        car22.setWeight(7L);
        car22.setPotency(4L);
        car22.setControl(1L);
        car22.setToughness(1L);
        car22.setAcceleration(1L);
        car22.setTopSpeed(281L);
        car22.setCarClass(CarClassEnum.MUSCLE);
        car22.setRarity(RarityEnum.COMMON);
        car22.setMaxLevel(100L);

        Car car23 = new Car();
        car23.setName("Ford F-150 SVT Raptor");
        car23.setImage("MW12_Ford_F150_SVT_Raptor.png");
        car23.setNitro(0L);
        car23.setWeight(7L);
        car23.setPotency(4L);
        car23.setControl(1L);
        car23.setToughness(1L);
        car23.setAcceleration(1L);
        car23.setTopSpeed(164L);
        car23.setCarClass(CarClassEnum.SUV);
        car23.setRarity(RarityEnum.COMMON);
        car23.setMaxLevel(100L);

        Car car24 = new Car();
        car24.setName("Ford Fiesta ST");
        car24.setImage("MW12_Ford_Fiesta_ST.jpg");
        car24.setNitro(0L);
        car24.setWeight(7L);
        car24.setPotency(4L);
        car24.setControl(1L);
        car24.setToughness(1L);
        car24.setAcceleration(1L);
        car24.setTopSpeed(207L);
        car24.setCarClass(CarClassEnum.EVERYDAY);
        car24.setRarity(RarityEnum.COMMON);
        car24.setMaxLevel(100L);

        Car car25 = new Car();
        car25.setName("Ford Focus RS500");
        car25.setImage("MW12_Ford_Focus_RS500.png");
        car25.setNitro(0L);
        car25.setWeight(7L);
        car25.setPotency(4L);
        car25.setControl(1L);
        car25.setToughness(1L);
        car25.setAcceleration(1L);
        car25.setTopSpeed(265L);
        car25.setCarClass(CarClassEnum.EVERYDAY);
        car25.setRarity(RarityEnum.COMMON);
        car25.setMaxLevel(100L);

        Car car26 = new Car();
        car26.setName("Ford Focus ST");
        car26.setImage("MW12_Ford_Focus_ST.png");
        car26.setNitro(0L);
        car26.setWeight(7L);
        car26.setPotency(4L);
        car26.setControl(1L);
        car26.setToughness(1L);
        car26.setAcceleration(1L);
        car26.setTopSpeed(247L);
        car26.setCarClass(CarClassEnum.EVERYDAY);
        car26.setRarity(RarityEnum.COMMON);
        car26.setMaxLevel(100L);

        Car car27 = new Car();
        car27.setName("Ford GT");
        car27.setImage("MW12_Ford_GT.png");
        car27.setNitro(0L);
        car27.setWeight(7L);
        car27.setPotency(4L);
        car27.setControl(1L);
        car27.setToughness(1L);
        car27.setAcceleration(1L);
        car27.setTopSpeed(341L);
        car27.setCarClass(CarClassEnum.EXOTIC);
        car27.setRarity(RarityEnum.COMMON);
        car27.setMaxLevel(100L);

        Car car28 = new Car();
        car28.setName("Ford Mustang Boss 302");
        car28.setImage("MW12_Ford_Mustang_Boss_302.png");
        car28.setNitro(0L);
        car28.setWeight(7L);
        car28.setPotency(4L);
        car28.setControl(1L);
        car28.setToughness(1L);
        car28.setAcceleration(1L);
        car28.setTopSpeed(249L);
        car28.setCarClass(CarClassEnum.MUSCLE);
        car28.setRarity(RarityEnum.COMMON);
        car28.setMaxLevel(100L);

        Car car29 = new Car();
        car29.setName("Hennessey Venom GT Spyder");
        car29.setImage("MW12_Hennessey_Venom_GT_Spyder.png");
        car29.setNitro(0L);
        car29.setWeight(7L);
        car29.setPotency(4L);
        car29.setControl(1L);
        car29.setToughness(1L);
        car29.setAcceleration(1L);
        car29.setTopSpeed(427L);
        car29.setCarClass(CarClassEnum.EXOTIC);
        car29.setRarity(RarityEnum.ASCENDED_MORE);
        car29.setMaxLevel(100L);

        Car car30 = new Car();
        car30.setName("Jaguar XKR");
        car30.setImage("MW12_Jaguar_XKR.png");
        car30.setNitro(0L);
        car30.setWeight(7L);
        car30.setPotency(4L);
        car30.setControl(1L);
        car30.setToughness(1L);
        car30.setAcceleration(1L);
        car30.setTopSpeed(250L);
        car30.setCarClass(CarClassEnum.SPORT);
        car30.setRarity(RarityEnum.COMMON);
        car30.setMaxLevel(100L);

        Car car31 = new Car();
        car31.setName("Koenigsegg Agera R");
        car31.setImage("MW12_Koenigsegg_Agera_R.png");
        car31.setNitro(0L);
        car31.setWeight(7L);
        car31.setPotency(4L);
        car31.setControl(1L);
        car31.setToughness(1L);
        car31.setAcceleration(1L);
        car31.setTopSpeed(420L);
        car31.setCarClass(CarClassEnum.EXOTIC);
        car31.setRarity(RarityEnum.ASCENDED);
        car31.setMaxLevel(100L);

        Car car32 = new Car();
        car32.setName("Lamborghini Aventador J");
        car32.setImage("MW12_Lamborghini_Aventador_J.png");
        car32.setNitro(0L);
        car32.setWeight(7L);
        car32.setPotency(4L);
        car32.setControl(1L);
        car32.setToughness(1L);
        car32.setAcceleration(1L);
        car32.setTopSpeed(340L);
        car32.setCarClass(CarClassEnum.EXOTIC);
        car32.setRarity(RarityEnum.LEGENDARY);
        car32.setMaxLevel(100L);

        Car car33 = new Car();
        car33.setName("Lamborghini Aventador LP 700-4");
        car33.setImage("MW12_Lamborghini_Aventador_LP_700_4.png");
        car33.setNitro(0L);
        car33.setWeight(7L);
        car33.setPotency(4L);
        car33.setControl(1L);
        car33.setToughness(1L);
        car33.setAcceleration(1L);
        car33.setTopSpeed(350L);
        car33.setCarClass(CarClassEnum.EXOTIC);
        car33.setRarity(RarityEnum.LEGENDARY);
        car33.setMaxLevel(100L);

        Car car34 = new Car();
        car34.setName("Lamborghini Countach 5000 quattrovalvole");
        car34.setImage("MW12_Lamborghini_Countach_5000_quattrovalvolve.png");
        car34.setNitro(0L);
        car34.setWeight(7L);
        car34.setPotency(4L);
        car34.setControl(1L);
        car34.setToughness(1L);
        car34.setAcceleration(1L);
        car34.setTopSpeed(293L);
        car34.setCarClass(CarClassEnum.SPORT);
        car34.setRarity(RarityEnum.COMMON);
        car34.setMaxLevel(100L);

        Car car35 = new Car();
        car35.setName("Lamborghini Diablo SV");
        car35.setImage("MW12_Lamborghini_Diablo_SV.jpg");
        car35.setNitro(0L);
        car35.setWeight(7L);
        car35.setPotency(4L);
        car35.setControl(1L);
        car35.setToughness(1L);
        car35.setAcceleration(1L);
        car35.setTopSpeed(328L);
        car35.setCarClass(CarClassEnum.SPORT);
        car35.setRarity(RarityEnum.COMMON);
        car35.setMaxLevel(100L);

        Car car36 = new Car();
        car36.setName("Lamborghini Gallardo LP 570-4 Spyder Performante");
        car36.setImage("MW12_Lamborghini_Gallardo_LP_570_4_Spyder_Perfomante.png");
        car36.setNitro(0L);
        car36.setWeight(7L);
        car36.setPotency(4L);
        car36.setControl(1L);
        car36.setToughness(1L);
        car36.setAcceleration(1L);
        car36.setTopSpeed(324L);
        car36.setCarClass(CarClassEnum.SPORT);
        car36.setRarity(RarityEnum.COMMON);
        car36.setMaxLevel(100L);

        Car car37 = new Car();
        car37.setName("Lancia Delta HF Integrale Evoluzione II");
        car37.setImage("MW12_Lancia_Delta_HF_Integrale_Evoluzione.png");
        car37.setNitro(0L);
        car37.setWeight(7L);
        car37.setPotency(4L);
        car37.setControl(1L);
        car37.setToughness(1L);
        car37.setAcceleration(1L);
        car37.setTopSpeed(220L);
        car37.setCarClass(CarClassEnum.EVERYDAY);
        car37.setRarity(RarityEnum.COMMON);
        car37.setMaxLevel(100L);

        Car car38 = new Car();
        car38.setName("Lexus LFA");
        car38.setImage("MW12_Lexus_LFA.png");
        car38.setNitro(0L);
        car38.setWeight(7L);
        car38.setPotency(4L);
        car38.setControl(1L);
        car38.setToughness(1L);
        car38.setAcceleration(1L);
        car38.setTopSpeed(326L);
        car38.setCarClass(CarClassEnum.EXOTIC);
        car38.setRarity(RarityEnum.COMMON);
        car38.setMaxLevel(100L);

        Car car39 = new Car();
        car39.setName("MW12_Lexus_LFA.png");
        car39.setImage("MW12_Marussia_B2.png");
        car39.setNitro(0L);
        car39.setWeight(7L);
        car39.setPotency(4L);
        car39.setControl(1L);
        car39.setToughness(1L);
        car39.setAcceleration(1L);
        car39.setTopSpeed(250L);
        car39.setCarClass(CarClassEnum.SPORT);
        car39.setRarity(RarityEnum.COMMON);
        car39.setMaxLevel(100L);

        Car car40 = new Car();
        car40.setName("Maserati GranTurismo MC Stradale");
        car40.setImage("MW12_Maserati_GranTurismo_MC_Stradale.png");
        car40.setNitro(0L);
        car40.setWeight(7L);
        car40.setPotency(4L);
        car40.setControl(1L);
        car40.setToughness(1L);
        car40.setAcceleration(1L);
        car40.setTopSpeed(301L);
        car40.setCarClass(CarClassEnum.GRAND_TOUR);
        car40.setRarity(RarityEnum.COMMON);
        car40.setMaxLevel(100L);

        Car car41 = new Car();
        car41.setName("McLaren F1 LM");
        car41.setImage("MW12_McLaren_F1_LM.png");
        car41.setNitro(0L);
        car41.setWeight(7L);
        car41.setPotency(4L);
        car41.setControl(1L);
        car41.setToughness(1L);
        car41.setAcceleration(1L);
        car41.setTopSpeed(362L);
        car41.setCarClass(CarClassEnum.EXOTIC);
        car41.setRarity(RarityEnum.COMMON);
        car41.setMaxLevel(100L);

        Car car42 = new Car();
        car42.setName("McLaren MP4-12C");
        car42.setImage("MW12_McLaren_MP4_12C.png");
        car42.setNitro(0L);
        car42.setWeight(7L);
        car42.setPotency(4L);
        car42.setControl(1L);
        car42.setToughness(1L);
        car42.setAcceleration(1L);
        car42.setTopSpeed(330L);
        car42.setCarClass(CarClassEnum.EXOTIC);
        car42.setRarity(RarityEnum.COMMON);
        car42.setMaxLevel(100L);

        Car car43 = new Car();
        car43.setName("Mercedes-Benz SL65 AMG Black Series");
        car43.setImage("MW12_Mercedes_Benz_SL65_AMG_Black_Series.png");
        car43.setNitro(0L);
        car43.setWeight(7L);
        car43.setPotency(4L);
        car43.setControl(1L);
        car43.setToughness(1L);
        car43.setAcceleration(1L);
        car43.setTopSpeed(320L);
        car43.setCarClass(CarClassEnum.MUSCLE);
        car43.setRarity(RarityEnum.COMMON);
        car43.setMaxLevel(100L);

        Car car44 = new Car();
        car44.setName("Mercedes-Benz SLS AMG");
        car44.setImage("MW12_Mercedes_Benz_SLS_AMG.png");
        car44.setNitro(0L);
        car44.setWeight(7L);
        car44.setPotency(4L);
        car44.setControl(1L);
        car44.setToughness(1L);
        car44.setAcceleration(1L);
        car44.setTopSpeed(318L);
        car44.setCarClass(CarClassEnum.GRAND_TOUR);
        car44.setRarity(RarityEnum.COMMON);
        car44.setMaxLevel(100L);

        Car car45 = new Car();
        car45.setName("Mitsubishi Lancer Evolution X");
        car45.setImage("MW12_Mitsubishi_Lancer_Evolution_X.png");
        car45.setNitro(0L);
        car45.setWeight(7L);
        car45.setPotency(4L);
        car45.setControl(1L);
        car45.setToughness(1L);
        car45.setAcceleration(1L);
        car45.setTopSpeed(250L);
        car45.setCarClass(CarClassEnum.EVERYDAY);
        car45.setRarity(RarityEnum.COMMON);
        car45.setMaxLevel(100L);

        Car car46 = new Car();
        car46.setName("Nissan 350Z (Z33");
        car46.setImage("MW12_Nissan_350Z_Z33.jpg");
        car46.setNitro(0L);
        car46.setWeight(7L);
        car46.setPotency(4L);
        car46.setControl(1L);
        car46.setToughness(1L);
        car46.setAcceleration(1L);
        car46.setTopSpeed(250L);
        car46.setCarClass(CarClassEnum.SPORT);
        car46.setRarity(RarityEnum.COMMON);
        car46.setMaxLevel(100L);

        Car car47 = new Car();
        car47.setName("Nissan GT-R (R35) Egoist");
        car47.setImage("MW12_Nissan_GTR_R35_Egoist.png");
        car47.setNitro(0L);
        car47.setWeight(7L);
        car47.setPotency(4L);
        car47.setControl(1L);
        car47.setToughness(1L);
        car47.setAcceleration(1L);
        car47.setTopSpeed(313L);
        car47.setCarClass(CarClassEnum.SPORT);
        car47.setRarity(RarityEnum.COMMON);
        car47.setMaxLevel(100L);

        Car car48 = new Car();
        car48.setName("Nissan Skyline GT-R (R34)");
        car48.setImage("MW12_Nissan_Skyline_GTR_R34.jpg");
        car48.setNitro(0L);
        car48.setWeight(7L);
        car48.setPotency(4L);
        car48.setControl(1L);
        car48.setToughness(1L);
        car48.setAcceleration(1L);
        car48.setTopSpeed(250L);
        car48.setCarClass(CarClassEnum.SPORT);
        car48.setRarity(RarityEnum.COMMON);
        car48.setMaxLevel(100L);

        Car car49 = new Car();
        car49.setName("Pagani Huayra");
        car49.setImage("MW12_Pagani_Huayra.png");
        car49.setNitro(0L);
        car49.setWeight(7L);
        car49.setPotency(4L);
        car49.setControl(1L);
        car49.setToughness(1L);
        car49.setAcceleration(1L);
        car49.setTopSpeed(370L);
        car49.setCarClass(CarClassEnum.EXOTIC);
        car49.setRarity(RarityEnum.COMMON);
        car49.setMaxLevel(100L);

        Car car50 = new Car();
        car50.setName("Pagani Zonda R");
        car50.setImage("MW12_Pagani_Zonda_R.png");
        car50.setNitro(0L);
        car50.setWeight(7L);
        car50.setPotency(4L);
        car50.setControl(1L);
        car50.setToughness(1L);
        car50.setAcceleration(1L);
        car50.setTopSpeed(350L);
        car50.setCarClass(CarClassEnum.EXOTIC);
        car50.setRarity(RarityEnum.COMMON);
        car50.setMaxLevel(100L);

        Car car51 = new Car();
        car51.setName("Pontiac Firebird Trans-Am Special Edition");
        car51.setImage("MW12_Pontiac_Firebird_Trans_Am_Special_Edition.jpg");
        car51.setNitro(0L);
        car51.setWeight(7L);
        car51.setPotency(4L);
        car51.setControl(1L);
        car51.setToughness(1L);
        car51.setAcceleration(1L);
        car51.setTopSpeed(230L);
        car51.setCarClass(CarClassEnum.MUSCLE);
        car51.setRarity(RarityEnum.COMMON);
        car51.setMaxLevel(100L);

        Car car52 = new Car();
        car52.setName("Porsche 911 (930) Carrera Turbo");
        car52.setImage("MW12_Porsche_930_Carrera_Turbo.png");
        car52.setNitro(0L);
        car52.setWeight(7L);
        car52.setPotency(4L);
        car52.setControl(1L);
        car52.setToughness(1L);
        car52.setAcceleration(1L);
        car52.setTopSpeed(246L);
        car52.setCarClass(CarClassEnum.SPORT);
        car52.setRarity(RarityEnum.COMMON);
        car52.setMaxLevel(100L);

        Car car53 = new Car();
        car53.setName("Porsche 911 (991) Carrera S");
        car53.setImage("MW12_Porsche_991_Carrera_S.png");
        car53.setNitro(0L);
        car53.setWeight(7L);
        car53.setPotency(4L);
        car53.setControl(1L);
        car53.setToughness(1L);
        car53.setAcceleration(1L);
        car53.setTopSpeed(300L);
        car53.setCarClass(CarClassEnum.SPORT);
        car53.setRarity(RarityEnum.COMMON);
        car53.setMaxLevel(100L);

        Car car54 = new Car();
        car54.setName("Porsche 911 (997) GT2 RS");
        car54.setImage("MW12_Porsche_911_997_GT2_RS.jpg");
        car54.setNitro(0L);
        car54.setWeight(7L);
        car54.setPotency(4L);
        car54.setControl(1L);
        car54.setToughness(1L);
        car54.setAcceleration(1L);
        car54.setTopSpeed(330L);
        car54.setCarClass(CarClassEnum.SPORT);
        car54.setRarity(RarityEnum.COMMON);
        car54.setMaxLevel(100L);

        Car car55 = new Car();
        car55.setName("Porsche 918 Spyder");
        car55.setImage("MW12_Porsche_918_Spyder.jpg");
        car55.setNitro(0L);
        car55.setWeight(7L);
        car55.setPotency(4L);
        car55.setControl(1L);
        car55.setToughness(1L);
        car55.setAcceleration(1L);
        car55.setTopSpeed(325L);
        car55.setCarClass(CarClassEnum.EXOTIC);
        car55.setRarity(RarityEnum.COMMON);
        car55.setMaxLevel(100L);

        Car car56 = new Car();
        car56.setName("Porsche 918 Spyder Concept");
        car56.setImage("MW12_Porsche_918_Spyder_Concept.png");
        car56.setNitro(0L);
        car56.setWeight(7L);
        car56.setPotency(4L);
        car56.setControl(1L);
        car56.setToughness(1L);
        car56.setAcceleration(1L);
        car56.setTopSpeed(318L);
        car56.setCarClass(CarClassEnum.EXOTIC);
        car56.setRarity(RarityEnum.COMMON);
        car56.setMaxLevel(100L);

        Car car57 = new Car();
        car57.setName("Porsche Panamera Turbo S");
        car57.setImage("MW12_Porsche_Panamera_Turbo_S.png");
        car57.setNitro(0L);
        car57.setWeight(7L);
        car57.setPotency(4L);
        car57.setControl(1L);
        car57.setToughness(1L);
        car57.setAcceleration(1L);
        car57.setTopSpeed(305L);
        car57.setCarClass(CarClassEnum.SPORT);
        car57.setRarity(RarityEnum.COMMON);
        car57.setMaxLevel(100L);

        Car car58 = new Car();
        car58.setName("Range Rover Evoque");
        car58.setImage("MW12_Range_Rover_Evoque.png");
        car58.setNitro(0L);
        car58.setWeight(7L);
        car58.setPotency(4L);
        car58.setControl(1L);
        car58.setToughness(1L);
        car58.setAcceleration(1L);
        car58.setTopSpeed(217L);
        car58.setCarClass(CarClassEnum.SUV);
        car58.setRarity(RarityEnum.COMMON);
        car58.setMaxLevel(100L);

        Car car59 = new Car();
        car59.setName("Shelby Cobra 427");
        car59.setImage("MW12_Shelby_Cobra_427.png");
        car59.setNitro(0L);
        car59.setWeight(7L);
        car59.setPotency(4L);
        car59.setControl(1L);
        car59.setToughness(1L);
        car59.setAcceleration(1L);
        car59.setTopSpeed(257L);
        car59.setCarClass(CarClassEnum.MUSCLE);
        car59.setRarity(RarityEnum.COMMON);
        car59.setMaxLevel(100L);

        Car car60 = new Car();
        car60.setName("Shelby Mustang GT500");
        car60.setImage("MW12_Shelby_Mustang_GT500.jpg");
        car60.setNitro(0L);
        car60.setWeight(7L);
        car60.setPotency(4L);
        car60.setControl(1L);
        car60.setToughness(1L);
        car60.setAcceleration(1L);
        car60.setTopSpeed(214L);
        car60.setCarClass(CarClassEnum.MUSCLE);
        car60.setRarity(RarityEnum.COMMON);
        car60.setMaxLevel(100L);

        Car car61 = new Car();
        car61.setName("SRT Viper GTS");
        car61.setImage("MW12_SRT_Viper_GTS.png");
        car61.setNitro(0L);
        car61.setWeight(7L);
        car61.setPotency(4L);
        car61.setControl(1L);
        car61.setToughness(1L);
        car61.setAcceleration(1L);
        car61.setTopSpeed(335L);
        car61.setCarClass(CarClassEnum.MUSCLE);
        car61.setRarity(RarityEnum.COMMON);
        car61.setMaxLevel(100L);

        Car car62 = new Car();
        car62.setName("Subaru Impreza Cosworth STI CS400");
        car62.setImage("MW12_Subaru_Impreza_Cosworth_STI_CS400.png");
        car62.setNitro(0L);
        car62.setWeight(7L);
        car62.setPotency(4L);
        car62.setControl(1L);
        car62.setToughness(1L);
        car62.setAcceleration(1L);
        car62.setTopSpeed(250L);
        car62.setCarClass(CarClassEnum.EVERYDAY);
        car62.setRarity(RarityEnum.COMMON);
        car62.setMaxLevel(100L);

        Car car63 = new Car();
        car63.setName("Tesla Roadster Sport");
        car63.setImage("MW12_Tesla_Roadster_Sport.png");
        car63.setNitro(0L);
        car63.setWeight(7L);
        car63.setPotency(4L);
        car63.setControl(1L);
        car63.setToughness(1L);
        car63.setAcceleration(1L);
        car63.setTopSpeed(201L);
        car63.setCarClass(CarClassEnum.SPORT);
        car63.setRarity(RarityEnum.COMMON);
        car63.setMaxLevel(100L);

        carRepository.saveAll(List.of(
                car1,
                car2,
                car3,
                car4,
                car5,
                car6,
                car7,
                car8,
                car9,
                car10,
                car11,
                car12,
                car13,
                car14,
                car15,
                car16,
                car17,
                car18,
                car19,
                car20,
                car21,
                car22,
                car23,
                car24,
                car25,
                car26,
                car27,
                car28,
                car29,
                car30,
                car31,
                car32,
                car33,
                car34,
                car35,
                car36,
                car37,
                car38,
                car39,
                car40,
                car41,
                car42,
                car43,
                car44,
                car45,
                car46,
                car47,
                car48,
                car49,
                car50,
                car51,
                car52,
                car53,
                car54,
                car55,
                car56,
                car57,
                car58,
                car59,
                car60,
                car61,
                car62,
                car63
        ));
    }
}
