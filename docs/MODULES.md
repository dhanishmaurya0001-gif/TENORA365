# TENORA365 modules

The product UI exposes the requested modules. Core identity, school, student, teacher, fee, subscription, payment-order, audit, refresh-token and OTP data use relational tables. Operational modules that vary heavily from school to school use tenant-scoped JSON records so the product can evolve without destructive schema changes.

Supported module keys:

- ATTENDANCE
- EXAMS
- TIMETABLE
- PAYROLL
- LIBRARY
- TRANSPORT
- PARENTS
- STUDENT-PORTAL
- NOTIFICATIONS
- DOCUMENTS
- SUBSCRIPTIONS

For a mature enterprise deployment, individual high-volume modules can later be promoted from JSON records to dedicated relational tables without changing the tenant model.
