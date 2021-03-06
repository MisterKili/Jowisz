<?php
 
class DbOperation
{
    //Database connection link
    private $con;
 
    //Class constructor
    function __construct()
    {
        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DbConnect.php';
 
        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();
 
        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }
	
	/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function createKlient($email, $haslo, $nazw, $imie, $dataUr){
		$stmt = $this->con->prepare("INSERT INTO klient (email, haslo, nazw, imie, dataUr) VALUES (?, ?, ?, ?, ?)");
		$stmt->bind_param("sssss", $email, $haslo, $nazw, $imie, $dataUr);
		if($stmt->execute())
			return true; 
		return false; 
	}
	
 
	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getSprzet(){
		$stmt = $this->con->prepare("SELECT idSpr, nazwa, opis, cena, zdjecie, liczbaSzt, nazwaProd, nazwaTyp 
										FROM sprzet S JOIN producent P ON S.IdProd = P.IdProd
										JOIN typsprzetu T ON S.IdTyp = T.IdTyp;");
		$stmt->execute();
		$stmt->bind_result($idSpr, $nazwa, $opis, $cena, $zdjecie, $liczbaSztuk, $producent, $typ);
		
		$sprzety = array(); 
		
		while($stmt->fetch()){
			$sprzet  = array();
			$sprzet['idSpr'] = $idSpr; 
			$sprzet['nazwa'] = $nazwa; 
			$sprzet['opis'] = $opis; 
			$sprzet['cena'] = $cena; 
			$sprzet['zdjecie'] = $zdjecie; 
			$sprzet['liczbaSztuk'] = $liczbaSztuk; 
			$sprzet['producent'] = $producent; 
			$sprzet['typ'] = $typ; 
			
			array_push($sprzety, $sprzet); 
		}
		
		return $sprzety; 
	}
	
	function getLastIDFromZamowienie(){
		$stmt = $this->con->prepare("SELECT MAX(IdZam) FROM zamowienie");
		$stmt->execute();
		$stmt->bind_result($result);
		
		$stmt->fetch();
		$lastID = $result;
		return $lastID;
	}
	
	function createZamowienie($nazw, $imie, $adres, $tel, $dataZam, $czyOplacono, $IdPlat){
		$IdPlatInt = (int) $IdPlat;
		$stmt = $this->con->prepare("INSERT INTO zamowienie (Nazw, Imie, Adres, Tel, DataZam, CzyOplacono, IdPlat) VALUES (?, ?, ?, ?, ?, ?, ?)");
		$stmt->bind_param("ssssssi", $nazw, $imie, $adres, $tel, $dataZam, $czyOplacono, $IdPlatInt);
		if($stmt->execute())
			return true; 
		return false; 
	}
	
	function createZamowienieSprzetu($IdZam, $IdSpr, $Liczba, $Cena){
		$IdZamI = (int) $IdZam;
		$IdSprI = (int) $IdSpr;
		$LiczbaI = (int) $Liczba;
		$CenaD = (double) $Cena;
		$stmt = $this->con->prepare("INSERT INTO zamowienie_sprzetu (IdZam, IdSpr, Liczba, Cena) VALUES (?, ?, ?, ?);");
		$stmt->bind_param("iiid", $IdZamI, $IdSprI, $LiczbaI, $CenaD);
		if($stmt->execute())
			return true; 
		return false; 
	}
	
	function getSprzetZKategorii($idTyp){
		$stmt = $this->con->prepare("SELECT idSpr, nazwa, opis, cena, zdjecie, liczbaSzt, nazwaProd, nazwaTyp 
										FROM sprzet S JOIN producent P ON S.IdProd = P.IdProd
										JOIN typsprzetu T ON S.IdTyp = T.IdTyp
										WHERE T.IdTyp = ? ");
		$stmt->bind_param("i", $idTyp);
		$stmt->execute();
		$stmt->bind_result($idSpr, $nazwa, $opis, $cena, $zdjecie, $liczbaSztuk, $producent, $typ);
		
		$sprzety = array(); 
		
		while($stmt->fetch()){
			$sprzet  = array();
			$sprzet['idSpr'] = $idSpr; 
			$sprzet['nazwa'] = $nazwa; 
			$sprzet['opis'] = $opis; 
			$sprzet['cena'] = $cena; 
			$sprzet['zdjecie'] = $zdjecie; 
			$sprzet['liczbaSztuk'] = $liczbaSztuk; 
			$sprzet['producent'] = $producent; 
			$sprzet['typ'] = $typ; 
			
			array_push($sprzety, $sprzet); 
		}
		
		return $sprzety; 
	}
	
	function getKategorie(){
		$stmt = $this->con->prepare("SELECT * 
										FROM typsprzetu
										ORDER BY 2;");
		$stmt->execute();
		$stmt->bind_result($idTypu, $nazwa);
		
		$kategorie = array(); 
		
		while($stmt->fetch()){
			$typ  = array();
			$typ['IdTyp'] = $idTypu; 
			$typ['NazwaTyp'] = $nazwa; 
			
			array_push($kategorie, $typ); 
		}
		
		return $kategorie; 
	}
	
	
	/*
	* The update operation
	* When this method is called the record with the given id is updated with the new given values
	*/
	/*
	function updateHero($id, $name, $realname, $rating, $teamaffiliation){
		$stmt = $this->con->prepare("UPDATE heroes SET name = ?, realname = ?, rating = ?, teamaffiliation = ? WHERE id = ?");
		$stmt->bind_param("ssisi", $name, $realname, $rating, $teamaffiliation, $id);
		if($stmt->execute())
			return true; 
		return false; 
	}
	*/
	
	
	/*
	* The delete operation
	* When this method is called record is deleted for the given id 
	*/
	/*
	function deleteHero($id){
		$stmt = $this->con->prepare("DELETE FROM heroes WHERE id = ? ");
		$stmt->bind_param("i", $id);
		if($stmt->execute())
			return true; 
		
		return false; 
	}
	*/
}