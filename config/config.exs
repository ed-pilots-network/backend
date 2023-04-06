import Config

config :eddn, Eddn.Repo,
  database: "eddn_repo",
  username: "postgres",
  password: "postgres",
  hostname: "localhost"

config :eddn,
  ecto_repos: [Eddn.Repo]
