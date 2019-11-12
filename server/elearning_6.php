<?php
$server = 'localhost';
$username = 'root';
$password = '';
$database = 'biodata_elearning6';
$con = mysqli_connect($server, $username, $password, $database) or die("Sepertinya terjadi masalah pada koneksi Database");

@$operasi = $_GET["operasi"];
switch ($operasi) {
    case "view":
        $q = mysqli_query($con, "SELECT * FROM isi_biodata");
        $output = [];
        if ($q) {
            while ($data = mysqli_fetch_assoc($q)) {
                $output[] = $data;
            }
            jsonOutput(true, "Berhasil mendapatkan data", $output);
        } else {
            jsonOutput(false, "Gagal mendapatkan data, error : " . mysqli_error($con));
        }
        break;
    case "insert":
        ///Fungsi ini digunakan untuk memasukan item biodata
        @$nim = $_GET["nim"]; //Mendapatkan Nim dari parameter GET
        @$nama = $_GET["nama"]; //Mendapatkan Nama dari parameter GET
        @$alamat = $_GET["alamat"];  //Mendapatkan Alamat dari parameter GET

        ///Validasi sederhana ----------------------------
        if (trim($nim) == '') {
            jsonOutput(false, "Kotak nim masih kosong");
            return;
        }
        if (strlen(trim($nim)) <> 8) {
            jsonOutput(false, "Jumlah nim tidak boleh dibawah/lebih dari 8 karakter");
            return;
        }
        if (trim($nama) == '') {
            jsonOutput(false, "Kotak nama masih kosong");
            return;
        }
        if (trim($alamat) == '') {
            jsonOutput(false, "Kotak alamat masih kosong");
            return;
        }
        //=================================================

        $q = mysqli_query($con, "INSERT INTO isi_biodata (nim, nama, alamat) VALUES ('$nim','$nama','$alamat')"); //Eksekusi menambah data ke database
        if ($q) { //Apakah berhasil 
            $id_last = mysqli_insert_id($con); //Mendapatkan ID yang dimasukan
            jsonOutput(true, "Berhasil memasukan data", [
                "id" => $id_last ?? '',
                "nim" => $nim,
                "nama" => $nama,
                "alamat" => $alamat
            ]);
        } else { //Jika tidak
            jsonOutput(false, "Gagal memasukan data, error : " . mysqli_error($con));
        }
        break;
    case "update":
        ///Fungsi ini digunakan untuk memperbarui biodata
        ///Secara spesifik, yaitu menggunakan ID
        @$id = $_GET["id"]; //Mendapatkan ID dari parameter GET (Yang ingin diubah)
        @$nim = $_GET["nim"]; //Mendapatkan Nim dari parameter GET (Yang baru)
        @$nama = $_GET["nama"]; //Mendapatkan Nama dari parameter GET (Yang baru)
        @$alamat = $_GET["alamat"]; //Mendapatkan Alamat dari parameter GET (Yang baru)

        ///Validasi sederhana ----------------------------
        if (trim($id) == '') {
            jsonOutput(false, "Kotak id masih kosong");
            return;
        }
        if (trim($nim) == '') {
            jsonOutput(false, "Kotak nim masih kosong");
            return;
        }
        if (strlen(trim($nim)) <> 8) {
            jsonOutput(false, "Jumlah nim tidak boleh dibawah/lebih dari 8 karakter");
            return;
        }
        if (trim($nama) == '') {
            jsonOutput(false, "Kotak nama masih kosong");
            return;
        }
        if (trim($alamat) == '') {
            jsonOutput(false, "Kotak alamat masih kosong");
            return;
        }
        //=================================================

        $q = mysqli_query($con, "UPDATE isi_biodata SET `nim` = '$nim', `nama`='$nama', `alamat`='$alamat' WHERE `id` = '$id';"); //Eksekusi pengambilan data dari database
        if ($q) { //Apakah berhasil 
            jsonOutput(true, "Data berhasil diupdate", [
                "id" => $id,
                "nim" => $nim,
                "nama" => $nama,
                "alamat" => $alamat,
            ]);
        } else { //Jika tidak
            jsonOutput(false, "Gagal mengupdate data, error : " . mysqli_error($con));
        }
        break;
    case "delete":
        ///Fungsi ini digunakan untuk menghapus data
        ///Secara spesifik, yaitu menggunakan ID
        @$id = $_GET["id"]; //Mendapatkan ID dari parameter GET
        ///Validasi sederhana ----------------------------
        if (trim($id) == '') {
            jsonOutput(false, "Kotak id masih kosong");
            return;
        }
        //=================================================
        $q = mysqli_query($con, "DELETE FROM isi_biodata WHERE id='$id'"); //Eksekusi penghapusan data dari database
        if ($q) { //Apakah berhasil menghapus
            jsonOutput(true, "Berhasil menghapus data");
        } else { //Jika tidak
            jsonOutput(false, "Gagal menghapus data, error : " . mysqli_error($con));
        }
        break;
    case "get_biodata_by_id":
        ///Fungsi ini digunakan untuk mengambil biodata
        ///Secara spesifik, yaitu menggunakan ID
        @$id = $_GET["id"]; //Mendapatkan ID dari parameter GET
        ///Validasi sederhana ----------------------------
        if (trim($id) == '') {
            jsonOutput(false, "Kotak id masih kosong");
            return;
        }
        //=================================================
        $q = mysqli_query($con, "SELECT * FROM isi_biodata WHERE id='$id'"); //Eksekusi pengambilan data dari database
        if ($q) { //Apakah berhasil
            while ($data = mysqli_fetch_assoc($q)) {
                $output[] = $data;
            }
            jsonOutput(true, "Berhasil mendapatkan data", $output);
        } else { //Jika tidak
            jsonOutput(false, "Gagal mendapatkan data, error : " . mysqli_error($con));
        }
        break;
    default: 
        jsonOutput(false, "Sepertinya perintah tidak valid");
        return;
}

// jsonOutput(false, "Ada yang tidak beres");

//Gunakan fungsi ini untuk menampilkan output berupa JSON
//Disarankan karena telah dibakukan.
function jsonOutput($success = false, $message = "", $data = [])
{
    header('content-type: application/json');
    echo json_encode([
        "success" => $success,
        "message" => $message,
        "data" => $data
    ]);
}
