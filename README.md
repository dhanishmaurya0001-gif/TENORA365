# TENORA365 - Multi-school ERP Portfolio Demo

A Java 21 / Spring Boot 3.5.4 and React/Vite school-administration project. The application demonstrates school tenants, JWT authentication, student management, teacher records, fees, PDF receipts and operational record workspaces.

## Interactive free-hosting demo

The root `Dockerfile` builds the React frontend and Spring Boot API into one same-origin application. `render.yaml` prepares a Render **Free** service named `dhanish-tenora365-demo`; its readiness check is `/actuator/health/readiness`, which waits for initial demonstration data to be ready. The final hosted URL is shown in the repository Website field after deployment.

Choose **Open demo school**, **Open second school**, or **Explore platform administrator** on the login page. These buttons perform real API authentication against deliberately public fictional accounts:

| Account | Username |
|---|---|
| Pragati school administrator | `admin@demo.tenora365.com` |
| Riverbank school administrator | `admin@riverbank.example` |
| Platform administrator | `superadmin` |

Public demo password: **`Demo@School2026!`**.

The `demo` Spring profile uses H2 in memory with two schools, six fictional students, two teachers, five fee entries and ten sample operational records. It does not need an external database account. Records are shared among visitors and reset on host restart, sleep or redeployment. Do not enter personal information. No real payments or external notifications are sent.

## Implemented demonstration

- JWT login and tenant-scoped access, with a separate platform administrator school list/onboarding API.
- Student create/list/update/delete API and student entry/search/delete UI.
- Structured teacher records and fee entries.
- School dashboard totals based on real database records.
- Fee PDF receipts.
- Tenant-scoped operational JSON records and student-operation audit records.
- Separate school data, so the two demonstration accounts can be used to check isolation.

Attendance, examinations, timetable, payroll, library, transport, parent/student portals, notification records and document metadata currently provide **generic JSON record workspaces**. They are prototypes rather than complete specialized workflows. Subscription and payment-order records are data-model demonstrations; a real payment-gateway adapter is not implemented. Settings and provider placeholders do not establish production readiness.

## Local demo run

Use Java 21 and Maven to start the backend with the `demo` profile:

```powershell
mvn -f backend/pom.xml spring-boot:run -Dspring-boot.run.profiles=demo
```

In a second terminal, use Node.js 22.12 or later:

```powershell
npm --prefix frontend ci
$env:VITE_DEMO_MODE='true'
npm --prefix frontend run dev
```

Open `http://localhost:5173`. Vite proxies `/api` to the backend on port 8080. The root Dockerfile serves both frontend and API on the same public origin.

For a combined Docker demonstration:

```bash
docker build -t tenora365-demo .
docker run --rm -p 8080:8080 tenora365-demo
```

Open `http://localhost:8080`. The H2 demonstration database is intentionally resettable.

## Existing MySQL deployment path

When the `demo` profile is absent, the original MySQL/Flyway configuration is used. The separate backend/frontend Compose deployment remains available in `docker-compose.yml` and `docker-compose.production.yml`.

For that path, copy `.env.example` to a local `.env` and provide your own database passwords, JWT secret and administrator passwords. Follow the deployment scripts and review `docs/COMMERCIAL_CHECKLIST.md` before planning a non-demo deployment. Do not reuse the public demonstration password or publish local secrets.

The original architecture is Caddy or Nginx, React frontend, Spring Boot API and MySQL, with tenant files in application storage. Database migrations remain in `backend/src/main/resources/db/migration`. The demo profile disables those MySQL migrations and creates a separate in-memory schema.

Provider account setup, real gateway verification, delivery testing, persistent storage, off-server backups and a tested restore procedure require separate implementation/configuration. The free portfolio container does not provide durable school records.

## Verification

- React production build passed.
- Maven package and the Spring Boot context test passed with the `demo` profile.
- Local bundled-JAR HTTP checks passed for three account logins, school lists, tenant isolation, protected API access, student CRUD, cross-tenant update denial, teacher/fee records, PDF receipt generation, ten operational workspaces, generic-record CRUD, audit records, disabled external email and invalid-token rejection.
- H2 JSON records were checked to round-trip as JSON objects rather than double-encoded strings.

These are selected functional checks. They are not a full security audit, a certification of every feature, or evidence of real payment/notification delivery. The local Docker engine was not used for this verification; the hosting provider must still build and run its deployment image.

Prepared for portfolio demonstration by Dhanish Kumar Maurya.
