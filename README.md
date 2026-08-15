# TENORA365 — Production SaaS Package

TENORA365 is a multi-tenant school management SaaS foundation built with Java 21/Spring Boot, MySQL 8.4, Flyway, JWT/refresh-token architecture and React/Vite. The package is designed so a VPS deployment can be brought online with Docker Compose and automatic HTTPS through Caddy.

## What is included

- Multi-school tenant isolation using school-scoped repositories and JWT tenant claims
- Super Admin school onboarding
- School Admin workspace
- Student management
- Teacher management
- Attendance module workspace
- Exams & report-card workspace
- Timetable workspace
- Parent portal workspace
- Student portal workspace
- Payroll workspace
- Library workspace
- Transport workspace
- Notifications workspace
- Documents/file upload with tenant directories and unsafe-extension blocking
- Fees + PDF fee receipt
- Subscription/billing records
- Payment-order data model ready for gateway adapter
- OTP/refresh-token entities and secure password hashing foundation
- Audit logs
- Flyway migrations
- Automated Spring context test
- MySQL backup and restore scripts
- Docker production build
- Automatic HTTPS via Caddy
- Ten Rings-inspired animated frontend

## Important deployment reality

No software package can contain your real domain, DNS credentials, payment gateway keys, WhatsApp/SMS provider account, SMTP account, or cloud-storage account. Those are secrets belonging to your business. The code is provider-ready, and `mock`/`log` modes are safe defaults for initial deployment.

For a real paid launch, set your provider credentials and switch the provider modes before accepting money or sending regulated messages.

## VPS deployment

Recommended: Ubuntu 24.04/22.04 VPS with at least 2 vCPU, 4 GB RAM, 50 GB SSD for a small first deployment.

1. Point your domain A record to the VPS public IP.
2. Install Docker Engine + Compose plugin.
3. Copy this folder to the VPS.
4. `cp .env.example .env`
5. Set `DOMAIN=app.yourdomain.com` and all strong passwords/secrets in `.env`.
6. Run `docker compose -f docker-compose.production.yml up -d --build`
7. Open `https://app.yourdomain.com`.

Caddy automatically requests and renews the TLS certificate when DNS points to the VPS and ports 80/443 are reachable.

## First login

The startup seeder creates:

- Super Admin: `superadmin`
- Demo School Admin: `admin@demo.tenora365.com`

Passwords are taken from `.env` (`SUPERADMIN_PASSWORD` and `DEMO_ADMIN_PASSWORD`). Change them before giving access to customers.

## Backups

Run daily from cron:

`./scripts/backup.sh`

Restore:

`./scripts/restore.sh backups/your-file.sql.gz`

Keep encrypted off-server copies. A backup is not considered production-safe until a restore has been tested.

## Provider integrations

### Payments

The data model has `payment_orders` and subscription records. Set `PAYMENT_PROVIDER` to your chosen adapter when you implement/enable a real provider. Never mark a payment paid from a browser redirect alone; verify the provider webhook/signature on the server and then update the database.

### Email/SMS/WhatsApp

Email SMTP variables are present. SMS/WhatsApp should be connected through your selected provider's API/webhook credentials and stored only as VPS environment secrets.

### Object storage

The included upload service stores tenant files under `/app/storage/<schoolId>`. For large commercial scale, mount persistent storage or replace the storage service with S3-compatible object storage. Do not put private student documents in a public bucket.

## Architecture

Internet → Caddy HTTPS → React/Nginx → Spring Boot API → JWT/Tenant Context → MySQL

Files are stored in a persistent tenant-scoped volume. Flyway owns schema migrations.

## Security checklist before selling

- Use unique 64+ character `JWT_SECRET`.
- Use unique MySQL/root passwords.
- Change demo/admin passwords.
- Restrict VPS SSH and enable firewall.
- Keep Docker/OS updated.
- Configure real payment webhooks and signature verification.
- Configure real notification providers and consent/opt-out rules.
- Configure off-server encrypted backups.
- Test tenant isolation with two schools.
- Test restore before launch.
- Do not expose MySQL port to the internet.
- Use HTTPS only.
