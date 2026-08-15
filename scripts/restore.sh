#!/usr/bin/env bash
set -euo pipefail
if [ -z "${1:-}" ]; then echo "Usage: ./scripts/restore.sh backups/file.sql.gz"; exit 1; fi
ROOT="$(cd "$(dirname "$0")/.." && pwd)"; source "$ROOT/.env"
read -r -p "This will overwrite database $MYSQL_DATABASE. Type RESTORE to continue: " OK
[ "$OK" = "RESTORE" ] || exit 1
gunzip -c "$1" | docker compose exec -T mysql sh -c 'mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" "$MYSQL_DATABASE"'
echo "Restore completed."
