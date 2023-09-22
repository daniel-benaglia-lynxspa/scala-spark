# Spark Shell e Spark Submit

## Prerequisiti
### Docker
Per eseguire la spark-shell o lo spark-submit è necessario avere Docker installato (e attivo)
- https://docs.docker.com/engine/install/

### Bash
I comandi degli script e tutti i comandi descritti di seguito sono per shell bash, se siete su Windows è necessario utilizzare WSL2 (è importante che sia WSL2 e non WSL1)
#### Windows Subsystem For Linux
Consiglio Ububtu o Debian  
https://learn.microsoft.com/it-it/windows/wsl/install
#### WSL2 Docker
Qua torvate le indicazioni per assicurarvi che state usando WSL2  
https://docs.docker.com/desktop/wsl
#### dos2unix
Dopo aver clonato il repository è necessario utilizzare dos2unix per convertire tutti gli script .sh in formato Unix prima di procedere
- installare dos2unix  
  `sudo apt update && sudo apt install dos2unix`
- eseguire dos2unix sugli script .sh  
  `sudo dos2unix *.sh && sudo dos2unix docker/scripts/*.sh`

## Spark Shell
Attivazione ambiente per eseguire la spark shell

    sudo docker compose -f docker/docker-compose-spark-shell.yml up -d

Lancio spark shell

    sudo ./run_spark_shell.sh <nome_script>
ad esempio

    sudo ./run_spark_shell.sh 01_ETL.sc

Una volta eseguita la spark shell, vi troverete al suo interno, qui potete continuare a scrivere codice Scala che verrà compilato ed eseguito immediatamente. Per uscire dalla spark shell digitare `:q` + `Invio`

Spegnimento ambiente per spark shell

    sudo docker compose -f docker/docker-compose-spark-shell.yml down


## Spark Submit
Se non avete già eseguito [i comandi per attivare la spark shell](#spark-shell), è necessario creare la sua immagine

    sudo docker compose -f docker/docker-compose-spark-shell.yml build

Attivazione ambiente per eseguire lo spark submit

    sudo docker compose -f docker/docker-compose-spark-submit.yml up -d

Prima di lanciare lo spark submit è necessario buildare il progetto, ad esempio tramite maven con il comando

    mvn clean package

che produrrà il seguente JAR: **target/scala-spark-1.0-SNAPSHOT.jar**

Una volta generato il JAR, sarà possibile eseguire lo spark submit tramite il seguente comando

    sudo ./run_spark_submit.sh <package.nome_classe>
ad esempio

    sudo ./run_spark_submit.sh com.lynxspa.E_spark.ApplicazioneSpark

Spegnimento ambiente per eseguire lo spark submit

    sudo docker compose -f docker/docker-compose-spark-submit.yml down 

### Link
Spark fornisce delle pagine web per monitorarne l'esecuzione
#### Link Spark Shell
Durante l'esecuzione della spark shell, prima di uscire con `:q` è disponibile la pagina web al seguente link:
http://localhost:4040/

#### Link Spark Submit
L'ambiente di spark submit fornisce due pagine web permanenti, che rimangono disponibili fino a quando l'ambiente viene spento tramite `docker compose ... down`, sono:
- master - per monitorare i job in esecuzione: http://localhost:8080/
- history server - per monitorare i job conclusi: http://localhost:18080/
