package de.back2heaven.crypt;

public class NoKey {
	// Austausch eines schlüssels
	
	// verschlüssel meine nachricht mit meinem eigenem PW
	
	
	// sende mit crypt flag gesetzt
	
	// empfänger verschlüsselt mit seinem PW
	
	// sendet mit cryptPW gesetzt und ACK
	
	// XOR ein zweites mal mit meinem schlüssel
	
	// sende mit crypt flag und ACK
	
	// empfenger entschlüsselt und kann das geheimnis lesen.
	
	// UDP geheimnis => HEADER + GEHEIMNIS d.h. geheimnis 510 Byte 
	
	// geheimnis ist temporer und muss nach absturz erst wieder syncronisiert werden.
	
	// alle 10min im idle wird das PW neu gewürfelt.
	
	// 2^510 möglichkeiten rotieren alle 10 min.
	
	// mehr als 3 Jahre möglich
	
	// alle schlüssel werden gesammelt und nicht noch einmal verwendet
	
	// verfall der schlüssel zum erneutem nutzen durch nutzer bestimmbar
	
	// A will KEYa nutzen B hat KEYa aber schon verbraucht
	
	// ==> B sendet TRAP + CRYPTED + ERROR 
	// ==> A muss neuen schlüssel generrieren (merkt sich verbraucht)
	
	
	
	// TABLE
	// KEY | LEFT_LINK | RIGHT_LINK | DATE
	// LEFT_LINK << REMOTE Rechner
	// RIGHT_LINK << normaler weise self, aber für den fall dass proxy funktion genutzt wird, 
	// brauch man kein CRYPT im lokalem LAN
	
	// ODER Fall2
	// RIGHT_LINK ist die stelle von der weitergeleitete strom herkommt.
	// LEFT_LINK ist der proxy (oder helper*downloader)
	
}
