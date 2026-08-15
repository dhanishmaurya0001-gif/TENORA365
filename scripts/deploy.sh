#!/usr/bin/env bash
set -euo pipefail
[ -f .env ] || { cp .env.example .env; echo "Created .env. Fill secrets, then rerun."; exit 1; }
docker compose up -d --build
echo "TENORA365 started. Frontend is bound to 127.0.0.1:8088. Put Nginx/HTTPS in front of it."
