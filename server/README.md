# `Warehouse project`
by Daniel Maramba

### `Set environment variables`

`Windows`: \
$env:DB_URL="..." \
$env:DB_USERNAME="..." \
$env:DB_PASSWORD="..."

`Linux`: \
export DB_URL=... \
export DB_USERNAME=... \
export DB_PASSWORD=...

### `Run migrations`

mvn flyway:migrate

### `Run the server`

mvn spring-boot:run
