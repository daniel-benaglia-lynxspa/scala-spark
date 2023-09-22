#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

if [ -z "$1" ]; then
    echo "Fornire uno script da eseguire"
    echo "Utilizzo: $0 nome_script"
    exit 1
fi

SCRIPT_NAME="com/lynxspa/E_spark/$1"
LOCAL_SCRIPT_PATH="$SCRIPT_DIR/src/main/scala/$SCRIPT_NAME"

if [ ! -f "$LOCAL_SCRIPT_PATH" ]; then
    echo "Il file non esiste: $LOCAL_SCRIPT_PATH"
    exit 1
fi

CONTAINER_NAME="spark-shell-container"
CLEANED_SCRIPT_NAME="spark-shell-script.scala"
LOCAL_CLEANED_SCRIPT_PATH="$SCRIPT_DIR/$CLEANED_SCRIPT_NAME"
CONTAINER_SCRIPT_PATH="/$CLEANED_SCRIPT_NAME"

LOCAL_RES_DIR="$SCRIPT_DIR/src/main/resources"
DEST_DIR="/"
CONTAINER_OUTPUT_DIR_NAME="output"

# Copia tutti i file in "resources" sul container nella cartella /
find "$LOCAL_RES_DIR" -type f -exec docker cp {} "$CONTAINER_NAME:$DEST_DIR" \;

# Rimuove la prima riga dello script
tail -n +2 "$LOCAL_SCRIPT_PATH" > "$LOCAL_CLEANED_SCRIPT_PATH"

# Copia sul container lo script scala "pulito"
docker cp "$LOCAL_CLEANED_SCRIPT_PATH" "$CONTAINER_NAME:$CONTAINER_SCRIPT_PATH"
rm "$LOCAL_CLEANED_SCRIPT_PATH"

# Rimuove tutti i file nella directory di output del container
docker exec "$CONTAINER_NAME" rm -rf "/$CONTAINER_OUTPUT_DIR_NAME"

# Esegue la spark shell con lo script fornito
docker exec -it "$CONTAINER_NAME" spark-shell \
  --master local[*] \
  --conf "spark.driver.extraJavaOptions=-Dlog4j.configuration=file:/log4j.properties" \
  -i "$CONTAINER_SCRIPT_PATH"

# Copia l'output di spark nella directory di output locale "output"
docker cp "$CONTAINER_NAME:/$CONTAINER_OUTPUT_DIR_NAME/" "$SCRIPT_DIR"

# Permessi per i file creati da spark
chmod -R 777 "$SCRIPT_DIR/$CONTAINER_OUTPUT_DIR_NAME/"