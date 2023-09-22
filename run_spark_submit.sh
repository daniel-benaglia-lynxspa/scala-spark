#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

if [ -z "$1" ]; then
    echo "Fornire uno script da eseguire"
    echo "Utilizzo: $0 nome_script"
    exit 1
fi

CLASS_NAME="$1"
JAR_NAME="scala-spark-1.0-SNAPSHOT.jar"
LOCAL_JAR_PATH="$SCRIPT_DIR/target/scala-spark-1.0-SNAPSHOT.jar"
CONTAINER_NAME="spark-master"
LOCAL_RES_DIR="$SCRIPT_DIR/src/main/resources"
DEST_DIR="/"
CONTAINER_OUTPUT_DIR_NAME="output"
NUM_WORKERS=2

# Copia tutti i file in "resources" sul container nella cartella /
# Per il master
find "$LOCAL_RES_DIR" -type f -exec docker cp {} "$CONTAINER_NAME:$DEST_DIR" \;

# Copia tutti i file in "resources" sul container nella cartella /
# Per i worker
for i in $(seq 1 $NUM_WORKERS); do
    CONTAINER_NAME="spark-worker-$i"
    find "$LOCAL_RES_DIR" -type f -exec docker cp {} "$CONTAINER_NAME:$DEST_DIR" \;
done

# Copia il JAR Scala sul container del master
docker cp "$LOCAL_JAR_PATH" "$CONTAINER_NAME:$DEST_DIR"

# Rimuove tutti i file nella directory di output del container
docker exec "$CONTAINER_NAME" rm -rf "/$CONTAINER_OUTPUT_DIR_NAME"

# Esegue lo spark submit specificando la classe fornita
docker exec -it "$CONTAINER_NAME" spark-submit \
  --name ScalaSpark \
  --executor-cores 4 \
  --executor-memory 2G \
  --driver-memory 2G \
  --master spark://spark-master:7077 \
  --class "$CLASS_NAME" \
  --conf "spark.driver.extraJavaOptions=-Dlog4j.configuration=file:/log4j.properties" \
  $JAR_NAME

# Copia l'output di spark nella directory di output locale "output"
docker cp "$CONTAINER_NAME:/$CONTAINER_OUTPUT_DIR_NAME/" "$SCRIPT_DIR"

# Permessi per i file creati da spark
chmod -R 777 "$SCRIPT_DIR/$CONTAINER_OUTPUT_DIR_NAME/"