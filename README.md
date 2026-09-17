# TV Shows API

Spring Boot / Java 17 service with a MongoDB cache for show details.

## Endpoints

- `GET /api/shows/{show_id}`: returns the complete show object. It first checks MongoDB's `show_cache` collection; on a cache miss it calls TVMaze, stores the result, and returns it.

## MongoDB configuration

The application loads the shared `.env` file in the project root. It must contain `MONGODB_URI`, `MONGODB_USERNAME`, and `MONGODB_PASSWORD`.
