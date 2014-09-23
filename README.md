CoursesUdacityCoursera
===========
# 1.	Opis problema
Cilj ovog projekta predstavlja izrada aplikacije koja će se koristiti za ekstakciju, struktuiranje i integraciju podataka o kursevima. Osnovna funkcijonalnost koju mora da obezbedi aplikacija odnosi se na ekstrakciju podataka sa sajta gde se nalaze kursevi. Podaci su  raspoloživi sa https://www.coursera.org/ cousera i https://www.udacity.com/ sajta, koji podatke čini dostupnim u JSON formatu. Korišćenjem njihovih API-jeva potrebno je preuzeti podatke, proveriti njihovu ispravnost, zatim konvertovati ih u RDF i sačuvati ih u RDF repozitorijum. Svaki podatak ekstrakovan iz odgovarajućeg API-ja se mapira kao odgovarajući element LRMI  vokabulara. LRMI specifikacija je kolekcija propertija za opisivanje obrazovnog sadržaja. Ona predstavlja proširenje Schema.org  vokaulara. Pretežno koristi propertije klase schema:CreativeWork koji su definisani u  Schema.org vokabularu kao i propertije koji su definisani u LRMI vokabularu, a koji su specifični za resure za učenje. Takođe, LRMI uvodi i neke nove klase koje su specifične za resurse za učenje, kao što su
klase AlignmentObject
i EducationalAudience.
Radi kreiranja aplikacije potrebno je bilo ispuniti sledeće zahteve:
* kreiranje web parsera koji prikuplja podatke o kursevima koristeci postojeće API-je
* kreiranje RDF baze i čuvanje ekstrahovanih podataka u skladu sa LRMI vokabularom
* omogućiti pristup podacima u bazi pomoću odgovarajućih REST servisa

Sačuvane RDF podatke potrebno je učiniti dostupnim korisnicima. Implementacija REST servisa koji će biti dostupan na Web-u predstavlja rešenje koje korisnicima pruža API sa dostupnim operacijama za pristup tim podacima. Pristupajući različitim URL-ovima koje nudi REST API, korisnik šalje upite i dobija željene podatke u JSON formatu. 
#2.	Domenski model

