## Einführung

Diese Übung soll die Funktionsweise und Implementierung von eine Message Oriented Middleware (MOM) mit Hilfe des **Frameworks Apache Active MQ** demonstrieren. **Message Oriented Middleware (MOM)** ist neben InterProcessCommunication (IPC), Remote Objects (RMI) und Remote Procedure Call (RPC) eine weitere Möglichkeit um eine Kommunikation zwischen mehreren Rechnern umzusetzen.

Die Umsetzung basiert auf einem praxisnahen Beispiel einer Windkraftanalage. Ein Windkraftanalage (Windrad) ist immer Teil eines Verbunds, genannt Windpark. Jede Windkraftanlage beinhaltet einen Rechner, der die Daten der Windkraftanalage aufzeichnet und diese steuern kann. Die Daten werden als REST Schnittstelle in XML oder JSON zur Verfügung gestellt. Die Daten aller Windkraftanlagen eines Windparks werden von einem Parkrechner gesammelt und abgespeichert. Der Parkrechner kommuniziert mit dem Rechenzentrum in der Zentrale. Eine Zentrale kommuniziert mit mehreren Windparks und steuert diese.

## 1.1 Ziele  

Das Ziel dieser Übung ist die **Implementierung einer Kommunikationsplattform für einen Windpark. Dabei erfolgt ein Datenaustausch von mehreren Windkraftanlage mit dem Parkrechner unter Verwendung einer Message Oriented Middleware (MOM)**. Die einzelnen Daten der Windkraftanlage sollen an den Parkrechner ü<span>bertragen werden</span>. Es sollen **nachrichtenbasierten Protokolle mit Message Queues** verwendet werden. Durch diese lose Kopplung kann gewährleistet werden, dass in Zukunft weitere Anlagen hinzugefügt bzw. Kooperationspartner eingebunden werden können.

Neben der REST-Schnittstelle muss nun eine Schnittstelle bei der Windkraftanlage implementiert werden, die in regelmässigen Abständen die Daten von der REST-Schnittstelle ausliest und diese in eine Message Queue von **Apache Active MQ** hinzufügt. Um die Datenintegrität zu garantieren, sollen jene Daten, die mit der Middleware übertragen werden in einer LOG-Datei abgespeichert werden.  

## 1.2 Voraussetzungen

*   Grundlagen Architektur von verteilten Systemen
*   Grundlagen zur nachrichtenbasierten Systemen / Message Oriented Middleware  
*   Verwendung des Message Brokers Apache ActiveMQ
*   Verwendung der XML- oder JSON Datenstruktur der Windkraftanlage
*   Verwendung der [JMSChat.jar](https://elearning.tgm.ac.at/mod/resource/view.php?id=78649) JAVA Applikation als Startpunkt für diese Aufgabenstellung  
*   Verwendung von [Windpark Example with REST/JSON](https://elearning.tgm.ac.at/mod/resource/view.php?id=78834)

## 1.3 Aufgabenstellung

Implementieren Sie die Windpark-Kommunikationsplattform mit Hilfe des Java Message Service. Verwenden Sie Apache ActiveMQ ([http://activemq.apache.org](http://activemq.apache.org/)) als Message Broker Ihrer Applikation. Das Programm soll folgende Funktionen beinhalten:

 *   Installation von Apache ActiveMQ am Parkrechner.
 *   Jede Windkraftanlage erstellt eine Message Queue mit einer ID am Parkrechner.
 *   Jede Windkraftanlage legt in regelmässigen Abständen die Daten der Anlage in der Message Queue ab.
 *   Bei einer erfolgreichen Übertragung empfängt die Windkraftanlage die Nachricht "SUCCESS".
 *   Der Parkrechner fragt in regelmässigen Abständen alle Message Queues ab.
 *   Der Parkrechner fuegt alle Daten aller Windkraftanlagen zusammen und stellt diese an einer REST Schnittstelle im JSON/XML Format zur Verfügung.  

## 1.4 Bewertung  

 *   Gruppengrösse: 1 Person  
 *   Anforderungen **"überwiegend erfüllt"**
	 *   Implementierung der Kommunikation zwischen einer Windkraftanlage und dem Parkrechner (JMS Queue)  
	 *   Implementierung der REST Schnittstelle am Parkrechner
	 *   Zusammensetzung der Daten aller Windkraftanlagen in eine zentrale JSON/XML-Struktur

 *   Anforderungen **"zur Gänze erfüllt"**
	 *   Implementierung der Kommunikation mit mehreren Windkraftanlage und dem Parkrechner  
	 *   Rückmeldung des Ergebnisses der Übertragung vom Parkrechner an dieWindkraftanlage (JMS: Topic)  
	 *   LOG-Datei bei jeder Windkraftanlage
	 *   LOG-Datei für den Par krechner mit allen eingehenden Daten aller Windkraftanlagen

## 1.5 Fragestellung für Protokoll

 *   Nennen Sie mindestens 4 Eigenschaften der Message Oriented Middleware?  
 *   Was versteht man unter einer transienten und synchronen Kommunikation?
 *   Beschreiben Sie die Funktionsweise einer JMS Queue?
 *   JMS Overview - Beschreiben Sie die wichtigsten JMS Klassen und deren Zusammenhang?
 *   Beschreiben Sie die Funktionsweise eines JMS Topic?
 *   Was versteht man unter einem lose gekoppelten verteilten System? Nennen Sie ein Beispiel dazu. Warum spricht man hier von lose?
`
## 1.6 Links & Dokumente

 *   Grundlagen Message Oriented Middleware: [Presentation](https://elearning.tgm.ac.at/mod/resource/view.php?id=75248&redirect=1)
*   Middleware:  
  [Apache ActiveMQ Installationspaket](http://activemq.apache.org/activemq-5153-release.html)  
  [Apache Active MQ 5.14.3 vom Moodle-Server](https://elearning.tgm.ac.at/mod/resource/view.php?id=78648)
 *   Apache ActiveMQ & JMS Tutorial:
*   http://activemq.apache.org/index.html
*   http://www.academictutorials.com/jms/jms-introduction.asp
*   http://docs.oracle.com/javaee/1.4/tutorial/doc/JMS.html#wp84181
*   http://www.oracle.com/technetwork/systems/middleware/jms-basics-jsp-135286.html
*   http://www.oracle.com/technetwork/articles/java/introjms-1577110.html
*   https://spring.io/guides/gs/messaging-jms/
*   https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html
*   https://dzone.com/articles/using-jms-in-spring-boot-1
