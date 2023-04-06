# EDDB 2.0

This is a proposed backend for a new EDDB.io.

This specifically will listen to EDDN messages and store them to a Postgres.

Dependencies
* `:erlzmq` used for listening to the ZMQ `tcp://eddn.edcd.io:9500` endpoint.
* `:jason` used for decoding json.
* `:ecto_sql` used as an interface to the SQL database (Postgres).
* `:postgrex` used as an adapter to Postgres.

## Running
1. Ensure that you have Postgres, Erlang, Elixir, and libzmq3-dev installed on your system.
2. Run `mix deps.get` to get Elixir dependencies.
3. Run `mix ecto.create` to initialize database.
4. Run `mix ecto.migrate` to create database tables.
3. Run `iex -S mix` to start the application with Elixir CLI.
4. Run `Eddn.start_listener()` in the Elixir CLI to start the listener.

## Todos
1. Currently focusing on getting the `lib/eddn/listener.ex` GenServer to be non-blocking when consuming ZMQ messages.
2. Need Ecto Schemas and Migrations for the shapes of data being messaged across EDDN.
3. `lib/eddn/listener.ex` should be storing EDDN messages to Postgres.