![slika nova 1](https://cloud.githubusercontent.com/assets/6192416/4350835/9bd8cd78-41fa-11e4-8704-7cea3e831c65.jpg)


Slika 1 Domen

Klasa CreativeWork opisuje strukturu kursa, obuhvatajući instruktore, univerzitete i same sesije kursa ukoliko ih ima. Za svaki kurs definisan je jezik, kao i samo trajanje kursa.

Klasa Person predstavlja insturtktora datog kursa i u modelu je definisan samo atribut koji predstavlja ime. 

Organizacija u modelu prestavlja univerzitet odnosno samog organizatora ovog događaja.

Klasa Duration prestavlja vremenski period trajanja samog kursa.
Mapiranje odgovarajucih elemenata  u odgovarajuće elemente RDF vokabulara LRMI Grafički prikaz
![slika nova 2](https://cloud.githubusercontent.com/assets/6192416/4350837/c2843dd6-41fa-11e4-8782-eb7db609ed64.jpg)





Prvi korak je transformisanje podataka iz JSON formata u odgovarajuće RDF triplete. Podaci se potom smeštaju u lokalnu RDF bazu. Aplikacija omogućava pristup sačuvanim podacima korišćenjem RESTful servisa. Radi transformisanja izvornih podataka izvršeno je parsiranje JSON dokumenta.

#3.	Rešenje
Coursera
Coursera je online platforma nekih od najprestižnijih američkih, kanadskih i australijskih univerziteta, koji pružaju potpuno besplatne kurseve svakome ko se registruje. Ovo omogućavama bilo kome, sa bilo koje tačke na planeti da dobija nova znanja i informacije. Kursevi koji se mogu pohađati su iz oblasti fizike, elektronike, hemije, medicine, biologije, sociologije, matematike, informatike, ekonomije itd.

Coursera API 
Kursevi sa Coursera sajta dostupni su preko njihovog API-ja i dati su u JSON formatu i primer jednog takvog kursa možemo videti na slici. API katalog sadrži listu kurseva, instruktora i univerziteta dostupnih na Coursera platformi. API  je dostupan javno bez autentifikacije preko Interneta. Osnovne URL adrese API-ja su:
*	Kursevi:  https://api.coursera.org/api/catalog.v1/courses
*	Univerziteti: https://api.coursera.org/api/catalog.v1/universities
*	Kategorije: https://api.coursera.org/api/catalog.v1/categories
* Instrukori: https://api.coursera.org/api/catalog.v1/instructors
* Sesije: https://api.coursera.org/api/catalog.v1/sessions

Primer zahteva
Slanje GET metode do osnovne adrese servisa će vratiti celu kolekciju datih resursa (kurseva, instruktora). Da bi se dobili podaci pojedinacnih elemenata, potrebno je dodati id samog resursa u putanju. Sledeća dva primera poziva su ekvivalentna:
``` 
* 1	curl https://api.coursera.org/api/catalog.v1/courses/2
* 2 curl https://api.coursera.org/api/catalog.v1/courses?id=2
``` 
Po defaultu, samo minimalni skup polja su uključeni u objektima odgovora. Da bi odgovor sadržao više polja, uključuju se njihova imena u upitu polja parametar. Na primer, sledeći zahtev obuhvata jezik i kratak opis u odgovoru.
``` 
curl https://api.coursera.org/api/catalog.v1/courses?fields=language,shortDescription
``` 
U samom linku kursa mogu biti uključeni više objekata. Na primer, sledeći zahtev će vratiti kurseve, kao i osnovne informacije o sesijama: 
``` 
curl https://api.coursera.org/api/catalog.v1/courses?includes=sessions
``` 

Primer JSON formata jednog od kurseva
``` json
{
"elements":
	[
		{
			"id":2,
			"shortName":"ml",
			"name":"Machine Learning",
			"language":"en",
			"previewLink":"https://class.coursera.org/ml-005/lecture/preview",
			"shortDescription":"Learn about the most effective machine learning techniques, and gain practice implementing them and getting them to work for yourself.","targetAudience":1,"instructor":"Andrew Ng, Associate Professor",
			"links":{
				"universities":[1],
				"instructors":[1244],
				"sessions":[
					16,152,970311,971489,972224,972303,972304
				]
			}
		}
	],
"linked":{
	"universities":[
		{
			"id":1,
			"shortName":"stanford"
		}
	],
	"instructors":[
		{
			"id":1244,
			"firstName":"Andrew",
			"lastName":"Ng"
		}
	],
	"sessions":[
		{	
			"id":152,
			"homeLink":https://class.coursera.org/ml-2012-002/
		},
``` 
Udacitu

Udacity trenutno nudi 11 kurseva na temu nauke i matematike. Udacity je bez rokova, bez preduslova, bez kvizova i drugih neugodnih školskih stvari. Kursevi na portalu Udacity razvrstani su prema kategorijama na početne, kurseve srednje težine i napredne kurseve. Moguće je izabrati nivo težine u zavisnosti od prethodnog znanja. Udacity kurs koristi nov i veoma zanimljiv način organizovanja onlajn predavanja, gde promoviše učenje pomoću rešavanja kraćih zadataka i učestvovanje u projektima koje vode profesori sa prestižnih svetskih univerziteta. Kao nagradu za uložen trud i rad, nakon odslušanih i položenih kurseva dodeljuju se sertifikati koji su priznati od strane velikih tehnoloških kompanija.
Udacity API
Udacity Course Catalog API  treba da olakša preduzećima, sajtovima i pojedincima programski pristup Udacity kursevima https://www.udacity.com/public-api/v0/courses
Format odgovora
Udacity API svoje podatke pruža u JSON formatu. Uspešan odgovor iz API krajnje tačke je JSON objekat na sledećem listingu.
```json
{
"courses":[
{
"key":"cs101",
"title":"IntrotoComputerScience",
"homepage":"https://www.udacity.com/course/cs101",
"subtitle":"BuildaSearchEngine&aSocialNetwork",
"level":"beginner",
"starter":true,"image":"https://lh5.ggpht.com/ITepKi-2pz4Q6lrLfv6QDNViEG…",
"banner_image":"https://lh4.ggpht.com/9L_ZBdT4T19FvJGW…",
"teaser_video":{
"youtube_url":"https://www.youtube.com/watch?v=Pm_WAWZNbdA"
},
"summary":"Inthisintroductorycourse,you'lllearn…",
"short_summary":"Learnkeycomputerscienceconceptsin…",
"required_knowledge":"Thereisnopriorprogramming…",
"expected_learning":"You'lllearntheprogramminglanguage…",
"featured":true,
"syllabus":"###Lesson1:HowtoGetStarted…",
"faq":"###Whendoesthecoursebegin?Thisclassisself…",
"full_course_available":true,
"expected_duration":3,
"expected_duration_unit":"months",
"new_release":false,
"tracks":["DataScience","WebDevelopment","SoftwareEng"],
"affiliates":[],
"instructors":[{"name":"DaveEvans","bio":"Daveis...","image":"https://lh6.ggpht.com/1x-8cXA7J…"}]
},
{"key":"cs046","title":"IntrotoJavaProgramming","faq":"","affiliates":[{"name":"SanJoseStateUniversity","image":"https://lh3.ggpht.com/MpxH41jmm6mn0XOaVq6d…"}],"instructors":[{"name":"CayHorstmann","bio":"CaygrewupinNorth…","image":"https://lh5.ggpht.com/bpMaSLEZOPel9P2s4AIo…"}],…},…],"tracks":[{"courses":["cs101","ud359","ud827","ud201","ud617",…],"name":"DataScience","description":"Learndatasciencefromindustryexperts…"},…]} 

```

#4.	Tehnička realizacija
Jenabean  je framework koji povezuje Java objekte i RDF podatke. On omogućava jednostavno mapiranje koncepata objektno orjentisanog programiranja u RDF klase i propertije. Jenabean koristi anotacije kako bi se deklarisao način na koji se objekti (beans) mapiraju u RDF. Različitim anotacijama moguće je mapirati klase u Javi sa određenim RDF klasama, postaviti neki atribut za jedinstveni identifikator, kao i mapirati određeno polje u Java klasi sa propertijem u RDF-u. Jenabean definiše klase RDF2Bean i Bean2RDF koje se oslanjaju na klasu RDFModel i omogućavaju konverziju podataka iz RDF-a u Java objekte i obrnuto. 
Za čitanje i upisivanje RDF podataka korišćena je Jenabean biblioteka. Ona rešava problem prevođenja RDF tripleta u Java objekte i obrnuto. Prevođenje se zasniva na anotacijama.

Za skladištenje podataka u RDF repozitorijum korišćena je TDB  komponenta Jena framework-a, koja, pored skladištenja, omogućava i izvršavanje različitih vrsta SPARQL upita nad podacima u RDF formatu. TDB omogućava i pojedine napredne mehanizme, poput transakcija, koji se mogu koristiti prilikom manipulacije podacima. 

REST servisi su u ovom projektu implementirani preko Jersey  biblioteke. Jersey omogućava kreiranje REST metoda jednostavnim anotiranjem Java klasa i metoda. Na osnovu anotacija, određene klase su predstavljene kao servisi, koji sadrže anotacijama označene metode koje prihvataju i obrađuju HTTP zahteve (GET, POST...). Aplikacija na ovaj način isporučuje podatke (u JSON formatu) klijentima, na osnovu različitih parametara koje oni proslede preko HTTP zahteva, a u skladu sa specifikacijom servisa datom u prethodnom poglavlju.

Gson  biblioteka konvertuje Java objekte u njihovu JSON reprezentaciju. Biblioteka za programski jezik Java koja se bavi serijalizacijom i deserijalizacijom Java objekata u JSON objekte. Takođe može da konstruiše Java objekat iz JSON stringa. 

#5. Korisničko upustvo

Pretraživanje kurseva se vrši prema četiri kriterijuma. Na sledećoj slici je prikazana forma za pretraživanje, odnosno početna strana.
![untitled](https://cloud.githubusercontent.com/assets/6192416/4350895/c14ddaf6-41fc-11e4-9d52-d10b314b93e6.png)

TypicalAgeRange, Duration i Language prestavljaju padajuće liste, koje u sebi sadrže sve moguće vrednosti datog kriterijuma. Pri učitavanju strane od REST servisa traže se vrednosti iz RDF repozitorijuma. Početna i podrazumevana vrednost sve tri liste je any, koja označava da se ne vrši filtriranje po datom kriterijumu.

Nakon pretrage korisniku se prikazuju kursevi u formi koja je prikazana na slici.
![untitled](https://cloud.githubusercontent.com/assets/6192416/4374066/65b3d728-4331-11e4-9253-a8fb30e994b8.png)


Na početku se nalaze osnovni podaci o kursu, o kom sajtu je reč, sam naziv,jezik kursa i strana kursa. Nakon opisa samog kursa nalazi se i njegovo trajanje, organizator (publisher), sami predavaci (authors) i podaci o sesijama ukoliko ih sam kurs poseduje.




