#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"; source "$ROOT/.env"
mkdir -p "$ROOT/backups"
FILE="$ROOT/backups/tenora365-$(date +%Y-%m-%d_%H-%M-%S).sql.gz"
docker compose exec -T mysql sh -c 'mysqldump -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" "$MYSQL_DATABASE"' | gzip > "$FILE"
find "$ROOT/backups" -type f -name '*.sql.gz' -mtime +14 -delete
echo "Backup created: $FILE"
