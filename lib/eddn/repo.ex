defmodule Eddn.Repo do
  use Ecto.Repo,
    otp_app: :eddn,
    adapter: Ecto.Adapters.Postgres
end
