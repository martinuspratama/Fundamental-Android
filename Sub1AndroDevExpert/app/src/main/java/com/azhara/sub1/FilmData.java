package com.azhara.sub1;

import java.util.ArrayList;

public class FilmData {
    private static int[] photo = {
            R.drawable.aladdin,
            R.drawable.frozen,
            R.drawable.godzilla,
            R.drawable.hellboy,
            R.drawable.joker,
            R.drawable.spiderman,
            R.drawable.mib,
            R.drawable.starisborn,
            R.drawable.venom,
            R.drawable.thenun
    };

    private static String[] judul = {
            "Alladin",
            "Frozen2",
            "Godzilla king of the monsters",
            "Hellboy",
            "Joker",
            "Spider-Man: Far From Home",
            "Men in Black: International",
            "A Star Is Born",
            "Venom",
            "The Nun"
    };

    private static String[] rilis = {
            "24 Mei 2019 (Indonesia)",
            "27 November 2019 (Belgia)",
            "29 Mei 2019 (Indonesia)",
            "10 April 2019 (Indonesia)",
            "31 Agustus 2019 (Italia)",
            "3 Juli 2019 (Indonesia)",
            "19 Juni 2019 (Indonesia)",
            "19 Oktober 2018 (Indonesia)",
            "3 Oktober 2018 (Indonesia)",
            "5 September 2018 (Indonesia)"
    };

    private static String[] sinopsis = {
            "Aladdin, seorang anak jalanan yang jatuh cinta pada seorang putri. Dengan perbedaan kasta dan harta, Aladdin pun berusaha mencari jalan agar bisa mejadi seorang pangeran, tak disangka ia menemukan lampu dengan jin di dalamnya. Aladdin pun menggunakan lampu untuk mengubah dirinya menjadi seorang pangeran untuk memenangkan hatiPutri Jasmine, tapi Wazir jahat juga mengejar lampu tersebut untuk menguasai kerajaan.",
            "Tiga tahun setelah peristiwa film pertama, Ratu Elsa, saudara perempuannya Anna, Kristoff, Olaf, dan Sven memulai perjalanan baru di luar tanah air mereka di Arendelle untuk menemukan asal mula kekuatan sihir Elsa dan menyelamatkan kerajaan mereka.",
            "Kisah baru ini mengikuti upaya heroik agensi kripto zoologi Monarch ketika anggotanya berhadapan dengan monster seukuran dewa, termasuk Godzilla yang perkasa, yang harus berhadapan dengan Mothra, Rodan, dan musuh bebuyutannya, si kepala tiga King Ghidorah. Ketika super spesies purba iniyang sebelumnya hanya dianggap mitosbangkit kembali, mereka semua bersaing untuk supremasi, membuat keberadaan manusia tergantung dalam keseimbangan.",
            "Pada tahun 1944 ketika Perang Dunia II, para anggota Nazi mencari cara untuk menaklukan dunia dengan cara mengabungkan ilmu pengetahuan dan ilmu gelap untuk bisa membuka portal yang dapat membangkitkan sebuah monster raksasa untuk menguasai bumi yaitu ogdru jahad, raksasa mengerikan dari angkasa yang dikenal sebagai tujuh dewa kematian, mereka disegel di sebuah kristal yang tidak mampu dihancuran. Grigori Rasputin ( karel roden ) dan ilsa haupstein ( biddy hodson ) yang sedang memberikan buku untuk membangkitkan kembali ketika rasputin mati nantinya, sementara itu tentara amerika bersama dengan profesor broom ( john hurt ) mengamati secara diam diam dan ritual itu dimulai dengan menggunakan mesin portal selanjutnya terbuka portal itu setelah itu tentara amerika itu pun melempar granat sehingga terjadi pertempuran profesor yang terluka itupun dihadang oleh karl ruprecht kroenen ( Ladislav Beran ).",
            "Pada tahun 1981, seorang komedian gagal bernama Arthur Fleck (Joaquin Phoenix) diabaikan oleh masyarakat dan kemudian menjalani kehidupan yang penuh kejahatan dan kekacauan di kota Gotham.",
            "Peter Parker (Tom Holland) tengah mengunjungi Eropa untuk liburan panjang bersama temaan-temanya. Sayangnya , Parker tidak bisa dengan tenang menikmati liburannya, karena Nick Fury datang secara tiba-tiba di kamar hotelnya. Selama di Eropa pun Peter harus menghadapi banyak musuh mulai dari Hydro Man, Sandman dan Molten Man.",
            "Men in Black selalu melindungi Bumi dari penjahat alam semesta. Dalam petualangan baru ini, mereka harus mengatasi ancaman terbesar mereka hingga saat ini: mata - mata yang ada dalam organisasi Men in Black.",
            "Seorang bintang musik country yang karirnya mulai memudar, Jackson Maine (Bradley Cooper) menemukan sebuah talenta yang sangat berbakat di dalam diri dari seorang musisi muda bernama Ally (Lady Gaga). Maine berhasil mengorbitkan Ally menjadi seorang bintang muda yang menjanjikan. Namun keduanya terlibat hubungan yang lebih jauh dari sekedar mentor dan anak didik. Seiring dengan meroketnya karir dari Ally dan dirinya, Maine mengalami dilema mengenai masalah ini.",
            "Seorang jurnalis, Eddie Brock (Tom Hardy) ingin melakukan sebuah investasi kasus terhadap penemuan yang dipimpin oleh Dr. Carlton Drake (Riz Ahmed). Karena investigasinya, Eddie mengunjungi lab milik Dr. Calrlton Drake. Semuanya ditujukan untuk membuktikan bahwa Dr. Carlton Drake sedang sedang melakukan tindakan jahat menggunakan Symbiote.",
            "Ketika seorang biarawati muda di biara terpencil Rumania mengorbankan hidupnya sendiri, seorang pendeta dengan masa lalu yang kelam dan seorang calon biawawati dikirim oleh Vatikan untuk menyelidiki peristiwa ini. Bersama-sama mereka membuka rahasia rahasia yang tidak terduga."
    };

    static ArrayList<Film> getFilmData(){
        ArrayList<Film> list = new ArrayList<>();
        for (int i = 0; i < judul.length; i++){
            Film film = new Film();
            film.setPicFilm(photo[i]);
            film.setJudulFilm(judul[i]);
            film.setRilisFilm(rilis[i]);
            film.setSinopsisFilm(sinopsis[i]);
            list.add(film);
        }
        return list;
    }
}
