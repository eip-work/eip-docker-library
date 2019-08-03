#!/bin/sh

process_init_file() {
        local f="$1"; shift
        local mysql=( "$@" )

        case "$f" in
                *.sql)    echo "$0: running $f"; mysql -uroot -p$MYSQL_ROOT_PASSWORD < $f; echo ;;
                *)        echo "$0: ignoring $f" ;;
        esac
        echo
}

ls /init_sql/ > /dev/null
for f in /init_sql/*; do
        process_init_file "$f"
done

process_init_file "/grant_exporter/grant_exporter.sql"