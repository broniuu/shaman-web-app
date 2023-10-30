package com.example.shamanApi.data;

import com.example.shamanApi.model.*;
import com.example.shamanApi.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

/**
 * Klasa umożliwia wypełnienie bazy odpowiednimi danymi
 */
public class Seed {

    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public Seed(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository,
                RestaurantRepository restaurantRepository,
                CartItemRepository cartItemRepository,
                DishRepository dishRepository, DiscountRepository discountRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.cartItemRepository = cartItemRepository;
        this.dishRepository = dishRepository;
        this.discountRepository = discountRepository;
    }

    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;
    private CartItemRepository cartItemRepository;
    private DishRepository dishRepository;
    private DiscountRepository discountRepository;

    /**
     * Wypełnia baze danymi o restauracjach, daniach oraz zniżkach
     */
    public void seedData() {
        List<Restaurant> restaurants;
        if (restaurantRepository.count() == 0){
        restaurants = List.of(
                new Restaurant("Restauracja u Jana" ,
                        "https://restauracjaujana.pl/wp-content/uploads/2018/03/logo-text.png",
                        "https://media.istockphoto.com/id/1204371265/photo/flat-lay-of-turkish-traditional-foods-for-celebrating-holiday-wode-composition.jpg?s=612x612&w=0&k=20&c=X-9XA8TIOe-GxtYnojNLUfu-_rXR1Zab1GYqAu1ne64="
                        , 3,
                        "Piłsudzkiego",
                        "23",
                        "Tarnów"),
                new Restaurant(
                        "American Dream" ,
                        "https://www.americanrestaurant.pl/wp-content/uploads/2019/01/american-restaurant-logo-footer-new-1.png",
                        "https://media.istockphoto.com/id/1160937956/photo/flat-lay-of-meat-salads-snacks-and-blooming-mimosa-wide-composition.jpg?s=612x612&w=0&k=20&c=cscX-CM4VFli1FHgL4OSFFl0glgSnUX0Q8fES6KU2oA=",
                        4,
                        "Wałowa",
                        "23",
                        "Tarnów"),
                new Restaurant(
                        "Bistro Przepis" ,
                        "https://restaumatic-production.imgix.net/uploads/restaurants/84699/logo/1629964980.png?auto=compress&crop=focalpoint&fit=clip&h=500&w=500",
                        "https://burst.shopifycdn.com/photos/flatlay-iron-skillet-with-meat-and-other-food.jpg?width=1200&format=pjpg&exif=1&iptc=1",
                        5,
                        "Słowackiego",
                       "1",
                        "Tarnów"),
                new Restaurant(
                        "Domowa Bistro" ,
                        "https://zakupersi.com/wp-content/uploads/2020/04/glovo.png",
                        "https://media.istockphoto.com/id/1155240408/photo/table-filled-with-large-variety-of-food.jpg?s=612x612&w=0&k=20&c=uJEbKmR3wOxwdhQR_36as5WeP6_HDqfU-QmAq63OVEE=",
                        4,
                        "Krakowska",
                        "15",
                        "Tarnów"),
                new Restaurant(
                        "Restauracja Pyza" ,
                        "https://restaumatic-production.imgix.net/uploads/restaurants/35383/logo/1591080813.png?auto=compress&crop=focalpoint&fit=max&h=200&w=200",
                        "https://burst.shopifycdn.com/photos/flatlay-iron-skillet-with-meat-and-other-food.jpg?width=1200&format=pjpg&exif=1&iptc=1",
                        2,
                        "Rozwojowa",
                        "22",
                        "Tarnów"),
                new Restaurant(
                        "Restauracja Soprano" ,
                        "https://restaumatic-production.imgix.net/uploads/restaurants/17770/logo/1507737005.png?auto=compress&crop=focalpoint&fit=clip&h=500&w=500",
                        "https://thumbs.dreamstime.com/b/assorted-indian-recipes-food-various-spices-rice-wooden-table-92742528.jpg",
                        5,
                        "Narutowicza",
                        "4",
                        "Tarnów"),
                new Restaurant(
                        "Restauracja Różana" ,
                        "http://www.rozana-batorego19.pl/wp-content/uploads/2020/02/81894919_110245320506614_5467250534652051456_o-300x254.jpg",
                        "https://img.freepik.com/premium-photo/healthy-food-clean-eating-selection_79782-19.jpg",
                        3,
                        "Mickiewicza",
                        "8",
                        "Tarnów"),
                new Restaurant(
                        "Restauracja Pół na Pół" ,
                        "https://polnapol-tarnow.pl/wp/wp-content/uploads/2020/08/pnp_logo_2020-sierpien.png",
                        "https://thumbs.dreamstime.com/b/healthy-food-selection-healthy-food-selection-fruits-vegetables-seeds-superfood-cereals-gray-background-121936825.jpg",
                        4,
                       "Dworcowa",
                        "8",
                        "Tarnów")
        );
            restaurantRepository.saveAll(restaurants);
        } else {
            restaurants = (List<Restaurant>) restaurantRepository.findAll();
        }
        List<Dish> dishes;
        if (dishRepository.count() == 0) {
            Restaurant firstRestaurant = restaurants.get(0);
            Restaurant secondRestaurant = restaurants.get(1);
            Restaurant thirdRestaurant = restaurants.get(2);
            Restaurant fourthRestaurant = restaurants.get(3);
            Restaurant fifthRestaurant = restaurants.get(4);
            Restaurant sixthRestaurant = restaurants.get(5);
            Restaurant seventhRestaurant = restaurants.get(6);
            Restaurant eighthRestaurant = restaurants.get(7);
            dishes = List.of(
                    new Dish(
                            "STEK Z POLĘDWICY WOŁOWEJ",
                            "warzywami pieczonymi w miodzie, z sosem pieprzowym",
                            75.0,
                            "https://static.gotujmy.pl/ZDJECIE_PRZEPISU_ETAP/steak-z-poledwicy-wolowej-526206.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "KOTLET DE VOLAILLE",
                            "Kotlet de Volaille z frytkami i surówką",
                            28.00,
                            "https://bi.im-g.pl/im/99/c5/18/z25975449AMP,Kotlet-de-volaille-z-maslem-i-czosnkiem---klasyk-n.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "KOTLET SCHABOWY",
                            "Kotlet schabowy z ziemniakami i kapustą zasmażana",
                            29.00,
                            "https://sklep.skansensmakow.pl/wp-content/uploads/2020/10/Kotelt-schabowy-z-zeimniakami-i-kapust%C4%85.jpeg",
                            firstRestaurant
                    ),
                    new Dish(
                            "SCHAB XL",
                            "Podwójny kotlet schabowy z ziemniakami i kapustą zasmażaną",
                            39.00,
                            "https://media-cdn.tripadvisor.com/media/photo-s/17/68/fd/fa/kotlet-schabowy-z-kapusta.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "FILET Z KURCZAKA",
                            "Filet z kurczaka w szynce serano , nadziewany parmezanem z sosem kurkowym , ziemniaki pure i warzywami pieczonymi w miodzie",
                            32.00,
                            "https://app.e-lunch.pl/images/c17513863ee0f4a0ac99c6459dc1cf7a.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "ŻEBERKA SZLACHECKIE W SOSIE BBQ",
                            "Żeberka wieprzowe z kapusta zasmażana i opiekanymi ziemniaczkami",
                            39.00,
                            "https://filozofiasmaku.pl/wp-content/uploads/2015/09/zeberkabbqprzepis.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "TAGLIATELLE Z KREWETKAMI KRÓLEWSKIMI",
                            "Czarny makaron spaghetti z krewetkami królewskimi z sosem śmietanowym z czerwonej papryki, suszonymi pomidorami, parmezanem, rukolą",
                            39.00,
                            "https://kuchniabreni.pl/wp-content/uploads/2014/04/czarny-007.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "DOMOWE PIEROGI RUSKIE",
                            "Pierogi z nadzieniem ziemniaczanym i białym serem",
                            20.00,
                            "https://domowe-potrawy.pl/media/2017/09/pierogi-ruskie.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "ARANCINI",
                            "Panierowane rizotto z pieczarkami i serem mozzarella podane na sosie pomidorowym, pieczona rukola",
                            16.00,
                            "https://assets.tmecosys.com/image/upload/t_web767x639/img/recipe/vimdb/22634.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "PSTRĄG Z PIECA",
                            "Pstrąg w całości z warzywami z patelni i frytkami",
                            38.00,
                            "https://static.smaker.pl/photos/1/b/1/1b1486deff1e2d02f655dce60126b71e_360821_5a9c400a29595_wm.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "KREWETKI KRÓLEWSKIE",
                            "Krewetki królewskie z masłem czosnkowo cytrynowym, pomidorem koktajlowym podane na grilowanej sałacie z ciabatą",
                            29.00,
                            "https://polki.pl/foto/16_9_LARGE/jak-smazyc-krewetki-sprawdzone-porady-2463213.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "CHEESE BURGER",
                            "Mięso wołowe, ser cheddar, majonez, sos bbq, sałata, pomidor, krążki cebulowe, ogórki konserwowe frytki",
                            35.00,
                            "https://cdn.mcdonalds.pl/uploads/20201125090250/mcroyal2.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "SKRZYDEŁKA Z KURCZAKA",
                            "Skrzydełka z kurczaka w sosie bbq podane z chrupiącą ciabatą",
                            26.00,
                            "https://media.kaufland.com/images/PPIM/AP_Content_1010/std.lang.all/70/21/Asset_9007021.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "KRĄŻKI CEBULOWE",
                            "Panierowane krążki cebulowe z sosem do wyboru",
                            13.00,
                            "https://d2oppgudvj7n6y.cloudfront.net/recipes/k/kra/krazki-cebulowe-w-chrupiacej-panierce-z-ketchupowa-salsa/adobestock_156958783_easy_resize_com-large.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "PIECZONE ZIEMNIACZKI",
                            "Półksiężyce ze śmietaną i sosem meksykańskim",
                            16.00,
                            "https://e-przepisykulinarne.pl/wp-content/uploads/2013/07/ziemniaki_smietana_sos.jpg",
                            firstRestaurant
                    ),
                    new Dish(
                            "KLASYCZNY BURGER",
                            "Wołowina, korniszon, pomidor, cebula czerwona, mix sałat, majonez sos BBQ",
                            30.00,
                            "https://cdn.upmenu.com/static/product-images/a52b5f5f-bb1f-11eb-a1e9-525400080521/9df71969-bc95-11eb-a1e9-525400080521/2/huge/b-ham.png",
                            secondRestaurant
                    ),
                    new Dish(
                            "BURGER BBQ",
                            "Wołowina, chrupiący bacon, ser chedar, mix sałat, pomidor, korniszon, czerwona cebula, sos BBQ",
                            33.00,
                            "https://cdn.upmenu.com/static/product-images/a52b5f5f-bb1f-11eb-a1e9-525400080521/bacafa92-bc95-11eb-a1e9-525400080521/2/huge/b-bacon.png",
                            secondRestaurant
                    ),
                    new Dish(
                            "AMERICAN DREAM BURGER",
                            "Wołowina, panierowana mozarella, pomidor, mix sałat, cebula, korniszon, majonez, sos BBQ",
                            39.00,
                            "https://cdn.upmenu.com/static/product-images/a52b5f5f-bb1f-11eb-a1e9-525400080521/85ad29e5-bc95-11eb-a1e9-525400080521/2/huge/b-premium.png",
                            secondRestaurant
                    ),
                    new Dish(
                            "FARMER BURGER",
                            "Wołowina, pieczarki, kiełbasa, jajko, pomidor, mix sałat, cebula, korniszon, majonez",
                            34.00,
                            "https://cdn.upmenu.com/static/product-images/a52b5f5f-bb1f-11eb-a1e9-525400080521/0b1b6568-bc96-11eb-a1e9-525400080521/2/huge/b-pari.png",
                            secondRestaurant
                    ),
                    new Dish(
                            "BACA BURGER",
                            "Wołowina, pomidor, mix sałat, czerwona cebula, korniszon, bacon, oscypek, żurawina, sos BBQ",
                            33.00,
                            "https://cdn.upmenu.com/static/product-images/a52b5f5f-bb1f-11eb-a1e9-525400080521/f55ba954-bc95-11eb-a1e9-525400080521/2/huge/b-stark.png",
                            secondRestaurant
                    ),
                    new Dish(
                            "CHICKEN BURGER",
                            "Grillowany kurczak, grillowany ananas, ser chedar, pomidor, mix sałat, korniszon, kukurydza, cebula, sos musztardowo - miodowy",
                            28.00,
                            "https://cdn.upmenu.com/static/product-images/a52b5f5f-bb1f-11eb-a1e9-525400080521/e721bff0-bc95-11eb-a1e9-525400080521/2/huge/b-chicken.png",
                            secondRestaurant
                    ),
                    new Dish(
                            "BURGER BOX",
                            "Wołowina, frytki, bacon, ser mozzarella, jajko sadzone, pomidor, mix sałat, korniszon, kukurydza, cebula, sos do wyboru",
                            39.00,
                            "https://picburger.pl/wp-content/uploads/2020/09/bacon_01.png",
                            secondRestaurant
                    ),
                    new Dish(
                            "CHICKEN&BACON",
                            "Grillowany kurczak z baconem",
                            28.00,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdiUAL7hHlhQn8hRTnBtcGk7ASGPdOd4jB2g&usqp=CAU",
                            secondRestaurant
                    ),
                    new Dish(
                            "GRILLED CHICKEN",
                            "Grillowany filet z kurczaka",
                            25.00,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSjdjJ4DsemtEFr7v0svaFbWtWrV-vA-doEDA&usqp=CAU",
                            secondRestaurant
                    ),
                    new Dish(
                            "LOUISIANA RIBS",
                            "Pieczone żeberka w marynacie bbq, coli, wina, miodu z przyprawami",
                            38.00,
                            "https://www.themagicalslowcooker.com/wp-content/uploads/2016/02/louisiana-ribs-AQUARE-500x375.jpg",
                            secondRestaurant
                    ),
                    new Dish(
                            "SOUTH CAROLINA RIBS",
                            "Żeberka w marynacie musztardowo rozmarynowej",
                            38.00,
                            "https://farm6.staticflickr.com/5744/23410746845_39d7605470_b.jpg",
                            secondRestaurant
                    ),
                    new Dish(
                            "CEZAR Z KURCZAKIEM",
                            "Grillowany kurczak, sałata rzymska, świeży ogórek, pomidor koktajlowy, czerwona cebula, grzanki, sos cezar",
                            28.00,
                            "https://www.kwestiasmaku.com/sites/v123.kwestiasmaku.com/files/salatka_cezar_z_kurczakiem_01.jpg",
                            secondRestaurant
                    ),
                    new Dish(
                            "PEPSI",
                            "Pepsi 0,2l",
                            7.00,
                            "https://www.mojedostawy.pl/wp-content/uploads/pepsi-0.2l.jpg",
                            secondRestaurant
                    ),
                    new Dish(
                            "MIRINDA",
                            "Mirinda 0,2l",
                            7.00,
                            "https://hurtum.pl/storage/products/16/22/58/53/31/1622585331_99099689_c11c82b2653149f621fdfc3c2ba93f56_hd.jpg",
                            secondRestaurant
                    ),
                    new Dish(
                            "SCHWEPPES TONIC",
                            "Schweppes Tonic 0,2l",
                            7.00,
                            "https://wirtualnetargowisko.pl/wp-content/uploads/2020/09/e14203bce0e2c1f0e35158aefb58f1fa.jpg",
                            secondRestaurant
                    ),
                    new Dish(
                            "PIZZA MARGHERITA",
                            "Sos pomidorowy, mozzarella fior di latte, oliwa extra virgin, parmigiano, świeża bazylia",
                            22.00,
                            "https://cdn.upmenu.com/static/product-images/f13a9fae-04f9-11ec-a1e9-525400080521/796c5f2f-05a1-11ec-a1e9-525400080521/2/huge/margherita_edited.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "PIZZA FUNGHI",
                            "Mozzarella fior di latte, pieczarki, grzyby leśne, ser tellagio, parmigiano, czosnek, świeży tymianek, świeżo mielony pieprz",
                            32.00,
                            "https://najlepszewkuchni.pl/storage/recipes/2017_11_15/QwcI4SMkbIGr4RztSHnr.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "PIZZA PROSCIUTTO COTTO",
                            "Sos pomidorowy, mozzarella fior di latte, prosciutto cotto (szynka włoska gotowana), parmigiano, oliwa extra virgin, świeża bazylia",
                            29.00,
                            "https://fotokulinarnie.pl/wp-content/uploads/2021/02/IMG_4279-2-792x528.jpg?v=1612889508",
                            thirdRestaurant
                    ),
                    new Dish(
                            "PIZZA PROSCIUTTO DI PARMA",
                            "Sos pomidorowy, mozzarella fior di latte, prosciutto di parma (dojrzewająca szynka z parmy), świeża rukola, świeża bazylia, parmigiano, oliwa extra virgin",
                            32.00,
                            "https://cdn.upmenu.com/static/product-images/f13a9fae-04f9-11ec-a1e9-525400080521/dc8de983-05a5-11ec-a1e9-525400080521/2/huge/_dsc0312_edited.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "PIZZA QUATRO FORMAGGI",
                            "Mozzarella fior di latte, gorgonzola, ser tellagio, parmigiano, świeżo mielony pieprz, orzechy włoskie, miód",
                            32.00,
                            "https://fotokulinarnie.pl/wp-content/uploads/2021/02/IMG_4009-2-scaled.jpg?v=1612895432",
                            thirdRestaurant
                    ),
                    new Dish(
                            "PIZZA PICANTE",
                            "Sos pomidorowy, mozzarella fior di latte, płatki chili, salame picante (ostre włoskie salami), oliwa extra virgin, parmigiano, świeża bazylia",
                            31.00,
                            "https://najlepszewkuchni.pl/storage/recipes/2017_11_15/XYX93lSsavTKfqlAwPhc.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "PIZZA POLLO",
                            "Sos pomidorowy, mozzarella fior di latte, kurczak, por, kukurydza, oliwa extra virgin, parmigiano, suszone pomidory",
                            27.00,
                            "https://bi.im-g.pl/im/9b/23/16/z23212955AMP,Pizza-pollo.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "PIZZA NAPOLI",
                            "Sos pomidorowy, mozzarella fior di latte, salame napoli (łagodne włoskie salami), czosnek, oliwa extra virgin, parmigiano, świeża bazylia",
                            30.00,
                            "https://cookmagazine.pl/wp-content/uploads/2016/07/fotolia_81597702_subscription_monthly_m-850x1273.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "PIZZA VEGETARIANA",
                            "Sos pomidorowy, mozzarella fior di latte, grillowany bakłażan, cukinia, papryka, czosnek, oregano, oliwa extra virgin, świeża bazylia",
                            26.00,
                            "https://dwormarcinkowo.pl/wp-content/uploads/2020/03/Pizza-Vegetariana.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "PIZZA BURAK",
                            "Sos śmietanowo-szpinakowy, mozzarella fior di latte, pieczony burak, świeży szpinak, migdały, oliwa extra virgin, świeża bazylia",
                            25.00,
                            "https://4.bp.blogspot.com/-UKsWAUUEbRM/VrpWjbNM8JI/AAAAAAAAC0k/xuhpJVurKwQ/s1600/pizza%2Bburaczana.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "PIZZA NAPOLITANA",
                            "Sos pomidorowy, mozzarella fior di latte, kapary, anchois (mała słona rybka), oliwki, płatki chili, czosnek, oliwa extra virgin, parmigiano, świeża bazylia",
                            34.00,
                            "https://i.ytimg.com/vi/OKpoBhOEVvw/maxresdefault.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "PIZZA SPINACI",
                            "Mozzarella fior di latte, czosnek, świeży szpinak, cebula szalotka, pancetta (suszony boczek), oliwa extra virgin, parmigiano, świeża bazylia",
                            32.00,
                            "https://onlineculinaryschool.net/wp-content/uploads/2018/10/online_culinary_school_pizza_spinaci-1.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "BURGER CLASSIC",
                            "Wołowina, boczek, cheddar, korniszon, sałata, pomidor, karmelizowana cebula, sos aioli, sos musztardowy, frytki z ziemniaków",
                            31.00,
                            "https://recipe4appetite.pl/img/recipe-img/klasyczne-burgery-wolowe.png",
                            thirdRestaurant
                    ),
                    new Dish(
                            "BURGER VEGE",
                            "Kotlet vege, burak grillowany, ogórek konserwowy, rukola, majonez, mozzarella, frytki z ziemniaków",
                            32.00,
                            "https://static.300gospodarka.pl/media/2020/10/shutterstock_1239647506.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "GRILLOWANY ŁOSOŚ",
                            "Filet z łososia, grillowane warzywa, sos śmietanowy",
                            39.00,
                            "https://cdn.upmenu.com/static/product-images/f13a9fae-04f9-11ec-a1e9-525400080521/6a09a5f4-05d0-11ec-a1e9-525400080521/2/huge/losos-2-up_edited.jpg",
                            thirdRestaurant
                    ),
                    new Dish(
                            "KIESZONKA DROBIOWA",
                            "Kieszonka drobiowa faszerowana polędwicą oraz żółtym serem",
                            23.00,
                            "https://static.smaker.pl/photos/4/a/9/4a90aa973470ae946c58e880ded44477_367255_5b2c018f41b85_wm.jpg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "KIESZONKA SCHABOWA W PANIERCE",
                            "Kieszonka schabowa w panierce faszerowana polędwicą oraz żółtym serem",
                            23.00,
                            "https://static.smaker.pl/photos/0/f/4/0f4b2e5e9fc0800732c6b74000d4a3f2_366283_59d910b73187f_wm.jpg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "TRADYCYJNY KOTLET DROBIOWY",
                            "Tradycyjny kotlet drobiowy z ziemniakami i surówką",
                            20.00,
                            "http://4.bp.blogspot.com/-CPKiHgthqtM/VMdRMvXTxCI/AAAAAAAAMKg/vqhSt3JU4gE/s1600/Kotlety%2Bz%2BKurczaka%2B5.jpg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "TRADYCYJNY KOTLET SCHABOWY",
                            "Tradycyjny kotlet schabowy z ziemniakami i surówką",
                            20.00,
                            "https://kuron.com.pl/wp-content/uploads/2018/04/P4124119.jpg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "SCHABOWY PO TARNOWSKU",
                            "Kotlet schabowy pod pierzynką ze smażonych pieczarek z cebulką oraz jajkiem sadzonym",
                            23.00,
                            "https://img-global.cpcdn.com/recipes/05dfd7ccc535466b/680x482cq70/kotlet-po-tarnowsku-przepis-glowne-zdjecie.jpg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "KOTLET DROBIOWY PO TARNOWSKU",
                            "Kotlet drobiowy pod pierzynką ze smażonych pieczarek z cebulką oraz jajkiem sadzonym",
                            23.00,
                            "https://img-global.cpcdn.com/recipes/05dfd7ccc535466b/680x482cq70/kotlet-po-tarnowsku-przepis-glowne-zdjecie.jpg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "FILET DROBIOWY",
                            "Filet drobiowy zapiekany pod pierzynką z pomidora i mozzarelli, podawany ze świeżą bazylią oraz parmezanem",
                            24.00,
                            "https://www.przyslijprzepis.pl/media/cache/big/uploads/media/recipe/0005/76/d600c5e34a1bd67c91bd83f4c55364e15ec1373c.jpeg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "GRILLOWANA KIESZEŃ Z FILETA DROBIOWEGO",
                            "Grillowana kieszeń z fileta drobiowego ze szpinakiem i żółtym serem lub szynką i żółtym serem",
                            24.00,
                            "https://static.smaker.pl/photos/0/4/4/04488d177e2cf541d1054a72cfe77f9a_354389_56baefa888e79_wm.jpg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "GRILLOWANY FILET DROBIOWY",
                            "Grillowany filet drobiowy pod pierzynką ze smażonym pieczarek lub smażonego na maśle szpinaku z dodatkiem sera fety i czosnku",
                            23.00,
                            "https://przepiski.pl/wp-content/uploads/2021/04/filet-z-kurczaka-pod-szpinakowa-pierzynka1-769x1024.jpg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "NALEŚNIKI Z FARSZEM",
                            "Naleśniki z farszem z grillowanymi warzywami oraz kurczakiem, podawane z sosem czosnkowym",
                            19.00,
                            "https://naszprzepis.pl/wp-content/uploads/2019/10/nalesniki_z_farszem_z_miesem_mielonym_land.jpg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "NALEŚNIKI ZE SZPINAKIEM",
                            "Naleśniki ze szpinakiem z czosnkiem i serem feta, zapiekane z żółtym serem",
                            18.00,
                            "https://akademiasmaku.pl/upload/recipes/3447/nalesniki-ze-szpinakiem-i-feta-3447.jpg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "NALEŚNIKI NA SŁODKO",
                            "Naleśniki na słodko z ucieranym białym serem, podawane z cukrem pudrem oraz sosem owocowym lub czekoladowym",
                            17.00,
                            "https://1.bp.blogspot.com/-_OVkil3snrc/XV_xud2WRbI/AAAAAAAACcA/jvZtJ9fLEBMKv_rwbpH9cx3u4ll82Bm-QCLcBGAs/w805/nalesniki%2Bz%2Btwarogiem%2Bna%2Bslodko%2Bposmakujto30.jpg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "NALEŚNIKI Z CZEKOLADĄ I BANANEM",
                            "Naleśniki z czekoladą i bananem podawane z cukrem pudrem",
                            18.00,
                            "https://www.mojegotowanie.pl/media/cache/default_view/uploads/media/default/0001/07/e7b377ba5add97eb84e43232a60afc414195059c.jpeg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "MAKARON PENNE Z KURCZAKIEM",
                            "Makaron penne z kurczakiem w sosie pieczarkowo śmietanowym, podawanym ze świeżo tartym parmezanem i natką pietruszki",
                            20.00,
                            "https://www.przyslijprzepis.pl/media/cache/big/uploads/media/recipe/0008/49/penne-z-kurczakiem-i-papryka_3.jpeg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "MAKARON TAGLIATELLE Z KURCZAKIEM",
                            "Makaron tagliatelle z kawałkami kurczaka w sosie pomidorowym, podawanym ze świeżo tartym parmezanem i liśćmi bazyli",
                            20.00,
                            "https://www.przyslijprzepis.pl/media/cache/big/uploads/media/recipe/0004/50/1319392714f112b6d336959223c4c8f48533bc8a.jpeg",
                            fourthRestaurant
                    ),
                    new Dish(
                            "KOTLET SCHABOWY",
                            "Kotlet schabowy z ziemniakami i kapustą zasmażaną",
                            25.00,
                            "https://sklep.skansensmakow.pl/wp-content/uploads/2020/10/Kotelt-schabowy-z-zeimniakami-i-kapust%C4%85.jpeg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "KARKÓWKA GRILLOWANA",
                            "Karkówka grillowana z ziemniakami po wiejsku",
                            25.00,
                            "https://static.gotujmy.pl/VIDEO_BIG/karkowka-z-ziemniakami-z-grilla-505904.jpg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "FILET Z DORSZA",
                            "Filiet z dorsza z ziemniakami i surówką",
                            25.00,
                            "https://www.przyslijprzepis.pl/media/cache/big/uploads/media/recipe/0006/15/dorsz-smazony-ziemniaki-surowka.jpeg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "FILET Z KURCZAKA",
                            "Filet z kurczaka z warzywami i ryżem",
                            25.00,
                            "https://static.gotujmy.pl/ZDJECIE_PRZEPISU_ETAP/kurczak-z-ryzem-z-warzywami-366171.jpg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "KASZANKA WIEJSKA",
                            "Kaszanka wiejska z ziemniakami i kapustą zasmażaną",
                            23.00,
                            "https://www.smakizpolski.com.pl/wp-content/uploads/2014/01/kaszanka-2.jpg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "ROSÓŁ",
                            "Rosół domowy z makaronem",
                            10.00,
                            "https://s3.przepisy.pl/przepisy3ii/img/variants/800x0/rosol_z_kury170242.jpg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "BARSZCZ",
                            "Barszcz czerwony z uszkami",
                            10.00,
                            "https://s3.przepisy.pl/przepisy3ii/img/variants/767x0/barszcz-czerwony-z-uszkami-i-majerankiem.jpg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "FLACZKI WOŁOWE",
                            "Flaczki wołowe z pieczywem",
                            16.00,
                            "https://1.bp.blogspot.com/-lJq39DaMIc8/X1IrDC4RukI/AAAAAAAATdg/h_Oh3d6V-eQQZ4QhpqoFN0GcEtPFxMCAwCNcBGAsYHQ/w1200-h630-p-k-no-nu/Photo_1599218034286.jpg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "NALEŚNIKI Z SEREM",
                            "Naleśniki na słodko z białym serem",
                            13.00,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTx1d8C3pTk9E6ODFuanffZD3x6BSShmNmrTw&usqp=CAU",
                            fifthRestaurant
                    ),
                    new Dish(
                            "NALEŚNIKI Z NUTELLĄ",
                            "Naleśniki na słodko z nutellą",
                            13.00,
                            "https://naszprzepis.pl/wp-content/uploads/2020/05/nalesniki_z_fit_nutella_land.jpg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "NALEŚNIKI Z SZARLOTKĄ",
                            "Naleśniki na słodko z szarlotką",
                            13.00,
                            "https://poprostupycha.com.pl/wp-content/uploads/2019/01/nalesniki-2.jpg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "NALEŚNIKI ZE SZPINAKIEM",
                            "Naleśniki ze szpinakiem i białym serem",
                            13.00,
                            "https://i.iplsc.com/nalesniki-ze-szpinakiem/00073C9FSCEXL4TT-C122-F4.jpg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "PYZY Z MIĘSEM",
                            "Pyzy ziemniaczane z mięsem",
                            16.00,
                            "https://static.fajnegotowanie.pl/media/uploads/media_image/original/przepis/4274/pyzy-ziemniaczane-z-miesem.jpg",
                            fifthRestaurant
                    ),
                    new Dish(
                            "WODA MINERALNA GAZOWANA",
                            "Woda mineralna gazowana 330ml",
                            5.00,
                            "https://a.allegroimg.com/original/114bea/41ab3364469682d6b25c01805519/WODA-MINERALNA-GAZOWANA-SZKLO-330-ml-BORJOMI-Bor",
                            fifthRestaurant
                    ),
                    new Dish(
                            "WODA MINERALNA NIEGAZOWANA",
                            "Woda mineralna niegazowana 330ml",
                            5.00,
                            "https://a.allegroimg.com/original/114bea/41ab3364469682d6b25c01805519/WODA-MINERALNA-GAZOWANA-SZKLO-330-ml-BORJOMI-Bor",
                            fifthRestaurant
                    ),
                    new Dish(
                            "PIZZA KATARINA",
                            "Pomidor, oliwki, parmezan, rucola, prosciutto, bufala",
                            44.00,
                            "https://www.zajadam.pl/wp-content/uploads/2015/09/pizza-z-pomidorami-oliwkami-4-891x500.jpg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA VULCANO",
                            "Serowe ranty, gorgonzola, sos śmietanowy, pieczarka, brokuł, cebula, jajko, boczek",
                            40.00,
                            "https://www.zasmakujkuchni.pl/wp-content/uploads/2017/08/5654.png",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA PERE VERDE",
                            "Gruszka, mozzarella, orzeszki pini, gorgonzola, parmezan, miód",
                            32.00,
                            "https://www.przyslijprzepis.pl/media/cache/big/uploads/media/recipe/0007/03/pizza-z-gruszka-gorgonzola-i-orzechami.jpeg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA MARGHERITA",
                            "Sos, ser, oliwa",
                            24.00,
                            "https://ocdn.eu/images/pulscms/MmE7MDA_/06d3b49c0634ba5de867c761bb0cdaf4.jpeg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA SALAMI",
                            "Sos, salami, mozzarella, czosnek",
                            32.00,
                            "https://www.zmiksowane.com/wp-content/uploads/2018/02/pizza-2859233_1280-848x477.jpg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA CAPRICCIOSA",
                            "Sos, ser, szynka, pieczarka, oliwki, oliwa",
                            30.00,
                            "https://www.unileverfoodsolutions.pl/dam/global-ufs/mcos/NEE/calcmenu/recipes/PL-recipes/general/pizza-capriciosa/main-header.jpg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA WEGETARIANA",
                            "Sos, ser, pieczarka, papryka, cukinia, rukola",
                            32.00,
                            "https://www.pogotujmy.pl/wp-content/uploads/2016/01/DSC_0422-9.jpg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA CALABRESE",
                            "Sos, ser, salami, pieczarka, chili",
                            32.00,
                            "https://www.lifesambrosia.com/wp-content/uploads/Calabrese-Honey-Pizza-3-800x1208.jpg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA CALZONE",
                            "Sos pomidorowy, mozzarella, pieczarka, kukurydza, szynka, oliwa",
                            30.00,
                            "https://www.przyslijprzepis.pl/media/cache/big/uploads/media/recipe/0006/55/d6ba23ee6d3c7e51293125a38410175630734273.jpeg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA TONNO",
                            "Sos, ser, tuńczyk, cebula, oliwki, kukurydza",
                            32.00,
                            "https://thumbs.dreamstime.com/b/pizza-tonno-ta-tradycyjna-odmiana-pizzy-jest-zwie%C5%84czona-sosem-pomidorowym-z-tu%C5%84czyka-mozzarella-uzupe%C5%82niona-i-krojon%C4%85-cebul%C4%85-182682422.jpg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA PENSA",
                            "Sos, ser, szynka, ananas, kukurydza",
                            32.00,
                            "https://1.bp.blogspot.com/-OGP412lCJqE/YKk3_faS0mI/AAAAAAAAHeM/R-atf3qM0mU2wYbanwSfyXs-RYvvlux2wCLcBGAsYHQ/s2048/DSC_5820.jpg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA QUATRO FORMAGI",
                            "Sos, mozzarella, gorgonzola, parmezan, ser Halloumi",
                            34.00,
                            "https://s3.przepisy.pl/przepisy3ii/img/variants/767x0/pizza-quattro-formaggi.jpg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA SOPRANO",
                            "Sos, ser, mozzarella, boczek, salami, oliwki, karczochy, jalapeno",
                            36.00,
                            "http://www.codogara.pl/wp-content/uploads/2015/11/Domowa-pizza-31.jpg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA TONY SOPRANO",
                            "Sos, ser, kurczak, szynka parmeńska, pieczarka, papryka, kukurydza",
                            36.00,
                            "https://www.mojegotowanie.pl/media/cache/big/uploads/media/recipe/0001/100/pizza-z-kurczakiem.jpeg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "PIZZA PIZZAIOLO",
                            "Ser, surowa szynka parmeńska, rukola, pomidorki koktajlowe, parmezan",
                            36.00,
                            "https://bi.im-g.pl/im/de/4c/19/z26529502ICR,Przepis-na-pizze-od-pizzaiolo-z-restauracji-Pizzai.jpg",
                            sixthRestaurant
                    ),
                    new Dish(
                            "TATAR WOŁOWY",
                            "Tatar wołowy podwędzany pędami róży",
                            40.00,
                            "https://restaumatic-production.imgix.net/uploads/accounts/31974/media_library/62e0f8a5-659a-46a4-94f7-dc8c77c7c794.jpg?auto=compress&blur=0&crop=focalpoint&fit=max&fp-x=0.5&fp-y=0.5&h=auto&rect=0%2C0%2C1333%2C2000&w=600",
                            seventhRestaurant
                    ),
                    new Dish(
                            "DOJRZEWAJĄCY ROSTBEFF WOŁOWY",
                            "Dojrzewający Rostbef wołowy z marynowaną kurką,kiszoną rzodkiewką i oliwą z pestek dyni",
                            36.00,
                            "https://restaumatic-production.imgix.net/uploads/accounts/31974/media_library/a5e2f457-ef62-4590-850e-130ec0e391c0.jpg?auto=compress&blur=0&crop=focalpoint&fit=max&fp-x=0.5&fp-y=0.5&h=auto&rect=0%2C0%2C2000%2C1499&w=600",
                            seventhRestaurant
                    ),
                    new Dish(
                            "SER KOŹLAK W PANIERCE",
                            "Ser Koźlak w panierce na wytrawnym gofrze orzechowym z konfiturą z pigwy",
                            28.00,
                            "https://polki.pl/foto/4_3_LARGE/smazony-ser-w-panierce-przekaska-prosto-z-czech-2462710.webp",
                            seventhRestaurant
                    ),
                    new Dish(
                            "ŻUREK TARNOWSKI",
                            "Żurek tarnowski z jajkiem, kiełbaskami i grzanką z wiejskiego chleba",
                            18.00,
                            "https://restaumatic-production.imgix.net/uploads/accounts/31974/media_library/bc256737-ccaa-4b3e-9c2e-02f206e7de20.jpg?auto=compress&blur=0&crop=focalpoint&fit=max&fp-x=0.5&fp-y=0.5&h=auto&rect=0%2C0%2C2000%2C1333&w=600",
                            seventhRestaurant
                    ),
                    new Dish(
                            "BARSZCZ CZERWONY",
                            "Barszcz czerwony z pierożkami gęsimi i assiette malinowym",
                            22.00,
                            "https://restaumatic-production.imgix.net/uploads/accounts/31974/media_library/5d1db8a1-c87f-450c-a0c2-d940beb8cf66.jpg?auto=compress&blur=0&crop=focalpoint&fit=max&fp-x=0.5&fp-y=0.5&h=auto&rect=0%2C0%2C2000%2C1333&w=600",
                            seventhRestaurant
                    ),
                    new Dish(
                            "ZUPA Z CZOSNKIEM NIEDŹWIEDZIM",
                            "Zupa z czosnkiem niedźwiedzim żółtkiem i grzankami",
                            17.00,
                            "https://restaumatic-production.imgix.net/uploads/accounts/31974/media_library/e55f338e-7bdf-41b2-ad9f-b4ab58a58abf.jpg?auto=compress&blur=0&crop=focalpoint&fit=max&fp-x=0.5&fp-y=0.5&h=auto&rect=0%2C0%2C2000%2C1334&w=600",
                            seventhRestaurant
                    ),
                    new Dish(
                            "KAPUŚNIAK",
                            "Kapuśniak z gęsim żołądkiem i omastą",
                            22.00,
                            "https://bi.im-g.pl/im/7a/8c/1a/z27838330IE,Soup-Of-Sauerkraut-White-Cabbage.jpg",
                            seventhRestaurant
                    ),
                    new Dish(
                            "STEK Z POLSKIEGO ANTRYKOTU",
                            "Stek z frytkami domowymi i sosem z wina czerwonego",
                            67.00,
                            "https://restaumatic-production.imgix.net/uploads/accounts/31974/media_library/7931c94a-733b-42cc-b153-69fc248f065d.jpg?auto=compress&blur=0&crop=focalpoint&fit=max&fp-x=0.5&fp-y=0.5&h=auto&rect=0%2C0%2C2000%2C1333&w=600",
                            seventhRestaurant
                    ),
                    new Dish(
                            "PLACKI ZIEMNIACZANE",
                            "Placki ziemniaczane z jagnięciną i warzywami blanszowanymi",
                            38.00,
                            "https://restaumatic-production.imgix.net/uploads/accounts/31974/media_library/f9bfb069-c1dc-416d-9507-106152f4e548.jpg?auto=compress&blur=0&crop=focalpoint&fit=max&fp-x=0.5&fp-y=0.5&h=auto&rect=0%2C0%2C2000%2C1334&w=600",
                            seventhRestaurant
                    ),
                    new Dish(
                            "CIELĘCINA DUSZONA W SOSIE ŚMIETANOWYM",
                            "Cielęcina duszona w sosie śmietanowym z kopytkami szpinakowymi, cukinią i marchewką",
                            48.00,
                            "https://restaumatic-production.imgix.net/uploads/accounts/31974/media_library/3a36f837-f38a-410c-9504-878fd229cb9d.jpg?auto=compress&blur=0&crop=focalpoint&fit=max&fp-x=0.5&fp-y=0.5&h=auto&rect=0%2C0%2C2000%2C1499&w=600",
                            seventhRestaurant
                    ),
                    new Dish(
                            "ŻEBRO WIEPRZOWE",
                            "Żebro wieprzowe z glazurą piwną, domowymi frytkami i kapustą zasmażaną",
                            42.00,
                            "https://baraboo.pl/wp-content/uploads/2021/07/Z%CC%87EBRO-BBQ.jpg",
                            seventhRestaurant
                    ),
                    new Dish(
                            "KARCZEK WIEPRZOWY",
                            "Karczek wieprzowy wolno gotowany z gołąbkami ziemniaczanymi i sosem z grzybów leśnych",
                            39.00,
                            "https://s3.przepisy.pl/przepisy3ii/img/variants/800x0/karkowka-duszona-w-sosie-wlasnym-z-cebula.jpg",
                            seventhRestaurant
                    ),
                    new Dish(
                            "GULASZ Z DZIKA",
                            "Gulasz z dzika i grzybów leśnych z jałowcem, kasza pęczak ze śliwkami",
                            44.00,
                            "https://karmelowy.pl/wp-content/uploads/2017/03/gulasz-z-dzika1.jpg",
                            seventhRestaurant
                    ),
                    new Dish(
                            "FILET Z JESIOTRA",
                            "Filet z jesiotra przygotowany na parze z soczewicą pomidorową",
                            59.00,
                            "https://restaumatic-production.imgix.net/uploads/accounts/31974/media_library/db0e648f-0772-49e7-8f0c-a8b46cd5871d.jpg?auto=compress&blur=0&crop=focalpoint&fit=max&fp-x=0.5&fp-y=0.5&h=auto&rect=0%2C0%2C2000%2C1499&w=600",
                            seventhRestaurant
                    ),
                    new Dish(
                            "SANDACZ FILETOWANY",
                            "Sandacz filetowany z ziemniakami, pieczonymi buraczkami i sosem śmietanowo maślanym",
                            48.00,
                            "https://restaumatic-production.imgix.net/uploads/accounts/31974/media_library/72e784ac-68f0-4bf3-a885-034db1d10464.jpg?auto=compress&blur=0&crop=focalpoint&fit=max&fp-x=0.5&fp-y=0.5&h=auto&rect=0%2C0%2C2000%2C1334&w=600",
                            seventhRestaurant
                    ),
                    new Dish(
                            "STEK WOŁOWY",
                            "Antrykot wołowy, grzybowy gratin ziemniaczany, pikantne warzywa stir fry z sezamem, krążki cebulowe, masło tymiankowe, sos whisky z czerwonym pieprzem",
                            55.00,
                            "https://kulinarnapolska.org/wp-content/uploads/2020/02/stek-wolowy-jak-zrobic-przepis-3.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "ŁOSOŚ PIECZONY",
                            "Risotto, gruszka marynowana w czerwonym winie, szalotka, pietruszka, cytryna, ser Pecorino Romano",
                            38.00,
                            "http://www.przepisykulinarne.info/wp-content/uploads/2016/11/pieczony-losos-bogdana.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "PAPPARDELE Z POLĘDWICĄ WOŁOWĄ",
                            "Sos demi glace z truflą, pieczarki, pietruszka, ser Pecorino Romano",
                            38.00,
                            "https://img-global.cpcdn.com/recipes/c3f5e87d4f2da3b4/640x640sq70/photo.webp",
                            eighthRestaurant
                    ),
                    new Dish(
                            "KREWETKI Z PIEROŻKAMI RAVIOLI",
                            "Dynia, seler naciowy, orzechy włoskie, chilli, pietruszka, sos maślany, ser Pecorino Romano",
                            36.00,
                            "https://czosnekwpomidorach.pl/wp-content/uploads/2014/06/DSC_6595.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "CASARECCE Z SEREM BURRATA",
                            "Pesto z suszonych pomidorów, oliwa chilli, pomidorki cherry, ser Pecorino Romano, bazylia",
                            32.00,
                            "https://www.servingdumplings.com/wp-content/uploads/2017/09/Tagliatelle-z-borowikami-i-serem-burrata-2.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "SPAGHETTI CARBONARA CON FUNGHI",
                            "Guanciale, jajo, pietruszka, pieczarki, ricotta ze szczypiorkiem, pangratatto ziołowe, ser Pecorino Romano",
                            32.00,
                            "https://blog.giallozafferano.it/lacucinadivane/wp-content/uploads/2014/11/pasta-alla-carbonara-con-i-funghi.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "WEGAŃSKIE RISOTTO Z DYNIĄ",
                            "Pieczona dynia, szalotka, cebula dymka, rosti z cebuli, wegański parmezan",
                            30.00,
                            "http://www.kuchniawformie.pl/wp-content/uploads/2014/01/MG_5730.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "CASARECCE CURRY Z KURCZAKIEM",
                            "Suszone pomidory, młody szpinak, śmietanka, poppadom, ser Pecorino Romano",
                            34.00,
                            "https://cdn.aniagotuje.com/pictures/articles/2020/01/1967553-v-1080x1080.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "WEGAŃSKIE KOTLECIKI Z BOCZNIAKA",
                            "Spaghetti aglio e olio z cukinii i marchewki, hummus z pietruszki i selera, pangratatto orzechowe, rosti z batata",
                            35.00,
                            "https://www.jadlonomia.com/wp-content/uploads/2014/03/MG_2794-600x900.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "PIEROŻKI MEZZELUNE Z FIGĄ",
                            "Figi, szalotka, rozmaryn, śmietana, ser blue, puder z trufli, migdały prażone, bazylia",
                            32.00,
                            "https://karmelowy.pl/wp-content/uploads/2016/12/pierogi-z-kaczka-i-figami3.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "TATAR WOŁOWY",
                            "Wołowina, popcorn z kaparów, majonez truflowy, pikle, grzyb marynowany, masło rozmarynowe, oliwa bazyliowa, żółtko jaja przepiórczego, czerwony pieprz, poppadom, focaccia",
                            35.00,
                            "https://www.mojegotowanie.pl/media/cache/default_view/uploads/media/recipe/0002/03/tatar-wolowy.jpeg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "PIERÓG RAVIOLO CON UVO",
                            "Płynne żółtko, ricotta grzybowa, ser Pecorino Romano, sos maślany, pietruszka, rosti z cebuli",
                            26.00,
                            "https://64.media.tumblr.com/2c65713f13054b1638d640c932643f30/529e0347806f4776-c1/s500x750/adfcef0ea5e5b4f00790c6db592e9da777264981.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "PIEROŻKI TIRAMISU DOPPIO RAVIOLI",
                            "Krem kawowy, mascarpone, pangratatto biszkoptowe, kakao, bita śmietana",
                            22.00,
                            "https://cdn.upmenu.com/static/product-images/425d51b0-5408-11eb-bdef-525400080521/29f6de11-5a63-11eb-bdef-525400080521/6/huge/img_0235.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "PIEROŻKI SCARPINOCC Z BANANEM I ORZECHAMI",
                            "Banan, serek Philadelphia, prażone orzechy laskowe, sos biała czekolada, kawior jagodowy, prażone migdały",
                            22.00,
                            "https://cdn.upmenu.com/static/product-images/425d51b0-5408-11eb-bdef-525400080521/820c1a83-5a64-11eb-bdef-525400080521/5/huge/img_1996.jpg",
                            eighthRestaurant
                    ),
                    new Dish(
                            "SERNIK Z BORÓWKAMI",
                            "Mus owocowy, kruszona beza, owoce sezonowe",
                            18.00,
                            "http://gotujebolubie.pl/app/webroot/upload/images/przepisy/sernik_jagodowy_z_beza_2_2012-07.jpg",
                            eighthRestaurant
                    )
            );
            dishRepository.saveAll(dishes);
        } else {
          dishes = (List<Dish>) dishRepository.findAll();
        }
        if (discountRepository.count() == 0){
            List<Discount> discounts = List.of(
                    new Discount(dishes.get(3), 0.4, LocalDate.now().plusMonths(5)),
                    new Discount(dishes.get(5), 0.22, LocalDate.now().plusMonths(5)),
                    new Discount(dishes.get(7), 0.45, LocalDate.now().plusMonths(5)),
                    new Discount(dishes.get(8), 0.75, LocalDate.now().plusMonths(5)),
                    new Discount(dishes.get(9), 0.22, LocalDate.now().plusMonths(5)),
                    new Discount(dishes.get(22), 0.6, LocalDate.now().plusMonths(5))

            );

            discountRepository.saveAll(discounts);
        }

        Role admin = new Role("admin");
        Role user = new Role("user");
        if (roleRepository.count() == 0) {
            List<Role> roles = List.of(admin, user);
            roleRepository.saveAll(roles);
        }
        if (userRepository.count() == 0) {
            List<User> users = List.of(new User(
                            "broniq1",
                            passwordEncoder.encode("bulka123"),
                            "Filip",
                            "Broniek",
                            "Spacerowa 8",
                            "234123123123",
                            "12/34",
                            "123",
                            "kon@gmail.com",
                            List.of(user, admin)
                    ),
                    new User("jchocho",
                            passwordEncoder.encode("haslo123"),
                            "Jakub",
                            "Chocholowicz",
                            "Polna 9",
                            "134123123123",
                            "12/33",
                            "321",
                            "jchocho@gmail.com",
                            List.of(user)
                    ));
            userRepository.saveAll(users);
        }
    }
}
